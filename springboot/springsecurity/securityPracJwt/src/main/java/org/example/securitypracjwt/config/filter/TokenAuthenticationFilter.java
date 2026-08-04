package org.example.securitypracjwt.config.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.securitypracjwt.config.jwt.TokenProvider;
import org.example.securitypracjwt.config.jwt.TokenStatus;
import org.example.securitypracjwt.domain.entity.User;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

//TokenAuthenticationFilter : HttpRequest와 함께 온 토큰의 정보를 읽어 Authentication(인증 정보)를 만드는 책임
@Slf4j
@Component
@RequiredArgsConstructor
public class TokenAuthenticationFilter extends OncePerRequestFilter {

    private final TokenProvider tokenProvider;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = resolveToken(request);

        if(token!=null){
            TokenStatus status = tokenProvider.validateToken(token);

            //유효한 토큰일 경우
            if(status == TokenStatus.VALID){
                //토큰에서 유저 정보를 가져와서 SecurityContextHolderdDp
                User user = tokenProvider.getTokenDetails(token);

                //Authentication 인터페이스 : 인증 주체/증명 수단/권한(인가)에 대한 정보를 가짐
                //Authentication 객체는 "인증 결과"임과 동시에 "사용자가 가진 권한 목록"까지 함께 가지는 객체
                //=> 인증/인가 둘 다 처리. SecurityContextHolder에는 Authentication 하나만 저장하도록 설계됨
                //
                Authentication authentication = tokenProvider.getAuthentication(user,token);

                //SecurityContextHolder에 인증 정보를 저장.
                //Spring Security가 해당 요청에 대해서 사용자가 인증된 상태임을 인식하게됨
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }else if (status==TokenStatus.EXPIRED){
//                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
//                return;
            }
        }
        filterChain.doFilter(request,response);
    }
    private String resolveToken(HttpServletRequest request){
        String bearerToken = request.getHeader(HttpHeaders.AUTHORIZATION);
        if(bearerToken !=null && bearerToken.startsWith("Bearer ")){
            return bearerToken.substring(7);
        }
        return null;
    }
}
