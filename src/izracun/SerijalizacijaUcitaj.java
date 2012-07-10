package izracun;

import model.Radio;
import model.Prepreka;
import model.Antena;
import model.Oprema;
import java.awt.*;
import java.util.*;
import java.io.ObjectInputStream;
import java.io.FileInputStream;
import java.io.IOException;

public class SerijalizacijaUcitaj {

    public Vector SerijalizacijaUcitaj(String args) {
        String filename = args;
        Vector elementSpremanja = null;
        FileInputStream fis = null;
        ObjectInputStream in = null;
        try {
            fis = new FileInputStream(filename);
            in = new ObjectInputStream(fis);
            elementSpremanja = (Vector) in.readObject();
            in.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        return elementSpremanja;
    }
}