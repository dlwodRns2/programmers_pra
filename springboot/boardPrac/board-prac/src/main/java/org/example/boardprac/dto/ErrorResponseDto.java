package org.example.boardprac.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
public class ErrorResponseDto {
    private int status;
    private String message;
}
