package org.example.boardprac.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.example.boardprac.config.security.CustomUserDetails;
import org.example.boardprac.domain.entity.Member;
import org.example.boardprac.dto.*;
import org.example.boardprac.service.MemberService;
import org.example.boardprac.util.CookieUtil;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Tag( name = "회원 API", description = "회원가입, 로그인, 로그아웃 (세션 기반, spring security 미사용)")
@RestController
@RequestMapping("/api/members")
@RequiredArgsConstructor
public class MemberApiController {
    private final MemberService memberService;

    @Operation(summary = "회원가입", description = "아이디/비밀번호/이름으로 새 회원을 등록한다. 성공 시 로그인 페이지 경로를 돌려준다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "가입 성공"),
            @ApiResponse(responseCode = "409", description = "이미 존재하는 아이디",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))
    })
    @PostMapping("/join")
    public MemberJoinResponseDto join(@RequestBody MemberJoinRequestDto dto){
        memberService.join(dto);
        return MemberJoinResponseDto.builder()
                .url("/members/login")
                .build();
    }

    @Operation(summary = "로그인",
            description = "아이디/비밀번호로 로그인한다. 성공 시 세션에 사용자 정보를 저장하고 loggedIn=true 를, 실패 시 loggedIn=false 와 안내 메시지를 돌려준다.")
    @PostMapping("/login")
    public LoginResponseDto login(
            @RequestBody LoginRequestDto dto){
        return memberService.login(dto);
    }

    @PostMapping("/logout")
    public LogoutResponseDto logout(
            HttpServletRequest request,
            HttpServletResponse response
    ){
        CookieUtil.deleteCookie(request,response, CookieUtil.REFRESH_TOKEN_COOKIE);
        return LogoutResponseDto.builder()
                .message("로그아웃 되었습니다")
                .url("/members/login")
                .build();

    }
    @GetMapping("/info")
    public UserInfoResponseDto getUserInfo(@AuthenticationPrincipal CustomUserDetails userDetails){
        Member member = userDetails.getMember();
        return UserInfoResponseDto.builder()
                .id(member.getId())
                .userId(member.getUserId())
                .userName(member.getUserName())
                .role(member.getRole())
                .build();
    }
    // "hasRole('USER')"는 내부적으로 "ROLE_USER"권한을 찾는다 (접두사 자동 부착)
    @PreAuthorize("hasRole('MEMBER')")
    @GetMapping("/member")
    public AuthorityResponseDto authority() {
        return AuthorityResponseDto.builder()
                .message("일반 사용자만 볼 수 있는 권한입니다.")
                .build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/admin")
    public AuthorityResponseDto authorityAdmin(){
        return AuthorityResponseDto.builder()
                .message("관리자만 볼 수 있는 권한입니다.")
                .build();
    }

}
