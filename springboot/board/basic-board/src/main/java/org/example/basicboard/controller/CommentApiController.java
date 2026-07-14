package org.example.basicboard.controller;

import lombok.RequiredArgsConstructor;
import org.example.basicboard.dto.CommentDeleteRequestDto;
import org.example.basicboard.dto.CommentListResponseDto;
import org.example.basicboard.dto.CommentUpdateRequestDto;
import org.example.basicboard.dto.CommentWriteRequestDto;
import org.example.basicboard.service.CommentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/boards/{boardId}/comments")
@RequiredArgsConstructor
public class CommentApiController {
    private final CommentService commentService;

    //댓글 수정,삭제 확인을 위한 임시 GetMapping
    @GetMapping
    public ResponseEntity<List<CommentListResponseDto>> list(){
        return commentService.list();
    }

    @PostMapping
    public void addComment(
            @PathVariable long boardId,
            @RequestBody CommentWriteRequestDto dto
    ){
        commentService.addComment(boardId,dto);
    }

    @DeleteMapping
    public void deleteComment(
            @PathVariable long boardId,
            @RequestBody CommentDeleteRequestDto dto
    ){
        commentService.deleteComment(boardId, dto);
    }

    @PutMapping
    public void updateComment(
            @PathVariable long boardId,
            @RequestBody CommentUpdateRequestDto dto
            ){
        commentService.updateComment(boardId,dto);
    }

}
