package org.example.bingoGamePrac;

public class Start {
    static final int SIZE = 5;
    static final int MAX=25;

    static int[][] myBoard = new int[SIZE][SIZE];
    static int[][] computerBoard=new int[SIZE][SIZE];;

    static void main(String[] args) {
        BingoGame game = new BingoGame();
        game.play();
        game.makeBoard(myBoard);
        game.makeBoard(computerBoard);
    }
}
