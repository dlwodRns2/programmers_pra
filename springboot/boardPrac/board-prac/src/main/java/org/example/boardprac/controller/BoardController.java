package org.example.boardprac.controller;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
@RequestMapping("/")
public class BoardController {
    @GetMapping
    public String boardList(HttpSession session, Model model){
        if (session.getAttribute("userId") == null) {
            return "redirect:/members/login";
        }
        setSession(session,model);
        return "board-list";
    }

    @GetMapping("/write")
    public String write(HttpSession session, Model model){
        setSession(session,model);
        return "board-write";
    }
    @GetMapping("/detail")
    public String detail(
            @RequestParam Long id,
            HttpSession session,
            Model model
    ){
        setSession(session,model);
        model.addAttribute("id",id);
        return "board-detail";
    }

    private void setSession(HttpSession session, Model model){
        String userId = session.getAttribute("userId").toString();
        String userName = session.getAttribute("userName").toString();

        model.addAttribute("userId",userId);
        model.addAttribute("userName",userName);
    }
}
