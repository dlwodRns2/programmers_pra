package org.example.textGame;

public class Hero extends Character{
    public Hero(String name) {
        super(name);
    }

    public Hero(String name, int hp, int power,int defense) {
        super(name, hp, power,defense);
    }
    //체력 회복 스킬
    public void heal(){
        this.hp+=10;
        if(this.hp>=100){
            hp=100;
        }
    }
}
