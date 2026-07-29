package org.example.token.config.filter;

import org.example.token.config.jwt.TokenProvider;
import org.example.token.config.jwt.TokenStatus;
import org.example.token.domain.User;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class TokenAuthenticationFilter extends OncePerRequestFilter {
    private final TokenProvider tokenProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String requestURI = request.getRequestURI();
        log.info("requestURI: {}",requestURI);

        String token = resolveToken(request);

        if(token!=null){
            TokenStatus status = tokenProvider.validateToken(token);
            log.debug("Token status: {}",status);
            if(status==TokenStatus.VALID){
                User user = tokenProvider.getTokenDetails(token);

                // * principal이란
                // Authentication 객체는 세 가지로 구성된다:
                // - principal   : "인증된 주체가 누구인가" - 사용자를 대표하는 객체 (보통 UserDetails)
                // - credentials : 인증에 사용한 증명 수단 (비밀번호, 토큰 등. 인증 후에 보통 지운다)
                // - authorities : 부여된 권한 목록
                // 즉 principal은 "이 요청의 주인"이고, 시큐리티 어디서든
                // SecurityContextHolder...getAuthentication().getPrincipal()로 꺼낼 수 있다.
                // 우리는 principal 자리에 CustomUserDetails를 넣는다.
                // 그래서 @AuthenticationPrincipal CustomUserDetails로 바로 주입받아
                // getUser()로 도메인 User까지 접근할 수 있다.
                Authentication authentication = tokenProvider.getAuthentication(user,token);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }else if (status == TokenStatus.EXPIRED){
                log.warn("{}, Token is expired",requestURI);
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                return;
            }
        }
        filterChain.doFilter(request,response);
    }

    //request의 Authorization 헤더에서 JWT 토큰을 추출하는 메서드
    private String resolveToken(HttpServletRequest request){
        String bearerToken = request.getHeader(HttpHeaders.AUTHORIZATION);

        if(bearerToken != null && bearerToken.startsWith("Bearer ")){
            return bearerToken.substring(7);
        }
        return null;
    }
}
