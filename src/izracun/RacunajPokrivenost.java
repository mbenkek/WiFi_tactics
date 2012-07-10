package izracun;

import model.ElementiPrikaza;
import model.Prepreka;
import model.Oprema;
import java.util.*;
import java.awt.*;

public class RacunajPokrivenost {

    public RacunajPokrivenost() {
        zadnjaIzmjera = 0;
        privremenaIzmjera = 0;
        FREQ = 800;
        prolazakZidovima = new Vector();
        rezultat = new Vector();
        osjetljivost = 6;
        xPocetni = 0;
        yPocetni = 0;
        AZidovi = 0;
        setSkala(10);
        definicijeParametara();
    }

    public RacunajPokrivenost(int sens, double f) {
        osjetljivost = sens;
        FREQ = f;
        zadnjaIzmjera = 0;
        privremenaIzmjera = 0;
        prolazakZidovima = new Vector();
        rezultat = new Vector();
        xPocetni = 0;
        yPocetni = 0;
        AZidovi = 0;
        setSkala(10);
        definicijeParametara();
    }

    private void definicijeParametara() {
        if (FREQ < 1900) {
            N = 3.5;
            A0 = 38;
        } else if (FREQ == 1900) {
            A0 = 38;
            N = 3.5;
        } else if (FREQ <= 2450) {
            A0 = 40.2;
            N = 4.2;
        } else if (FREQ <= 2500) {
            A0 = 38;
            N = 3.5;
        } else if (FREQ <= 5000) {
            A0 = 46.4;
            N = 3.5;
        } else { // Za sve FREQ veće od 5 Ghz postavljamo vrijedosti od 5.25 Ghz
            A0 = 46.8;
            N = 4.6;
        }
    }
    // Postavlja razinu zuma

    public void setSkala(int skl) {
        if (skl > 0) {
            skala = skl;
        }
    }
    // Računa udaljenost dvije točke

    public static double racunajUdaljenostTockaAntena(Point inicial, Point antena) {
        double dx = Math.pow((antena.getX() - inicial.getX()) / skala, 2);
        double dy = Math.pow((antena.getY() - inicial.getY()) / skala, 2);
        return Math.sqrt((dx + dy));
    }

    // Račun heat-karte po modelu potencija
    public Vector calcModelPotencija(Vector prepreke, Vector oprema, int visina, int sirina) {
        int antenaSignalMax = 0;
        for (int kolona = 0; kolona < sirina; kolona += osjetljivost) {
            for (int redak = 0, i; redak < visina; redak += osjetljivost) {
                najvecaIzmjera = Double.MIN_VALUE;
                for (i = 0, e = oprema.elements(), privremenaIzmjera = 0; e.hasMoreElements(); i++) {
                    eq = (Oprema) e.nextElement();
                    udaljenost = racunajUdaljenostTockaAntena(new Point((kolona + osjetljivost / 2), (redak + osjetljivost / 2)), eq.getAntena().getSmjestaj());
                    atenuacija = A0 + 10 * N * Math.log(udaljenost) / Math.log(10);
                    privremenaIzmjera = eq.getRadio().getSnagaTX() + eq.getAntena().getDobitAntene() - atenuacija;
                    if ((i == 0) || (Math.round(privremenaIzmjera) > Math.round(najvecaIzmjera))) {
                        antenaSignalMax = i;
                        najvecaIzmjera = privremenaIzmjera;
                    }
                }
                if (Math.round(zadnjaIzmjera) != Math.round(najvecaIzmjera)) {
                    rezultat.add(new ElementiPrikaza(new Point(xPocetni, yPocetni), (kolona - xPocetni), (redak - yPocetni), najvecaIzmjera, antenaSignalMax));
                    xPocetni = (kolona);
                    yPocetni = (redak) + 3;
                    zadnjaIzmjera = privremenaIzmjera;
                }
            }
        }
        return rezultat;
    }
    // Račun heat-karte po modelu Montley-Keenan

    public Vector calcMontleyKeenan(Vector prepreke, Vector oprema, int visina, int sirina) {
        int antenaSignalMax = 0;
        for (int kolona = 0; kolona < sirina; kolona += osjetljivost) {
            for (int redak = 0, i; redak < visina; redak += osjetljivost) {
                najvecaIzmjera = Double.MIN_VALUE;
                for (i = 0, e = oprema.elements(), privremenaIzmjera = 0; e.hasMoreElements(); i++) {
                    eq = (Oprema) e.nextElement();
                    // Uzeli smo neku točku na grafu (idemo redom) i kordinate nekog AP-a (enumeriramo)
                    // te vidjeli jakost signala u nekoj točki bez gušenja, tj. samo sa gušenjem 
                    // prouzrokovanim propagacijom signala kroz zrak
                    udaljenost = racunajUdaljenostTockaAntena(new Point((kolona + osjetljivost / 2), (redak + osjetljivost / 2)), eq.getAntena().getSmjestaj());
                    //Gušenje slobodnog prostora
                    A0 = 32.4 + 20 * Math.log(FREQ) / Math.log(10) + 20 * Math.log(udaljenost) / Math.log(10);
                    atenuacija = A0 + 10 * Math.log(udaljenost) / Math.log(10);
                    for (e2 = prepreke.elements(), AZidovi = 0; e2.hasMoreElements();) {
                        ob = (Prepreka) e2.nextElement();
                        // Uzeli smo neku točku na grafu (idemo redom) i kordinate nekog AP-a (enumeriramo), 
                        // te provjerili da li vektor provučen kroz njih siječe zid
                        Pii = new Point((kolona + osjetljivost / 2), (redak + osjetljivost / 2));
                        Pi = eq.getAntena().getSmjestaj();
                        if (ob.provjeraPresjeka(Pi, Pii)) { // Vektor AP-točka siječe trenutno selektirani zid
                            // Ukupno gušenje zidova=gušenje starih + novog
                            AZidovi += ob.getGusenjeZida();
                        }
                    }
                    // Privremena vrijednost jakosti signala na nekoj točki za odabrani AP i 
                    // nakon što smo prošli sve zidove za taj AP
                     privremenaIzmjera = eq.getRadio().getSnagaTX() - atenuacija - AZidovi;
                   

                    // Ako je signal za odabrani AP bolji u odabranoj točki od prethodnog najboljeg rezultata, to je onda
                    // naš novi najbolji rezultat
                    if ((i == 0) || (Math.round(privremenaIzmjera) > Math.round(najvecaIzmjera))) {
                        // Bilježimo identifikaciju AP-a koji dominira za odabranu točku u prostoru
                        antenaSignalMax = i;
                        najvecaIzmjera = privremenaIzmjera;
                    }
                }
                // Nova vrijednost jakosti u točki prostora se razlikuje od prethodne i ažuriramo graf
                // Bilježimo koordinate, jakost signala i od kojeg je AP-a
                if (Math.round(zadnjaIzmjera) != Math.round(najvecaIzmjera)) {
                    rezultat.add(new ElementiPrikaza(new Point(xPocetni, yPocetni), (kolona - xPocetni), (redak - yPocetni), najvecaIzmjera, antenaSignalMax));
                    xPocetni = (kolona);
                    yPocetni = (redak) + 3;
                    zadnjaIzmjera = privremenaIzmjera;
                }
            }
        }
        return rezultat;
    }
    private Point Pii, Pi;
    private double A0, N, udaljenost, zadnjaIzmjera, privremenaIzmjera, najvecaIzmjera, atenuacija, FREQ, AZidovi;
    private Vector prolazakZidovima, rezultat;
    private Oprema eq;
    private Prepreka ob, ob2;
    private Enumeration e, e2;
    // Osjetljivost je  finoća prikaza grafa
    private int osjetljivost, xPocetni, yPocetni;
    // Skala je razina zuma
    private static int skala = 10;
}