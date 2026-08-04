package org.example.securitypracjwt.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.example.securitypracjwt.config.jwt.JwtProperties;
import org.example.securitypracjwt.dto.ErrorResponse;
import org.example.securitypracjwt.dto.RefreshTokenResponseDto;
import org.example.securitypracjwt.service.TokenService;
import org.example.securitypracjwt.util.CookieUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/tokens")
public class TokenApiController {
    private final TokenService tokenService;
    private final JwtProperties jwtProperties;

    @PostMapping("/refresh")
    public ResponseEntity<?> refreshToken(
            HttpServletRequest request,
            HttpServletResponse response
    ){
        RefreshTokenResponseDto refreshTokenResponseDto = tokenService.refreshToken(request.getCookies());

        //리프레시 토큰이 유효한 경우
        if(refreshTokenResponseDto.isValidated()){
            CookieUtil.addCookie(
                    response,
                    CookieUtil.REFRESH_TOKEN_COOKIE,
                    refreshTokenResponseDto.getRefreshToken(),
                    (int) jwtProperties.getRefreshTokenValidity().toSeconds()
            );

            refreshTokenResponseDto.setRefreshToken(null);

            return ResponseEntity.ok(refreshTokenResponseDto);
        }

        //리프레시 토큰이 만료된 경우
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(new ErrorResponse(HttpStatus.UNAUTHORIZED.value(),"리프레시 토큰이 만료되었습니다."));
    }

}
