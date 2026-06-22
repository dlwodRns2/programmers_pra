package org.example.springthreory.solidPrac;

import java.util.ArrayList;

class Bird{
    String name;
    public void eat(){
        System.out.println("식사");
    }

}
class FlyingBird extends Bird{
    public void fly(){
        System.out.println("비행");
    }
}
class Penguin extends Bird {
    public void swim(){
        System.out.println("수영");
    }
}
class Sparrow extends FlyingBird{

}
public class Lsp {
    public static void main(String[] args) {
        ArrayList<Bird> arr = new ArrayList<>();
        arr.add(new Sparrow());
        arr.add(new Penguin());

        for(Bird b : arr){
            b.eat();
        }
    }
}
