package org.example;

public class F_person5 {
    String name;
    int age;

    public F_person5(){
        //아래의 매개변수 있는 생성자를 호출함
        this("John",25);
    }
    public F_person5(String name, int age){
        this.name= name;
        this.age=age;
    }
    public void display(){
        System.out.println("name: "+name+", age: "+age);
    }
}
