package org.example.authservice.domain.repository;

import org.example.authservice.config.oauth2.AuthProvider;
import org.example.authservice.domain.entity.User;
import org.example.authservice.domain.entity.UserStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUserId(String userId);

    // 글 하나당 한 번씩 부르면 N+1이 되므로 in 절로 묶는다.
    List<User> findByUserIdIn(List<String> userIds);

    boolean existsByUserId(String userId);

    Optional<User> findByProviderIdAndProvider(String providerId, AuthProvider authProvider);

    List<User> findByStatusAndStatusUpdatedAtBefore(UserStatus status, LocalDateTime before);
}
