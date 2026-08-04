package org.example.securitypracjwt.config.jwt;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.time.Duration;

@Getter
@Setter
@Component
// yaml/properties의 값을 자바 객체로 자동 바인딩
//yaml의 jwt 속성(issuer, secret_key, ... -> issuer, secretKey ...)
@ConfigurationProperties(prefix = "jwt")
public class JwtProperties {
    private String issuer;
    private String secretKey; // relaxed binding: secret_key → secretKey
    private Duration accessTokenValidity;
    private Duration refreshTokenValidity;
}
