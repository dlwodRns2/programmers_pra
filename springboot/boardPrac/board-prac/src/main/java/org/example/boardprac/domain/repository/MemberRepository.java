package org.example.boardprac.domain.repository;

import org.example.boardprac.domain.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
    boolean existsByUserId(String userId);
}
