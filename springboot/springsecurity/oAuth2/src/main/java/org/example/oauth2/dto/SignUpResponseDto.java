package org.example.oauth2.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@AllArgsConstructor
public class SignUpResponseDto {
    private String url;
}
