package model;

public class Disk {

    private DiskColour colour;


    public Disk(DiskColour colour) {
        this.colour = colour;
    }

    public DiskColour getColour() {
        return colour;
    }

    public void setColour(DiskColour colour) {
        this.colour = colour;
    }
}
