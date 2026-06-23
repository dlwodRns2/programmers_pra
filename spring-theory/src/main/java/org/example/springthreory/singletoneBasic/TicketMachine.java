package org.example.springthreory.singletoneBasic;

//Step2. Eager Singletone
public class TicketMachine {
    //1. 유일한 객체를 클래스 내부에 보관
    private static final TicketMachine instance = new TicketMachine();
    private int num;

    //2. private 생성자 : 외부에서 객체 생성을 방지
    private TicketMachine(){}
    //3. getInstance()라는 메서드로만 객체를 가져올 수 있음
    public static TicketMachine getInstance(){
        return instance;
    }
    public int issue(){
        return ++num;
    }

    public static void main(String[] args) {
        //getInstance는 항상 똑같은 객체를 반환
        TicketMachine tm = instance.getInstance();

        //똑같은 객체 -> issue()시, 같은 num을 건드림
        System.out.println(tm.issue()); //1
        System.out.println(tm.issue()); //2

        //Step3
        TicketMachine w1 =instance.getInstance();
        TicketMachine w2 = instance.getInstance();
        TicketMachine w3 = instance.getInstance();

        System.out.println(w1.issue());
        System.out.println(w2.issue());
        System.out.println(w3.issue());
        System.out.println(w1==w2);

    }
}
