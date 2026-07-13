package org.example.basicboard.service;

import lombok.RequiredArgsConstructor;
import org.example.basicboard.domain.entity.Board;
import org.example.basicboard.domain.repository.BoardRepository;
import org.example.basicboard.dto.*;
import org.example.basicboard.exception.BoardNotFountException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;
    private final FileService fileService;

    public List<Board> getBoardList(int page, int size){
        Pageable pageable = PageRequest.of(page-1, size, Sort.by("id").descending());
        // * findAll(pageable).getContent()의 getContent()란?
        // findAll(pageable)의 반환 타입은 Page<Board>다
        // Page가 제공하는 것들
        // - getContent() -> List<Board> : "이번 페이지의 게시글 목록"
        // - getTotalElements() -> long : 전체 게시글 수
        // - getTotalPages() -> int : 전체 페이지 수
        // - isLast() -> boolean : 마지막 페이지 여부
        // 주의 : getContent()의 'content'는 Board 엔티티의 content가 아니다.

        return boardRepository.findAll(pageable).getContent();
    }
    public int getTotalBoards(){
        return (int) boardRepository.count();
    }

    @Transactional
    public void saveBoard(BoardWriteRequestDto dto){
        String filePath = fileService.storeFile(dto.getFile());

        boardRepository.save(Board.builder()
                        .userId(dto.getUserId())
                        .title(dto.getTitle())
                        .content(dto.getContent())
                        .filePath(filePath)
                        .created(LocalDateTime.now())
                        .build());
    }
    public Board getBoardDetail(Long id){
        return boardRepository.findById(id)
                .orElseThrow(()->new BoardNotFountException("[BOARD] 게시글을 찾을 수 없습니다."));
    }

    @Transactional
    public void updateBoard(Long id, BoardUpdateRequestDto dto){
        Board board = boardRepository.findById(id)
                .orElseThrow(()-> new BoardNotFountException("[Board] 수정할 게시글을 찾을 수 없습니다."));

        String filePath = board.getFilePath();

        //파일 변경이 있었을 경우
        if(dto.isFileFlag()){
            fileService.deleteFile(filePath); //기존 파일 삭제
            filePath = fileService.storeFile(dto.getFile()); //새 파일 저장
        }

        board.update(dto.getTitle(),dto.getContent(),filePath);
    }
    @Transactional
    public void deleteBoard(Long id, BoardDeleteRequestDto dto){
        if(!boardRepository.existsById(id)){
            throw new BoardNotFountException("[BOARD] 삭제할 게시글을 찾을 수 없습니다.");
        }
        boardRepository.deleteById(id);
        fileService.deleteFile(dto.getFilePath());
    }

    public Page<BoardListItemResponseDto> searchBoards(BoardSearchRequestDto dto, Pageable pageable){
        return boardRepository.searchBoards(dto,pageable);

    }
}
