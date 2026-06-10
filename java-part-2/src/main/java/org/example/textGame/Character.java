package org.example.textGame;

public class Character {
//    private String name;
//    private int hp;
//    private int power;
//    private int defense;

    protected String name;
    protected int hp;
    protected int power;
    protected int defense; //방어력 추가

    //생성자 체이닝
    public Character(String name){
        this(name,30,5,0);
    }
    //생성자 오버로딩
    public Character(String name, int hp, int power,int defense){
        this.name= name;
        this.hp=hp;
        this.power=power;
        this.defense=defense;
    }
    //Getter
    public String getName(){
        return this.name;
    }
    public int getDefense(){
        return this.defense;
    }
    public int getHp(){
        return this.hp;
    }

    //상태창
    public void showStatus(){
        System.out.println(name+" (HP: "+hp+")");
    }
    //생존 여부 확인
    public boolean isAlive(){
        return hp>0;
    }
    //데미지 계산
    public void takeDamage(int dmg){
        if(dmg<0){
            return;
        }
        hp-=dmg;
        if(hp<0){
            hp=0;
        }
    }
    //공격 : (power- 타겟의 방어력)만큼 공격
    public void attack(Character target){
        int targetDef = target.getDefense();
        System.out.println(name + "의 공격! " + target.name + "에게 " + power + " 피해");
        target.takeDamage(power-targetDef);
    }
}
