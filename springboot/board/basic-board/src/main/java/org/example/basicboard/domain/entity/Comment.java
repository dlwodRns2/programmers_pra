package org.example.basicboard.domain.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "comment")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    @Column(nullable = false, length=50)
    private String userId;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime created;

    //fetchType.Lazy :
    //fetchType.Eager : 음

    //* fetchType.Lazy를 "직접 명시" 해야 하는 이유
    //fetchType 기본적으로 어노테이션 마다 다르다.
    //@ManyToOne, OneToMany -> 기본 Eager(즉시 로딩)
    //@OneToMany, @ManyToMany -> 기본 Lazy(지연 로딩)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="board_id",nullable = false)
    private Board board;

    public void update(String content){
        this.content=content;
    }
}
