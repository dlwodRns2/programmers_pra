package org.example.threadPrac.semaphoreGame;

public class Main {
    static void main(String[] args) {
        Dungeon dungeon = new Dungeon(2);

        String[] names = { "전사", "마법사", "궁수", "도적", "성기사" };
        for (String name : names) {
            new Adventurer(dungeon, name).start();
        }
        //멀티쓰레드에서 실행 순서는 보장되지 않는다 : names의 순서대로 입장이 아님
        //순서대로 쓰레드 생성 => OS 맘대로 스케줄링 => 스케줄링 대로 CPU 분배
        //다음 실행에서는 또 다른 순서로 입장

        //성기사 던전 입장 대기...
        //궁수 던전 입장 대기...
        //도적 던전 입장 대기...
        //전사 던전 입장 대기...
        //마법사 던전 입장 대기...

    }
}
