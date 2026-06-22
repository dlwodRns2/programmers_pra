package org.example.springthreory.ch01.ex_1_1.dao;

import org.example.springthreory.ch01.ex_1_1.domain.User;

import java.sql.*;

//* UserDAO의 관심사항
//DB와 연결을 위한 커넥션을 어떻게 가져올 것인가?
//UserDAO1 -> UserDAO2( 메서드 추출, 리팩토링 )
public class UserDAO_2 {
    public void add(User user) throws ClassNotFoundException, SQLException {

        String query = "insert into users (id,name,password) values (?,?,?)";
        try(
                Connection conn = getConnection();
                PreparedStatement pstmt = conn.prepareStatement(query);
        ){
            pstmt.setString(1,user.getId());
            pstmt.setString(2,user.getName());
            pstmt.setString(3,user.getPassword());
            pstmt.executeUpdate();
        }

    }
    public User get(String id) throws ClassNotFoundException, SQLException {

        String query = "select * from user where id=?";
        try(
                Connection conn = getConnection();
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

    //중복 코드의 메서드 추출
    //리팩토링 : 기존의 코드를 외부의 동작 방식에는 변화없이 내부 구조를 변경하는 것
    private Connection getConnection() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/springtheory","root","re00");
        return conn;
    }
}
