package org.example.springthreory.ex_3_4.dao;


import com.mysql.cj.x.protobuf.MysqlxPrepare;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class JdbcContext {
    //느슨한 결합 : 인터페이스 의존
    private SimpleConnectionMaker simpleConnectionMaker;

    public JdbcContext(SimpleConnectionMaker simpleConnectionMaker){
        this.simpleConnectionMaker=simpleConnectionMaker;
    }

    public void workWithStatementStrategy(StatementStrategy statementStrategy) throws SQLException, ClassNotFoundException {
        try(
                Connection conn = simpleConnectionMaker.makeNewConnection();
                PreparedStatement pstmt = statementStrategy.makeStatement(conn);  // 변하는 부분을 전략에 위임
                ){
            pstmt.executeUpdate();
        }
    }
}
