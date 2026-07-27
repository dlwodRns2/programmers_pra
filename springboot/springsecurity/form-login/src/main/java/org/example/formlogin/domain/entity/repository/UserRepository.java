package org.example.formlogin.domain.entity.repository;

import org.example.formlogin.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {
    boolean existsByUserId(String userId);
}
