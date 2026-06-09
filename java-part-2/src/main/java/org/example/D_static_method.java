package org.example;

//* 정적 메서드
//클래스 메서드는 자바에서 static 키워드로 선언된 메서드를 의미하며, 정적 메서드라고도 불린다
//따라서 객체를 생성하지 않고도 클래스 이름을 통해 직접 호출이 가능
//클래스 메서드도 클래스 변수처럼, 객체를 생성하지 않고도 '클래스이름.메서드명'과 같은 식으로 호출이 가능

//* 주요 특징
//1. 인스턴스 필요 없음 : 정적 메서드는 클래스 이름으로 직접 호출이 가능. 객체를 생성할 필요가 없다.
//2. 정적 변수와 상수만 접근 가능 : 정적 메서드는 클래스의 다른 정적멤버(정적 변수 or 정적 메서드)하고만 상호작용이 가능.
//=> 인스턴스 변수, 인스턴스 메서드(new 키워드)에는 접근할 수 없다.
//3. 유틸리티 메서드로 자주 사용 : 자주 사용되거나 공통적인 작업을 수행하는 유틸리티 메서드는 일반적으로 정적 메서드로 구현.
//예) Math 클래스의 메서드 : 인스턴스와 관련이 없는 계산을 수행하는 경우가 많다.

//* 결론
//- 정적 메서드는 클래스에 속하는 메서드로(객체X), 특정 인스턴스와 관계없이 호출할 수 있다.
//- 주로 유틸리티 성격의 메서드나, 클래스 자체의 특성과 관련된 기능을 제공하는 메서드를 정의할 때 사용한다.
//- 인스턴스 변수를 사용하지 않고, 클래스 차원에서 공통으로 사용할 수 있는 기능을 제공할 때 유용하다.
class MathOperation{
    static final double PI = 3.14159;

    static double add(double a, double b){
        return a+b;
    }
    static double calculateArea(double radius){
        return PI * radius * radius;
    }
}
public class D_static_method {
    static void main(String[] args) {
        double added = MathOperation.add(10,20);
        double v = MathOperation.calculateArea(5);

        System.out.println(added);
        System.out.println(v);
    }
}
