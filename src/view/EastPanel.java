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

public class EastPanel extends JPanel {

    private final MainFrame view;
    private GridLayout layout;

    private final int width;
    private final int height;

    private JLabel lblLastAction;
    private JButton btnAIGuess;
    private JButton btnResetGame;
    private JButton btnNewRules; //if user wants to change the size of the board

    public EastPanel(MainFrame view, int width, int height) {

        this.view = view;
        this.width = width;
        this.height = height;

        setupPanel();
    }
    private void setupPanel() {

        layout = new GridLayout(7, 2);
        setLayout(layout);
        setSize(width, height);

        setBorder(BorderFactory.createEmptyBorder(10,10,10,10));

        lblLastAction = new JLabel();
        add(lblLastAction);

        btnNewRules = new JButton("Change board size");
        btnNewRules.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                view.newRules();
            }
        });
        add(btnNewRules);
    }

    //displays the result of previous guess
    public void updateLastActionInfo(String text){

        lblLastAction.setText(text);
    }

}
