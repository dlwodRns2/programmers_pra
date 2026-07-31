package org.example.boardprac.service;

import jakarta.servlet.http.Cookie;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.boardprac.config.jwt.JwtProperties;
import org.example.boardprac.config.jwt.TokenProvider;
import org.example.boardprac.config.jwt.TokenStatus;
import org.example.boardprac.domain.entity.Member;
import org.example.boardprac.dto.RefreshTokenResponseDto;
import org.example.boardprac.util.CookieUtil;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class TokenService {
    private final TokenProvider tokenProvider;
    private final JwtProperties jwtProperties;

    public record TokenPair(String accessToken, String refreshToken){}

    public TokenPair issueToken(Member member){
        String accessToken = tokenProvider.generateToken(member, jwtProperties.getAccessTokenValidity());
        String refreshToken=tokenProvider.generateToken(member,jwtProperties.getRefreshTokenValidity());

        return new TokenPair(accessToken,refreshToken);
    }

    public RefreshTokenResponseDto refreshToken(Cookie[] cookies){
        String refreshToken = getRefreshToken(cookies);

        if(refreshToken!=null&&tokenProvider.validateToken(refreshToken)== TokenStatus.VALID){
            Member member = tokenProvider.getTokenDetails(refreshToken);

            TokenPair tokenPair = issueToken(member);

            return RefreshTokenResponseDto.builder()
                    .validated(true)
                    .accessToken(tokenPair.accessToken())
                    .refreshToken(tokenPair.refreshToken())
                    .build();
        }
        return RefreshTokenResponseDto.builder()
                .validated(false)
                .build();
    }
    private String getRefreshToken(Cookie[] cookies){
        if(cookies==null){
            return null;
        }
        for(Cookie cookie:cookies){
            if(cookie.getName().equals(CookieUtil.REFRESH_TOKEN_COOKIE)){
                return cookie.getValue();
            }
        }
        return null;
    }

}
