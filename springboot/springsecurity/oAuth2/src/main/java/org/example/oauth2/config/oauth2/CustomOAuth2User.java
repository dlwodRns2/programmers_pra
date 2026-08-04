package org.example.oauth2.config.oauth2;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.example.oauth2.domain.entity.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.List;
import java.util.Map;

//flow : sns 로그인 -> login SuccessHandler
//-> 이미 우리 서비스에 가입된 회원 -> 로그인 완료
//-> 서비스에 가입되지 않음 -> 우리 서비스 회원가입 안내

//* CustomOAuth2User
//CustomUserDetail의 OAuth2 버전
//- 자체 로그인 경로 : 시큐리티가 요구하는 표준 = UserDetails -> CustomUserDetails가 User를 감싼다
//- 소셜 로그인 경로 : 시큐리티가 요구하는 표준 = OAuth2User -> 이 클래스가 User를 감싼다
//즉 이 클래스도 "우리 도메인(User)과 시큐리티 사이의 어댑터" 이고,
//어떤 경로로 로그인하든 principal에서 우리 User 엔티티를 꺼낼 수 있게 만드는 장치이다.

//Spring Security에서의 동작 흐름 - oauth2Login()
//위 표준 흐름을 필터 2개가 나눠서 대신 처리한다.
//1) OAuth2AuthorizationRequestRedirectFilter
//-> /oAuth2/authorization/{registrationId} 요청을 가로채 인가 페이지로 리다이렉트
//2) OAuth2LoginAuthenticationFilter
//-> /login/oauth2/code/{registrationId}로 돌아온 code를 받아
//"state 검증 -> 토큰 교환 -> 사용자 정보 조회까지 수행"
//3) 조회된 사용자 정보를 OAuth2UserService.loadUser()에 넘긴다.
//-> 여기서 "제공자의 회원"을 "우리 서비스 DB 회원"으로 연결(없으면 가입)하는 것이 개발자의 몫
//4) 반환된 OAuth2User로 Authentication을 만들어 SecurityContext에 저장 -> 로그인 완료
//5) 마지막으로 SuccessHandler 호출 -> 로그인 후처리(JWT 발급)도 개발자의 몫

//정리하면 개발자가 구현하는 것은 파이프라인의 양 끝 훅(hook) 두 개 뿐이다.
//-OAuth2UserService : 제공자 응답 -> 우리 회원 매핑
//-SuccessHandler : 로그인 성공 -> 후처리(토큰 발급, 리다이렉트 등)
//나머지는(리다이렉트, state, 코드-토큰 교환, 정보 조회)는 전부 프레임워크가 처리하고
//제공자별 차이(엔드포인트, URL, scope 등)는 코드가 아닌 설정 파일의 registration, provider 항목으로 흡수된다.
//그래서 네이버 같은 새로운 provider를 추가해도 java 코드는 거의 변경되지 않음.

//** 프로젝트에서의 OAuth2 로그인 흐름

@Getter
@RequiredArgsConstructor
public class CustomOAuth2User implements OAuth2User {

    //우리 DB 회원. SuccessHandlerrk JWT 발급에 사용
    private final User user;

    //어떤 SNS로 인증을 진행했는지
    private final AuthProvider authProvider;

    //제공자 프로필의 공통 창구(다형석). 제공자별 파싱 결과가 필요할 때 사용
    private final OAuth2UserInfo userInfo;

    //제공자가 준 원시 속성 맵
    //OAuth2User 계약상 보관 의무가 있고
    //userInfo가 안 꺼내주는 필드가 나중에 필요해질 때의 탈출구이기도 함.
    private final Map<String, Object> attributes;

    //원시 맵에서 "사용자 식별자"를 가리키는 것. 제공자마다 다름
    private final String nameAttributeKey;

    public static CustomOAuth2User unregistered(AuthProvider provider, OAuth2UserInfo userInfo, Map<String, Object> attributes, String nameAttributeKey) {
        return new CustomOAuth2User(null,provider,userInfo,attributes,nameAttributeKey);
    }

    //우리 DB에 이미 가입된 회원인지 확인. SuccessHandler가 "로그인 완료 vs 가입 안내"
    public boolean isRegistered(){
        return user!=null;
    }
    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }

    //인가판단의 재료
    //빈 리스트를 반환하면 "로그인은 됐지만 아무 권한 없는 사용자"가 되어
    //hasRole 검사를 전부 통과하지 못하므로 반드시 채워야 한다.
    //미가입(user==null) 상태는 sns인증은 됐지만, 아직 우리 서비스 회원이 아니므로 임시 권한 "ROLE_GUEST"만 가진다
    //- 가입 안내 페이지까지만 갈 수 있고, hasRole('USER')가 걸린 자원에는 접근할 수 없음.
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if(user==null){
            return List.of(new SimpleGrantedAuthority("ROLE_GUEST"));
        }
        return List.of(new SimpleGrantedAuthority(user.getRole().name()));
    }

    //OAuth2User 계약 : "이 Principal의 이름"을 반환한다. UserDetails.getUsername()에 대응
    @Override
    public String getName() {
        return String.valueOf(attributes.get(nameAttributeKey));
    }
}
