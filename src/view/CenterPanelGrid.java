/*
  Author: <Christopher O'Driscoll>
  Id: <al0038>
  Study program: <Artificial Intelligence DA272A>
*/
package view;

import model.Square;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class CenterPanelGrid extends JPanel{

    private final MainFrame view;

    private final int height;
    private final int width;
    private final int buttonSize;
    private final int size;

    private JButton[][] buttonGrid; //array of buttons which represent each square on board
    private final boolean[][] buttonEnabled;    //keeps track of used/disabled buttons

    //First set up the grid and relative sizes
    public CenterPanelGrid(MainFrame view, int size, int width, int height) {

        this.view = view;
        this.width = width;
        this.height = height;
        this.buttonSize = width/7;
        this.size = size;
        buttonEnabled = new boolean[size][size];
        setupPanel();
        createComponents();
    }

    private void setupPanel() {

        GridLayout layout = new GridLayout(0, size + 1);
        setLayout(layout);
        setSize(width,height);
        setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
    }
    private void createComponents() {

        // Add an empty label to occupy the top-left cell
        add(new JLabel());

        for (int i = 0; i < size; i++) {
            JLabel colLabel = new JLabel("" + i, SwingConstants.CENTER);
            add(colLabel);
        }
        buttonGrid = new JButton[size][size];
        //create a grid of buttons for each square on the board
        for (int row = 0; row < buttonGrid.length; row++) {
            JLabel rowLabel = new JLabel(Character.toString((char) ('A' + row)), SwingConstants.CENTER);
            add(rowLabel);
            for(int column = 0; column < buttonGrid[row].length; column++){
                buttonGrid[row][column] = new JButton();
                buttonGrid[row][column].setPreferredSize(new Dimension(buttonSize, buttonSize));
                buttonGrid[row][column].add(Box.createRigidArea(new Dimension(buttonSize,buttonSize)));
                //resize image to fit button
                buttonGrid[row][column].setIcon(resizeImage("resources/empty.png", buttonSize));
                buttonEnabled[row][column] = false;
                int x = row;
                int y = column;
                buttonGrid[row][column].addActionListener(e -> buttonPressed(x, y));
                add(buttonGrid[row][column]);
            }
        }
    }
    // Method to resize the image
    public static ImageIcon resizeImage(String imagePath, int imageSize) {
        BufferedImage img = null;
        try {
            img = ImageIO.read(new File(imagePath));
        } catch (IOException e) {
            e.printStackTrace();
        }
        assert img != null;
        Image resizedImage = img.getScaledInstance(imageSize, imageSize, Image.SCALE_SMOOTH);
        return new ImageIcon(resizedImage);
    }

    //if the button hasn't already been pressed, sets a guess in motion
    public void buttonPressed(int x, int y){
        boolean playerTurn = view.isPlayerTurn();
        //if valid move, allow button to be pressed
        if(!buttonEnabled[x][y]) return; {
            view.CenterButtonPressed(x, y, playerTurn);
            view.setPlayerTurn(!playerTurn);
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

        if (view.isPlayerTurn()) {
            buttonGrid[x][y].setIcon(resizeImage("resources/open.png", buttonSize));
        }
        else {
            buttonGrid[x][y].setIcon(resizeImage("resources/openwhite.png", buttonSize));
        }
        buttonEnabled[x][y] = true;
    }
    public void changeButtonToEmpty(int x, int y) {

        buttonGrid[x][y].setIcon(resizeImage("resources/empty.png", buttonSize));
        buttonEnabled[x][y] = false;
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
