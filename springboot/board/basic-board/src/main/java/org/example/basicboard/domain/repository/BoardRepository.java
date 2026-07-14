package org.example.basicboard.domain.repository;

import org.example.basicboard.domain.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;

//* extends A,B : 클래스와 인터페이스의 규칙이 다르다
//- 클래스 : extends는 딱 하나만(다중 상속 금지)
//- 인터페이스 : extends를 여러 개 나열 가능

//* 왜 클래스만 금지인가? : "다이아몬드 문제" 때문
//클래스는 "상태(필드) + 구현(메서드)"를 물려준다
//=> 두 부모가 같은 필드/메서드를 다르게 가지면 자식은 어느 쪽을 따라야 하는 지 알 수 없음(모호성)
//=> 인터페이스는 기본적으로 구현 없는 "약속 목록"
public interface BoardRepository extends JpaRepository<Board,Long>, BoardRepositoryCustom {
}
