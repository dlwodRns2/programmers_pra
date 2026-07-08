package org.example.boardprac.service;

import lombok.RequiredArgsConstructor;
import org.example.boardprac.domain.entity.Board;
import org.example.boardprac.domain.repository.BoardRepository;
import org.example.boardprac.domain.repository.MemberRepository;
import org.example.boardprac.exception.BoardNotFountException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;

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



}
