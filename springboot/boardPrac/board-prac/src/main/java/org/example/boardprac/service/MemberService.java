package org.example.boardprac.service;

import lombok.RequiredArgsConstructor;
import org.example.boardprac.config.security.CustomUserDetails;
import org.example.boardprac.domain.entity.Member;
import org.example.boardprac.domain.repository.MemberRepository;
import org.example.boardprac.dto.LoginResponseDto;
import org.example.boardprac.dto.MemberJoinRequestDto;
import org.example.boardprac.dto.LoginRequestDto;
import org.example.boardprac.exception.DuplicateUserIdException;
import org.example.boardprac.mapper.MemberMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {
    private final MemberRepository memberRepository;
    private final MemberMapper memberMapper;
    private final TokenService tokenService;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public void join(MemberJoinRequestDto dto){
        //Id 중복 시, 예외처리
        if(memberRepository.existsByUserId(dto.getUserId())){
            throw new DuplicateUserIdException("이미 존재하는 Id입니다.");
        }
        Member member = dto.toMember(passwordEncoder.encode(dto.getPassword()));
        memberRepository.save(member);
    }

    public LoginResponseDto login(LoginRequestDto dto){

        //1. authenticationManager 빈은 내부적으로 DaoAuthenticationProvider 사용
        //2.DaoAuthenticationProvider가 UserDetailServoce.loadUserByname 호출.
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(dto.getUsername(),dto.getPassword())
        );
        Member member =((CustomUserDetails) authentication.getPrincipal()).getMember();
        TokenService.TokenPair tokenPair = tokenService.issueToken(member);

        return LoginResponseDto.builder()
                .isLoggedIn(true)
                .message("로그인 성공")
                .url("/")
                .accessToken(tokenPair.accessToken())
                .refreshToken(tokenPair.refreshToken())
                .userName(member.getUserName())
                .userId(member.getUserId())
                .build();

    }
}
