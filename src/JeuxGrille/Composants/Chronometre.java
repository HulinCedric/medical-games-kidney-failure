/**
 * IUT de Nice / Departement informatique / Module APO-Java
 * Annee 2009_2010 - Applications graphiques
 * 
 * @edition A : edition initiale
 * 
 * @version 1.0.0 :
 * 
 *          version initiale
 * 
 */

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.border.EmptyBorder;

import org.jdom.Element;

/**
 * Classe permettant de recuperer un chronometre dans un
 * panneau
 * 
 * @version 1.0.0
 * @author Charles Fouco, Cedric Hulin
 */
public class Chronometre extends PanneauG {
    

    /**
     * Parametre de temps
     * 
     * @since 1.0.0
     */
    private int heure, minute, seconde;
    
    /**
     * Delai de rafraichissement en millisecondes
     * 
     * @since 1.0.0
     */
    private int delais;
    
    /**
     * "Cadre" support pour l'affichage
     * 
     * @since 1.0.0
     */
    private LcdDigit[] lcd;
    
    /**
     * Couleur de la led allumee
     * 
     * @since 1.0.0
     */
    private Color ledOn;
    
    /**
     * Couleur de la led eteinte
     * 
     * @since 1.0.0
     */
    private Color ledOff;
    
    /**
     * Parametre d'affichage du temps
     * 
     * @since 1.0.0
     */
    private String time;
    
    /**
     * Chronometre - start() - stop()
     * 
     * @since 1.0.0
     */
    private Timer chrono;
    
    /**
     * Action realise par le chrono en fonction du delai
     * 
     * @since 1.0.0
     */
    private ActionListener tache_timer;
    
    /**
     * Config
     * 
     * @since 2.0.0
     */
    private Element config;
    
    /**
     * Class Chronometre
     * 
     * @version 1.0.0
     * @author Charles Fouco, Cedric Hulin
     */
    public Chronometre(Object config) {
        super(null, config);
        
        // Sauvegarder la configuration
        //
        this.config = (Element) config;
        
        // Definir la couleur des leds
        //
        setLedOn(this.config);
        setLedOff(this.config);
        
        // Initialiser le chronometre
        //
        heure = 0;
        minute = 0;
        seconde = 0;
        delais = 1000;
        time = "00:00:00";
        lcd = setLCD();
        
        // Definir l'action realise par le timer
        //
        tache_timer = new ActionListener() {
            public void actionPerformed(ActionEvent e1) {
                seconde++;
                if (seconde == 59) {
                    seconde = 0;
                    minute++;
                }
                if (minute == 59) {
                    minute = 0;
                    heure++;
                }
                
                // Mettre a jour l'affichage
                //
                time = setTime();
                lcd = setLCD();
                
                repaint();
            }
        };
        
        // Instanciation du chronometre
        //
        chrono = new Timer(delais, tache_timer);
        
        // Definir le contour du chronometre
        //
        setBorder(new EmptyBorder(10, 85, 20, 10));
    }
    
    /**
     * Demarer le chronometre
     * 
     * @since 1.0.0
     */
    public void demarer() {
        chrono.start();
    }
    
    /**
     * Stoper le chronometre
     * 
     *@since 1.0.0
     */
    public void stoper() {
        chrono.stop();
    }
    
    /**
     * Initialiser le chronometre
     * 
     * @since 1.0.0
     */
    public void initialiser() {
        heure = 0;
        minute = 0;
        seconde = 0;
        
        time = "00:00:00";
        lcd = setLCD();
        repaint();
    }
    
    /**
     * setTime Formatage de l'affichage
     * 
     * @return format
     * 
     * @since 1.0.0
     */
    private String setTime() {
        String s1, s2, s3;
        if (heure < 10)
            s1 = "0" + heure;
        else s1 = "" + heure;
        if (minute < 10)
            s2 = ":0" + minute;
        else s2 = ":" + minute;
        if (seconde < 10)
            s3 = ":0" + seconde;
        else s3 = ":" + seconde;
        
        return s1 + s2 + s3;
    }
    
    /**
     * paintComponent Dessine le support LCD
     * 
     * @since 1.0.0
     */
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        lcd[0].draw(g, 10, 10);
        lcd[1].draw(g, 22, 10);
        lcd[2].draw(g, 42, 10);
        lcd[3].draw(g, 54, 10);
        lcd[4].draw(g, 74, 10);
        lcd[5].draw(g, 86, 10);
        
    }
    
    /*
     * Formatage du support LCD en fontion du temps
     * @since 1.0.0
     */
    private LcdDigit[] setLCD() {
        LcdDigit[] lc = new LcdDigit[8];
        lc[0] = new LcdDigit(Integer.parseInt(time.substring(0, 1)), 10, 20, ledOn, ledOff);
        lc[1] = new LcdDigit(Integer.parseInt(time.substring(1, 2)), 10, 20, ledOn, ledOff);
        lc[2] = new LcdDigit(Integer.parseInt(time.substring(3, 4)), 10, 20, ledOn, ledOff);
        lc[3] = new LcdDigit(Integer.parseInt(time.substring(4, 5)), 10, 20, ledOn, ledOff);
        lc[4] = new LcdDigit(Integer.parseInt(time.substring(6, 7)), 10, 20, ledOn, ledOff);
        lc[5] = new LcdDigit(Integer.parseInt(time.substring(7, 8)), 10, 20, ledOn, ledOff);
        
        return lc;
    }
    
    /**
     * setLedOn
     * 
     * @since 2.0.0
     */
    private void setLedOn(Element config) {
        
        String StringCouleur;
        
        // Verifier le parametre
        //
        if (config == null) {
            ledOn = Color.YELLOW;
            return;
        }
        
        // Verifier la presence de la cle
        //
        if (config.getChild("LedOn") == null) {
            ledOn = Color.YELLOW;
            return;
        }
        
        // Recuperer la valeur associee
        //
        StringCouleur = config.getChild("LedOn").getTextTrim();
        
        // Affecter la nouvelle couleur
        //
        ledOn = XML.obtenirColorFont(StringCouleur);
    }
    
    /**
     * setLedOff
     * 
     * @since 2.0.0
     */
    private void setLedOff(Element config) {
        
        String StringCouleur;
        
        // Verifier le parametre
        //
        if (config == null) {
            ledOff = Color.DARK_GRAY;
            return;
        }
        
        // Verifier la presence de la cle
        //
        if (config.getChild("LedOff") == null) {
            ledOff = Color.DARK_GRAY;
            return;
        }
        
        // Recuperer la valeur associee
        //
        StringCouleur = config.getChild("LedOff").getTextTrim();
        
        // Affecter la nouvelle couleur
        //
        ledOff = XML.obtenirColorFont(StringCouleur);
    }
    
    /**
     * Programme principal
     * 
     * @param args
     * @since 1.0.0
     */
    public static void main(String[] args) {
        
        Element configChrono = (Element) ConfigXML.load("Configuration/ConfigMemoChrono", "1.0.0");
        if (configChrono == null) return;
        
        // Instancier un nouveau chronometre
        //
        Chronometre c = new Chronometre(configChrono);
        
        // Creer une fentre support
        //
        JFrame frame = new JFrame();
        
        // Attacher le chronometre
        // au frame support
        //
        frame.getContentPane().add(c);
        
        // Demarer le chronometre
        //
        c.demarer();
        
        // Affichage de l'IHM
        //
        frame.pack();
        frame.setLocation(350, 200);
        frame.setSize(230, 80);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}