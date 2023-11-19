package model;

import javax.swing.*;

public class Disk {

    private DiskColour colour;
    private ImageIcon diskIcon;

    public Disk(DiskColour colour, ImageIcon diskIcon) {
        this.colour = colour;
        this.diskIcon = diskIcon;
    }

    public DiskColour getColour() {
        return colour;
    }

    public void setColour(DiskColour colour) {
        this.colour = colour;
    }

    public ImageIcon getDiskIcon() {
        return diskIcon;
    }

    public void setDiskIcon(ImageIcon diskIcon) {
        this.diskIcon = diskIcon;
    }
}
