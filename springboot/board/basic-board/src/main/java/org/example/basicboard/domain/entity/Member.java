package org.example.basicboard.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

//회원 엔티티 - DB의 member 테이블과 매핑
@Entity
@Table(name="member")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //Auto Increment
    private Long id;

    @Column(nullable = false, length=50)
    private String userId;

    @Column(nullable = false, length=50)
    private String password;

    @Column(nullable = false, length=20)
    private String userName;
}
