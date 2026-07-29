package org.example.token.dto;

import org.example.token.domain.Role;
import org.example.token.domain.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class SignUpRequestDto {
    private String userId;
    private String password;
    private String userName;
    private Role role;

    public User toUser(String encodedPassword){
        return User.builder()
                .userId(userId)
                .password(encodedPassword)
                .name(userName)
                .role(role!=null?role: Role.ROLE_USER)
                .build();
    }
}
