/*
  Author: <Christopher O'Driscoll>
  Id: <al0038>
  Study program: <Systemutvecklare TGSYA20h>
*/
package view;

import javax.swing.*;
import java.awt.*;

public class MainPanel extends JPanel {

    private final MainFrame view;

    private BorderLayout layout;

    private CenterPanelGrid pnlCenter;
    private WestPanel pnlWest;
    private EastPanel pnlEast;

    private final int height;
    private final int width;

    public MainPanel(MainFrame view, int width, int height, int size) {

        this.view = view;
        this.width = width;
        this.height = height;
        setupPanel(size);
    }

    private void setupPanel(int size) {

        layout = new BorderLayout();
        setLayout(layout);

        pnlCenter = new CenterPanelGrid(view, size, width/2, height);
        pnlWest = new WestPanel(view, width/4, height);
        pnlEast = new EastPanel(view, width/4, height);

        add(pnlCenter, BorderLayout.CENTER);
        add(pnlWest, BorderLayout.WEST);
        add(pnlEast, BorderLayout.EAST);

    }
    public CenterPanelGrid getPnlCenter(){
        return pnlCenter;
    }
    public WestPanel getPnlWest(){
        return pnlWest;
    }
    public EastPanel getPnlEast(){
        return pnlEast;
    }
}
