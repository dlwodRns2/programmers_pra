package org.example.oauth2.domain.entity;

import jakarta.persistence.*;
import lombok.*;
import org.example.oauth2.config.oauth2.AuthProvider;

@Entity
@Table(name = "user")
@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 20)
    private String name;

    @Column(length = 50)
    private String email;

    @Column(length = 50)
    private String userId;

    @Column(length = 100)
    private String password;

    @Enumerated(EnumType.STRING)
    @Builder.Default
    private Role role = Role.ROLE_USER;

    @Enumerated(EnumType.STRING)
    @Column(length=20)
    @Builder.Default
    private AuthProvider provider = AuthProvider.LOCAL;

    @Column(length=100)
    private String providerId;



}
