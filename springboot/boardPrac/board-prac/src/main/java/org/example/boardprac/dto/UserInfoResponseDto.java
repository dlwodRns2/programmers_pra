package org.example.boardprac.dto;

import lombok.Builder;
import lombok.Getter;
import org.example.boardprac.domain.entity.Role;
import org.springframework.web.bind.annotation.GetMapping;

@Getter
@Builder
public class UserInfoResponseDto {
    private Long id;
    private String userId;
    private String userName;
    private Role role;
}
