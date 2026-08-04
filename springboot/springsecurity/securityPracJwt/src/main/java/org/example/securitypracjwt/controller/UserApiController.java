package org.example.securitypracjwt.controller;

import jakarta.servlet.http.HttpServletResponse;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.example.securitypracjwt.config.jwt.JwtProperties;
import org.example.securitypracjwt.config.security.CustomUserDetails;
import org.example.securitypracjwt.domain.entity.User;
import org.example.securitypracjwt.dto.*;
import org.example.securitypracjwt.service.UserService;
import org.example.securitypracjwt.util.CookieUtil;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserApiController {
    private final UserService userService;
    private final JwtProperties jwtProperties;

    //동의하고 가입까지 하는 순간 로그인까지 처리
    //signUpRequest를 받고 SignInResponse를 반환
    @PostMapping("/oauth-join")
    public SignInResponseDto oauthJoin(
            @RequestBody OAuthSignupRequestDto request,
            HttpServletResponse response){
        SignInResponseDto signInResponseDto = userService.oauthSignUp(request);
        CookieUtil.addCookie(
                response,
                CookieUtil.REFRESH_TOKEN_COOKIE,
                signInResponseDto.getRefreshToken(),
                (int) jwtProperties.getRefreshTokenValidity().toSeconds()
        );
        signInResponseDto.setRefreshToken(null);
        return signInResponseDto;
    }
    @PostMapping("/join")
    public SignUpResponseDto join(@RequestBody SignUpRequestDto request){
        userService.signUp(request);

        return SignUpResponseDto.builder()
                .url("/")
                .build();
    }
    @PostMapping("/login")
    public SignInResponseDto login(
            @RequestBody SignInRequestDto request,
            HttpServletResponse response){
        SignInResponseDto signInResponseDto = userService.signIn(request.getUserId(), request.getPassword());

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
    public UserInfoResponseDto getUserInfo(@AuthenticationPrincipal CustomUserDetails userDetails){
        User user = userDetails.getUser();

        return UserInfoResponseDto.builder()
                .userId(user.getUserId())
                .userName(user.getName())
                .role(user.getRole())
                .build();
    }

    //Authorization 관련
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/user")
    public AuthorityResponseDto authority(){
        return AuthorityResponseDto.builder()
                .message("일반 사용자만 볼 수 있는 권한입니다.")
                .build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/admin")
    public AuthorityResponseDto authorityAdmin(){
        return AuthorityResponseDto.builder()
                .message("관리자만 볼 수 있는 권한입니다.")
                .build();
    }


}
