package org.example.springthreory.ch01.ex_1_2.dao;

import java.sql.Connection;
import java.sql.SQLException;

//인터페이스를 사용하는 userDAO 입장에서
//어떤 클래스로 만들어졌는지 상관없이 makeNewConnection을 호출하면
//Connection 타입의 오브젝트를 돌려줄 것이라고 기대할 수 있다.
public interface SimpleConnectionMaker_2 {
    Connection makeNewConnection()throws ClassNotFoundException, SQLException;

}
