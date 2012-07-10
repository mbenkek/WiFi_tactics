package model;

import java.awt.*;
// Ovo je točka u prostoru za koju bilježimo kordinate, jakost signala i od koje antene primamo taj signal.
public class ElementiPrikaza {

    public ElementiPrikaza(Point loc, double sir, double vis, double izmjerenSignal, int antena) {
        smjestaj = loc;
        sirina = sir;
        visina = vis;
        signal = izmjerenSignal;
        signalAntene = antena;
    }

    public Point getSmjestaj() {
        return smjestaj;
    }

    public double getSirina() {
        return sirina;
    }

    public double getVisina() {
        return visina;
    }

    public double getSinal() {
        return signal;
    }

    public int getAntena() {
        return signalAntene;
    }
    private Point smjestaj;
    private int signalAntene;
    private double sirina, visina, signal;
}