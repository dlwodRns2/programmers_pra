package org.example.boardservice.domain.repository;

import org.example.boardservice.domain.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    void deleteByBoardId(Long id);

    // 사용작 쓴 댓글 전부
    long deleteByUserId(String userId);

    // 그 사용자의 "글에 달린" 댓글 전부
    long deleteByBoardUserId(String userId);

}