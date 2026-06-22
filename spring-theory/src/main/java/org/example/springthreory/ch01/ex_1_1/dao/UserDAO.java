package org.example.springthreory.ch01.ex_1_1.dao;

//DAO(Data Access Object)
//DB를 사용해 데이터를 조회하거나 조작하는 기능을 전담하는 오브젝트

import org.example.springthreory.ch01.ex_1_1.domain.User;

import java.sql.*;

public class UserDAO {
    public void add(User user) throws ClassNotFoundException, SQLException {
        //JDBC 드라이버를 메모리에 로딩하는 코드
        //com.mysql.cj.jdbc.Driver는 MySql 드라이버 클래스의 전체 이름
        //Class.forName(..)은 해당 이름의 클래스를 찾아 메모리에 적재함
        // 이 클래스가 로딩될 때 내부적으로 DriverManager에 자기 자신을 자동 등록함
        // → 등록이 되어야 아래 DriverManager.getConnection(...)으로 DB에 연결할 수 있음
        // (참고: JDBC 4.0+ 부터는 자동 등록되어 생략 가능하지만, 동작 원리 이해를 위해 작성함)
        Class.forName("com.mysql.cj.jdbc.Driver");
        String query = "insert into users (id,name,password) values (?,?,?)";

        try(
                Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/springtheory","root","re00");
                PreparedStatement pstmt = conn.prepareStatement(query);
                ){
            pstmt.setString(1,user.getId());
            pstmt.setString(2,user.getName());
            pstmt.setString(3,user.getPassword());
            pstmt.executeUpdate();
        }

    }
    public User get(String id) throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        String query = "select * from user where id=?";

        try(
                Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/springtheory","root","re00");
                PreparedStatement pstmt = conn.prepareStatement(query);
                ){
            pstmt.setString(1,id);
            ResultSet resultSet = pstmt.executeQuery();

            resultSet.next();

            User user = new User();
            user.setId(resultSet.getString("id"));
            user.setName(resultSet.getString("name"));
            user.setPassword(resultSet.getString("password"));

            return user;
        }
    }
}
