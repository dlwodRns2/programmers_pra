package org.example.boardprac.domain.repository;

import org.example.boardprac.domain.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<Board,Long> {
}
