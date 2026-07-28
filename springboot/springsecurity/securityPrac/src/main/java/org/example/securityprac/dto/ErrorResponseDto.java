package org.example.securityprac.dto;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ErrorResponseDto {
    private int status;
    private String message;
}
