package org.example;

// * 메서드
//메서드는 특정 작업을 수행하는 일련의 문장들을 하나로 묶은 것

//* 메서드를 사용하는 이유
//1. 높은 재사용성
//2. 중복된 코드의 제거
//3. 프로그램의 구조화

//** 메서드의 선언과 구현
//메서드는 크게 '선언부', '구현부'로 구성
//* 메서드 선언부 : 메서드 선언부는 메서드의 이름 / 매개변수 선언 / 반환 타입으로 구성

/*
    public int add(int a,int b) => 선언부
    {
        => 구현부
        return a+b;
    }
 */

public class B_method {
    static void main(String[] args) {
        B_calculator calculator = new B_calculator();
        System.out.println(calculator.add(10,20));
        System.out.println(calculator.sub(10,20));
        System.out.println(calculator.mul(10,20));
        System.out.println(calculator.div(10,20));
    }
}
