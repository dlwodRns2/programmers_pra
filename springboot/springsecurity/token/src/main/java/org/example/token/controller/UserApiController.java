package org.example.token.controller;

import org.example.token.dto.SignUpRequestDto;
import org.example.token.dto.SignUpResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.example.token.service.UserService;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserApiController {
    private final UserService userService;
    @PostMapping("/join")
    public SignUpResponseDto join(@RequestBody SignUpRequestDto request){
        userService.join(request);
        return SignUpResponseDto.builder()
                .url("/users/login")
                .build();
    }
}
