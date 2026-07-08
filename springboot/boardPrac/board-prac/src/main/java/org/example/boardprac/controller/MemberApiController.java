package org.example.boardprac.controller;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.example.boardprac.constant.SessionConst;
import org.example.boardprac.domain.entity.Member;
import org.example.boardprac.dto.MemberJoinRequestDto;
import org.example.boardprac.dto.MemberJoinResponseDto;
import org.example.boardprac.dto.LoginRequestDto;
import org.example.boardprac.dto.LoginResponseDto;
import org.example.boardprac.service.MemberService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/members")
@RequiredArgsConstructor
public class MemberApiController {
    private final MemberService memberService;

    @PostMapping("/join")
    public MemberJoinResponseDto join(@RequestBody MemberJoinRequestDto dto){
        memberService.join(dto);
        return new MemberJoinResponseDto("/members/login");
    }
    @PostMapping("/login")
    public LoginResponseDto login(
            @RequestBody LoginRequestDto dto,
            HttpSession session){
        return memberService.login(dto)
                .map(member ->{
                    session.setAttribute(SessionConst.USER_ID,member.getUserId());
                    session.setAttribute(SessionConst.USER_NAME, member.getUserName());
                    return LoginResponseDto.success();
                })
                .orElseGet(LoginResponseDto::fail);
    }

}
