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
    private GameBoard board;

    private final int size;
    private boolean gameOver;

    public GameManager(Controller controller, int size){

        this.controller = controller;
        highScoreKeeper = new HighScoreKeeper(size);
        board = new GameBoard(this, size);
        this.size = size;
    }

    public GameManager(GameManager model) {
        this.controller = model.controller;
        this.highScoreKeeper = null;
        this.board = new GameBoard(model.board);
        this.size = model.size;
        this.gameOver = model.gameOver;
    }

    //updates high score list with name and score
    public void setNewHighScore(String name) {

        //highScoreKeeper.setNewHighScore(name, numberOfActions);
    }
    //gets a list of high scores
    public String[] getHighScores(){

        return highScoreKeeper.getHighScores();
    }
    //sets game as over when no more free squares
    public boolean isGameOver() {

        //TODO update
        if(true){
            gameOver = true;
        }
        return gameOver;
    }
    //checks if new high score has been made, and ends the game
    public void gameIsOver() {

        //boolean newHighScore = highScoreKeeper.checkHighScore(numberOfActions);
        //controller.gameOver(newHighScore);
    }

    public GameBoard getBoard(){
        return board;
    }
    //resets the board and repopulates it with a new array of ships
    public void resetBoard(){

        board = new GameBoard(this, size);
        gameOver = false;
    }

}
