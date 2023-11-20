/*
  Author: <Christopher O'Driscoll>
  Id: <al0038>
  Study program: <Systemutvecklare TGSYA20h>
*/
package view;

import model.Square;

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
    private final int buttonSize;
    private final int size;

    private JButton[][] buttonGrid; //array of buttons which represent each square on board
    private final boolean[][] buttonEnabled;    //keeps track of used/disabled buttons

    public CenterPanelGrid(MainFrame view, int size, int width, int height) {

        this.view = view;
        this.width = width;
        this.height = height;
        this.buttonSize = width/6;
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
                buttonGrid[row][column].setPreferredSize(new Dimension(buttonSize, buttonSize));
                buttonGrid[row][column].add(Box.createRigidArea(new Dimension(buttonSize,buttonSize)));
                buttonGrid[row][column].setIcon(resizeImage("resources/empty.png", buttonSize));
                buttonEnabled[row][column] = false;
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
    public boolean testBoolean = true;
    //if the button hasn't already been pressed, sets a guess in motion
    public void buttonPressed(int x, int y, boolean player1){
        //if valid move, allow button to be pressed
        if(!buttonEnabled[x][y]) return; {
            view.CenterButtonPressed(x, y, testBoolean);
            testBoolean = !testBoolean;
        }
    }
    //change the image on a button to represent it's current state
    public void changeButtonToBlack(int x, int y) {

        buttonGrid[x][y].setIcon(resizeImage("resources/black.png", buttonSize));
        buttonEnabled[x][y] = false;
    }
    public void changeButtonToWhite(int x, int y){

        buttonGrid[x][y].setIcon(resizeImage("resources/white.png", buttonSize));
        buttonEnabled[x][y] = false;
    }
    public void changeButtonToOpen(int x, int y) {

        buttonGrid[x][y].setIcon(resizeImage("resources/open.png", buttonSize));
        buttonEnabled[x][y] = true;
    }
    public void changeButtonToEmpty(int x, int y) {

        buttonGrid[x][y].setIcon(resizeImage("resources/empty.png", buttonSize));
        buttonEnabled[x][y] = false;
    }

    //disables all the buttons in case of game over state
    public void disableButtons() {
        for (int i = 0; i<size; i++){
            for(int j = 0; j<size; j++){
                buttonEnabled[i][j] = false;
            }
        }
    }

    public void updateSquare(Square square, int x, int y) {
        switch (square.getState()) {
            case Black -> changeButtonToBlack(x, y);
            case White -> changeButtonToWhite(x,y);
            case Open -> changeButtonToOpen(x, y);
            default -> changeButtonToEmpty(x,y);
        }
    }
}
