package org.example.springthreory.ch01.ex_1_2.dao;

import org.example.springthreory.ch01.ex_1_1.domain.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

//* 문제점
//1. 단지 커넥션 객체를 가져오는 방법을 분리하게 위해 상속구조를 만들어버리면,
//후에 다른 목적으로 UserDAO에 상속을 적용하기 힘들다(자바는 다중상속이 불가능)
//2. 상속을 통한 상하위 클래스의 관계는 생각보다 밀접하다는 점이다.
//상속관계는 두 가지 다른 관심사에 대해 긴밀한 결합을 허용한다.
//서브클래스는 슈퍼클래스의 기능을 직접 사용 가능
//=> 슈퍼클래스 내부의 변경사항에 대해서 모든 서브클래스를 함께 수정하거나 다시 개발해야함
//3. 확장된 기능인 DB 커넥션을 생성하는 코드를 다른 DAO 클래스에 적용할 수 없다.

//해결방법 : "클래스의 분리"
//DB 커넥션과 관련된 부분을 서브클래스가 아닌 아예 별도의 클래스에 담는다.

public abstract class UserDAO {
    public SimpleConnectionMaker simpleConnectionMaker;
    public UserDAO(){
        simpleConnectionMaker=new SimpleConnectionMaker();
    }
    public void add(User user) throws ClassNotFoundException, SQLException {

        String query = "insert into users (id,name,password) values (?,?,?)";
        try(
                Connection conn = simpleConnectionMaker.makeNewConnection();
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
                Connection conn = simpleConnectionMaker.makeNewConnection();
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
