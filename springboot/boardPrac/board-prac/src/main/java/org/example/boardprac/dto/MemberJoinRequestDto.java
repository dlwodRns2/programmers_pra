package org.example.boardprac.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import org.example.boardprac.domain.entity.Member;
import org.example.boardprac.domain.entity.Role;
import org.springframework.security.core.userdetails.User;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MemberJoinRequestDto {
    @Schema(description = "회원가입 아이디", example = "user01")
    private String userId;
    @Schema(description = "회원가입 비밀번호",example = "password1234")
    private String password;
    @Schema(description = "사용할 유저 이름", example="홍길동")
    private String userName;
    private Role role;

    public Member toMember(String encodedPassword){
        return Member.builder()
                .userId(userId)
                .password(encodedPassword)
                .userName(userName)
                .role(role!=null?role:Role.ROLE_MEMBER)
                .build();
    }
}
