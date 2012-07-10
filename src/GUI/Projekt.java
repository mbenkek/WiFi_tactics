package GUI;

import izracun.RacunajPokrivenost;
import izracun.SerijalizacijaSpremi;
import izracun.SerijalizacijaUcitaj;
import model.ElementiPrikaza;
import model.Prepreka;
import model.Radio;
import model.Antena;
import model.Oprema;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;

public class Projekt extends JFrame {

    public Projekt() {
        super("WiFi tactics");
        this.setMaximizedBounds(new Rectangle(1250, 650));
        setSize(1250, 650);
        setLocation(75, 75);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        barMenu = new JMenuBar();
        menuCrtez = new JMenu("Crtež");
        menuPomoc = new JMenu("Pomoć");
        menuItemNovo = new JMenuItem("Novi (zatvoreni prostor, WLAN, 2400 MHz)");
        menuItemNovo2 = new JMenuItem("Novi (otvoreni prostor, EDGE, 900 MHz)");
        menuItemNovo3 = new JMenuItem("Novi (otvoreni prostor, 3G, 2100 MHz)");
        menuItemPomoc = new JMenuItem("Upute");
        menuItemOProgramu = new JMenuItem("O projektu");
        menuCrtez.add(menuItemNovo);
        menuCrtez.add(menuItemNovo2);
        menuCrtez.add(menuItemNovo3);
        menuPomoc.add(menuItemPomoc);
        menuPomoc.add(menuItemOProgramu);
        barMenu.add(menuCrtez);
        barMenu.add(menuPomoc);
        setJMenuBar(barMenu);
        panelDesktop = new JDesktopPane();
        Container contentPane = getContentPane();
        contentPane.add(panelDesktop);
        menuItemNovo.addActionListener(
                new ActionListener() {

                    public void actionPerformed(ActionEvent evt) {
                        frame = new JInternalFrame(
                                "Karta", false, true, true, true);
                        Container container = frame.getContentPane();
                        UpravljanjeObjektima painel = new UpravljanjeObjektima(2400, 100);
                        container.add(painel, BorderLayout.CENTER);
                        frame.setSize(1200, 641);
                        panelDesktop.add(frame);
                        frame.setVisible(true);
                    }
                });
        menuItemNovo2.addActionListener(
                new ActionListener() {

                    public void actionPerformed(ActionEvent evt) {
                        frame = new JInternalFrame(
                                "Karta", false, true, true, true);
                        Container container = frame.getContentPane();
                        UpravljanjeObjektima painel = new UpravljanjeObjektima(800, 1000);
                        container.add(painel, BorderLayout.CENTER);
                        frame.setSize(1200, 641);
                        panelDesktop.add(frame);
                        frame.setVisible(true);
                    }
                });
        menuItemNovo3.addActionListener(
                new ActionListener() {

                    public void actionPerformed(ActionEvent evt) {
                        frame = new JInternalFrame(
                                "Karta", false, true, true, true);
                        Container container = frame.getContentPane();
                        UpravljanjeObjektima painel = new UpravljanjeObjektima(2100, 1000);
                        container.add(painel, BorderLayout.CENTER);
                        frame.setSize(1200, 641);
                        panelDesktop.add(frame);
                        frame.setVisible(true);
                    }
                });
        menuItemPomoc.addActionListener(
                new ActionListener() {

                    public void actionPerformed(ActionEvent evt) {
                        JOptionPane.showMessageDialog(null, "Inačica 0.9.4 - Program je napisan u sklopu diplomskog rada.\n"
                                + "Zabranjena je daljnja uporaba, modifikacija i distribucija za komercijalne svrhe!\n\n"
                                + "Upute su dio projektne dokumentacije i nalaze se kao poglavlje diplomskog rada.\n\n"
                                + "Dvostruki klik na opremu ili zid za promjenu svojstava. Desni klik je brisanje. Lijeva tipka je drag'n'drop.");
                    }
                });
        menuItemOProgramu.addActionListener(
                new ActionListener() {

                    public void actionPerformed(ActionEvent evt) {
                        String oinfo = "Aplikacija je napisana za potrebe diplomskog rada u programskom jeziku Java\n"
                                + "koristeći razvojno okruženje NetBeans IDE.\n";
                        oinfo += "Autor: Marko Benkek\nE-pošta: marko.benkek@me.com";
                        JOptionPane.showMessageDialog(null, oinfo, "WiFi tactics", JOptionPane.INFORMATION_MESSAGE);
                    }
                });
    }

    public static void main(String args[]) {
        Projekt aplikacija = new Projekt();
        aplikacija.setVisible(true);
    }
    private JInternalFrame frame;
    private JDesktopPane panelDesktop;
    private JMenuBar barMenu;
    private JMenu menuCrtez, menuPomoc;
    private JMenuItem menuItemNovo, menuItemNovo2, menuItemNovo3, menuItemPomoc, menuItemOProgramu, menuItemSpremi;

    class UpravljanjeObjektima extends JPanel implements MouseMotionListener {

        public UpravljanjeObjektima(double f, int z) {
            FREQ = f;
            ZOOM = z;
            this.setBackground(Color.LIGHT_GRAY);
            // Klik za svojstva zida
            btZid.addActionListener(new ActionListener() {

                public void actionPerformed(ActionEvent e) {
                    String naredba = e.getActionCommand();
                    // Definira vrstu objekta kojeg crtamo
                    if (naredba.equals("Zid")) {
                        setTipElementaUDizajnu(ZID);
                    }
                }
            });
            // Klik za svojstva antene
            btOprema.addActionListener(new ActionListener() {

                public void actionPerformed(ActionEvent e) {
                    String naredba = e.getActionCommand();
                    // Definira vrstu objekta kojeg crtamo
                    if (naredba.equals("Oprema")) {
                        setTipElementaUDizajnu(OPREMA);
                    }
                }
            });
            // Klik na gumb izračunaj
            btIzracunaj.addActionListener(new ActionListener() {

                public void actionPerformed(ActionEvent e) {
                    String naredba = e.getActionCommand();
                    RacunajPokrivenost racunPokrivenosti = new RacunajPokrivenost(3, FREQ);
                    if (naredba.equals("Izradi")) {
                        zidovi.trimToSize();
                        oprema.trimToSize();
                        switch (combo2.getSelectedIndex()) {
                            case 1:
                                racunPokrivenosti.setSkala(ZOOM);
                                // setSkaliranje_zida(100);
                                break;
                            case 2:
                                racunPokrivenosti.setSkala(ZOOM * 10);
                                // setSkaliranje_zida(0.1);
                                break;
                        }
                        switch (combo.getSelectedIndex()) {
                            case 1:
                                vIzracunaRezultata = racunPokrivenosti.calcModelPotencija(zidovi, oprema, getHeight() + 100, getWidth() + 100);
                                break;
                            default:
                                vIzracunaRezultata = racunPokrivenosti.calcMontleyKeenan(zidovi, oprema, getHeight() + 100, getWidth() + 100);
                        }
                    }
                }
            });
            // Klik na gumb sve obriši / novi crtež
            btSveObrisi.addActionListener(new ActionListener() {

                public void actionPerformed(ActionEvent e) {
                    String naredba = e.getActionCommand();
                    // Ako je stisnut gumb za novi crtež
                    if (naredba.equals("Novo")) {
                        vIzracunaRezultata.removeAllElements();
                        oprema.removeAllElements();
                        zidovi.removeAllElements();
                        trenutnaVrijednost.setText("");
                        duljinaZida.setText("");
                    }
                }
            });
            btUcitaj.addActionListener(new ActionListener() {

                int retVal;

                public void actionPerformed(ActionEvent e) {
                    String naredba = e.getActionCommand();
                    // Ako je stisnut gumb za učitavnje
                    if (naredba.equals("Ucitaj")) {
                        oprema.removeAllElements();
                        zidovi.removeAllElements();
                        SerijalizacijaUcitaj p = new SerijalizacijaUcitaj();
                        fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
                        retVal = fc.showOpenDialog(new JInternalFrame(
                                "Ucitavanje opreme!", false, true, true, true));
                        if (retVal == JFileChooser.APPROVE_OPTION) {
                            oprema = p.SerijalizacijaUcitaj(fc.getSelectedFile().getPath());
                        } else if (retVal == JFileChooser.CANCEL_OPTION) {
                            JOptionPane.showMessageDialog(null, "Otkazano.");

                        } else if (retVal == JFileChooser.ERROR_OPTION) {
                            JOptionPane.showMessageDialog(null, "Nije odabran file.");

                        } else {
                            JOptionPane.showMessageDialog(null, "Nepoznata pogreška.");
                        }
                        fc.showOpenDialog(new JInternalFrame(
                                "Ucitavanje zidova!", false, true, true, true));
                        if (retVal == JFileChooser.APPROVE_OPTION) {
                            zidovi = p.SerijalizacijaUcitaj(fc.getSelectedFile().getPath());
                        }
                    } else if (retVal == JFileChooser.CANCEL_OPTION) {
                        JOptionPane.showMessageDialog(null, "Otkazano.");
                    } else if (retVal == JFileChooser.ERROR_OPTION) {
                        JOptionPane.showMessageDialog(null, "Nije odabran file.");
                    } else {
                        JOptionPane.showMessageDialog(null, "Nepoznata pogreška.");
                    }
                }
            });
            btSpremi.addActionListener(new ActionListener() {

                int retVal;

                public void actionPerformed(ActionEvent e) {
                    String naredba = e.getActionCommand();
                    // Ako je stisnut gumb za spremanje
                    if (naredba.equals("Spremi")) {
                        fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
                        retVal = fc.showSaveDialog(new JInternalFrame(
                                "Spremanje", false, true, true, true));
                        if (retVal == JFileChooser.APPROVE_OPTION) {
                            String naziv = fc.getSelectedFile().getPath();
                            String prvi_fajl = naziv + "_1_oprema.sav";
                            String drugi_fajl = naziv + "_2_zidovi.sav";
                            SerijalizacijaSpremi p1 = new SerijalizacijaSpremi(prvi_fajl, oprema);
                            SerijalizacijaSpremi p2 = new SerijalizacijaSpremi(drugi_fajl, zidovi);
                        }
                    } else if (retVal == JFileChooser.CANCEL_OPTION) {
                        JOptionPane.showMessageDialog(null, "Otkazano.");
                    } else if (retVal == JFileChooser.ERROR_OPTION) {
                        JOptionPane.showMessageDialog(null, "Nije odabran file.");
                    } else {
                        JOptionPane.showMessageDialog(null, "Nepoznata pogreška.");
                    }
                }
            });
            this.setLayout(new BorderLayout());
            Vector v = new Vector();
            v.add("Montley Keenan");
            v.add("Model potencija");
            combo = new JComboBox(v);
            Vector v2 = new Vector();
            v2.add("1px = 1m");
            v2.add("1px = 0.1m");
            combo2 = new JComboBox(v2);
            bar = new JToolBar(JToolBar.HORIZONTAL);
            bar.setSize(this.WIDTH, 50);
            bar.add(btSveObrisi);
            bar.add(btSpremi);
            bar.add(btUcitaj);
            bar.addSeparator();
            bar.add(btOprema);
            bar.add(btZid);
            bar.addSeparator();
            JLabel j = new JLabel("Model: ");
            j.setFont(new Font(null, Font.BOLD, 16));
            bar.add(j);
            bar.add(combo);
            bar.addSeparator();
            JLabel j2 = new JLabel("Mjerilo: ");
            j2.setFont(new Font(null, Font.BOLD, 16));
            bar.add(j2);
            bar.add(combo2);
            bar.addSeparator();
            bar.add(btIzracunaj);
            bar.addSeparator();
            trenutnaVrijednost = new JLabel("");
            bar.addSeparator();
            duljinaZida = new JLabel("");
            bar.add(trenutnaVrijednost);
            bar.add(duljinaZida);
            bar.addSeparator();
            bar.setVisible(true);
            btSveObrisi.setMaximumSize(new Dimension(80, 50));
            btOprema.setMaximumSize(new Dimension(90, 50));
            btZid.setMaximumSize(new Dimension(90, 50));
            btIzracunaj.setMaximumSize(new Dimension(100, 50));
            combo.setMaximumSize(new Dimension(100, 50));
            combo2.setMaximumSize(new Dimension(90, 50));
            trenutnaVrijednost.setMinimumSize(new Dimension(80, 50));
            duljinaZida.setMinimumSize(new Dimension(80, 50));
            this.add(bar, "North");
            // Akcije miša za panel       
            addMouseListener(
                    new MouseAdapter() {

                        public void mousePressed(MouseEvent evt) {
                            x = evt.getX();
                            y = evt.getY();
                            xC = x;
                            yC = y;
                            // Pronađi stisnuti element
                            trenutno = nadiOpremu(x, y);
                            if (trenutno < 0) {
                                opremaOdabrano = false;
                                switch (tipElementaUDizajnu) {
                                    case 1: // Zid
                                        pInicial = new Point(x, y);
                                        break;
                                    case 2:// Oprema
                                        break;
                                }
                            } else {// Oprema je odabrana
                                opremaOdabrano = true;
                            }
                            trenutno = nadiZidove(x, y);
                            if (trenutno > 0) {
                                zidOdabrano = true;
                            }
                        }

                        public void mouseReleased(MouseEvent evt) {
                            x = evt.getX();
                            y = evt.getY();
                            if (!opremaOdabrano) { // Ako nije označena oprema
                                switch (tipElementaUDizajnu) {
                                    case 1:
                                        if (!zidPovuceno) {
                                            pFinal = new Point(x, y);
                                            zidovi.add(new Prepreka(pInicial, pFinal, 1, FREQ));
                                        }
                                        break;
                                    case 2:
                                        oprema.add(new Oprema(new Point(x, y), PROMJER_OPREME));
                                        break;
                                }
                            } else {
                                if (opremaPovuceno) { // Ako odabrana oprema nije povučena
                                    oprema.add(new Oprema(new Point(x, y), PROMJER_OPREME));
                                }
                            }
                            if (zidPovuceno) {// Premještanje zida x-xC yC
                                Point p = zidPrivremeno.getPInicial();
                                int tempX = (x - xC);
                                int tempY = (y - yC);
                                p.translate(tempX, tempY);
                                zidPrivremeno.setPInicial(p);
                                p = zidPrivremeno.getPFinal();
                                p.translate(tempX, tempY);
                                zidPrivremeno.setPFinal(p);
                                zidovi.add(zidPrivremeno);
                            }
                            zidPrivremeno = null;
                            zidUklonjeno = false;
                            zidOdabrano = false;
                            zidPovuceno = false;
                            opremaPovuceno = false;
                            opremaOdabrano = false;
                            opremaUklonjeno = false;
                        }

                        public void mouseClicked(MouseEvent evt) {
                            x = evt.getX();
                            y = evt.getY();
                            if (evt.getButton() == evt.BUTTON1) {
                                if (evt.getClickCount() == 2) {  // Pronađi koji je element odabran
                                    trenutno = nadiOpremu(x, y);
                                    if (trenutno >= 0) {
                                        String msg, inicijalnaVrijednost;
                                        Object vrijednost;
                                        double dbl;
                                        Oprema e = (Oprema) oprema.get(trenutno);
                                        Antena a = e.getAntena();
                                        Radio r = e.getRadio();
                                        msg = "Unesi dobit antene:";
                                        inicijalnaVrijednost = "" + a.getDobitAntene();
                                        vrijednost = JOptionPane.showInternalInputDialog(frame, msg, "PROMJENA DOBITI ANTENE", JOptionPane.INFORMATION_MESSAGE, null, null, inicijalnaVrijednost);
                                        dbl = a.getDobitAntene();
                                        if (vrijednost != null) {
                                            try {
                                                dbl = Double.parseDouble(vrijednost.toString());
                                            } catch (NumberFormatException exc) {
                                                dbl = a.getDobitAntene();
                                            }
                                        }
                                        a.setDobitAntene(dbl);
                                        msg = "Unesi snagu odašiljanja radija (dBm):";
                                        inicijalnaVrijednost = "" + r.getSnagaTX();
                                        vrijednost = JOptionPane.showInternalInputDialog(frame, msg, "PROMJENA SNAGE ODAŠILJANJA", JOptionPane.INFORMATION_MESSAGE, null, null, inicijalnaVrijednost);
                                        dbl = r.getSnagaTX();
                                        if (vrijednost != null) {
                                            try {
                                                dbl = Double.parseDouble(vrijednost.toString());
                                            } catch (NumberFormatException exc) {
                                                dbl = r.getSnagaTX();
                                            }
                                        }
                                        r.setSnagaTX(dbl);
                                        oprema.removeElementAt(trenutno);
                                        oprema.add(new Oprema(a, r));
                                    } else {
                                        trenutno = nadiZidove(x, y);
                                        if (trenutno >= 0) {
                                            Prepreka o = (Prepreka) zidovi.get(trenutno);
                                            int vrstaZ;
                                            String msg = "Unesi vrstu zida: 1-tanki, 2-debeli ili 3-metalni";
                                            String inicijalnaVrijednost = "" + o.getTipZida();
                                            Object vrijednost = JOptionPane.showInternalInputDialog(frame, msg, "PROMJENA VRSTE ZIDA", JOptionPane.INFORMATION_MESSAGE, null, null, inicijalnaVrijednost);
                                            vrstaZ = o.getTipZida();
                                            if (vrijednost != null) {
                                                try {
                                                    vrstaZ = Integer.parseInt(vrijednost.toString());
                                                } catch (NumberFormatException exc) {
                                                    vrstaZ = o.getTipZida();
                                                }
                                            }
                                            o.setVrstaZida(vrstaZ, FREQ);
                                        }
                                    }
                                }
                            } else if (evt.getButton() == evt.BUTTON3) { // Uklanjamo elemente iz prikaza
                                trenutno = nadiOpremu(x, y);
                                if (trenutno >= 0) {
                                    int i = JOptionPane.showInternalConfirmDialog(frame, "Ukloniti opremu?", "BRISANJE", JOptionPane.INFORMATION_MESSAGE);
                                    if (i == 0) {
                                        oprema.removeElementAt(trenutno);
                                    }
                                } else {
                                    trenutno = nadiZidove(x, y);
                                    if (trenutno >= 0) {
                                        int i = JOptionPane.showInternalConfirmDialog(frame, "Ukloniti zid?", "BRISANJE", JOptionPane.INFORMATION_MESSAGE);
                                        if (i == 0) {
                                            zidovi.removeElementAt(trenutno);
                                        }
                                    }
                                }
                            }
                        }
                    });
            addMouseMotionListener(this);
        }

        public void mouseDragged(MouseEvent evt) {
            x = evt.getX();
            y = evt.getY();
            if (opremaOdabrano) {// Označena je oprema
                opremaPovuceno = true;
                setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            }
            trenutno = nadiOpremu(xC, yC);
            if (trenutno >= 0 && !opremaUklonjeno) {
                oprema.removeElementAt(trenutno);
                opremaPovuceno = true;
                opremaUklonjeno = true;
            }
            trenutno = nadiZidove(xC, yC);
            if (trenutno >= 0 && !zidUklonjeno) {
                zidPrivremeno = (Prepreka) zidovi.get(trenutno);
                zidUklonjeno = true;
                zidPovuceno = true;
            }
            if ((tipElementaUDizajnu == 1) && (!zidPovuceno)) {
                java.text.DecimalFormat df = new java.text.DecimalFormat("0.00");
                duljinaZida.setText(" Duljina: " + df.format(Prepreka.dajModul(new Prepreka(new Point(x, y), new Point((int) xC, (int) yC), 1, FREQ)) * skaliranje_zida) + " px");
                Graphics g1 = this.getGraphics();
                g1.drawLine(xC, yC, x, y);
            }
        }

        public void mouseMoved(MouseEvent evt) {
            x = evt.getX();
            y = evt.getY();
            if ((nadiOpremu(x, y) >= 0) || (nadiZidove(x, y) >= 0)) {
                setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            } else {
                setCursor(Cursor.getDefaultCursor());
            }
            for (e = vIzracunaRezultata.elements(); e.hasMoreElements();) {
                elGra = (ElementiPrikaza) e.nextElement();
                p = elGra.getSmjestaj();
                if ((x >= p.getX()) && (x <= (p.getX() + elGra.getSirina()))) {
                    if ((y >= p.getY()) && (y <= (p.getY() + elGra.getVisina()))) {
                        trenutnaVrijednost.setText("S: " + Math.round(elGra.getSinal()) + " dBm A:" + elGra.getAntena() + " ");
                    }
                }
            }
        }

        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            draw(g);
        }

        public int nadiOpremu(int x, int y) {
            cont = 0;
            for (e = oprema.elements(); e.hasMoreElements(); cont++) {
                eq = (Oprema) e.nextElement();
                if (eq.getAntena().provjeraPresjeka(new Point(x, y))) {
                    return cont;
                }
            }
            return -1;
        }

        public int nadiZidove(int x, int y) {
            cont = 0;
            for (e = zidovi.elements(); e.hasMoreElements(); cont++) {
                ob = (Prepreka) e.nextElement();
                if (ob.provjeraPresjeka(new Point(x, y))) {
                    return cont;
                }
            }
            return -1;
        }

        public void draw(Graphics g) {
            for (e = vIzracunaRezultata.elements(); e.hasMoreElements();) {
                elGra = (ElementiPrikaza) e.nextElement();
                p = elGra.getSmjestaj();
                g.setColor(Color.BLACK);
                if ((OSJETLJIVOST + Math.abs(0.3 * OSJETLJIVOST)) < elGra.getSinal()) {
                    g.setColor(Color.RED);
                } else if ((OSJETLJIVOST + Math.abs(0.2 * OSJETLJIVOST)) < elGra.getSinal()) {
                    g.setColor(Color.ORANGE);
                } else if ((OSJETLJIVOST + Math.abs(0.1 * OSJETLJIVOST)) < elGra.getSinal()) {
                    g.setColor(Color.YELLOW);
                } else if ((OSJETLJIVOST) < elGra.getSinal()) {
                    g.setColor(Color.CYAN);
                } else if ((OSJETLJIVOST - Math.abs(0.1 * OSJETLJIVOST)) < elGra.getSinal()) {
                    g.setColor(Color.darkGray);
                } else if ((OSJETLJIVOST - Math.abs(0.2 * OSJETLJIVOST)) < elGra.getSinal()) {
                    g.setColor(Color.LIGHT_GRAY);
                } else if ((OSJETLJIVOST - Math.abs(0.3 * OSJETLJIVOST)) < elGra.getSinal()) {
                    g.setColor(Color.MAGENTA);
                }
                g.drawRect((int) Math.round(p.getX()), (int) Math.round(p.getY()), (int) Math.round(elGra.getSirina()), (int) Math.round(elGra.getVisina()));
            }
            g.setColor(Color.BLACK);
            for (e = zidovi.elements(); e.hasMoreElements();) {
                ob = (Prepreka) e.nextElement();
                pI = ob.getPInicial();
                pF = ob.getPFinal();
                g.drawLine((int) Math.round(pI.getX()), (int) Math.round(pI.getY()),
                        (int) Math.round(pF.getX()), (int) Math.round(pF.getY()));
            }
            g.setColor(Color.BLUE);
            for (e = oprema.elements(); e.hasMoreElements();) {
                eq = (Oprema) e.nextElement();
                p = eq.getAntena().getSmjestaj();
                g.fillOval((int) Math.round(p.getX()), (int) Math.round(p.getY()), PROMJER_OPREME, PROMJER_OPREME);
            }
            repaint();
        }

        public void setTipElementaUDizajnu(int i) {
            if (tipElementaUDizajnu > 0) {
                tipElementaUDizajnu = i;
            }
        }
        private Vector zidovi = new Vector(50), oprema = new Vector(50), vIzracunaRezultata = new Vector();
        private Point pInicial = new Point(), pFinal = new Point();
        private JButton btZid = new JButton("Zid", new ImageIcon(getClass().getClassLoader().getResource("grafika/zid.jpg"))),
                btOprema = new JButton("Oprema", new ImageIcon(getClass().getClassLoader().getResource("grafika/ap.jpg"))),
                btIzracunaj = new JButton("Izradi", new ImageIcon(getClass().getClassLoader().getResource("grafika/izgradi.png"))),
                btSveObrisi = new JButton("Novo", new ImageIcon(getClass().getClassLoader().getResource("grafika/iks.png"))),
                btSpremi = new JButton("Spremi", new ImageIcon(getClass().getClassLoader().getResource("grafika/dolje.png"))),
                btUcitaj = new JButton("Ucitaj", new ImageIcon(getClass().getClassLoader().getResource("grafika/gore.png")));
        private JToolBar bar;
        private JComboBox combo, combo2;
        private int trenutno = -1, tipElementaUDizajnu = 2, x, y, cont, xC, yC;
        private double OSJETLJIVOST = -115;
        private boolean opremaPovuceno = false, opremaOdabrano = false, opremaUklonjeno = false, zidOdabrano = false,
                zidUklonjeno = false, zidPovuceno = false;
        private Enumeration e;
        private ElementiPrikaza elGra;
        private Oprema eq;
        private Point p, pI, pF;
        private Prepreka ob, zidPrivremeno;
        private static final int OPREMA = 2, ZID = 1, PROMJER_OPREME = 10;
        private double FREQ;
        private int ZOOM;
        private double skaliranje_zida = 1;
        private JLabel trenutnaVrijednost, duljinaZida;
        final JFileChooser fc = new JFileChooser();
    }
}