package org.example.authservice.config.oauth2;

public enum AuthProvider {
    KAKAO,
    LOCAL;

    //"kakao" -> AuthProvider.KAKAO
    public static AuthProvider from(String registrationId){
        return AuthProvider.valueOf(registrationId.toUpperCase());

    }
}
