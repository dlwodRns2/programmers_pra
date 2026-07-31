package org.example.boardprac.config.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.boardprac.config.jwt.TokenProvider;
import org.example.boardprac.config.jwt.TokenStatus;
import org.example.boardprac.domain.entity.Member;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
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
            TokenStatus tokenStatus = tokenProvider.validateToken(token);
            log.debug("Token Status: {}",tokenStatus);
            if(tokenStatus==TokenStatus.VALID){
                Member member = tokenProvider.getTokenDetails(token);
                Authentication authentication = tokenProvider.getAuthentication(member,token);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }else if(tokenStatus==TokenStatus.EXPIRED){
                log.warn("{}, Token is expired",requestURI);
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                return;
            }
        }
        filterChain.doFilter(request,response);
    }

    private String resolveToken(HttpServletRequest request ){
        String bearerToken = request.getHeader(HttpHeaders.AUTHORIZATION);
        if(bearerToken!=null&&bearerToken.startsWith("Bearer ")){
            return bearerToken.substring(7);
        }
        return null;
    }
}
