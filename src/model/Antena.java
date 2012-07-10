package model;

import java.io.Serializable;
import java.awt.*;

public class Antena implements Serializable {
// Ovaj razred predstavlja karakteristike antene.
// vrsta - označava vrstu zida: tanki, debeli, metalni.
// dobit - označava dobit antene u db. 
// mjesto - sještaj antene. 
// radijus - promjer kruga koji se iscrtava.

    public Antena(int vrsta, double dobit, Point mjesto, int radijus) {
        if (radijus > 0) {
            MINIMALNI_OKVIR_ANTENE = radijus;
        }
        if (vrsta > 0) {
            vrstaAntene = vrsta;
        }
        if (dobit > 0) {
            dobitAntene = dobit;
        }
        smjestaj = mjesto;
    }

    public int getVrstaAntene() {
        return vrstaAntene;
    }

    public void setVrstaAntene(int vrsta) {
        if (vrsta > 0) {
            vrstaAntene = vrsta;
        }
    }

    public double getDobitAntene() {
        return dobitAntene;
    }

    public void setDobitAntene(double dobit) {
        dobitAntene = dobit;
    }

    public void setSmjestaj(Point mjesto) {
        smjestaj = mjesto;
    }

    public Point getSmjestaj() {
        return smjestaj;
    }

    public boolean provjeraPresjeka(Point pI) {
        if ((((pI.getX() - Math.abs(Math.round(smjestaj.getX()))) <= MINIMALNI_OKVIR_ANTENE) && (pI.getX() - Math.abs(Math.round(smjestaj.getX()))) >= 0)) {
            if (((pI.getY() - Math.abs(Math.round(smjestaj.getY()))) <= MINIMALNI_OKVIR_ANTENE) && (pI.getY() - Math.abs(Math.round(smjestaj.getY()))) >= 0) {
                return true;
            }
        }
        return false;
    }

    public String toString() {
        return "Vrsta:" + vrstaAntene + " Dobit:" + dobitAntene + " Smjestaj:" + smjestaj;
    }
    private int vrstaAntene = 1;
    private double dobitAntene = 0;
    private Point smjestaj;
    private int MINIMALNI_OKVIR_ANTENE = 10;
}