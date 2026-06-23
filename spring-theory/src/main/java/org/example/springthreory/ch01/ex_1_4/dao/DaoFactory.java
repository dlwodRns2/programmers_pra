package org.example.springthreory.ch01.ex_1_4.dao;

//오브젝트 팩토권
public class DaoFactory {
    public UserDAO userDAO(){
//        SimpleConnectionMaker connection = new DConnectionMaker();
//        UserDAO userDAO = new UserDAO(connection);

        return new UserDAO(connectionMaker());
    }
    public AccountDAO accountDAO(){
//        SimpleConnectionMaker connection = new DConnectionMaker();
//        AccountDAO accountDAO = new AccountDAO(connectionMaker());
        return new AccountDAO(connectionMaker());
    }
    public MessageDAO messageDAO(){
        return new MessageDAO(connectionMaker());
    }
    private SimpleConnectionMaker connectionMaker(){
        return new DConnectionMaker();
    }
}
