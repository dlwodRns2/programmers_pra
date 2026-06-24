package org.example.springthreory.ch03.ex_3_2.dao;

import org.example.springthreory.ch03.ex_3_2.domain.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UserDAOAdd implements StatementStrategy{
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
