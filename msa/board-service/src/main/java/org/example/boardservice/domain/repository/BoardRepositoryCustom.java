package org.example.boardservice.domain.repository;

import org.example.boardservice.domain.entity.Board;
import org.example.boardservice.dto.BoardAuthorStatsResponseDto;
import org.example.boardservice.dto.BoardListItemResponseDto;
import org.example.boardservice.dto.BoardSearchRequestDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface BoardRepositoryCustom {

    Page<BoardListItemResponseDto> searchBoards(BoardSearchRequestDto condition, Pageable pageable);

    Optional<Board> findWithComments(Long id);

    List<BoardAuthorStatsResponseDto> countBoardsByAuthor(long minCount);
}
