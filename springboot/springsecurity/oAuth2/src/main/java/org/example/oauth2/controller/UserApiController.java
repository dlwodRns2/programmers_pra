package org.example.oauth2.controller;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.example.oauth2.config.jwt.JwtProperties;
import org.example.oauth2.config.security.CustomUserDetails;
import org.example.oauth2.domain.entity.User;
import org.example.oauth2.dto.*;
import org.example.oauth2.service.UserService;
import org.example.oauth2.util.CookieUtil;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserApiController {
    private final UserService userService;
    private final JwtProperties jwtProperties;
    @PostMapping("/join")
    public SignUpResponseDto join(@RequestBody SignUpRequestDto requestDto){
        userService.signUp(requestDto);

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
        signInResponseDto.setRefreshToken(null);
        return signInResponseDto;
    }
    @GetMapping("/info")
    public UserInfoResponseDto getUserInfo(@AuthenticationPrincipal CustomUserDetails userDetails) {
        User user = userDetails.getUser();
        return UserInfoResponseDto.builder()
                .id(user.getId())
                .userId(user.getUserId())
                .userName(user.getName())
                .role(user.getRole())
                .build();
    }

    // "hasRole('USER')"는 내부적으로 "ROLE_USER"권한을 찾는다 (접두사 자동 부착)
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/user")
    public AuthorityResponseDto authority() {
        return AuthorityResponseDto.builder()
                .message("일반 사용자만 볼 수 있는 권한입니다.")
                .build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/admin")
    public AuthorityResponseDto authorityAdmin() {
        return AuthorityResponseDto.builder()
                .message("관리자만 볼 수 있는 권한입니다.")
                .build();
    }

}
