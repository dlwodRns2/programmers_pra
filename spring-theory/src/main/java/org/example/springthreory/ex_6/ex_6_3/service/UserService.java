package org.example.springthreory.ex_6.ex_6_3.service;

import org.example.springthreory.ex_6.ex_6_3.domain.User;

import java.sql.SQLException;

//UserService를 인터페이스로 두고, 구현을 둘로 나눈다
//1. UserServiceImpl : 순수 비즈니스 로직만 책임
//2. UserServiceTx : 트랜잭션 경계만 책임
//클라이언트는 인터페이스에만 의존하므로 둘의 분리를 몰라도됨
public interface UserService {
    void add(User user) throws ClassNotFoundException, SQLException;
    void upgradeLevels();
}
