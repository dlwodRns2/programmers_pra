package org.example.boardservice.controller;

import lombok.RequiredArgsConstructor;
import org.example.boardservice.dto.CommentWriteRequestDto;
import org.example.boardservice.service.CommentService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/boards/{boardId}/comments")
public class CommentApiController {

    private final CommentService commentService;

    @PostMapping
    public void addComment(
            @PathVariable Long boardId,
            @RequestBody CommentWriteRequestDto dto
    ) {
        commentService.addComment(boardId, dto);
    }

}