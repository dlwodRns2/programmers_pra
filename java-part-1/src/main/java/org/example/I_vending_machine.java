package org.example;

import java.util.Scanner;

public class I_vending_machine {

    // [요구사항]
    // 돈 넣기
    // 메뉴선택 시 -> 음료가 나온다, 돈이 차감된다.
    // 종료시 잔돈이 반환된다.

    static final int COKE = 500, CIDER = 500, FANTA = 300, WATER = 200;

    // 사용자 메뉴가 출력 -> 현재 잔금도 표시
    public static void printMenu(int totalMoney) {
        System.out.println("============== 자판기 ==============");
        System.out.println("[1]콜라 : 500, [2]사이다 : 500, [3]환타 : 300, [4]물 : 200, [5]돈 넣기, [6]종료");
        System.out.println("현재 금액 : " + totalMoney);
        System.out.println("====================================");
    }

    // 사용자로부터 메뉴 번호를 받는 함수
    public static int getChoice() {
        Scanner sc = new Scanner(System.in);
        System.out.println("원하는 메뉴를 선택하시오.");

        return sc.nextInt();
    }

    // 사용자한테 돈받기
    public static int getMoney() {
        Scanner sc = new Scanner(System.in);
        System.out.println("돈을 넣으시오.");

        return sc.nextInt();
    }

    // calcMoney 완성해주세요
    public static int calcMoney(int totalMoney, int price) {
        return totalMoney - price;
    }

    // 예외
    public static void calcMoneyException() {
        System.out.println("잔돈이 부족합니다.");
    }

    static void main(String[] args) {

        int totalMoney = 0;

        while ( true ) {
            printMenu(totalMoney);
            // 사용자 주문번호
            int choice = getChoice();
            int result = -1;
            switch (choice) {
                case 1:
                    result = calcMoney(totalMoney, COKE);
                    // t : 300, coke : 500, result : -200
                    if ( result < 0 ) {
                        calcMoneyException();
                    } else {
                        totalMoney = result;
                        System.out.println("콜라가 나왔습니다.");
                    }
                    break;
                case 2:
                    result = calcMoney(totalMoney,CIDER);
                    if(result<0){
                        calcMoneyException();
                    }
                    else{
                        totalMoney=result;
                        System.out.println("사이다가 나왔습니다.");
                    }
                    break;
                case 3:
                    result = calcMoney(totalMoney,FANTA);
                    if(result<0){
                        calcMoneyException();
                    }
                    else{
                        totalMoney=result;
                        System.out.println("환타가 나왔습니다.");
                    }
                    break;
                case 4:
                    result = calcMoney(totalMoney,WATER);
                    if(result<0){
                        calcMoneyException();
                    }
                    else{
                        totalMoney=result;
                        System.out.println("물이 나왔습니다.");
                    }
                    break;
                case 5:
                    totalMoney += getMoney();
                    break;
                case 6:
                    System.out.println("\n잔돈 " + totalMoney + "원이 반환되었습니다.");
                    return;
                default:
                    System.out.println("잘못 입력하셨습니다. 다시 입력해주세요.");
                    break;
            }
        }

    }

}
