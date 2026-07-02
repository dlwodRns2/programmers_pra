package org.example.springthreory.ex_5.ex_5_4.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public interface StatementStrategy {
    PreparedStatement makeStatement(Connection conn) throws SQLException;
}
