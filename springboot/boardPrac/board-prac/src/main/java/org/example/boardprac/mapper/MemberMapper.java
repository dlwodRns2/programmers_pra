package org.example.boardprac.mapper;

import org.example.boardprac.domain.entity.Member;
import org.example.boardprac.dto.MemberJoinRequestDto;
import org.springframework.stereotype.Component;

//Dto -> Entity
@Component
public class MemberMapper {
    public Member toEntity(MemberJoinRequestDto dto){
        return Member.builder()
                .userId(dto.getUserId())
                .password(dto.getPassword())
                .userName(dto.getUserName())
                .build();
    }
}
