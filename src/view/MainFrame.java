/*
  Author: <Christopher O'Driscoll>
  Id: <al0038>
  Study program: <Systemutvecklare TGSYA20h>
*/
package view;

import controller.Controller;

import javax.swing.*;

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
    }
    //ask the player how big the board should be (min 4x4, max 8x8)
    public static int getBoardSize() {

        int choice = 4;
        try {
            choice = Integer.parseInt(JOptionPane.showInputDialog("How many squares wide should the board be? (Min 4, Max 8)"));
        } catch (Exception e) {

        }
        if (choice > 8) {
            choice = 8;
            //TODO show error message and confirm board is 8 wide
        } else if (choice < 4) {
            choice = 4;
            //TODO show error message and confirm board is 4 wide
        }
        return choice;
    }
    public void newRules(){

        dispose();
        new Controller();
    }

    public void CenterButtonPressed(int x, int y, boolean player1){

        controller.buttonPressed(x, y, player1);
    }

    public void changeButtonImageToBlack(int x, int y) {
        panel.getPnlCenter().changeButtonImageToBlack(x,y);
    }
    public void changeButtonImageToWhite(int x, int y) {
        panel.getPnlCenter().changeButtonImageToWhite(x, y);
    }
    public void changeButtonImageToOpen(int x, int y) {
        panel.getPnlCenter().changeButtonImageToOpen(x, y);
    }
    public void changeButtonImageToEmpty(int x, int y) {
        panel.getPnlCenter().changeButtonImageToEmpty(x, y);
    }


    public int getRows() {

        return size;
    }

    public void pressButtonAI(int x, int y) {

        panel.getPnlCenter().buttonPressed(x, y, false);
    }
    public void letAIPressButton() {
        controller.letAIPlay();
    }
    //commiserates the player
    public void betterLuck() {

        JOptionPane.showMessageDialog(null, "No high score, better luck next time!");
    }
    //opens dialog to get player's name when high score made
    public void getHighScoreName() {

        new HighScoreDialog(this);
    }
    public void setHSName(String name) {

        controller.setHSName(name);
    }
    public void updateHighScores(String[] highScores) {
        panel.getPnlWest().updateHighScores(highScores);
    }
    //information on previous guess

    public void resetGame() {

        controller.resetGame();
        panel.getPnlCenter().resetGrid();
    }
    public void gameOver() {
        panel.getPnlCenter().disableButtons();
        panel.getPnlEast().updateLastActionInfo("All ships sunk! Game Over");
    }
}
