package model;

import java.io.Serializable;
import java.awt.*;

public class Prepreka implements Serializable {
// pI - označava početnu točku prepreke.
// pF -  označava konačnu točku prepreke.
// tipPrepreke -  vrstu zida: 1-tanki, 2-debeli, 3-metalni.
// FREQ - označava frekvenciju prepreke.

    public Prepreka(Point pI, Point pF, int tipPrepreke, double FREQ) {
        // pI je niže po visini od pF
        if (pI.getY() > pF.getY()) {
            pInicial = pF;
            pFinal = pI;
        } else {
            pInicial = pI;
            pFinal = pF;
        }
        racunajParametre();
        setVrstaZida(tipPrepreke, FREQ);
    }

    public int getTipZida() {
        return vrstaZida;
    }

    public double getGusenjeZida() {
        return gusenjeZida;
    }

    public Point getPInicial() {
        return pInicial;
    }

    public Point getPFinal() {
        return pFinal;
    }

    public double getNagib() {
        return nagib;
    }

    public double getB() {
        return b;
    }

    public void setVrstaZida(int tip, double freq) {
        if (tip > -1) {
            // Mogućnosti
            vrstaZida = tip;
            if (freq <= 1900) {
                switch (tip) {
                    case 1://tanki
                        gusenjeZida = 2.1;
                        break;
                    case 2://debeli
                        gusenjeZida = 4.4;
                        break;
                    case 3://metalni
                        gusenjeZida = 1.3;
                        break;
                    default:
                        gusenjeZida = 2.1;
                };
            } else if (freq <= 2450) {
                switch (tip) {
                    case 1://tanki
                        gusenjeZida = 5.9;
                        break;
                    case 2://debeli
                        gusenjeZida = 8.0;
                        break;
                    case 3://metalni
                        gusenjeZida = 4.1;
                        break;
                    default:
                        gusenjeZida = 5.9;
                };
            } else if (freq > 2450) { // Za frekvencije veće od 2.45 dodijeljujemo vrijednosti za one od 5 GHz
                switch (tip) {
                    case 1://tanki
                        gusenjeZida = 6.5;
                        break;
                    case 2://debeli
                        gusenjeZida = 11.7;
                        break;
                    case 3://metalni
                        gusenjeZida = 200;
                        break;
                    default:
                        gusenjeZida = 6.5;
                };
            }
        }
    }

    public void setGusenjeZida(double at) {
        gusenjeZida = at;
    }

    public void setPInicial(Point p) {
        pInicial = p;
        racunajParametre();
    }

    public void setPFinal(Point p) {
        pFinal = p;
        racunajParametre();
    }

    private void racunajParametre() {
        // Računa nagib, tj. koeficijent smjera
        if ((Math.abs(pFinal.getX() - pInicial.getX()) >= MINIMALNI_OKVIR_PREPREKE)
                && (Math.abs(pFinal.getY() - pInicial.getY()) >= MINIMALNI_OKVIR_PREPREKE)) {
            nagib = (pFinal.getY() - pInicial.getY()) / (pFinal.getX() - pInicial.getX());
        } else // Koef. smjera pravca 
        {
            nagib = 0;
        }
        // Odsječak na y-osi
        b = pFinal.getY() - nagib * pFinal.getX();
    }
    // Sjecište točke i vektora

    public boolean provjeraPresjeka(Point pI) {
        // Uz koef. pravca 0 ili pravac horizontalno probija točku ili vertikalno
        if (getNagib() == 0) {

            // Računaj vertikalni slučaj
            if ((Math.abs(pInicial.getX() - pFinal.getX()) <= MINIMALNI_OKVIR_PREPREKE)
                    && ((Math.abs(pI.getX() - pInicial.getX()) <= MINIMALNI_OKVIR_PREPREKE)
                    || (Math.abs(pI.getX() - pFinal.getX()) <= MINIMALNI_OKVIR_PREPREKE))
                    && (((pI.getY() + MINIMALNI_OKVIR_PREPREKE) >= pInicial.getY())
                    && ((pI.getY() - MINIMALNI_OKVIR_PREPREKE) <= pFinal.getY()))) {
                return true;
            } else // Računaj horizontalni slučaj
            if ((Math.abs(pFinal.getY() - pInicial.getY()) <= MINIMALNI_OKVIR_PREPREKE)
                    && ((Math.abs(pI.getY() - pInicial.getY()) <= MINIMALNI_OKVIR_PREPREKE)
                    || (Math.abs(pI.getY() - pFinal.getY()) <= MINIMALNI_OKVIR_PREPREKE))
                    && ((((pI.getX() + MINIMALNI_OKVIR_PREPREKE) >= pInicial.getX()) && ((pI.getX() - MINIMALNI_OKVIR_PREPREKE) <= pFinal.getX()))
                    || (((pI.getX() + MINIMALNI_OKVIR_PREPREKE) >= pFinal.getX()) && ((pI.getX() - MINIMALNI_OKVIR_PREPREKE) <= pInicial.getX())))) {
                return true;
            }
            return false;
        } else {
            // Uz koef. pravca različit od nule, pravac siječe točku pod nekim kutem
            // Računali smo nagib po formuli: y = m(x-x1)-y1
            if ((Math.abs(pI.getY() - (getNagib() * pI.getX() + getB())) <= MINIMALNI_OKVIR_PREPREKE)
                    && ((((pI.getX() + MINIMALNI_OKVIR_PREPREKE) >= pInicial.getX())
                    && (pI.getX() <= (pFinal.getX() + MINIMALNI_OKVIR_PREPREKE)))
                    || ((pI.getX() <= pInicial.getX() + MINIMALNI_OKVIR_PREPREKE)
                    && ((pI.getX() + MINIMALNI_OKVIR_PREPREKE) >= pFinal.getX())))) {
                return true;
            } else {
                // Pravac ne probija točku
                return false;
            }
        }
    }
    // Modul vektora

    public static double dajModul(Prepreka o1) {
        return Math.sqrt(Math.pow(o1.getPFinal().getX() - o1.getPInicial().getX(), 2) + Math.pow(o1.getPFinal().getY() - o1.getPInicial().getY(), 2));
    }

    public boolean provjeraPresjeka(Point pI, Point pF) {
        Prepreka ob = new Prepreka(pI, pF, 1, 800);
        // Provjera da li su smjer prepreke i vektora paralelni?
        if (((ob.getNagib() - getNagib()) == 0)) {
            // Vektori su paralelni i ne sijeku se
            return false;
        }
        // Računa X i Y koordinatu točke-sjecišta dvaju pravaca
        // po Xi= b2-b1/m1-m2 (b1,b2 su vrijednosti y, a m1,m2 koeficijenti smjera pravaca),
        // te Yi= b2*m1 - b1*m2 / m1-m2
        Xi = (int) Math.abs(Math.round((ob.getB() - getB()) / (ob.getNagib() - getNagib())));
        Yi = (int) Math.abs(Math.round((ob.getB() * getNagib() - getB() * ob.getNagib()) / (ob.getNagib() - getNagib())));
        // Točka mora ležati i na jednom i na drugom vektoru
        if (provjeraPresjeka(new Point(Xi, Yi)) && ob.provjeraPresjeka(new Point(Xi, Yi))) {
            return true;
        } else {
            return false;
        }
    }

    public String toString() {
        return getClass().getName() + "[m=" + getNagib() + ",b=" + getB() + "]\t " + pInicial + " " + pFinal;
    }
    private Point pInicial, pFinal;
    private double nagib;
    private int Xi, Yi, MINIMALNI_OKVIR_PREPREKE = 1, vrstaZida;
    private double b, gusenjeZida;
}