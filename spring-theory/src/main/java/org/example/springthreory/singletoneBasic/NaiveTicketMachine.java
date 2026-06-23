package org.example.springthreory.singletoneBasic;

//Step1
public class NaiveTicketMachine {
    int num;
    public int issue(){
        return ++num;
    }
    public static void main(String[] args) {
        //서로 다른 두 객체 => num을 공유하지 않고 각각 num을 저장
        NaiveTicketMachine a = new NaiveTicketMachine();
        NaiveTicketMachine b = new NaiveTicketMachine();

        System.out.println(a.issue()); //1
        System.out.println(b.issue()); //1
    }
}
