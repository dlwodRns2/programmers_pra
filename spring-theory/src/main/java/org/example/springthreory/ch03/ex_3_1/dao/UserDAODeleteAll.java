package org.example.springthreory.ch03.ex_3_1.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UserDAODeleteAll extends UserDAO {
    public UserDAODeleteAll(SimpleConnectionMaker simpleConnectionMaker) {
        super(simpleConnectionMaker);
    }

    @Override
    protected PreparedStatement makeStatement(Connection conn) throws SQLException {
        return conn.prepareStatement("delete from users");
    }
}
