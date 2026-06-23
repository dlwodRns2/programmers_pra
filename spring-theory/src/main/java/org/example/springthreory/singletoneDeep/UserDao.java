package org.example.springthreory.singletoneDeep;


interface ConnectionMaker{
    String makeConnection();
}
class SimpleConnectionMaker implements ConnectionMaker{
    private static final SimpleConnectionMaker instance = new SimpleConnectionMaker();
    private SimpleConnectionMaker(){}

    public static SimpleConnectionMaker getInstance(){
        return instance;
    }
    @Override
    public String makeConnection() {
        return "DB연결";
    }
}
public class UserDao {
    private static final UserDao instance = new UserDao();
    private ConnectionMaker connectionMaker = SimpleConnectionMaker.getInstance();

    private UserDao(){}
    public static UserDao getInstance(){
        return instance;
    }
    public String findUser(String userId){
        return userId+" 조회 ["+connectionMaker.makeConnection()+"]";
    }
}
