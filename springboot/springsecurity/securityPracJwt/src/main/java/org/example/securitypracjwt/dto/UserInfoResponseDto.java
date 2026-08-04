package org.example.securitypracjwt.dto;

import lombok.Builder;
import lombok.Getter;
import org.example.securitypracjwt.domain.entity.Role;

@Getter
@Builder
public class UserInfoResponseDto {
    private Long id;
    private String userId;
    private String userName;
    private Role role;
}
