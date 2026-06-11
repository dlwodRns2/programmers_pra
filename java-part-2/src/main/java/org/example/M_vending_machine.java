package org.example;

import java.util.Scanner;

public class M_vending_machine {
    Scanner sc = new Scanner(System.in);
    private int totalMoney;
    private M_drink[] drinks;

    public M_vending_machine(){
        totalMoney=0;
        //부모 타입 (Drink) 배열에 자식 객체들을 넣는다(다형성)
        drinks = new M_drink[]{
                new M_coke(),
                new M_cider(),
                new M_fanta(),
                new M_water()
        };
    }
    public int getTotalMoney() {
        return totalMoney;
    }

    //돈 넣기 : insertMoney(int money)
    public void insertMoney(int money){
        totalMoney+=money;
        System.out.println(money+"원을 넣었습니다.");
        System.out.println("잔액 : "+totalMoney);
    }
    //음료 구매 : buy(int menu) : 메뉴 번호(1~4)로 선택
    public void buy(int menu){
        String name =drinks[menu-1].getName();
        int price = drinks[menu-1].getPrice();

        if(totalMoney<price){
            System.out.println("잔액 부족입니다.");
        }
        else{
            totalMoney-=price;
            drinks[menu-1].dispense();
            System.out.println("잔액 : "+totalMoney);
        }
    }
    //종료 시, 잔돈 반환
    public void returnMoney(){
        System.out.println("잔액 "+totalMoney+"원을 반환합니다.");
    }
    //메뉴 출력
    public void printMenu(){
        System.out.println("============== 자판기 ==============");
        System.out.println("[1]콜라 : 500  [2]사이다 : 500  [3]환타 : 300  [4]물 : 200");
        System.out.println("[5]돈 넣기  [6]종료");
        System.out.println("현재 금액 : " + totalMoney);
        System.out.println("====================================");
    }
    //메뉴 선택
    public int selectMenu(){
        int menu;
        while(true){
            try{
                System.out.println("메뉴를 입력하세요.");
                menu = Integer.parseInt(sc.nextLine());
            }catch (NumberFormatException e){
                System.out.println("숫자를 입력하세요.");
                continue;
            }
            if(menu<1||menu>6){
                System.out.println("1~6 사이의 숫자를 입력하세요.");
                continue;
            }
            break;
        }
        return menu;
    }
    public void start(){
        while(true){
            printMenu();
            int menu = selectMenu();
            if(menu<5){
                buy(menu);
            }
            else if(menu==5){
                int insert;
                while(true){
                    try{
                        System.out.println("넣으실 금액을 입력해주세요.");
                        insert = Integer.parseInt(sc.nextLine());
                    }catch (NumberFormatException e){
                        System.out.println("숫자로 입력해주세요.");
                        continue;
                    }
                    if(insert<0){
                        System.out.println("양의 정수를 입력해주세요.");
                        continue;
                    }
                    break;
                }
                insertMoney(insert);

            }else{
                returnMoney();
                System.out.println("자판기 사용을 종료합니다.");
                break;
            }
        }
    }

}
