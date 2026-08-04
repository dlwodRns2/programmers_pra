package org.example.securitypracjwt.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.example.securitypracjwt.domain.entity.Role;

@Getter
@AllArgsConstructor
public class OAuthSignupRequestDto {
    private String signupToken;
    private Role role;
}
