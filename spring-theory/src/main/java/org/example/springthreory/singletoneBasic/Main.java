package org.example.springthreory.singletoneBasic;

public class Main {
    public static void main(String[] args) {
        //Step1
        System.out.println("===== 1. 싱글톤 없이: 번호표 두 대 (버그!) =====");
        NaiveTicketMachine n1 = new NaiveTicketMachine();
        NaiveTicketMachine n2 = new NaiveTicketMachine();

        System.out.println(n1.issue()+", "+n2.issue()); //1,1
        System.out.println(n1==n2); //n1,n2는 다른 객체임

        //Step2
        System.out.println("===== 2. 싱글톤 적용: 번호표는 하나뿐 =====");
        TicketMachine t1 = TicketMachine.getInstance();
        TicketMachine t2 = TicketMachine.getInstance();

        System.out.println(t1.issue()+", "+t2.issue()); //1,2
        System.out.println(t1==t2); //t1,t2는 같은 객체

        //Step3
        System.out.println("===== 3. lazy 초기화 (설정 관리자) =====");
        Settings s1 = Settings.getInstance();
        s1.setTheme("dark");
        System.out.println(Settings.getInstance().getTheme());

    }
}
