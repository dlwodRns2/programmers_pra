package org.example.boardprac.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
public class MemberJoinRequestDto {
    private String userId;
    private String password;
    private String userName;
}
