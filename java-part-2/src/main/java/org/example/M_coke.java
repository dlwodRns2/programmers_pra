package org.example;

public class M_coke implements M_drink {
    private String name;
    private int price;

    public M_coke(){
        this.name="콜라";
        this.price=500;
    }
    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public int getPrice() {
        return this.price;
    }

    @Override
    public void dispense() {
        System.out.println("콜라 나왔습니다.");
    }
}
