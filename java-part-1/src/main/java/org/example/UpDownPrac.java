package org.example;

import java.util.Random;
import java.util.Scanner;

public class UpDownPrac {
    static Scanner sc = new Scanner(System.in);

    static int cnt=0; //1게임 내에서 시도 횟수 저장
    static int[] level = {50,100,500}; //난이도 저장
    static int bestScore=100; //최단 기록 저장

    //최단 기록 출력
    static void showBestScore(){
        System.out.println("최단 기록 : "+bestScore+"회 내에 맞추셨습니다!");
    }
    //최단 기록 갱신
    static void updateBest(int cur){
        if(cur<bestScore){
            bestScore=cur;
        }
    }
    //역할 바꾸기 : 내가 정답을 정하고 컴퓨터가 맞추는 게임 시작
    //최단 기록 갱신X
    static void startGameComputer(int lev){
        int st=1;
        int ed=level[lev-1];
        int mid = (st+ed)/2;
        int answer=0;

        System.out.println("VS 컴퓨터");
        //사용자 정답 입력
        while(true){
            try{
                System.out.println("정답 입력 > ");
                String str = sc.nextLine();
                answer = Integer.parseInt(str);
            }catch(NumberFormatException e){
                System.out.println("1~"+level[lev-1]+ "사이 숫자를 입력해주세요.");
                continue;
            }
            if(answer<1||answer>level[lev-1]){
                System.out.println("1~"+level[lev-1]+ "사이 숫자를 입력해주세요.");
                continue;
            }
            break;
        }

        //컴퓨터 시도 : 이분탐색으로 찾기 => 기회 제한X
        while(mid!=answer){
            mid=(st+ed)/2;
            cnt++;
            if(mid==answer){
                System.out.println("정답입니다! "+cnt+"번 만에 맞췄어요.");
                cnt=0;
                return;
            }else if(mid>answer){
                ed=mid;
            }else {
                st = mid;
            }
        }
        cnt=0;
        System.out.println("게임 오버! 정답은 "+answer+"이였습니다!");
        return;
    }
    static int startGame(int lev){
        Random rand = new Random();
        int answer = rand.nextInt(level[lev-1])+1;
        //System.out.println("answer : "+answer);
        System.out.println("숫자를 맞혀보세요! (1~"+level[lev-1]+")");

        //기회 제한 : 7번 내에 못 맞히면 게임오버
        while(cnt<7){
            int guess=-1;
            try{
                System.out.println("입력 > ");
                String str = sc.nextLine();
                guess = Integer.parseInt(str);

            }catch(NumberFormatException e){
                System.out.println("1~"+level[lev-1]+ "사이 숫자를 입력해주세요.");
                continue;
            }
            System.out.println("guess : "+guess);
            if(guess<0 || guess>level[lev-1]){
                System.out.println("1~"+level[lev-1]+ "사이 숫자를 입력해주세요.");
                continue;
            }

            cnt++;
            System.out.println(cnt+"번째 입력");

            //다시 하기
            if(guess==answer){
                updateBest(cnt);
                System.out.println("정답입니다! "+cnt+"번 만에 맞췄어요.");
                System.out.println("한 판 더? (y/n)");
                String nxt = sc.nextLine();
                if(nxt.equals("y")){
                    cnt=0;
                    return 1;
                }else{
                    System.out.println("게임 종료");
                    return 0;
                }
            }else if(guess>answer){
                System.out.println("Down! 더 작은 수 입니다.");
            }else if(guess<answer){
                System.out.println("UP! 더 큰 수 입니다.");
            }

        }
        System.out.println("게임 오버! 정답은 "+answer+"이였습니다!");
        return 0;
    }
    public static int selectLevel(){
        System.out.println("게임 난이도를 선택해주세요");
        System.out.println("1. easy(1~50)");
        System.out.println("2. normal(1~100)");
        System.out.println("3. hard(1~500)");
        int select=0;

        while(true){
            try{
                String str = sc.nextLine();
                select = Integer.parseInt(str);
            }catch(NumberFormatException e){
                System.out.println("1~3 사이의 숫자를 입력해주세요");
                continue;
            }
            if(select>3||select<1){
                System.out.println("1~3 사이의 숫자를 입력해주세요");
                continue;
            }
            break;
        }
        return select;
    }
    static void main(String[] args) {
        //난이도 선택
        int level = selectLevel();

        //컴퓨터 대전(단판)
        startGameComputer(level);

        //게임 시작
        while(startGame(level)==1){}

        //최단 기록 출력
        showBestScore();
    }
}
