package org.example.webservice.controller;

import lombok.RequiredArgsConstructor;
import org.example.webservice.dto.BoardPageResponseDto;
import org.example.webservice.dto.BoardSearchRequestDto;
import org.example.webservice.service.BoardService;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/boards")
public class BoardApiController {
    private final BoardService boardService;

    @GetMapping("/search")
    public BoardPageResponseDto searchBoards(
            @RequestHeader(value= HttpHeaders.AUTHORIZATION, required = false)String authorization,
            @ModelAttribute BoardSearchRequestDto boardSearchRequestDto,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10")int size
            ){
        return boardService.searchBoard(authorization, boardSearchRequestDto, page, size);
    }
}
