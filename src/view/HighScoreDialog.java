/*
  Author: <Christopher O'Driscoll>
  Id: <al0038>
  Study program: <Artificial Intelligence DA272A>
*/
package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

public class HighScoreDialog extends JDialog {

    private final MainFrame view;
    private JTextField txtHSName;

    //a dialog which pops up and asks the user for their name(in case of a new high score). closes itself after use
    public HighScoreDialog(MainFrame view){
        this.view = view;
        setupDialog();
    }
    private void setupDialog(){

        setSize(new Dimension(500, 100));
        setLocationRelativeTo(view);
        setAlwaysOnTop(true);
        setTitle("****Black Wins! *** New High Score!******");

        JPanel hsPanel = new JPanel();
        hsPanel.setPreferredSize(new Dimension(300, 40));
        setContentPane(hsPanel);
        setResizable(false);
        setVisible(true);
        pack();

        txtHSName = new JTextField("Enter your initials here");
        txtHSName.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                txtHSName.setText("");
            }
            @Override
            public void focusLost(FocusEvent e) {
                if(txtHSName.getText().equals("")){
                    txtHSName.setText("A.A.A.");
                }
            }
        });
        hsPanel.add(txtHSName);
        JButton btnAddHS = new JButton("Save High Score");
        btnAddHS.addActionListener(e -> addHighScore());

        hsPanel.add(btnAddHS);
    }
    public void addHighScore(){

        String name = txtHSName.getText();
        view.setHSName(name);
        dispose();
    }
}
