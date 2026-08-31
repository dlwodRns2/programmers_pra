package org.example.webservice.dto;

import lombok.Getter;
import org.example.webservice.enums.Role;

@Getter
public class SignUpRequestDto {
    private String userId;
    private String password;
    private String userName;
    private Role role;
}
