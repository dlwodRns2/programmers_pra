package org.example.basicboard.service;

import lombok.RequiredArgsConstructor;
import org.example.basicboard.domain.entity.Member;
import org.example.basicboard.domain.repository.MemberRepository;
import org.example.basicboard.dto.LoginRequestDto;
import org.example.basicboard.dto.MemberJoinRequestDto;
import org.example.basicboard.exception.DuplicateUserIdException;
import org.example.basicboard.mapper.MemberMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

@Service
@RequiredArgsConstructor
//이 클래스의 "모든 메서드"에 기본 적용
//- readOnly = true의 효과
//"이 트랜잭션은 데이터를 변경하지 안흔ㄴ다" 라고 JPA에 선언 -> 최적화
//하이버네이트가 변경감지를 위한 스냅샷을 만들지 않음 -> 메모리/성능에 유리
//Insert/Update/Delete가 필요한 메서드는 메서드에 @Transactional을 다시 붙여 덮어 쓴다
@Transactional(readOnly = true)
public class MemberService {
    private final MemberRepository memberRepository;
    private final MemberMapper memberMapper;

    @Transactional
    public void join(MemberJoinRequestDto dto){
        //아이디 중복 체크
        if(memberRepository.existsByUserId(dto.getUserId())){
            //예외 중복처리
            throw new DuplicateUserIdException("[회원가입] 이미 존재하는 아이디입니다.");
        }
        memberRepository.save(memberMapper.toEntity(dto));
    }

    public Optional<Member> login(LoginRequestDto dto){
        return memberRepository.findByUserId(dto.getUsername())
                .filter(
                        member -> member.getPassword().equals(dto.getPassword())
                );
    }
}
