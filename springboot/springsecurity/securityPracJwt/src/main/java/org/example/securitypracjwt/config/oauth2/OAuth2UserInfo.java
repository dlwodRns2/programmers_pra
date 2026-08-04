package org.example.securitypracjwt.config.oauth2;

import java.util.Map;

public interface OAuth2UserInfo {
    //원시 응답 속성
    Map<String,Object> attributes();

    //제공자가 부여한 변하지 않는 고유 식별자
    String id();

    //이메일(우리 User의 필수 값. 없으면 로그인 거부)
    String email();

    //이름 / 닉네임
    String name();

    //프로필 이미지 URL(선택값)
    String imageUrl();

}
