package org.example.basicboard.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.Response;
import org.example.basicboard.domain.entity.Board;
import org.example.basicboard.domain.entity.Comment;
import org.example.basicboard.domain.repository.BoardRepository;
import org.example.basicboard.domain.repository.CommentRepository;
import org.example.basicboard.dto.CommentDeleteRequestDto;
import org.example.basicboard.dto.CommentListResponseDto;
import org.example.basicboard.dto.CommentUpdateRequestDto;
import org.example.basicboard.dto.CommentWriteRequestDto;
import org.example.basicboard.exception.BoardNotFountException;
import org.example.basicboard.exception.CommentNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentService {
    private final BoardRepository boardRepository;
    private final CommentRepository commentRepository;

    @Transactional
    public void addComment(Long boardId, CommentWriteRequestDto dto){
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new BoardNotFountException("게시글을 찾을 수 없습니다. id=" + boardId));

        Comment comment = Comment.builder()
                .content(dto.getContent())
                .userId(dto.getUserId())
                .board(board)
                .created(LocalDateTime.now())
                .board(board)
                .build();

        commentRepository.save(comment);
        log.info("댓글 등록 : commentId = {}, boardId = {}, userId = {}",comment.getId(),boardId,dto.getUserId());
    }

    @Transactional
    public void deleteComment(Long boardId, CommentDeleteRequestDto dto){
        //1. 해당하는 Comment를 찾아서
        if(!boardRepository.existsById(boardId)){
            throw new BoardNotFountException("게시글을 찾을 수 없습니다. id = "+boardId);
        }
        Comment comment = commentRepository.findById(dto.getId())
                .orElseThrow(()->new CommentNotFoundException("댓글을 찾을 수 없습니다."));

        //해당 board에 달린 댓글이 아닌 경우
        if(!comment.getBoard().getId().equals(boardId)){
            throw new CommentNotFoundException("해당 게시글에 속한 댓글이 아닙니다. id = "+dto.getId());
        }

        //2. 삭제
        commentRepository.delete(comment);

        //Jpa의 deleteById(id)는 내부적으로 findById(id).ifPresent(this::delete)로 구현
        //위에서 이미 조회(findById) +  조회기능이 한 번 더 추가됨(deleteById) => delete로 변경해서 불필요한 조회를 줄일 수 있음
        //commentRepository.deleteById(dto.getId());
    }

    @Transactional
    public void updateComment(Long boardId, CommentUpdateRequestDto dto){
        //1. 해당하는 Comment를 찾아서
        if(!boardRepository.existsById(boardId)){
            throw new BoardNotFountException("게시글을 찾을 수 없습니다. id = "+boardId);
        }
        Comment comment = commentRepository.findById(dto.getId())
                .orElseThrow(() -> new CommentNotFoundException("댓글을 찾을 수 없습니다. id = " + dto.getId()));

        //해당 board에 달린 댓글이 아닌 경우
        if(!comment.getBoard().getId().equals(boardId)){
            throw new CommentNotFoundException("해당 게시글에 속한 댓글이 아닙니다. id = "+dto.getId());
        }

        comment.update(dto.getContent());

    }
    public ResponseEntity<List<CommentListResponseDto>> list(){
        return ResponseEntity.status(HttpStatus.OK)
                .body(commentRepository.findAll().stream()
                        .map(CommentListResponseDto::from)
                        .toList()
                );
    }

}
