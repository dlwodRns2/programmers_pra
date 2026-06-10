package org.example.pet;

public class Pet {
    private String name;
    private String type;
    private int fullness;
    private int happiness;

    public Pet(String name){
        this.name=name;
        this.fullness=50;
        this.happiness=50;
    }
    //생성자 오버로딩
    public Pet(String name, String type){
        this.name=name;
        this.type=type;
        this.fullness=50;
        this.happiness=50;
    }

    public String getName(){
        return this.name;
    }
    //상태창 : 기분 표시 기능 추가
    public void showStatus(){
        String str;
        if(happiness>=80){
            str="행복해요";
        }else if(happiness>=50){
            str="그저 그래요";
        }else{
            str="시무룩";
        }
        System.out.println("지금 기분은 "+str);
        System.out.println("["+name+"] 포만감: "+fullness+" / 행복: "+happiness );
    }
    public void feed(){
        fullness+=20;
        if(fullness>=100){
            fullness=100;
        }
        happiness+=5;
        if(happiness>=100){
            happiness=100;
        }
        subFullness();
        System.out.println(name+"에게 먹이를 줬어요! 냠냠");
    }
    public void play(){
        happiness+=20;
        if(happiness>=100){
            happiness=100;
        }
        fullness-=10;
        if(fullness<=0){
            fullness=0;
        }
        subFullness();
        System.out.println(name+"와(과) 신나게 놀았어요!");
    }
    //잠자기 : 행복/포만감 회복
    public void sleep(){
        happiness+=10;
        fullness+=10;
        if(happiness>=100){
            happiness=100;
        }
        if(fullness>=100){
            fullness=100;
        }
        subFullness();
    }
    //행동 시, 포만감 감소
    public void subFullness(){
        fullness-=5;
        if(fullness<=0){
            fullness=0;
        }
    }
}
