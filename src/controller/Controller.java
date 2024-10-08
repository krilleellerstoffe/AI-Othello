/*
  Author: <Christopher O'Driscoll>
  Id: <al0038>
  Study program: <Artificial Intelligence DA272A>
*/
package controller;

import ai.AIPlayer;
import model.GameManager;
import model.SquareState;
import view.MainFrame;

public class Controller {

    private final GameManager model;
    private final MainFrame view;
    private AIPlayer aiPlayer;

    public Controller(){

        int size = MainFrame.getBoardSize();    //asks the user for a board size, uses this size to create components
        model = new GameManager(this, size);
        view = new MainFrame(this, size);
        aiPlayer = new AIPlayer(this);
        updateHighScores();
        updateBoard();

    }

    //checks who pressed the button. Updates view based on result. Finally, checks if the game is over.
    public void buttonPressed(int x, int y, boolean blacksTurn) {
        if (blacksTurn) {
            //first turn selected square to black
            model.getBoard().setSquare(x, y, SquareState.Black);
            //search for opposite squares to flip
            model.getBoard().validMove(x, y, SquareState.White, true);
            //update open squares for next player
            model.getBoard().setBlacksTurn(false);
            model.getBoard().updateOpenSquares(SquareState.Black);
        }
        else {
            //first turn selected square to black
            model.getBoard().setSquare(x, y, SquareState.White);
            //search for opposite squares to flip
            model.getBoard().validMove(x, y, SquareState.Black, true);
            //update open squares for next player
            model.getBoard().setBlacksTurn(true);
            model.getBoard().updateOpenSquares(SquareState.White);
        }
        System.out.println("updating board");
        updateBoard();
        if (model.isGameOver()) {
            model.gameIsOver();
        }
    }

    public void updateBoard() {
        view.updateBoard(model.getBoard());
    }

    //relays a button press from the AI player
    public void pressButtonAI(int x, int y){

        view.pressButtonAI(x,y);
    }

    //if game over, checks for a new high score
    public void gameOver(boolean newHighScore, int blackScore, int whiteScore) {
        int scoreDiff = blackScore - whiteScore;
        if(newHighScore && scoreDiff > 0){
            view.getHighScoreName();
        }
        else if (scoreDiff == 0) {
            view.draw();
        }
        else
            view.betterLuck(scoreDiff);
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
        view.resetTestPlayer();
        aiPlayer = new AIPlayer(this);
    }

    public void letAIPlay() {
        //let the AI search for the best button to press, then press it!
        aiPlayer.nextBestMove();
    }

    public GameManager getModel() {
        return model;
    }

    public void randomGuess() {
        aiPlayer.nextRandomMove();
    }

    public boolean isPlayerTurn() {
        return model.getBoard().isBlacksTurn();
    }

    public void setPlayerTurn(boolean b) {
        model.getBoard().setBlacksTurn(b);
    }
}
