package org.example;

/*
    * 모든 음료의 공통 클래스
    * 이름, 가격
    * 음료가 나올 때의 동작 -> dispense() : 추상 메서드
 */
abstract public class L_drink {
    protected String name;
    protected int price;

    public L_drink(String name, int price){
        this.name=name;
        this.price = price;
    }

    public int getPrice() {
        return price;
    }

    public String getName() {
        return name;
    }

    //음료가 나올 때의 동작
    public abstract void dispense();
}
