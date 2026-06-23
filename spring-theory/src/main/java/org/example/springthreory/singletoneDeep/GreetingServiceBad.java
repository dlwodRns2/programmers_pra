package org.example.springthreory.singletoneDeep;

public class GreetingServiceBad {
    private static final GreetingServiceBad instance = new GreetingServiceBad();

    private String name;
    private GreetingServiceBad(){}

    public static GreetingServiceBad getInstance() {
        return instance;
    }
    public String greet(String reqName){
        this.name=reqName;
        try {
            Thread.sleep(5);
        } catch (InterruptedException e) {
            //throw new RuntimeException(e);
        }
        return this.name;
    }
}
