package org.example;

import java.util.Scanner;

public class Prac3 {
    static final int COKE = 500, CIDER = 700, FANTA = 300, WATER = 200;
    public static void printWarn(){
        System.out.println("잔액이 부족합니다.");
    }
    public static void printmoney(int money){
        System.out.println("잔액 : "+money);
    }

    public static boolean calc(int totalMoney, int price){
        return totalMoney<price;
    }
    public static void printMenu(int totalMoney){
        int coke=0,cider=0,fanta=0,water=0;
        System.out.println("================================= 자판기 ================================");
        System.out.println("[1]콜라-500원 [2]사이다-700원 [3]환타-300원 [4]물-200원 [5]돈넣기 [6]종료");
        System.out.println("현재 금액 : " + totalMoney + "원");
        System.out.println("==========================================================================");

        Scanner sc = new Scanner(System.in);


        while(sc.hasNextInt()){
            int next = sc.nextInt();
            if(next==6){
                System.out.println("자판기를 종료합니다.");
                break;
            }

            if(next==1){
                if(calc(totalMoney,500)){
                    printWarn();
                }
                else{
                    totalMoney-=500;
                    coke++;
                    printmoney(totalMoney);
                }
            }
            else if(next==2){
                if(calc(totalMoney,700)){
                    printWarn();
                }
                else{
                    totalMoney-=700;
                    cider++;
                    printmoney(totalMoney);
                }
            }
            else if(next==3){
                if(calc(totalMoney,300)){
                    printWarn();
                }
                else{
                    totalMoney-=300;
                    fanta++;
                    printmoney(totalMoney);

                }
            }
            else if(next==4){
                if(calc(totalMoney,200)){
                    printWarn();
                }
                else{
                    totalMoney-=200;
                    water++;
                    printmoney(totalMoney);
                }
            }
            else if(next==5){
                System.out.println("넣을 금액을 입력해주세요.");
                int money = sc.nextInt();
                totalMoney+=money;
                System.out.println("현재 금액 : "+totalMoney+"원");
            }

        }
        System.out.println("콜라 "+coke+"개, 사이다 "+cider+"개, 환타 "+fanta+"개, 물 "+water+"개 구매완료");
        System.out.println("거스름돈 "+totalMoney+"원");
    }
    static void main(String[] args) {
        Scanner sc  = new Scanner(System.in);
        int money = sc.nextInt();
        printMenu(money);
    }
}
