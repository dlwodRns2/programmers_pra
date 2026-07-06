package org.example.basicboard.domain.repository;

import org.example.basicboard.domain.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

//이 인터페이스에는 구현 클래스가 없다
//우리는 interface만 선언하고 구현체(class)는 만들지 않는다
//애플리케이션이 뜰 때 Spring Data Jpa가 이 인터페이스의 구현체를 프록시로 자동 생성해서 빈으로 등록
//그래서 서비스 memberRepositry를 주입받아 바로 사용이 가능함

//* JPARepository<Member, Long>의 두 타입 파라미터
//Member : 이 레포지토리가 다루는 엔티티 타입
//Long : 그 엔티티의 기본 타입(PK의 타입)
//save(엔티티) : 저장/수정
//findById(id) : 기본키로 1건 조회 -> Optional 반환
//findAll() : 전체 조회
//delete(엔티티) : 삭제

public interface MemberRepository extends JpaRepository<Member, Long> {
    boolean existsByUserId(String id);
    Optional<Member> findByUserId(String userId);
}
