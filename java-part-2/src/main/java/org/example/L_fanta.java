package org.example;

public class L_fanta extends L_drink{
    public L_fanta() {
        super("환타", 300);
    }

    @Override
    public void dispense() {
        System.out.println("환타 나왔습니다.");
    }
}
