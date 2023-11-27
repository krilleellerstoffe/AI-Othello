/*
  Author: <Christopher O'Driscoll>
  Id: <al0038>
  Study program: <Artificial Intelligence DA272A>
*/
package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EastPanel extends JPanel {

    private final MainFrame view;

    private final int width;
    private final int height;

    private JLabel lblGameState;

    public EastPanel(MainFrame view, int width, int height) {

        this.view = view;
        this.width = width;
        this.height = height;

        setupPanel();
    }
    private void setupPanel() {

        GridLayout layout = new GridLayout(7, 2);
        setLayout(layout);
        setSize(width, height);

        setBorder(BorderFactory.createEmptyBorder(10,10,10,10));

        lblGameState = new JLabel();
        add(lblGameState);

        //TODO Display game state, scores, latest search results, etc
        //if user wants to change the size of the board
        JButton btnNewRules = new JButton("Change board size");
        btnNewRules.addActionListener(e -> view.newRules());
        add(btnNewRules);
    }

    //displays the result of previous guess
    public void updateGameStateInfo(String text){

        lblGameState.setText(text);
    }
}
