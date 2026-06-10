package org.example;

//* 생성자(Constructor)의 주요 특징
//객체가 생성될 때 호출되는 특수한 메서드
//생성자는 객체를 초기화하는데 사용되며, 클래스 이름과 동일한 이름을 가져야 함
//생성자는 반환 타입이 없음

//* 생성자으 주요 특징
//1. 클래스 이름과 동일 : 생성자는 클래스 이름과 동일한 이름을 가져야 한다.
//2. 반환 타입 없음 : 생성자는 반환 타입이 없음
//3. 오버로딩 가능 : 생성자는 메서드와 마찬가지로 오버로딩이 가능하다. 즉 매개변수의 개수나 타입이 다른 여러 개의 생성자를 정의할 수 있다.
//4. 기본 생성자 : 클래스에 생성자를 명시하지 않으면, 컴파일러가 매개변수가 없는 기본 생성자를 자동으로 추가함
//5. 객체 초기화 : 생성자는 객체의 필드를 초기화하는 역할

//* 생성자의 종류
//1. 기본 생성자 : 매개변수X
//2. 매개변수가 있는 생성자 : 객체 생성 시,인자를 전달받아 필드를 초기화
//3. 복사 생성자 : 동일한 클래스의 다른 객체의 값을 복사하여 필드를 초기화
public class F_constructor {
    static void main(String[] args) {
        //기본 생성자를 사용하여 객체 생성 및 초기화
        F_person1 person1 = new F_person1();
        person1.display();

        //매개변수가 있는 생성자를 사용하여 객체 생성 및 초기화
        F_person2 person2 = new F_person2("Alice",30);
        person2.display();

        //기본 생성자 호출
        F_person3 person3 = new F_person3();
        person3.display();

        //이름만 초기화하는 생성자
        F_person3 person3_1 = new F_person3("Bob");
        person3_1.display();

        //이름,나이 초기화하는 생성자
        F_person3 person3_2 = new F_person3("David",20);
        person3_2.display();

        //원본 객체
        F_person4 person4 = new F_person4("Kelly",25);
        //복사 생성자를 사용하여 객체 복사
        F_person4 person4_1 = new F_person4(person4);
        person4_1.display();

        //생성자에서 다른 생성자 호출하기 -> this()
        //- 생성자의 이름으로 클래스 대신 this를 사용한다
        //- 한 생성자에서 다른 생성자를 호출할 때는 반드시 첫 줄에서만 호출이 가능하다.
        F_person5 person5 = new F_person5();
    }
}
