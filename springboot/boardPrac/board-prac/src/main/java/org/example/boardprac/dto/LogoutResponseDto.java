package org.example.boardprac.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class LogoutResponseDto {
    private String message;
    private String url;
}
