package org.example.token.controller;

import jakarta.servlet.http.HttpServletResponse;
import org.example.token.config.jwt.JwtProperties;
import org.example.token.config.security.CustomUserDetails;
import org.example.token.domain.entity.User;
import org.example.token.dto.*;
import lombok.RequiredArgsConstructor;
import org.example.token.util.CookieUtil;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.example.token.service.UserService;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserApiController {

    private final JwtProperties jwtProperties;
    private final UserService userService;

    @PostMapping("/join")
    public SignUpResponseDto join(@RequestBody SignUpRequestDto request){
        userService.join(request);
        return SignUpResponseDto.builder()
                .url("/users/login")
                .build();
    }
    @PostMapping("/login")
    public SignInResponseDto login(
            @RequestBody SignInRequestDto request,
            HttpServletResponse response
            ){
        SignInResponseDto signInResponseDto = userService.login(request);
        CookieUtil.addCookie(
                response,
                CookieUtil.REFRESH_TOKEN_COOKIE,
                signInResponseDto.getRefreshToken(),
                (int) jwtProperties.getRefreshTokenValidity().toSeconds()
        );
        signInResponseDto.setAccessToken(null);
        return signInResponseDto;
    }

    @GetMapping("/info")
    public UserInfoResponseDto getUserInfo(@AuthenticationPrincipal CustomUserDetails userDetails){
        User user = userDetails.getUser();
        return UserInfoResponseDto.builder()
                .id(user.getId())
                .userId(user.getUserId())
                .userName(user.getName())
                .role(user.getRole())
                .build();
    }
}
