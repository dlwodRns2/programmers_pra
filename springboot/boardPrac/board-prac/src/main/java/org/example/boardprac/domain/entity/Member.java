package org.example.boardprac.domain.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name="member")
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length=50)
    private String userId;

    @Column(nullable = false, length=50)
    private String password;

    @Column(nullable = false, length=30)
    private String userName;
}
