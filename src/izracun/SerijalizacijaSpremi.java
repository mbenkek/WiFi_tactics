package izracun;

import model.Radio;
import model.Prepreka;
import model.Antena;
import model.Oprema;
import GUI.Projekt;
import java.awt.*;
import java.util.*;
import java.io.ObjectOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class SerijalizacijaSpremi {

    public SerijalizacijaSpremi(String args, Vector elementSpremanja) {
        String filename = args;
        FileOutputStream fos = null;
        ObjectOutputStream out = null;
        try {
            fos = new FileOutputStream(filename);
            out = new ObjectOutputStream(fos);
            out.writeObject(elementSpremanja);
            out.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}