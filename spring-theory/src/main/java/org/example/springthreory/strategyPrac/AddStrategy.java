package org.example.springthreory.strategyPrac;

public class AddStrategy implements StatementStrategy{
    private final User user;

    public AddStrategy(User user) {
        this.user = user;
    }

    @Override
    public void run(Database db) {
        db.getUsers().add(user);
        System.out.println("  [전략-별도클래스] 추가: " + user.getName());
    }
}
