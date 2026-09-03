package org.example.boardservice.mapper;

import org.example.boardservice.domain.entity.Board;
import org.example.boardservice.domain.entity.Comment;
import org.example.boardservice.dto.BoardWithCommentsResponseDto;
import org.example.boardservice.dto.CommentResponseDto;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BoardMapper {
    public BoardWithCommentsResponseDto toBoardWithCommentsResponseDto(Board board){
        List<CommentResponseDto> comments = board.getComments().stream()
                .map(this::toCommentDto)
                .toList();

        return BoardWithCommentsResponseDto.builder()
                .title(board.getTitle())
                .content(board.getContent())
                .userId(board.getUserId())
                .created(board.getCreated())
                .filePath(board.getFilePath())
                .comments(comments)
                .build();
    }
    public CommentResponseDto toCommentDto(Comment comment){
        return CommentResponseDto.builder()
                .id(comment.getId())
                .userId(comment.getUserId())
                .content(comment.getContent())
                .created(comment.getCreated())
                .build();
    }
}
