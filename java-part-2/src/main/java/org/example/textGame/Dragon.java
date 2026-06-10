package org.example.textGame;

public class Dragon extends Monster {
    public Dragon(String name, int hp, int power, int defense) {
        super(name, hp, power, defense);
    }

    public Dragon(String name) {
        super(name);
    }
    @Override
    public void attack(Character target){
        System.out.println(name+"의 브레스!");
        super.attack(target);
    }
}
