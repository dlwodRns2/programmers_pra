package org.example.basicboard.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class CommentUpdateRequestDto {
    private Long id;
    private String content;
}
