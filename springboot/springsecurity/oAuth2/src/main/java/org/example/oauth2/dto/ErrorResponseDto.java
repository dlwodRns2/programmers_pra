package org.example.oauth2.dto;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ErrorResponseDto {
    private int status;
    private String message;
}
