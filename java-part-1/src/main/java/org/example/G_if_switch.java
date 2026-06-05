package org.example;

import java.util.Scanner;

// * 조건문
// 조건식의 연산결과에 따라 실행할 문장이 달라져서 프로그램의 실행흐름을 바꿀 수 있다.
public class G_if_switch {
    // 1) if문
    // if (조건식 참) { 실행 }
    public static void exam1(){
        int score = 90;

        if (score >= 90) {
            // 조건식 참일 때 수행할 내용
            System.out.println("A학점 입니다.");
        }
    }
    // 1-1) if - else
    public static void exam2(){
        int score = 9;

        if (score >= 60) {
            System.out.println("합격입니다.");
        } else {
            System.out.println("불합격입니다.");
        }
    }
    // 1-2) if - else if - else
    public static void exam3(){
        System.out.println("점수를 입력해주세요.");
        Scanner sc = new Scanner(System.in);
        int score = sc.nextInt(); // 사용자 값을 입력받아주세요.

        if (score >= 90) {
            System.out.println("A학점 입니다.");
        } else if (score >= 80) {
            System.out.println("B학점 입니다.");
        } // C, D학점 복습
        else if(score>=70){
            System.out.println("C학점 입니다.");
        }
        else if(score>=60){
            System.out.println("D학점 입니다.");
        }
        else {
            System.out.println("F학점 입니다.");
        }

    }
    // 1-3) 중첩 if
    public static void exam4() {
        Scanner sc = new Scanner(System.in);
        System.out.println("점수를 입력하세요.");
        int score = sc.nextInt();

        if (score >= 90) {
            if (score >= 95) {
                System.out.println("A++학점 입니다.");
            }
            System.out.println("A학점 입니다.");
        } else if (score >= 80) {
            System.out.println("B학점 입니다.");
        } // C, D학점 복습
        else {
            System.out.println("F학점 입니다.");
        }
    }

    // 2) switch
    // - switch문의 조건식 결과값은 결과값이 반드시 정수 또는 문자열이어야 한다.
    // - case문의 값은 정수 상수만 가능하며, 중복되지 않아야 한다.
    public static void exam5() {

        Scanner sc = new Scanner(System.in);
        System.out.println("음료수 번호를 누르세요");
        System.out.println("[1]콜라 [2]사이다 [3]환타");
        int menuNum = sc.nextInt();

        switch (menuNum) {
            case 1:
                System.out.println("주문하신 콜라가 나왔습니다.");
                break;
            case 2:
                System.out.println("주문하신 사이다가 나왔습니다.");
                break;
            case 3:
                System.out.println("주문하신 환타가 나왔습니다.");
                break;
            default:
                System.out.println("잘 못 누르셨습니다.");
        }

    }


    //A학점 90점 이상, B학점 80점 이상, C학점 70점 이상, 그 외 학점 F학점
    //점수에 맞는 학점을 출력하는 기능을 만들어주세여
    public static char getGrade(int score){
        char grade = 'F';
        if(score>=90){
            grade = 'A';
        }
        else if(score>=80){
            grade = 'B';
        }
        else if(score>=70){
            grade = 'C';
        }
        return grade;
    }

    public static void practice1(){
        Scanner sc = new Scanner(System.in);
        int score = sc.nextInt();

        switch(getGrade(score)){
            case 'A' :
                System.out.println("A학점입니다.");
            case 'B' :
                System.out.println("B학점입니다.");
            case 'C' :
                System.out.println("C학점입니다.");
            default :
                System.out.println("F학점입니다.");

        }
    }

    public static void exam6() {
        Scanner sc = new Scanner(System.in);
        System.out.println("1~12월 중 하나를 입력하세요.");
        int month = sc.nextInt();

        switch (month) {
            case 1:
            case 2:
            case 3:
                System.out.println("1분기입니다.");
                break;
            case 4:
            case 5:
            case 6:
                System.out.println("2분기입니다.");
                break;
            case 7:
            case 8:
            case 9:
                System.out.println("3분기입니다.");
                break;
            case 10:
            case 11:
            case 12:
                System.out.println("4분기입니다.");
                break;
        }

    }
    static void main(String[] args) {
        exam1();
        exam2();
        exam3();
    }
}
