package org.example.springthreory.ex_3_4.dao;

import org.example.springthreory.ex_3_4.domain.User;

import javax.swing.plaf.nimbus.State;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UserDAO {
    //구체 클래스 의존 : 강한 결합 //반드시 필요(connectionMake)
    //필요에 따라서는 구체클래스를 의존해도 좋결
    private JdbcContext jdbcContext;

    public UserDAO(JdbcContext jdbcContext) {
        this.jdbcContext=jdbcContext;
    }

    public UserDAO(){}

    public void add(User user) throws ClassNotFoundException, SQLException {

        StatementStrategy strategy = new StatementStrategy() {
            @Override
            public PreparedStatement makeStatement(Connection conn) throws SQLException {
                PreparedStatement pstmt = conn.prepareStatement(
                        "insert into users (id,name,password) values (?,?,?)"
                );
                pstmt.setString(1, user.getId());
                pstmt.setString(2, user.getName());
                pstmt.setString(3, user.getPassword());

                return pstmt;
            }
        };
        jdbcContext.workWithStatementStrategy(strategy);
    }

    public void deleteAll() throws SQLException, ClassNotFoundException {
        StatementStrategy strategy = new StatementStrategy() {
            @Override
            public PreparedStatement makeStatement(Connection conn) throws SQLException {
                return conn.prepareStatement("delete from users");
            }
        };

       jdbcContext.workWithStatementStrategy(strategy);
    }

}
