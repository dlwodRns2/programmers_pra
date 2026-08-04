package org.example.securitypracjwt.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.example.securitypracjwt.config.oauth2.AuthProvider;

@Getter
@Builder
@AllArgsConstructor
public class SignupPayloadDto {
    private AuthProvider provider;
    private String providerId;
    private String email;
    private String name;
}
