/*
  Author: <Christopher O'Driscoll>
  Id: <al0038>
  Study program: <Systemutvecklare TGSYA20h>
*/
package model;

import controller.Controller;

import javax.swing.*;

public class GameManager {

    private final Controller controller;
    private final HighScoreKeeper highScoreKeeper;
    private final GameBoard board;

    private final int size;

    private final int[] totalShips = new int[5];    //list of ships on board and how many of each

    private int numberOfActions;    //number of guesses made by player
    private boolean gameOver;
    private final boolean[][] hits; //keeps track of any hits which have not yet sunk a ship
    private final int[] hitFound = new int[2];   //saves the coordinates of the first hit found in above array

    public GameManager(Controller controller, int size){

        this.controller = controller;
        highScoreKeeper = new HighScoreKeeper(size);
        board = new GameBoard(this, size);
        this.size = size;
        hits = new boolean[size][size];
    }
    //updates high score list with name and score
    public void setNewHighScore(String name) {

        highScoreKeeper.setNewHighScore(name, numberOfActions);
    }
    //gets a list of high scores
    public String[] getHighScores(){

        return highScoreKeeper.getHighScores();
    }
    //sets game as over when no more free squares
    public boolean isGameOver() {

        if(true){
            gameOver = true;
        }
        return gameOver;
    }
    //checks if new high score has been made, and ends the game
    public void gameIsOver() {

        boolean newHighScore = highScoreKeeper.checkHighScore(numberOfActions);
        controller.gameOver(newHighScore);
    }
    //resets the board and repopulates it with a new array of ships
    public void resetBoard(){

        board.resetBoard();
        gameOver = false;
    }

    public void setHitSurrounded(int x, int y) {
        hits[x][y] = false;
    }
}
