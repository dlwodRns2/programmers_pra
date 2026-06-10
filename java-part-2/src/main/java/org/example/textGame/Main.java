package org.example.textGame;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    static Scanner sc = new Scanner(System.in);

    //플레이어의 행동 선택
    static public int select(){
        System.out.println("1. 공격 2. 방어 3. 회복");
        System.out.print(">> ");
        int nxt;
        while(true){
            try{
                nxt=Integer.parseInt(sc.nextLine());

            }catch(NumberFormatException e){
                System.out.println("숫자를 입력해주세요");
                continue;
            }
            if(nxt<1||nxt>3){
                System.out.println("1~3사이의 숫자를 입력하세요.");
                continue;
            }
            return nxt;
        }
    }
    static void main(String[] args) {

        Character[] monsters = {new Slime("슬라임"),
                new Goblin("고블린", 50, 8,0),
                new Dragon("드래곤", 120, 100,5)};

        Character hero = new Hero("용사",100,25,5);
        hero.showStatus();

        //다형성 연습 : Character 타입 배열에 Hero, Monster 담아 다루기
        ArrayList<Character> deathCharacter = new ArrayList<Character>();

        for(Character monster : monsters){
            while(hero.isAlive()&& monster.isAlive()){
                //플레이어 턴
                int nxt=select();
                //1. 공격
                if(nxt==1){
                    hero.attack(monster);
                    hero.showStatus();
                }
                //2. 방어
                else if(nxt==2){
                    System.out.println("몬스터의 공격을 방어!");
                    hero.showStatus();
                    continue;
                }
                //3. 회복
                else{
                    System.out.println("플레이어의 체력 회복!");
                    ((Hero) hero).heal();
                    hero.showStatus();
                }
                if(!monster.isAlive()){
                    System.out.println(">> "+monster.getName()+"을(를) 쓰러뜨렸다!");
                    deathCharacter.add(monster);
                    break;
                }

                //몬스터 턴
                monster.attack(hero);
                hero.showStatus();

                //게임 오버
                if(!hero.isAlive()){
                    System.out.println(">> 용사가 쓰러졌다!");
                    deathCharacter.add(hero);
                    System.out.println("게임 오버!");

                    //게임 오버 시, 사망한 캐릭터 순서로 출력 후 프로그램 종료
                    for(int i=0;i<deathCharacter.size();i++){
                        System.out.println((i+1)+"번째 사망 캐릭터 : "+deathCharacter.get(i).getName());
                    }
                    return;
                }
            }
        }
        System.out.println("클리어!");
    }
}
