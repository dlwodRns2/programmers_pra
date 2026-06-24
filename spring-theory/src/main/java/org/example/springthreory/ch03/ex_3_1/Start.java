package org.example.springthreory.ch03.ex_3_1;

import org.example.springthreory.ch03.ex_3_1.dao.DaoFactory;
import org.example.springthreory.ch03.ex_3_1.dao.UserDAO;
import org.example.springthreory.ch03.ex_3_1.domain.User;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.sql.SQLException;

//* 템플릿 : 코드 중 변하는 부분/변하지 않는 부분이 존재
//변하지 않는 고정 코드 부분을 하나의 틀로 사용하는 것을 의미
public class Start {
    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(DaoFactory.class);

        UserDAO userDAO = context.getBean("userDAO", UserDAO.class);
        User user = userDAO.get("test1");
    }
}
