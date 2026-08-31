package org.example.boardservice.domain.repository;

import org.example.boardservice.dto.BoardListItemResponseDto;
import org.example.boardservice.dto.BoardSearchRequestDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BoardRepositoryCustom {
    Page<BoardListItemResponseDto> searchBoards(BoardSearchRequestDto condition, Pageable pageable);
}
