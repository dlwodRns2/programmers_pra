package org.example.boardprac.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
@AllArgsConstructor
public class LoginResponseDto {
    private boolean isLoggedIn;
    private String url;
    private String userName;
    private String userId;
    private String accessToken;
    private String refreshToken;
    private String message;

//    public static LoginResponseDto success(){
//        return new LoginResponseDto(true,"/","로그인에 성공했습니다.");
//    }
//    public static LoginResponseDto fail(){
//        return new LoginResponseDto(false,null, "아이디 또는 비밀번호가 일치하지 않습니다.");
//    }
}
