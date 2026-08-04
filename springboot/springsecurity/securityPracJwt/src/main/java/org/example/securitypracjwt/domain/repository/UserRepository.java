package org.example.securitypracjwt.domain.repository;

import org.example.securitypracjwt.config.oauth2.AuthProvider;
import org.example.securitypracjwt.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {
    boolean existsByUserId(String userId);
    Optional<User> findByUserId(String userId);
    Optional<User> findByProviderAndProviderId(AuthProvider provider, String providerId);
}
