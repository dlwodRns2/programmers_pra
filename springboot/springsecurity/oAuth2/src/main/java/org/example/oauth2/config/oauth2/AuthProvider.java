package org.example.oauth2.config.oauth2;

//구글, 네이버, 카카오 등. 현재는 카카오만 사용
public enum AuthProvider {
    LOCAL,
    KAKAO;

    //registrationId("kakao") => AuthProvider.KAKAO
    public static AuthProvider from(String registrationId){
        return AuthProvider.valueOf(registrationId.toUpperCase());
    }
}
