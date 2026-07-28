package org.example.securityprac.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.example.securityprac.entity.domain.User;

@Getter
@AllArgsConstructor
public class SignUpRequestDto {
    private String userName;
    private String userId;
    private String password;

    public  User toUser(String encodedPassword){
        return User.builder()
                .userId(userId)
                .password(encodedPassword)
                .name(userName)
                .build();
    }
}
