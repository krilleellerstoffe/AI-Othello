/*
  Author: <Christopher O'Driscoll>
  Id: <al0038>
  Study program: <Artificial Intelligence DA272A>
*/
package model;

import controller.Controller;

public class GameManager {

    private final Controller controller;
    private final HighScoreKeeper highScoreKeeper;
    private GameBoard board;

    private final int size;
    private boolean gameOver;

    public GameManager(Controller controller, int size){

        this.controller = controller;
        highScoreKeeper = new HighScoreKeeper();
        board = new GameBoard(this, size);
        this.size = size;
    }
    //updates high score list with name and score
    public void setNewHighScore(String name) {

        highScoreKeeper.setNewHighScore(name, board.getBlackScore());
    }
    //gets a list of high scores
    public String[] getHighScores(){

        return highScoreKeeper.getHighScores();
    }
    //sets game as over when no more free squares
    public boolean isGameOver() {

        //TODO update
        if(board.getValidMoves() <= 0){
            gameOver = true;
        }
        return gameOver;
    }
    //checks if new high score has been made, and ends the game
    public void gameIsOver() {

        int blackScore = board.getBlackScore();
        int whiteScore = board.getWhiteScore();
        boolean newHighScore = highScoreKeeper.checkHighScore(blackScore);
        controller.gameOver(newHighScore, blackScore, whiteScore);
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
