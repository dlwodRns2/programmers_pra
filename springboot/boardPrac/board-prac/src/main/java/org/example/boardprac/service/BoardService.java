package org.example.boardprac.service;

import lombok.RequiredArgsConstructor;
import org.example.boardprac.domain.entity.Board;
import org.example.boardprac.domain.repository.BoardRepository;
import org.example.boardprac.domain.repository.MemberRepository;
import org.example.boardprac.dto.BoardDeleteRequestDto;
import org.example.boardprac.dto.BoardUpdateRequestDto;
import org.example.boardprac.dto.BoardWriteRequestDto;
import org.example.boardprac.exception.BoardNotFountException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;
    private final FileService fileService;

    // 최신 글이 위로 오도록 id 내림차순. 화면 page(1~)를 Pageable(0~)로 바꾸려 page - 1
    public List<Board> getBoardList(int page,int size){
        Pageable pageable = PageRequest.of(page-1, size, Sort.by("id").descending());
        return boardRepository.findAll(pageable).getContent();

    }
    public int getTotalBoards(){
        return Math.toIntExact(boardRepository.count());
    }

    public Board getBoardDetail(Long id){
        return boardRepository.findById(id)
                .orElseThrow(()->new BoardNotFountException("게시글을 찾을 수 없습니다. id=" + id));
    }

    @Transactional
    public void saveArticle(BoardWriteRequestDto dto){
        String filePath = fileService.storeFile(dto.getFile());

        Board board  = Board.builder()
                .userId(dto.getUserId())
                .title(dto.getTitle())
                .content(dto.getContent())
                .filePath(filePath)
                .created(LocalDateTime.now())
                .build();

        boardRepository.save(board);
    }

    @Transactional
    public void deleteArticle(Long id, BoardDeleteRequestDto dto){
        if(!boardRepository.existsById(id)){
            throw new BoardNotFountException("게시글을 찾을 수 없습니다. id="+ id);
        }
        boardRepository.deleteById(id);
        fileService.deleteFile(dto.getFilePath());
    }

    @Transactional
    public void updateArticle(Long id, BoardUpdateRequestDto dto){
        Board board = boardRepository.findById(id)
                .orElseThrow(()->new BoardNotFountException("게시글을 찾을 수 없습니다. id="+id));

        String filePath = board.getFilePath();
        if(dto.isFileFlag()){
            fileService.deleteFile(board.getFilePath());
            filePath= fileService.storeFile(dto.getFile());
        }

        board.update(dto.getTitle(),dto.getContent(),filePath);
    }
}
