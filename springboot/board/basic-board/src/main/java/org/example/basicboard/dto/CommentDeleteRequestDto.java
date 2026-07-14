package org.example.basicboard.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class CommentDeleteRequestDto {
    private Long id;
}
