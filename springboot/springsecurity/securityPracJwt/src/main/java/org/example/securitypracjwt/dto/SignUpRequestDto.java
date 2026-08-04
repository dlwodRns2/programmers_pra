package org.example.securitypracjwt.dto;

import jakarta.persistence.GeneratedValue;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.securitypracjwt.domain.entity.Role;
import org.example.securitypracjwt.domain.entity.User;

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
