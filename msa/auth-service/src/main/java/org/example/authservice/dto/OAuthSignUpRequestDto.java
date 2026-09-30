package org.example.authservice.dto;

import lombok.Getter;
import org.example.authservice.domain.entity.Role;

@Getter
public class OAuthSignUpRequestDto {
    private String signupToken;
    private Role role;
}
