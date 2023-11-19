/*
  Author: <Christopher O'Driscoll>
  Id: <al0038>
  Study program: <Systemutvecklare TGSYA20h>
*/
package view;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class CenterPanelGrid extends JPanel{

    private final MainFrame view;

    private GridLayout layout;

    private final int height;
    private final int width;

    private final int size;

    private JButton[][] buttonGrid; //array of buttons which represent each square on board
    private final boolean[][] buttonEnabled;    //keeps track of used/disabled buttons

    public CenterPanelGrid(MainFrame view, int size, int width, int height) {

        this.view = view;
        this.width = width;
        this.height = height;
        this.size = size;
        buttonEnabled = new boolean[size][size];
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
                buttonGrid[row][column].setPreferredSize(new Dimension(50, 50));
                buttonGrid[row][column].add(Box.createRigidArea(new Dimension(50,50)));
                buttonGrid[row][column].setIcon(resizeImage("resources/empty.png", 50));
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
    // Method to resize the image
    public static ImageIcon resizeImage(String imagePath, int width) {
        BufferedImage img = null;
        try {
            img = ImageIO.read(new File(imagePath));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Image resizedImage = img.getScaledInstance(width, width, Image.SCALE_SMOOTH);
        return new ImageIcon(resizedImage);
    }
    //if the button hasn't already been pressed, sets a guess in motion
    public void buttonPressed(int x, int y, boolean player1){

        //if valid move, allow button to be pressed
        if(!buttonEnabled[x][y]) {
            buttonEnabled[x][y] = true;
            view.CenterButtonPressed(x, y, player1);
        }
    }
    //change the image on a button to represent it's current state
    public void changeButtonImageToBlack(int x, int y) {

        buttonGrid[x][y].setIcon(resizeImage("resources/black.png", 50));
    }
    public void changeButtonImageToWhite(int x, int y){

        buttonGrid[x][y].setIcon(resizeImage("resources/white.png", 50));
    }
    public void changeButtonImageToOpen(int x, int y) {

        buttonGrid[x][y].setIcon(resizeImage("resources/open.png", 50));
    }
    public void changeButtonImageToEmpty(int x, int y) {

        buttonGrid[x][y].setIcon(resizeImage("resources/empty.png", 50));
    }
    //resets all the buttons
    public void resetGrid(){

        for (int i = 0; i<size; i++){
            for(int j = 0; j<size; j++){
                buttonGrid[i][j].setIcon(resizeImage("resources/empty.png", 50));
                buttonEnabled[i][j] = false;
            }
        }
    }

    private void enableButton(int x, int y) {
        buttonEnabled[x][y] = true;
        changeButtonImageToOpen(x, y);
    }

    //disables all the buttons in case of game over state
    public void disableButtons() {
        for (int i = 0; i<size; i++){
            for(int j = 0; j<size; j++){
                buttonEnabled[i][j] = true;
            }
        }
    }
}
