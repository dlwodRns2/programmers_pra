package org.example.oauth2.domain.repository;

import org.example.oauth2.config.oauth2.AuthProvider;
import org.example.oauth2.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByUserId(String userId);
    Optional<User> findByUserId(String userId);
    Optional<User> findByProviderAndProviderId(AuthProvider provider, String providerId);
}
