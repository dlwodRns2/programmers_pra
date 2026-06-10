package org.example;

public class G_bird extends G_animal{
    String wing;

    public void fly(){
        System.out.println(kind + " is flying");
    }
    @Override
    public void walk() {
        System.out.println("사뿐사뿐");
    }
}
