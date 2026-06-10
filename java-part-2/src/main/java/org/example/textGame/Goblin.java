package org.example.textGame;

public class Goblin extends Monster{
    public Goblin(String name, int hp, int power, int defense) {
        super(name, hp, power, defense);
    }

    public Goblin(String name) {
        super(name);
    }
    @Override
    public void attack(Character target){
        System.out.println(name+"의 할퀴기!");
        super.attack(target);
    }
}
