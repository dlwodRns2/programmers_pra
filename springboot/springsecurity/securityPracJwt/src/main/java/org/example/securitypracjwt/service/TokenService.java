package org.example.securitypracjwt.service;

import jakarta.servlet.http.Cookie;
import lombok.RequiredArgsConstructor;
import org.example.securitypracjwt.config.jwt.JwtProperties;
import org.example.securitypracjwt.config.jwt.TokenProvider;
import org.example.securitypracjwt.config.jwt.TokenStatus;
import org.example.securitypracjwt.domain.entity.User;
import org.example.securitypracjwt.dto.RefreshTokenResponseDto;
import org.example.securitypracjwt.dto.SignupPayloadDto;
import org.example.securitypracjwt.util.CookieUtil;
import org.springframework.stereotype.Service;

//TokenService :
@Service
@RequiredArgsConstructor
public class TokenService {
    private final TokenProvider tokenProvider;
    private final JwtProperties jwtProperties;

    public record TokenPair(String accessToken, String refreshToken){}

    //토큰 수명 정책은 yaml파일 한 곳에서 결정된다
    public TokenPair issueTokens(User user){
        String accessToken = tokenProvider.generateToken(user,jwtProperties.getAccessTokenValidity());
        String refreshToken = tokenProvider.generateToken(user,jwtProperties.getRefreshTokenValidity());
        return new TokenPair(accessToken,refreshToken);
    }

    //리프레시 토큰 만료 여부 판단 후, 재발급
    public RefreshTokenResponseDto refreshToken(Cookie[] cookies){
        String refreshToken = getRefreshTokenFromCookies(cookies);

        //리프레시 토큰이 유효한 경우 재발급
        if(refreshToken!=null&&tokenProvider.validateToken(refreshToken)== TokenStatus.VALID){
            User user = tokenProvider.getTokenDetails(refreshToken);
            TokenPair tokens = issueTokens(user);
            return RefreshTokenResponseDto.builder()
                    .validated(true)
                    .accessToken(tokens.accessToken)
                    .refreshToken(tokens.refreshToken)
                    .build();

        }
        //리프레시 토큰이 유효하지 않거나 없는 경우
        return RefreshTokenResponseDto.builder()
                .validated(false)
                .build();
    }
    private String getRefreshTokenFromCookies(Cookie[] cookies){
        if(cookies!=null){
            for(Cookie cookie: cookies){
                if(cookie.getName().equals(CookieUtil.REFRESH_TOKEN_COOKIE)){
                    return cookie.getValue();
                }
            }
        }
        return null;
    }
    
    //tokenProvider의 작업을 tokenServiced에서 감싼다
    //=> 다른 클래스는 tokenService를 통해서 메서드를 사용
    public SignupPayloadDto getSignupPayload(String token){
        return tokenProvider.getSignupPayload(token);
    }
}
