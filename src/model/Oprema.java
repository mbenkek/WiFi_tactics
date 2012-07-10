package model;

import java.io.Serializable;
import java.awt.*;

public class Oprema implements Serializable {
// Podaci o opremi koju definira korisnik.
// local - označva gdje je antena smještena.
// radijus - označava obavijest o promjeru antene koji se iscrtava.

    public Oprema(Point local, int radijus) {
        setAntena(new Antena(1, 1.0, local, radijus));
        setRadio(new Radio(14, local));
    }
    // ant -  označava svojstva antene 
    // rad -  svojstva radia na opremi

    public Oprema(Antena ant, Radio rad) {
        setAntena(ant);
        setRadio(rad);
    }

    public void setAntena(Antena ant) {
        antena = ant;
    }

    public void setRadio(Radio rad) {
        radio = rad;
    }

    public Antena getAntena() {
        return antena;
    }

    public Radio getRadio() {
        return radio;
    }

    public String toString() {
        return radio + " " + antena;
    }
    private Antena antena;
    private Radio radio;
}