package org.example.boardprac.controller;

import lombok.RequiredArgsConstructor;
import org.example.boardprac.domain.entity.Board;
import org.example.boardprac.dto.BoardDetailResponseDto;
import org.example.boardprac.dto.BoardListResponseDto;
import org.example.boardprac.service.BoardService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/boards")
public class BoardApiController {
    private final BoardService boardService;

    @GetMapping
    public BoardListResponseDto getBoardList(@RequestParam(defaultValue = "1") int page,
                                             @RequestParam(defaultValue = "10") int size){
        List<Board> boards = boardService.getBoardList(page,size);
        int totalBoards = boardService.getTotalBoards();

        int totalPages = (int) Math.ceil((double) totalBoards /size);
        boolean last = totalPages<=page;

        return BoardListResponseDto.builder()
                .boards(boards)
                .last(last)
                .totalPages(totalPages)
                .build();
    }

    @GetMapping("/{id}")
    public BoardDetailResponseDto getBoardDetail(@PathVariable Long id){
        Board board = boardService.getBoardDetail(id);
        return BoardDetailResponseDto.builder()
                .title(board.getTitle())
                .content(board.getContent())
                .userId(board.getUserId())
                .created(board.getCreated())
                .filePath(board.getFilePath())
                .build();
    }
}
