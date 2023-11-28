/*
  Author: <Christopher O'Driscoll>
  Id: <al0038>
  Study program: <Artificial Intelligence DA272A>
*/
package view;

import controller.Controller;
import model.GameBoard;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {

    private final Controller controller;
    private MainPanel panel;

    private final int width = 1000;
    private final int height = 600;

    private final int size;

    public MainFrame (Controller controller, int size){

        this.controller = controller;
        this.size = size;
        setupFrame();
    }

    private void setupFrame () {

        int offsetX = width/4;
        int offsetY = width/4;

        setSize(width, height);
        setLocation(offsetX, offsetY);
        setTitle("AI-Othello by Christopher O'Driscoll");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        panel = new MainPanel(this, width, height, size);
        setContentPane(panel);
        setResizable(false);
        setVisible(true);
        pack();
        centreWindow(this);
    }
    public static void centreWindow(Window frame) {
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (int) ((dimension.getWidth() - frame.getWidth()) / 2);
        int y = (int) ((dimension.getHeight() - frame.getHeight()) / 2);
        frame.setLocation(x, y);
    }
    //ask the player how big the board should be (min 4x4, max 8x8)
    public static int getBoardSize() {

        int choice = 4;
        try {
            choice = Integer.parseInt(JOptionPane.showInputDialog("How many squares wide should the board be? (Min 4, Max 8)"));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Please enter a number between 4 and 8!");
        }
        if (choice > 8) {
            choice = 8;
        } else if (choice < 4) {
            choice = 4;
        }
        return choice;
    }
    //discard current window and create new game
    public void newRules(){
        dispose();
        new Controller();
    }
    //if button successfully pressed, tell the controller about it
    public void CenterButtonPressed(int x, int y, boolean player1){
        controller.buttonPressed(x, y, player1);
    }

    //return the height of the grid
    public int getRows() {
        return size;
    }
    //button pressed by AI player
    public void pressButtonAI(int x, int y) {
        panel.getPnlCenter().buttonPressed(x, y);
    }
    //ask controller to tell AI it can play
    public void letAIPressButton() {
        controller.letAIPlay();
    }
    //commiserates the player if loss or no high score
    public void betterLuck(int scoreDiff) {
        if (scoreDiff > 0) {
            JOptionPane.showMessageDialog(null, "Black Wins! But no high score, better luck next time!");
        }
        else {
            JOptionPane.showMessageDialog(null, "White Wins! Better luck next time!");

        }
    }
    //opens dialog to get player's name when high score made
    public void getHighScoreName() {

        new HighScoreDialog(this);
    }
    //adds name to high score list
    public void setHSName(String name) {

        controller.setHSName(name);
    }
    //update high score list
    public void updateHighScores(String[] highScores) {
        panel.getPnlWest().updateHighScores(highScores);
    }
    //information on previous guess

    //clear board and start new game
    public void resetGame() {
        controller.resetGame();
        controller.updateBoard();
    }

    public void updateBoard(GameBoard board) {
        panel.getPnlEast().updateGameStateInfo(("Black: " + board.getBlackScore() + "  -  White: " + board.getWhiteScore()));
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                panel.getPnlCenter().updateSquare(board.getSquare(i,j), i, j);
            }
        }
    }

    public void enableAIButton(boolean enable) {
        panel.getPnlWest().enableAIButton(enable);
    }

    public void resetTestPlayer() {
        panel.getPnlCenter().playerTurn = true;
    }

    public void draw() {
        JOptionPane.showMessageDialog(null, "Draw! Better luck next time!");
    }

    public boolean isAIEnabled() {
        return panel.getPnlWest().isAIButtonEnabled();
    }
}
