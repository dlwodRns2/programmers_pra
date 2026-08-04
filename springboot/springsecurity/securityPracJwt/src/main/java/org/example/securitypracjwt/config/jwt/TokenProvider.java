package org.example.securitypracjwt.config.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.securitypracjwt.config.oauth2.AuthProvider;
import org.example.securitypracjwt.config.oauth2.OAuth2UserInfo;
import org.example.securitypracjwt.config.security.CustomUserDetails;
import org.example.securitypracjwt.domain.entity.Role;
import org.example.securitypracjwt.domain.entity.User;
import org.example.securitypracjwt.dto.SignupPayloadDto;
import org.example.securitypracjwt.service.TokenService;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.time.Duration;
import java.util.Base64;
import java.util.Date;

//액세스토큰, 리프레시 토큰 생성 역할
@Slf4j
@Service
@RequiredArgsConstructor
public class TokenProvider {
    private static final String CLAIM_ID = "id";
    private static final String CLAIM_ROLE="role";
    private static final String CLAIM_NAME="name";

    //가입 토큰용
    private static final String CLAIM_PROVIDER = "provider";
    private static final String CLAIM_EMAIL = "email";
    private static final String CLAIM_TYPE = "type";
    private static final String TOKEN_TYPE_SIGNUP = "signup";
    private static final Duration SIGNUP_TOKEN_VALIDITY = Duration.ofMinutes(10);

    private final JwtProperties jwtProperties;

    private SecretKey secretKey;
    private JwtParser jwtParser;

    //secretKey, jwtParser은 빈이 아니라 @RequiredArgsConstructor로 주입 불가
    @PostConstruct
    private void init(){
        // 키와 파서는 불변 → 요청마다 만들지 않고 한 번만 생성해 재사용
        this.secretKey = Keys.hmacShaKeyFor(Base64.getDecoder().decode(jwtProperties.getSecretKey()));
        this.jwtParser = Jwts.parser().verifyWith(secretKey).build();
    }


    //시간 계산/토큰 조립의 관심사를 분리하여 generate/makeToken 메서드로 나눔
    //
    public String generateToken(User user, Duration expiredAt){
        Date now = new Date();
        return makeToken(user,new Date(now.getTime()+expiredAt.toMillis()));
    }

    //토큰 생성 : 액세스/리프레시 토큰인지 판단하지 않고, user과 expire을 받아 JWT하나를 조립하는 범용 메서드
    //TokenService에서 토큰 2개를 생성할 때, 만료 시간으로 access/refresh를 나눈다.
    private String makeToken(User user, Date expire){
        Date now = new Date();

        //JWT의 헤더/payload/서명 조립 후 반환
        return Jwts.builder()
                .header().type("JWT").and()
                .issuer(jwtProperties.getIssuer())
                .issuedAt(now)
                .expiration(expire)
                .subject(user.getUserId())
                .claim(CLAIM_ID,user.getId())
                .claim(CLAIM_ROLE,user.getRole())
                .claim(CLAIM_NAME,user.getName())
                .signWith(secretKey,Jwts.SIG.HS512)
                .compact();
    }

    //토큰 상태를 검증
    public TokenStatus validateToken(String token){
        try{
            //parseSignedClaims 한 줄로 검증할 수 있음
            //1. 토큰을 3단 분리(헤더/payload/서명)
            //2. parser가 init에서 받은 secretKey로 서명 재계산/비교 - 토큰의 헤더/payload를 secretKey로 재서명
            //위 값 != 서명 => 토큰 변조 / 같으면 3번으로
            //3. payload의 클레임 중 exp 확인해서 토큰 만료여부 확인, 만료 시 ExpiredJwtException 발생
            jwtParser.parseSignedClaims(token);
            return TokenStatus.VALID;
        }catch (ExpiredJwtException e){
            log.warn("Token is expired");
            return TokenStatus.EXPIRED;
        }catch (Exception e){
            log.warn("Token is not valid");
            return TokenStatus.INVALID;
        }
    }

    //JWT의 존재 이유 : DB 조회 없는 인증
    //클레임 -> 도메인 User 복원(DB조회없이 토큰만으로!!)
    //서명 검증을 validateToken으로 통과 : 토큰에 있는 값이 서버가 발급 시점에 직접 넣었던 값 그대로임을 암호학적으로 보장
    //=> 토큰 자체를 신뢰 가능한 데이터 소스로 삼아 User 객체를 재구성
    //trade-off : 성능/확장성 Up, 최신성 손실. DB에서 유저의 정보(예: role)를 변경해도 발급된 토큰의 role은 예전 값 그대로
    public User getTokenDetails(String token){
        Claims claims = getClaims(token);
        return User.builder()
                .id(claims.get(CLAIM_ID,Long.class))
                .userId(claims.getSubject())
                .name(claims.get(CLAIM_NAME,String.class))
                .role(Role.valueOf(claims.get(CLAIM_ROLE,String.class)))
                .build();
    }

    //복원된 User -> 시큐리티가 이해하는 Authentication
    //Authentication : Spring Security의 인터페이스. 크게 3가지 값을 가짐
    //UsernamePasswordAuhenticationToken : 스프링 시큐리티가 기본 제공하는 대표적인 Authentication 구현체
    //getPrincipal() : 누구인지(인증 주체)(UserDetails 구현체)
    //getCredentials() : 증명 수단(비밀번호 or 토큰)(원본 JWT 토큰 문자열)
    //getAuthorities() : 무슨 권한을 갖는지(principal.getAuthories())
    public Authentication getAuthentication(User user, String token){
        //principal 자리에 CustomUserDetails를 넣으면
        //컨트롤러에서 @AuthenticationPrincipal로 도메인 User까지 바로 꺼낼 수 있음
        CustomUserDetails principal = CustomUserDetails.builder().user(user).build();

        //principal, credentials 넣기 : 인증 전 상태 - 로그인 시도 시 사용 //setAuthenticated(false)
        //principal, credentials, authorities 넣기 : 이미 검증 완료된 사용자 등록 시 사용 //setAuthenticated(true)
        return new UsernamePasswordAuthenticationToken(principal, token, principal.getAuthorities());
    }
    private Claims getClaims(String token){
        return jwtParser.parseSignedClaims(token).getPayload();
    }

    public String createSignupToken(AuthProvider provider, OAuth2UserInfo userInfo) {
        Date now = new Date();
        return Jwts.builder()
                .header().type("JWT").and()
                .issuer(jwtProperties.getIssuer())
                .expiration(new Date(now.getTime()+SIGNUP_TOKEN_VALIDITY.toMillis()))
                .issuedAt(now)
                .subject(userInfo.id())
                .claim(CLAIM_NAME,userInfo.name())
                .claim(CLAIM_TYPE,TOKEN_TYPE_SIGNUP)
                .claim(CLAIM_PROVIDER,provider.name())
                .claim(CLAIM_EMAIL,userInfo.email())
                .signWith(secretKey,Jwts.SIG.HS512)
                .compact();
    }
    public SignupPayloadDto getSignupPayload(String token){
        Claims claims;
        try{
            claims=getClaims(token);
        }catch (Exception e){
            throw new IllegalArgumentException("유효하지 않거나 만료된 가입 토큰입니다.");
        }

        if(!TOKEN_TYPE_SIGNUP.equals(claims.get(CLAIM_TYPE,String.class))){
            throw new IllegalArgumentException("가입 토큰이 아닙니다.");
        }

        return new SignupPayloadDto(
                AuthProvider.valueOf(claims.get(CLAIM_PROVIDER,String.class)),
                claims.getSubject(),
                claims.get(CLAIM_EMAIL,String.class),
                claims.get(CLAIM_NAME,String.class)
        );
    }
}
