package org.example.accountBookPrac;

import java.util.Scanner;

public class AccountBookPrac {
    static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        AccountBook book = new AccountBookImpl();

        while(true){

            System.out.println("===== 가계부 =====");
            System.out.println("1. 내역 추가");
            System.out.println("2. 내역 조회");
            System.out.println("3. 전체 삭제");
            System.out.println("4. 내역 삭제");
            System.out.println("5. 종료");

            System.out.println("메뉴를 입력하세요.");
            int menu = 0;

            //메뉴 입력 : 숫자만 입력 가능
            try{
                String input = sc.nextLine();
                menu = Integer.parseInt(input);

            }catch(NumberFormatException e){
                System.out.println("숫자만 입력이 가능합니다.");
                continue;
            }


            switch(menu){
                case 1:
                    book.addAccount();
                    break;
                case 2:
                    book.showAccount();
                    break;
                case 3:
                    book.deleteAll();
                    break;
                case 4:
                    book.deleteItem();
                    break;
                case 5:
                    System.out.println("종료합니다");
                    return;
                default :
                    System.out.println("잘못된 번호입니다");

            }
        }

    }
}
