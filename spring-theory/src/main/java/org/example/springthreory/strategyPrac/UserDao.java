package org.example.springthreory.strategyPrac;

public class UserDao {
    private Database db;
    public UserDao(Database db){
        this.db=db;
    }
    public void context(StatementStrategy strategy){
        db.open();
        strategy.run(db);
        db.close();
    }
    public void deleteAll(){
        //람다 버전
//        context(db->{
//           db.getUsers().clear();
//            System.out.println("  [전략-람다] 전체 삭제");
//        });

        //익명 클래스 버전
        StatementStrategy deleteAllStrategy = new StatementStrategy() {
            @Override
            public void run(Database db) {
                db.getUsers().clear();
                System.out.println("  [전략-익명] 전체 삭제");
            }
        };
        context(deleteAllStrategy);
    }
    public void add(User user){
        //람다 ver
        context(db -> {
            db.getUsers().add(user);
            System.out.println("  [전략-익명] 추가: " + user.getName());
        });

    //익명 클래스 ver
//        StatementStrategy addStrategy = new StatementStrategy() {
//            @Override
//            public void run(Database db) {
//                db.getUsers().add(user);
//                System.out.println("  [전략-익명] 추가: " + user.getName());
//            }
//        };
//        context(addStrategy);
    }
}
