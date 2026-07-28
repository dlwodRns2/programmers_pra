package org.example.securityprac.controller;

import lombok.RequiredArgsConstructor;
import org.example.securityprac.dto.SignUpRequestDto;
import org.example.securityprac.dto.SignupResponseDto;
import org.example.securityprac.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserApiController {
    private final UserService userService;

    @PostMapping("/join")
    public SignupResponseDto signUp(@RequestBody SignUpRequestDto request){
        userService.signUp(request);
        return new SignupResponseDto("/users/login");
    }

}
