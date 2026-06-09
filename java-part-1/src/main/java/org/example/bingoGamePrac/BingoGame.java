package org.example.bingoGamePrac;

import java.util.*;

public class BingoGame {
    static Random random = new Random();
    static Scanner sc = new Scanner(System.in);
    static final int SIZE = 5;
    static final int MAX=25;
    static final int TARGET = 3; //목표 빙고 줄 개수

    static int[][] myBoard = new int[SIZE][SIZE];
    static int[][] computerBoard=new int[SIZE][SIZE];;

    static boolean[][] myMarkedBoard = new boolean[SIZE][SIZE];
    static boolean[][] computerMarkedBoard =  new boolean[SIZE][SIZE];

    static boolean[] called = new boolean[MAX+1]; //숫자 사용 여부

    //사용자 숫자 입력
    public int playerPick(){
        int num;
        while(true){
            System.out.println("부를 숫자 입력 (1~25) > ");

            try{
                String str = sc.nextLine();
                num=Integer.parseInt(str);

            }catch(NumberFormatException e){
                System.out.println("숫자만 입력하세요.");
                continue;
            }
            if(num<1||num>25){
                System.out.println("1~25 사이로 입력하세요");
                continue;
            }else if(called[num]){
                System.out.println("이미 부른 숫자입니다.");
                continue;
            }
            else{
                break;
            }
        }
        return num;
    }
    //컴퓨터 숫자 입력(랜덤)
    public int computerPick(){
        int num;
        do{
            num = random.nextInt(MAX)+1;
        }while(called[num]);
        System.out.println("컴퓨터 입력 > "+num);
        return num;
    }
    public void play(){
        //보드판 채우기
        makeBoard(myBoard);
        makeBoard(computerBoard);

        System.out.println("===== 빙고 게임 =====");
        System.out.println("컴퓨터와 번갈아 숫자를 불러 빙고를 완성하세요!");
        System.out.println("먼저 "+TARGET+"줄을 완성하면 승리!");

        //시작
        while(true){
            System.out.println("\n===== 내 빙고판 =====");
            printBoard(myBoard,myMarkedBoard);
//            System.out.println("\n===== 컴퓨터 빙고판 =====");
//            printBoard(computerBoard,computerMarkedBoard);

            //내 차례
            int num = playerPick();
            called[num]=true;
            mark(myBoard, myMarkedBoard,num);
            mark(computerBoard,computerMarkedBoard,num);

            //승패 결정 시, 종료
            if(checkWin()){
                break;
            }

            //컴퓨터 차례
            int cNum = computerPick();
            called[cNum]=true;
            mark(myBoard, myMarkedBoard,cNum);
            mark(computerBoard,computerMarkedBoard,cNum);
            //승패 결정 시, 종료
            if(checkWin()){
                break;
            }

            //턴이 끝날 때마다, 현재 빙고 상황 출력
            System.out.println("\n현재 빙고 줄 -> 나 : "+countBingo(myMarkedBoard)+"줄, 컴퓨터 : "+countBingo(computerMarkedBoard)+"줄");
        }
        //게임 종료 후, 결과 출력
        printBoard(myBoard,myMarkedBoard);
        printBoard(computerBoard,computerMarkedBoard);
    }
    //보드판 채우기
    public void makeBoard(int[][] board){
        List<Integer> nums = new ArrayList<>();
        for(int i=1;i<=MAX;i++){
            nums.add(i);
        }
        Collections.shuffle(nums);

        int idx=0;
        for(int r=0;r<SIZE;r++){
            for(int c=0;c<SIZE;c++){
                board[r][c]=nums.get(idx++);
            }
        }
    }
    //보드판 출력
    public void printBoard(int[][] board, boolean[][] marked){
        for(int r=0;r<SIZE;r++){
            for(int c=0;c<SIZE;c++){
                if(marked[r][c]){
                    System.out.print("[ *]");
                }else{
                    System.out.printf("[%2d]",board[r][c]);
                }
            }
            System.out.println();
        }
    }
    //보드 채우기
    public void mark(int[][] board, boolean[][] marked, int num){
        for(int r=0;r<SIZE;r++){
            for(int c=0;c<SIZE;c++){
                if(board[r][c]==num){
                    marked[r][c]=true;
                }
            }
        }
    }
    //빙고 줄 세기
    public int countBingo(boolean[][] marked){
        int bingo=0;
        //가로5줄
        for(int r=0;r<SIZE;r++){
            int cnt=0;
            for(int c=0;c<SIZE;c++){
                if(marked[r][c]){
                    cnt++;
                }
            }
            if(cnt==5){
                bingo++;
            }
        }
        //세로5줄
        for(int c=0;c<SIZE;c++){
            int cnt=0;
            for(int r=0;r<SIZE;r++){
                if(marked[r][c]){
                    cnt++;
                }
            }
            if(cnt==5){
                bingo++;
            }
        }
        //대각선2줄
        boolean b1 = true;
        for(int i=0;i<SIZE;i++){
            if(!marked[i][i]){
               b1=false;
            }
        }
        if(b1){
            bingo++;
        }

        boolean b2=true;
        for(int i=0;i<SIZE;i++){
            if(!marked[i][SIZE-1-i]){
                b2=false;
            }
        }
        if(b2){
            bingo++;
        }

        return bingo;
    }
    //숫자를 입력 후 보드 마킹 후, 승패 여부 확인
    public boolean checkWin(){
        int myScore=countBingo(myMarkedBoard);
        int comScore = countBingo(computerMarkedBoard);

        //무승부
        if(myScore==3&&myScore==comScore){
            System.out.println("무승부입니다.");
            return true;
        }else if(myScore==3){
            System.out.println("플레이어의 승리입니다.");
            return true;
        }else if(comScore==3){
            System.out.println("컴퓨터의 승리입니다.");
            return true;
        }
        else{
            return false;
        }
    }
}
