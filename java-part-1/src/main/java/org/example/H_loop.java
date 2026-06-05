package org.example;

import java.util.Scanner;

// * 반복문
// 어떤 작업이 반복적으로 수행되도록 할 때 사용된다.

import java.util.Scanner;

public class H_loop {

    /*
        * for 문
        for ( 초기값; 조건식; 증감식 ) {
            // 조건식이 참일 때 수행될 문장들을 적는다.
        }
     */
    public static void exam1() {
        for ( int i = 0; i < 10; i++) {
            System.out.println("cnt : " + i);
        }
    }

    public static void exam2() {
        for ( int i = 1; i < 10; i++ ) {
            System.out.println("2 * " + i + " = " + (2 * i));
        }
    }

    // 중첩
    public static void exam3() {
        for ( int i = 1; i < 3; i++ ) {
            System.out.println("i= " + i);
            for ( int k = 1; k < 5; k++ ) {
                System.out.println("k= " + k);
            }
        }
    }

    // 구구단 : 2단부터 9단까지 중첩for문을 사용해서
    public static void practice1() {
        System.out.println("=== 구구단 전체 ===");
        for ( int dan = 2; dan < 10; dan++ ) {
            System.out.println("=== " + dan + "단 ===");
            for ( int k = 1; k < 10; k++ ) {
                System.out.println(dan + " * " + k + " = " + (dan * k));
            }
        }
    }

    // continue : 1 ~ 100까지 짝수만 출력
    public static void exam4() {
        for ( int i = 1; i <= 100; i++ ) {
            if ( i % 2 != 0 ) {
                // 홀수
                continue;
            }
            // 짝수
            System.out.println(i);
        }
    }

    // 홀수만 출력
    public static void practice2() {
        for ( int i = 1; i <= 100; i++ ) {
            if ( i % 2 == 0 ) {
                // 짝수
                continue;
            }
            // 홀수
            System.out.println(i);
        }
    }

    // break : 1 ~ 100까지 올라가는데, 30에 도달했을 때 멈춤(탈출)
    public static void exam5() {
        for ( int num = 1; num <= 100; num++ ) {
            if ( num == 30 ) {
                break;
            }
            System.out.println(num);
        }

        System.out.println("탈출한다");
    }

    public static void exam6() {

        for ( int i = 0; i < 3; i++ ) {
            System.out.println(" - 첫 번째 루프 : " + i);
            for ( int j = 0; j < 3; j++ ) {
                System.out.println(" - 두 번째 루프 : " + j);
                for ( int k = 0; k < 3; k++ ) {
                    System.out.println(" - 세 번째 루프 : " + k);
                }
            }
        }

    }

    public static void exam7() {
        int i = 0;
        for (; i < 3; ) {
            // 수행
            System.out.println(i++);
        }
    }

    public static void exam8() {
        int i = 0;
        for (;;) {
            if ( i > 10 ) break;
            System.out.println(++i);
        }
    }

    public static void exam9() {
        // 무한 루프
        int i = 0;
        for (;true;) {
            System.out.println(i++);
        }
    }

    public static void exam10() {
        for (int i = 9; i >= 0; i--) {
            System.out.println(i);
        }
    }

    // 구구단을 9단부터 2단까지 역으로 출력해주세요.
    public static void practice3() {

        for ( int dan = 9; dan > 1; dan-- ) {
            System.out.println("dan = " + dan);
            for ( int i = 9; i >= 1; --i) {
                System.out.println(dan + " * " + i + " = " + (dan * i));
            }
        }

    }

    // * while문
    /*
        while (조건식) {
            // 조건식의 연산결과가 참(true)인 동안, 반복될 문장을 적는다.
        }
     */
    public static void exam11() {
        int cnt = 0;
        while ( cnt <= 10 ) {
            System.out.println("cnt = " + cnt);
            cnt++;
        }
    }

    public static void practice4() {
        System.out.println("구구단 2단 출력");
        int i = 1;
        while ( i < 10 ) {
            System.out.println(" 2 * " + i + " = " + (2 * i));
            i++;
        }
    }


    // while 중첩가능 -> 전체 구구단 출력
    public static void practice5() {
        int dan = 2;
        while ( dan < 10 ) {
            System.out.println("[" + dan + "단]");
            int i = 1;
            while ( i < 10 ) {
                System.out.println(dan + " * " + i + " = " + (dan * i));
                i++;
            }
            dan++;
        }
    }

    public static void exam12() {
        int i = 3;
        while ( true ) {
            if ( i == 0 ) break;
            System.out.println(i--);
        }
    }

    public static void exam13() {
        int i = 10;
        while ( --i > 0 ) {
            System.out.println(i);
        }

        System.out.println("=========");
        i = 10;
        while ( i-- > 0 ) {
            System.out.println(i);
        }
    }

    // 무한루프 : while(true)
    // 사용자로부터 정수값을 입력받아 합계를 내도록 구현하시요.
    // ex) 사용자 : 10입력 -> 10 + 9 + 8 + 7 + 6 + 5 + 4 + 3 + 2 + 1 = 55
    // 결과 : 55 출력 하고
    // 다시 정수값을 입력받아 합계를 낸다.
    // 사용자가 0을 입력하면 종료한다.
    public static void practice6() {
        Scanner sc = new Scanner(System.in);
        // 무한루프를 건다.
        while ( true ) {
            // 사용자한테 정수 값을 입력받는다.
            System.out.println("합을 구할 정수를 입력해주세요.(만약 종료를 원하면 0을 누르시요.)");
            int num = sc.nextInt();

            // 0을 누르면 탈출하는 조건을 넣어준다.
            if ( num == 0 ) break;

            // 합계 로직을 구현한다.
            int sum = 0; // 합계값
            for ( int i = 1; i <= num; i++ ) {
                sum += i; // sum = sum + i;
            }
            System.out.println("결과 : " + sum);
        }
        // 0을 눌러 탈출했을 경우
        System.out.println("감사합니다.");
    }

    // 요구사항 : 원하는 구구단의 단을 입력하면 -> ex) 2 -> 2 * 1 = 2, 2 * 2 = 4, 2 * 3 = 6, 2 * 4 = 8, 2 * 5 = 10 .. 2*9 = 18
    // 0을 입력하면 종료한다.
    public static void practice7() {
        Scanner sc = new Scanner(System.in);

        while (true) {

            System.out.println("원하는 구구단의 단을 입력하시오.(0을 누르면 종료됩니다)");
            int dan = sc.nextInt();

            if (dan == 0) break;

            for (int i = 1; i <= 9; i++) {
                System.out.println(dan + " * " + i + " = " + (dan * i));
            }

        }
    }

    // 짝수인 경우만 출력해주세요. while문 사용
    // 1 ~ 10 까지 중
    public static void exam14() {
        int i = 1;
        while ( i <= 10 ) {
            if ( i % 2 == 0 ) {
                System.out.println(i);
            }
            i++;
        }
    }

    // * do-while문 : 최소한 한번은 수행될 것을 보장한다.
    /*
           do {
                // 조건식의 연산결과가 참일 때 수행될 문장들을 적는다.
           } while (조건식)
           ...
     */
    public static void exam15() {
        int i = 0;

        while ( i != 0 ) {
            System.out.println("while문입니다.");
        }

        do {
            System.out.println("do-while문입니다.");
        } while ( i != 0 );

    }

    static void main(String[] args) {
        exam15();
    }
}
