package org.example.basicboard.dto;

import lombok.Builder;
import lombok.Getter;
import org.example.basicboard.domain.entity.Comment;

@Getter
@Builder
public class CommentListResponseDto {
    private Long id;
    private String content;

    public static CommentListResponseDto from(Comment comment){
        return new CommentListResponseDto(comment.getId(), comment.getContent());
    }
}
