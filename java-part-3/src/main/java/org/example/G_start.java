package org.example;

import java.util.Scanner;

public class G_start {
    static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        G_account_book book = new G_account_book_impl(sc);

        while (true) {
            System.out.println("===== 가계부 (File) =====");
            System.out.println("1. 내역 추가");
            System.out.println("2. 내역 조회");
            System.out.println("3. 삭제");
            System.out.println("4. 종료");
            System.out.println("번호 입력");

            int choice;

            try {
                choice = Integer.parseInt(sc.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("잘 못된 번호입니다.");
                continue;
            }

            switch (choice) {
                case 1:
                    book.addAccount();
                    break;
                case 2:
                    book.showAccount();
                    break;
                case 3:
                    book.deleteAccount();
                    break;
                case 4:
                    System.out.println("프로그램을 종료합니다.");
                    return;
                default:
                    System.out.println("잘못된 번호입니다.");
            }

        }

    }
}