package org.example;

/*
    음료 인터페이스
    추상 클래스와 달리 인터페이스는 필드나 생성자를 가질 수 없다.
    "무엇을 할 수 있는지(메서드 목록)" 만 약속한다.
 */
public interface M_drink {
    String getName();
    int getPrice();
    void dispense();
}
