/*
  Author: <Christopher O'Driscoll>
  Id: <al0038>
  Study program: <Systemutvecklare TGSYA20h>
*/
package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CenterPanelGrid extends JPanel{

    private final MainFrame view;

    private GridLayout layout;

    private final int height;
    private final int width;

    private final int size;

    private JButton[][] buttonGrid; //array of buttons which represent each square on board
    private final boolean[][] buttonAlreadyUsed;    //keeps track of used/disabled buttons

    public CenterPanelGrid(MainFrame view, int size, int width, int height) {

        this.view = view;
        this.width = width;
        this.height = height;
        this.size = size;
        buttonAlreadyUsed = new boolean[size][size];
        setupPanel();
        createComponents();
    }

    private void setupPanel() {

        layout = new GridLayout(size, size);
        setLayout(layout);
        setSize(width,height);

        setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
    }
    private void createComponents() {

        buttonGrid = new JButton[size][size];

        for (int row = 0; row < buttonGrid.length; row++) {
            for(int column = 0; column < buttonGrid[row].length; column++){
                buttonGrid[row][column] = new JButton();
                //buttonGrid[row][column].setPreferredSize(new Dimension(50, 50));
                buttonGrid[row][column].add(Box.createRigidArea(new Dimension(50,50)));
                buttonGrid[row][column].setIcon(new ImageIcon("resources/open.png"));
                int x = row;
                int y = column;
                buttonGrid[row][column].addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        buttonPressed(x, y, true);
                    }
                });
                add(buttonGrid[row][column]);
            }
        }
    }
    //if the button hasn't already been pressed, sets a guess in motion
    public void buttonPressed(int x, int y, boolean player1){

        if(!buttonAlreadyUsed[x][y]) {
            buttonAlreadyUsed[x][y] = true;
            view.CenterButtonPressed(x, y, player1);
        }
    }
    //change the image on a button to represent it's current state
    public void changeButtonImageToBlack(int x, int y) {

        buttonGrid[x][y].setIcon(new ImageIcon("resources/black.png"));
    }
    public void changeButtonImageToWhite(int x, int y){

        buttonGrid[x][y].setIcon(new ImageIcon("resources/white.png"));
    }
    public void changeButtonImageToOpen(int x, int y) {

        buttonGrid[x][y].setIcon(new ImageIcon("resources/open.png"));
    }
    //resets all the buttons
    public void resetGrid(){

        for (int i = 0; i<size; i++){
            for(int j = 0; j<size; j++){
                buttonGrid[i][j].setIcon(new ImageIcon("resources/wave.png"));
                buttonAlreadyUsed[i][j] = false;
            }
        }

    }
    //disables all the buttons in case of game over state
    public void disableButtons() {
        for (int i = 0; i<size; i++){
            for(int j = 0; j<size; j++){
                buttonAlreadyUsed[i][j] = true;
            }
        }
    }
}
