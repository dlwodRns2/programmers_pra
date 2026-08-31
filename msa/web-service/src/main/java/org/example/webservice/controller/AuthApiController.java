package org.example.webservice.controller;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.http.HeaderUtil;
import org.example.webservice.dto.*;
import org.example.webservice.service.AuthService;
import org.example.webservice.util.HeaderRelayUtil;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class AuthApiController {
    private final AuthService authService;

    @PostMapping("/join")
    public SignUpResponseDto join(SignUpRequestDto signUpRequestDto){
        return authService.signUp(signUpRequestDto);
    }

    @PostMapping("/login")
    public SignInResponseDto login(
            @RequestBody SignInRequestDto signInRequestDto,
            HttpServletResponse response
            ){
        return HeaderRelayUtil.relaySetCookie(authService.signIn(signInRequestDto),response);
    }
    @GetMapping("/info")
    public UserInfoResponseDto getUserInfo(
            @RequestHeader(value= HttpHeaders.AUTHORIZATION, required = false) String authorization
    ){
        return authService.getUserInfo(authorization);
    }
}
