create database java_basic
CHARACTER set utf8mb4
collate utf8mb4_unicode_ci;

#데이터베이스 삭제
#drop database java_basic;

#데이터베이스 조회
show databases;
#해당 데이터베이스로 이동
use java_basic;

#데이터베이스 내의 테이블 조회
show
    tables;

#테이블 생성
CREATE TABLE IF NOT EXISTS member (
                                      id    INT          NOT NULL AUTO_INCREMENT,   -- PK, 자동 증가
                                      name  VARCHAR(50)  NOT NULL,                  -- 이름
    age   INT,                                    -- 나이
    phone VARCHAR(20),                            -- 전화번호
    PRIMARY KEY (id)
    );

# 테이블 스키마 조회
desc member;

select id,name,age,phone from member;
select * from member;

# 테이블에 데이터 삽입
insert into member (name,age,phone) values ('홍길동',20,'010-2860-9697');