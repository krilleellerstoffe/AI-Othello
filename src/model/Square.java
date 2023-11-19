/*
  Author: <Christopher O'Driscoll>
  Id: <al0038>
  Study program: <Systemutvecklare TGSYA20h>
*/
package model;

import javax.swing.*;

public class Square {

    private Disk disk;
    private ImageIcon diskIcon;

    public Square(Disk disk, ImageIcon diskIcon) {
        this.disk = disk;
        this.diskIcon = diskIcon;
    }

    public Disk getDisk() {
        return disk;
    }

    public void setDisk(Disk disk) {
        this.disk = disk;
    }

    public ImageIcon getDiskIcon() {
        return diskIcon;
    }

    public void setDiskIcon(ImageIcon diskIcon) {
        this.diskIcon = diskIcon;
    }


}
