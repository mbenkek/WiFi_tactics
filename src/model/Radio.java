package model;

import java.io.Serializable;
import java.awt.*;

public class Radio implements Serializable {
    // snaga - označava snagu radija je u dB.
    // local - označava lokaciju radija.       

    public Radio(double snaga, Point local) {
        if (snaga > 0) {
            snagaTX = snaga;
        }
        smjestaj = local;
    }

    public double getSnagaTX() {
        return snagaTX;
    }

    public void setSnagaTX(double jakost) {
        if (jakost > 0) {
            snagaTX = jakost;
        }
    }

    public Point getSmjestaj() {
        return smjestaj;
    }

    public String toString() {
        return "Snaga: " + snagaTX + " Smjestaj: " + smjestaj;
    }
    private double snagaTX = 14;
    private Point smjestaj;
}