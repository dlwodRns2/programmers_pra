package org.example.essentials.session_cookie_prac;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/session-cookie-prac")
public class LoginController {
    @GetMapping("/login")
    public String loginForm(
            HttpSession httpSession
    ){
        if(httpSession.getAttribute("username")!=null){
            return "redirect:/session-cookie-prac/dashboard";
        }
        return "session-cookie-prac/login";
    }

    @PostMapping("/login")
    public String login(
            @RequestParam String username,
            HttpSession httpSession
    ){
        httpSession.setAttribute("username",username);
        return "redirect:/session-cookie-prac/dashboard";
    }
    @GetMapping("/logout")
    public String logout(
            HttpSession httpSession
    ){
        httpSession.invalidate();
        return "redirect:/session-cookie-prac/login";
    }
}
