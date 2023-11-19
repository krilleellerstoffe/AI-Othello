/*
  Author: <Christopher O'Driscoll>
  Id: <al0038>
  Study program: <Systemutvecklare TGSYA20h>
*/
package controller;

import model.AIPlayer;
import model.GameManager;
import model.SquareState;
import view.MainFrame;

public class Controller {

    private final GameManager model;
    private final MainFrame view;
    private AIPlayer aiPlayer;
    private final int size;

    public Controller(){

        size = MainFrame.getBoardSize();    //asks the user for a board size, uses this size to create components
        model = new GameManager(this, size);
        view = new MainFrame(this, size);
        aiPlayer = new AIPlayer(this, model);
        updateHighScores();
        updateBoard();

    }
    //checks who pressed the button. Updates view based on result. Finally, checks if the game is over.
    public void buttonPressed(int x, int y, boolean player1) {

        if (player1) {
            model.getBoard().setSquare(x, y, SquareState.Black);
            model.getBoard().updateOpenSquares(SquareState.White);
            view.enableAIButton(true);
        }
        else {
            model.getBoard().setSquare(x, y, SquareState.White);
            model.getBoard().updateOpenSquares(SquareState.Black);
            view.enableAIButton(false);
        }

        view.updateBoard(model.getBoard());
        //update score
        //update gamestate
        //update available squares for each player

    }

    public void updateBoard() {
        view.updateBoard(model.getBoard());
    }
    //relays a button press from the AI player
    public void pressButtonAI(int x, int y){

        view.pressButtonAI(x,y);
    }

    //if game over, checks for a new high score
    public void gameOver(boolean newHighScore) {

        if(newHighScore){
            view.getHighScoreName();
        }
        else
            view.betterLuck();
        updateHighScores();
    }

    //relays a name to the high score list and updates the list in view
    public void setHSName(String name) {
        model.setNewHighScore(name);
        updateHighScores();
    }

    //gets the list of current high scores
    public void updateHighScores(){

        view.updateHighScores(model.getHighScores());
    }
    //resets the board and AI
    public void resetGame() {

        model.resetBoard();
        aiPlayer = new AIPlayer(this, model);
    }

    public void letAIPlay() {
        //let the AI search for the best button to press, then press it!
        aiPlayer.nextBestMove();
    }
}
