/*
  Author: <Christopher O'Driscoll>
  Id: <al0038>
  Study program: <Artificial Intelligence DA272A>
*/
package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;

public class WestPanel extends JPanel {

    private final MainFrame view;

    private final int width;
    private final int height;

    private JButton btnAIGuess;
    private JButton btnRandom;
    private JList<String> lstHighScores;

    public WestPanel(MainFrame view, int width, int height) {

        this.view = view;
        this.width = width;
        this.height = height;

        setupPanel();
    }
    private void setupPanel() {

        setSize(width, height);
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        setBorder(BorderFactory.createEmptyBorder(10,10,10,10));

        btnAIGuess = new JButton("AI guess");
        btnAIGuess.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnAIGuess.add(Box.createRigidArea(new Dimension(100,50)));
        btnAIGuess.addActionListener(e -> {
            view.letAIPressButton();
        });
        btnAIGuess.setMnemonic(KeyEvent.VK_Q);
        btnAIGuess.setEnabled(true);
        add(btnAIGuess);

        add(Box.createRigidArea(new Dimension(0, 10)));
        btnRandom = new JButton("Random guess");
        btnRandom.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnRandom.add(Box.createRigidArea(new Dimension(100,50)));
        btnRandom.addActionListener(e -> {
            view.pressRandomButton();
        });
        btnRandom.setMnemonic(KeyEvent.VK_R);
        btnRandom.setEnabled(true);
        add(btnRandom);
        add(Box.createRigidArea(new Dimension(0, 10)));

        JButton btnResetGame = new JButton("Reset Board");
        btnResetGame.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnResetGame.add(Box.createRigidArea(new Dimension(100,50)));
        btnResetGame.addActionListener(e -> {
            view.resetGame();
        });
        add(btnResetGame);
        add(Box.createRigidArea(new Dimension(0, 10)));

        lstHighScores = new JList<>();
        lstHighScores.setAlignmentX(Component.CENTER_ALIGNMENT);
        lstHighScores.setFixedCellWidth(130);
        String highScoreTitle = String.format("*Best Scores %dx%d*", view.getRows(), view.getRows());
        lstHighScores.setBorder(BorderFactory.createTitledBorder(highScoreTitle));
        add(lstHighScores);

    }

    public void updateHighScores(String[] highScores) {

        lstHighScores.setListData(highScores);
    }



}
