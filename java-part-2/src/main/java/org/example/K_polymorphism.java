package org.example;

//* 다형성
//동일한 인터페이스나 부모 클래스를 공유하는 여러 객체들이 각자 다른 방식으로 동작하도록 할 수 있는 기능을 의미
//다형성은 하나의 인터페이스로 여러 형태의 객체를 처리할 수 있다는 의미를 가지고 있다.
//이를 통해 코드의 유연성과 확장성을 크게 향상시킬 수 있다.
class K_Animal{
    public void sound(){
        System.out.println("Animal sound");
    }
}
class K_Dog extends K_Animal{
    @Override
    public void sound(){
        System.out.println("Dog sound");
    }
    public void fetch(){
        System.out.println("Dog fetches a ball");
    }
}
class K_Cat extends K_Animal{
    @Override
    public void sound(){
        System.out.println("Cat sound");
    }
}
public class K_polymorphism {
    static void main(String[] args) {
        K_Animal myAnimal = new K_Animal();
        K_Animal myDog = new K_Dog();
        K_Animal myCat = new K_Cat();

        myAnimal.sound();
        myDog.sound();
        myCat.sound();

        //다운캐스팅을 통해 다시 Dog 타입으로 변환
        //K_Dog mydog2 = (K_Dog) myAnimal //다운캐스팅(X)
        K_Dog mydog2 = (K_Dog) myDog; //실질적인 인스턴스가 dog이기 때문에 다운캐스팅 가능

    }
}
