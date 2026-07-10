package org.example.boardprac.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
public class MemberJoinRequestDto {
    @Schema(description = "회원가입 아이디", example = "user01")
    private String userId;
    @Schema(description = "회원가입 비밀번호",example = "password1234")
    private String password;
    @Schema(description = "사용할 유저 이름", example="홍길동")
    private String userName;
}
