package org.example;

public class M_fanta implements M_drink{
    private String name;
    private int price;

    public M_fanta(){
        this.name="환타";
        this.price=300;
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
        System.out.println("환타 나왔습니다.");
    }
}
