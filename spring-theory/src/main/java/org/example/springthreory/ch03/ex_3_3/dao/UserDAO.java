package org.example.springthreory.ch03.ex_3_3.dao;

import org.example.springthreory.ch03.ex_3_3.domain.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

//* 전략 패턴의 적용
//- 컨텍스트
//변하지 않는 부분(jdbc 커넥션/실행/자원관리(try-with-resource)등의 공통 흐름)
//- 전략
//변하는 부분 : 어떤 preparedStatement를 만들지 -> 인터페이스로 추상화

// 컨텍스트 : 변하지 않는 jdbc 작업의 공통 흐름
// 컨텍스트는 '인터페이스(StatementStrategy)에만' 의존하고, 실제 전략은 런타임에 주입받는다.
// 그래서 새 기능을 추가해도 컨텍스트 코드는 닫혀 있고(수정X) 전략만 새로 만들면 된다(확장O) = OCP.
public class UserDAO {
    private SimpleConnectionMaker simpleConnectionMaker;

    public UserDAO(SimpleConnectionMaker simpleConnectionMaker) {
        this.simpleConnectionMaker = simpleConnectionMaker;
    }

//  - 커넥션을 얻고, 전달받은 '전략'에게 statement 생성을 맡기고, 실행하고, 자원을 정리한다.
//  - 어떤 SQL을 실행할지는 전혀 모른다. 그건 strategy가 결정한다(인터페이스에만 의존).
    public void jdbcContextWithStatementStrategy(StatementStrategy statementStrategy) throws SQLException, ClassNotFoundException {
        try(
                Connection conn = simpleConnectionMaker.makeNewConnection();
                PreparedStatement pstmt = statementStrategy.makeStatement(conn);
                ){
            pstmt.executeUpdate();
        }
    }
    public void add(User user) throws ClassNotFoundException, SQLException {

        //메서드마다 addUserDAO와 같이 새로운 클래스를 만들어야하는 문제점을
        //로컬 클래스를 통한 해결
        class UserDAOAdd implements StatementStrategy {
            private final User user;

            public UserDAOAdd(User user) {
                this.user = user;
            }
            @Override
            public PreparedStatement makeStatement(Connection conn) throws SQLException {
                PreparedStatement pstmt = conn.prepareStatement("insert into users (id,name,password) values (?,?,?)");

                pstmt.setString(1,user.getId());
                pstmt.setString(2,user.getName());
                pstmt.setString(3,user.getPassword());

                return pstmt;
            }
        }
        jdbcContextWithStatementStrategy(new UserDAOAdd(user));
    }

    public void deleteAll() throws SQLException, ClassNotFoundException {
        //로컬 클래스를 통한 해결
        class UserDAODeleteAll implements StatementStrategy {
            @Override
            public PreparedStatement makeStatement(Connection conn) throws SQLException {
                return conn.prepareStatement("delete from users");
            }
        }

        jdbcContextWithStatementStrategy(new UserDAODeleteAll());
    }

}
