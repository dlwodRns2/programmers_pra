package org.example.essentials.session_cookie_prac;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

@Controller
@RequestMapping("/session-cookie-prac")
public class DashBoardController {
    private static final DateTimeFormatter FMT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    //브라우저 쿠키에 lastVisit, theme, JSESSIONID가 그대로 남아있음
    //=> 로그아웃 후, 재로그인시에도 기존 테마 유지
    @GetMapping("/dashboard")
    public String dashboard(
            HttpSession httpsession,
            @CookieValue(value="lastVisit",required = false) String lastVisit,
            @CookieValue(value="theme",defaultValue = "light") String theme,
            HttpServletResponse response,
            Model model
    ){
        String username = httpsession.getAttribute("username").toString();
        if(username==null){
            return "redirect:/session-cookie-prac/login";
        }
        model.addAttribute("username",username);

        if(lastVisit!=null){
            long milis = Long.parseLong(lastVisit);
            String readable = Instant.ofEpochMilli(milis)
                    .atZone(ZoneId.systemDefault())
                    .format(FMT);
            model.addAttribute("lastVisit",readable);
        }
        if(theme!=null){
            model.addAttribute("theme",theme);
        }

        Cookie visit = new Cookie("lastVisit",String.valueOf(System.currentTimeMillis()));
        visit.setMaxAge(30*24*60*60);
        visit.setPath("/");
        visit.setHttpOnly(true);
        response.addCookie(visit);

        return "session-cookie-prac/dashboard";
    }

    @GetMapping("/theme")
    public String setTheme(
            @RequestParam String mode,
            HttpServletResponse response)
    {
        String value = ("dark".equals(mode))?"dark":"light";
        Cookie theme = new Cookie("theme",value);
        theme.setMaxAge(30*24*60*60);
        theme.setPath("/");
        theme.setHttpOnly(true);
        response.addCookie(theme);

        return "redirect:/session-cookie-prac/dashboard";
    }
}
