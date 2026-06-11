package org.example;

public class M_cider implements M_drink {
    private String name;
    private int price;

    public M_cider(){
        this.name="사이다";
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
        System.out.println("사이다 나왔습니다.");
    }
}
