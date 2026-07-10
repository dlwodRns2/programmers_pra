package org.example.basicboard;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//* Restful API
//자원을 URI로 표현하고, HTTP ㅔ서드로 그 자원에 대한 행위를 표현하는 API 설계 원칙
//controller의 매핑을 아래 규칙에 맞게 설계하면 RestFul하다고 할 수 있음

//* 핵심원칙
//1. 자원 중심 URI : URI는 명사(자원)로, 동사는 쓰지 않는다(예: /boards(O), /getBoards(X))
//2. HTTP 메서드로 행위 구분
//- GET : 조회
//- POST
//- PUT
//- PATCH
//- DELETE

//* 영속성(Persistence) - 관계 : ORM(개념) > JPA(표준)  > Hibernates(구현체)
//영속성이란?
//- 프로그램이 종료해도 데이터가 사라지지 않게 영구히 보존하는 성질
//- 자바 객체는 메모리에만 있어, 프로그램이 꺼지면 사라짐(휘발성)
//- 이를 DB같은 영구 저장소에 저장해 다시 꺼내올 수 있게 만드는 것이 영속성.
//- "객체 <-> DB" 변환을 담당하는 계층을 영속성 계층이라 하며, domain.repository가 그 역할.
//- 관련 용어
//- 영속성 : 객체를 DB에 저장하는 행위
//- 영속성 컨텍스트 : JPA가 엔티티를 관리하는 1차 캐시공간. 엔티티의 상태 변화를 추적해 자동을 SQL을 실행.

//ORM(Object Relational Mapping, 객체-관계 맵핑)
//자바의 객체와 관계형 DB의 테이블을 서로 연결(매핑)해주는 기술.
//자바 객체(Entity) <-> DB 테이블/행을 자동으로 매핑하고, 우리가 짠 코드 대신 SQL을 만들어 실행
@SpringBootApplication
public class BasicBoardApplication {
    public static void main(String[] args) {
        SpringApplication.run(BasicBoardApplication.class, args);
    }

}
