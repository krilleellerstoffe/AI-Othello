/*
  Author: <Christopher O'Driscoll>
  Id: <al0038>
  Study program: <Systemutvecklare TGSYA20h>
*/
package model;

import javax.swing.*;
import java.util.Random;

public class GameBoard {

    private GameManager model;

    private final int size;  //height of game board

    private final Square[][] squares; //an array of squares on the board

    private int whiteScore;
    private int blackScore;


    public GameBoard(GameManager model, int size) {

        this.model = model;
        this.size = size;
        squares = new Square[size][size];
    }

    //returns square and disk
    public Square getSquare(int x,int y) {
        return squares[x][y];
    }

    public Disk getDisk(int x, int y) {
        return squares[x][y].getDisk();
    }

    //Clear squares of all disks
    public void resetBoard(){
        for(int i = 0; i<size; i++){
            for(int j = 0; j<size; j++){
                squares[i][j] = null;
            }
        }
    }

    public int getWhiteScore() {
        return whiteScore;
    }

    public void setWhiteScore(int whiteScore) {
        this.whiteScore = whiteScore;
    }

    public int getBlackScore() {
        return blackScore;
    }

    public void setBlackScore(int blackScore) {
        this.blackScore = blackScore;
    }
}
