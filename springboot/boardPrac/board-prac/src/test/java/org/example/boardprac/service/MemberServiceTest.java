package org.example.boardprac.service;

import org.example.boardprac.aop.LogginAspect;
import org.example.boardprac.domain.entity.Member;
import org.example.boardprac.domain.repository.MemberRepository;
import org.example.boardprac.dto.LoginRequestDto;
import org.example.boardprac.dto.MemberJoinRequestDto;
import org.example.boardprac.exception.DuplicateUserIdException;
import org.example.boardprac.mapper.MemberMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class MemberServiceTest {

    @Mock
    MemberRepository memberRepository;
    @Mock
    MemberMapper memberMapper;
    @InjectMocks MemberService memberService;

    @Test
    void login_성공(){
        Member member = Member.builder().userId("hong").password("1234").userName("홍길동").build();

        given(memberRepository.findByUserId("hong")).willReturn(Optional.of(member));

        LoginRequestDto req = new LoginRequestDto();
        req.setUsername("hong"); req.setPassword("1234");

        Optional<Member> result = memberService.login(req);
        assertThat(result).isPresent();
    }

    @Test
    void join_중복이면_예외(){
        MemberJoinRequestDto req = new MemberJoinRequestDto();
        req.setUserName("홍길동"); req.setPassword("1234"); req.setUserId("hong");
        given(memberRepository.existsByUserId("hong")).willReturn(true);

        assertThatThrownBy(()->memberService.join(req))
                .isInstanceOf(DuplicateUserIdException.class);
        verify(memberRepository, never()).save(any());
    }

}