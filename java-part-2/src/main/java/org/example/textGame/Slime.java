package org.example.textGame;

public class Slime extends Monster{
    public Slime(String name, int hp, int power, int defense) {
        super(name, hp, power, defense);
    }

    public Slime(String name) {
        super(name);
    }
    @Override
    public void attack(Character target){
        System.out.println(name+"의 몸통박치기!");
        super.attack(target);
    }
}
