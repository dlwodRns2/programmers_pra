package org.example.securitypracjwt.config.oauth2;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.example.securitypracjwt.config.jwt.JwtProperties;
import org.example.securitypracjwt.config.jwt.TokenProvider;
import org.example.securitypracjwt.service.TokenService;
import org.example.securitypracjwt.util.CookieUtil;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class OAuth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    private final TokenService tokenService;
    private final TokenProvider tokenProvider;
    private final JwtProperties jwtProperties;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        CustomOAuth2User principal = (CustomOAuth2User) authentication.getPrincipal();

        String targetUrl;
        if(principal.isRegistered()){
            TokenService.TokenPair tokens = tokenService.issueTokens(principal.getUser());
            CookieUtil.addCookie(
                    response,
                    CookieUtil.REFRESH_TOKEN_COOKIE,
                    tokens.refreshToken(),
                    (int) jwtProperties.getRefreshTokenValidity().toSeconds()
            );
            targetUrl="/";
        }else{
            String signupToken = tokenProvider.createSignupToken(principal.getAuthProvider(),principal.getOAuth2UserInfo());
            targetUrl= UriComponentsBuilder.fromUriString("/users/oauth-join")
                    .queryParam("signupToken",signupToken)
                    .build()
                    .toUriString();
        }

        if(response.isCommitted()){
            logger.debug("Response has already been committed. Redirecting is not possible.");
            return;
        }

        getRedirectStrategy().sendRedirect(request,response,targetUrl);
    }
}
