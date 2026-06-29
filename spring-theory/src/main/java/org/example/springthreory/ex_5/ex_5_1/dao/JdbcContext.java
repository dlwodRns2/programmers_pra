package org.example.springthreory.ex_5.ex_5_1.dao;


import org.springframework.dao.EmptyResultDataAccessException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class JdbcContext {
    //느슨한 겷합 : 인터페이스 의존
    private SimpleConnectionMaker simpleConnectionMaker;

    public JdbcContext(SimpleConnectionMaker simpleConnectionMaker){
        this.simpleConnectionMaker=simpleConnectionMaker;
    }

    public void workWithStatementStrategy(StatementStrategy statementStrategy) throws SQLException, ClassNotFoundException {
        try(
                Connection conn = simpleConnectionMaker.makeNewConnection();
                PreparedStatement pstmt = statementStrategy.makeStatement(conn);
                ){
            pstmt.executeUpdate();
        }
    }

    //조회용 컨텍스트 : 여러 건을 조회해 리스트로 돌려준다
    // - 제네릭 타입 T는 사용하기 전에 반드시 어딘가에서 선언해야 한다.
    // 1) 클래스 레벨 선언
    // 2) 메서드 레벨 선언
    public <T> List<T> query(StatementStrategy strategy, RowMapper<T> rowMapper) throws SQLException, ClassNotFoundException {
        try(
                Connection conn = simpleConnectionMaker.makeNewConnection();
                PreparedStatement pstmt = strategy.makeStatement(conn);
                ResultSet rs = pstmt.executeQuery();
                ){
            ArrayList<T> results = new ArrayList<>();
            while(rs.next()){
                results.add(rowMapper.mapRow(rs));
            }
            return results;
        }
    }
    public <T> T queryForObject(StatementStrategy strategy, RowMapper<T> rowMapper) throws SQLException, ClassNotFoundException {
        List<T> results = query(strategy, rowMapper);

        if ( results.isEmpty() ) {
            throw new EmptyResultDataAccessException(1); // 기대한 1건이 없음
        }

        return results.get(0);
    }
}
