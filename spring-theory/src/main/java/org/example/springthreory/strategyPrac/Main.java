package org.example.springthreory.strategyPrac;

public class Main {
    public static void main(String[] args) {
        Database db = new Database();
        UserDao userDao = new UserDao(db);
        StatementStrategy deleteAllStrategy = new DeleteAllStrategy();

        //1. 별도 클래스로 전략 구현
        System.out.println("== (별도 클래스) deleteAll ==");
        deleteAllStrategy.run(db);

        //2. 익명 클래스로 전략 구현
        System.out.println("== (익명) deleteAll ==");
        userDao.deleteAll();

        //3. 람다로 전략 구현
        System.out.println("== (람다) add(이) ==");
        userDao.add(new User("1","kim"));
        userDao.add(new User("2","lee"));

        System.out.println("\n현재 사용자 수: "+db.getUsers().size());
        for(User user: db.getUsers()){
            System.out.println("사용자: "+user.getName());
        }
    }
}
