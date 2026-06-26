package org.example.springthreory.strategyPrac;

@FunctionalInterface
public interface StatementStrategy {
    void run(Database db);

}
