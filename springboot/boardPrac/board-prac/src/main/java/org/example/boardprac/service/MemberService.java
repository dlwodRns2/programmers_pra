package org.example.boardprac.service;

import lombok.RequiredArgsConstructor;
import org.example.boardprac.domain.entity.Member;
import org.example.boardprac.domain.repository.MemberRepository;
import org.example.boardprac.dto.MemberJoinRequestDto;
import org.example.boardprac.dto.LoginRequestDto;
import org.example.boardprac.exception.DuplicateUserIdException;
import org.example.boardprac.mapper.MemberMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {
    private final MemberRepository memberRepository;
    private final MemberMapper memberMapper;

    @Transactional
    public void join(MemberJoinRequestDto dto){
        //Id 중복 시, 예외처리
        if(memberRepository.existsByUserId(dto.getUserId())){
            throw new DuplicateUserIdException("이미 존재하는 Id입니다.");
        }
        memberRepository.save(memberMapper.toEntity(dto));
    }

    public Optional<Member> login(LoginRequestDto dto){
        return memberRepository.findByUserId(dto.getUsername())
                .filter(member->member.getPassword().equals(dto.getPassword()));
    }
}
