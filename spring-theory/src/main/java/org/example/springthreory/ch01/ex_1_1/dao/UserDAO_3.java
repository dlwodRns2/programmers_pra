package org.example.springthreory.ch01.ex_1_1.dao;

import org.example.springthreory.ch01.ex_1_1.domain.User;

import java.sql.*;

//* 문제점
//요구사항1 : N사, D사가 UserDAO 구매를 희망 -> N사와 D사가 각기 다른 종류의 DB를 사용하고 싶다
//요구사항2 : UserDAO를 구매한 후에도 DB 커넥션을 가져오는 방법을 변경할 수 있다

//-> 고객에게 미리 컴파일된 클래스 바이너리 파일만 제공하고 싶은데(=외부에 코드 누출 X)
//-> 이런 것은 userDAO 소스코드를 N,D사에 제공해주지 않고도
//고객 스스로 원하는 DB커넥션 생성 방식을 적용해가면서 UserDAO를 사용하게 할 수 있을까?

//해결방법 : 상속을 통한 확쟝(각 회사가 자신이 원하는 종류의 DB 커넥션을 가져올 수 있게끔)
//클래스 계층 구조를 통해 두 개의 관심이 독립적으로 분리되어 변경 작업은 한층 용이해짐
//새로운 DB 연결 방법을 적용해야 할 때는 UserDAO를 상속을 통해 확장해주기만 하면됨.

//이렇게 슈퍼클래스에 기본적인 로직의 흐름을 만들고(add(),get())
//그 기능의 일부를 추상 메서드나 오버라이딩이 가능한 메서드 등으로 만든 뒤,
//서브클래스에서 이런 메서드를 필요에 맞게 구현해서 사용하도록 하는 방법을 "템플릿 메서드 패턴" 이라 함.
//이렇게 서브클래스에서 구체적인 오브젝트 생성 방법을 결정하게 하는 것을 "팩토리 메서드 패턴" 이라 함.
//getConnection() 메서드에서 생성하는 Connection 오브젝트의 구현 클래스를 제각각이지만
//UserDAO는 Connection 인터페이스 타입의 오브젝트라는 것 외에는 관심을 두지 않는다.

// * 디자인 패턴
// 소프트웨어 설계 시 특정 상황에서 자주 만나는 문제를 해결하기 위해 사용할 수 있는 재사용 가능한 솔루션을 말한다.

// * 템플릿 메서드 패턴
// 변하지 않는 기능은 슈퍼클래스에 만들어두고 자주 변경되며 확장할 기능은 서브클래스에서 만들도록 한다.
// 슈퍼클래스에서 디폴트 기능을 정의해두거나 비워뒀다가
// 서브클래스에서 선택적으로 오버라이드할 수 있도록 만들어둔 메서드를 훅(hook) 메서드라고 한다.

public abstract class UserDAO_3 {
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

    //UserDAO의 소스코드를 제공하면, getConnection() 메서드를 원하는 방식으로 확장(상속)한 후,
    //UserDAO의 기능과 함께 사용할 수 있음
    //기존에는 같은 클래스에 다른 메서드로 분리했던 DB 커넥션 연결이라는 관심을
    //상속을 통해 서브클래스로 분리해버리는 것.
    public abstract Connection getConnection() throws ClassNotFoundException, SQLException;
}
