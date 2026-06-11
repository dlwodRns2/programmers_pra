package org.example;

public class M_water implements M_drink{
    private String name;
    private int price;

    public M_water(){
        this.name="생수";
        this.price=200;
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
        System.out.println("물 나왔습니다.");
    }
}
