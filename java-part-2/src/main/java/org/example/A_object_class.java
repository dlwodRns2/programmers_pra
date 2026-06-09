package org.example;

// * 객체지향 프로그래밍(Object Oriented Programming, OOP)
// 프로그램을 여러 개의 객체(Object)로 나누어 작성하는 빙법을 말한다.
// 객체지향 프로그래밍은 코드의 재사용성과 유지보수성을 높이고
// 복잡한 문제를 쉽게 해결할 수 있도록 하는 데 중점을 둔다.

// * class : 객체를 정의해 놓은 것
// 클래스 -> (인스턴스화) -> 인스턴스(객체)

class TV {
    //TV의 속성
    String color;
    boolean power;
    int volume;
    int channel;

    public void power(){
        power = !power;
        System.out.println("TV 전원을 "+(power?"켰다":"껐다"));
    }

    public void volumeUp(){
        volume++;
        System.out.println("볼륨을 올렸다");
    }
    public void volumeDown(){
        volume--;
        System.out.println("볼륨을 내렸다");
    }
    public void channelUp(){
        channel++;
        System.out.println("채널을 올렸다");
    }
    public void channelDown(){
        channel--;
        System.out.println("채널을 내렸다");
    }
}
public class A_object_class {
    static void main(String[] args) {
        TV tv = new TV();

        tv.channel=7;
        tv.color="black";
        tv.power = false;
        tv.volume =8;

        tv.power();
        tv.volumeDown();
        tv.volumeUp();
        tv.channelUp();
        tv.channelDown();
    }
}
