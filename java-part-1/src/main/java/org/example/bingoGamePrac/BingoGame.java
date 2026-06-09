package org.example.bingoGamePrac;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BingoGame {
    static final int SIZE = 5;
    static final int MAX=25;

    public void play(){
        System.out.println("===== 빙고 게임 =====");
        System.out.println("컴퓨터와 번갈아 숫자를 불러 빙고를 완성하세요!");
    }

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

    public void printBoard(int[][] board, boolean[][] marked){

    }
}
