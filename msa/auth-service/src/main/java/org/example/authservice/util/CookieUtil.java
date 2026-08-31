package org.example.authservice.util;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class CookieUtil {
    public static final String REFRESH_TOKEN_COOKIE="refreshToken";

    public static void addCookie(
            HttpServletResponse response,
            String name,
            String value,
            int maxAge
    ){
        Cookie cookie = new Cookie(name,value);
        cookie.setHttpOnly(true); //JS로 접근 불가
        cookie.setSecure(false); //로컬(http) 개발용. 운영 배포(https)시 true
        cookie.setPath("/");
        cookie.setMaxAge(maxAge);
        response.addCookie(cookie);
    }

    public static void deleteCookie(
            HttpServletRequest request,
            HttpServletResponse response,
            String name
    ){
        Cookie[] cookies = request.getCookies();

        if(cookies==null){
            return;
        }
        for(Cookie cookie: cookies){
            if(name.equals(cookie.getName())){
                cookie.setMaxAge(0);
                cookie.setPath("/");
                cookie.setValue("");
                response.addCookie(cookie);
            }
        }
    }
}
