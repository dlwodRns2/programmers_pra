package org.example;

import java.util.Random;
import java.util.Scanner;

public class UpDownPrac {
    static int cnt=0;
    static Scanner sc = new Scanner(System.in);

    static void main(String[] args) {
        Random rand = new Random();
        int answer = rand.nextInt(100)+1;
        System.out.println("answer : "+answer);

        System.out.println("숫자를 맞혀보세요! (1~100)");
        while(cnt<7){
            int guess=-1;
            try{
                System.out.println("입력 > ");
                String str = sc.nextLine();
                guess = Integer.parseInt(str);

            }catch(NumberFormatException e){
                System.out.println("1~100 사이 숫자를 입력해주세요.");
                continue;
            }
            System.out.println("guess : "+guess);
            if(guess<0 || guess>100){
                System.out.println("1~100 사이 숫자를 입력해주세요.");
                continue;
            }

            cnt++;
            System.out.println(cnt+"번째 입력");

            if(guess==answer){
                System.out.println("정답입니다! "+cnt+"번 만에 맞췄어요.");
                cnt=0;
                break;
            }else if(guess>answer){
                System.out.println("Down! 더 작은 수 입니다.");
            }else if(guess<answer){
                System.out.println("UP! 더 큰 수 입니다.");
            }

        }
    }
}
