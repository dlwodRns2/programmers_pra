package org.example.securitypracjwt.config;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.example.securitypracjwt.config.filter.TokenAuthenticationFilter;
import org.example.securitypracjwt.config.oauth2.OAuth2FailureHandler;
import org.example.securitypracjwt.config.oauth2.OAuth2SuccessHandler;
import org.example.securitypracjwt.service.CustomOAuth2UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig {
    private final TokenAuthenticationFilter tokenAuthenticationFilter;

    //OAuth2 로그인 파이프라인에서 "개발자가 구현하는 훅"
    private final CustomOAuth2UserService customOAuth2UserService;
    private final OAuth2SuccessHandler oAuth2SuccessHandler;
    private final OAuth2FailureHandler oAuth2FailureHandler;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http){
        http
                .csrf(AbstractHttpConfigurer::disable)
                .logout(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                //JWT사용 -> 세션을 만들지도 사용하지도 않음(stateless)
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/users/login","/api/users/login",
                                "/users/join","/api/users/join",
                                "/api/users/logout",
                                "/","/admin","/users/oauth-join",

                                "/api/tokens/refresh",
                                "/api/users/oauth-join",
                                "/css/**", "/js/**",
                                "/access-denied","/error"
                        ).permitAll()
                        .anyRequest()
                        .authenticated()
                )
                .oauth2Login(
                        //user-info 조회 "직후"
                        oauth2 -> oauth2
                                .userInfoEndpoint(userInfo -> userInfo
                                        .userService(customOAuth2UserService))
                                .successHandler(oAuth2SuccessHandler)
                                .failureHandler(oAuth2FailureHandler)
                )
                //커스텀 필터 추가 : tokenAuthenticationFilter
                .addFilterBefore(tokenAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling(exception -> exception
                        .accessDeniedHandler(accessDeniedHandler())
                        .authenticationEntryPoint(authenticationEntryPoint())
                );
        return http.build();
    }
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public RoleHierarchy roleHierarchy(){
        return RoleHierarchyImpl.withDefaultRolePrefix() // "ROLE_" 접두사 자동 부착
                .role("ADMIN").implies("USER")
                .build();
    }
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration){
        return authenticationConfiguration.getAuthenticationManager();
    }
    @Bean
    public AccessDeniedHandler accessDeniedHandler(){
        return (request,response,e)->{
            if(request.getRequestURI().startsWith("/api/")){
                sendErrorJson(response, HttpServletResponse.SC_FORBIDDEN,"접근 권한이 없습니다.");
            }else{
                response.sendRedirect("/access-denied");
            }
        };
    }
    @Bean
    public AuthenticationEntryPoint authenticationEntryPoint(){
        return (request,response,e)->{
            if(request.getRequestURI().startsWith("/api/")){
                sendErrorJson(response,HttpServletResponse.SC_UNAUTHORIZED,"인증이 필요합니다.");
            }else{
                response.sendRedirect("/access-denied");
            }
        };
    }
    private void sendErrorJson(HttpServletResponse response, int status,String message) throws IOException {
        response.setStatus(status);
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write("{\"status\": " + status + ", \"message\": \"" + message + "\"}");
    }

}
