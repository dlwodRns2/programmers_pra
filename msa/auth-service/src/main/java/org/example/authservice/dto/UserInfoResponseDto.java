package org.example.authservice.dto;

import lombok.Builder;
import lombok.Getter;
import org.example.authservice.domain.entity.Role;

@Getter
@Builder
public class UserInfoResponseDto {
    private long id;
    private String userId;
    private String userName;
    private Role role;
}
