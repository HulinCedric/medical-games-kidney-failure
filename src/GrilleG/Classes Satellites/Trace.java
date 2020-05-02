/**
 * IUT de Nice / Departement informatique / Module APO-Java
 * Annee 2009_2010 - Package SWING
 * 
 * @edition A : edition initiale
 * 
 * @version 1.0.0 :
 * 
 *          mise a disposition du necessaire pour trace un
 *          trait sur un composant Java
 */
import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;

/**
 * Figure graphique - Trait
 * 
 * @author Cedric Hulin, Charles Fouco
 * @version 1.0.0
 */
public class Trace {
    /**
     * Couleur du trait
     * 
     * @since 1.0.0
     */
    private Color couleur;
    
    /**
     * Point du debut du segment
     * 
     * @since 1.0.0
     */
    private Point x;
    
    /**
     * Point de fin de segment
     * 
     * @since 1.0.0
     */
    private Point y;
    
    /**
     * Constructeur normal
     * 
     * @param couleur
     * @param x1
     * @param y1
     * @param x2
     * @param y2
     * @since 1.0.0
     */
    public Trace(Color couleur, int x1, int y1, int x2, int y2) {
        this.couleur = couleur;
        this.x = new Point(x1, y1);
        this.y = new Point(x2, y2);
    }
    
    /**
     * Constructeur normal
     * 
     * @param couleur
     * @param x1
     * @param y1
     * @param x2
     * @param y2
     * @since 1.0.0
     */
    public Trace(Color couleur, double x1, double y1, double x2, double y2) {
        this.couleur = couleur;
        this.x = new Point((int) x1, (int) y1);
        this.y = new Point((int) x2, (int) y2);
    }
    
    /**
     * Modification du point de fin de segment
     * 
     * @param x
     * @param y
     * @since 1.0.0
     */
    public void modifierY(int x, int y) {
        this.y = new Point(x, y);
    }
    
    /**
     * Modification du point de debut de segment
     * 
     * @param x
     * @param y
     * @since 1.0.0
     */
    public void modifierX(int x, int y) {
        this.x = new Point(x, y);
    }
    
    /**
     * Dessine le trait
     * 
     * @param g
     * @since 1.0.0
     */
    public void dessiner(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        
        // Renseigne la couleur du trait
        //
        g2d.setColor(couleur);
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.6f));
        
        // Anti aliasing
        //
        g2d.setRenderingHint(java.awt.RenderingHints.KEY_ANTIALIASING, java.awt.RenderingHints.VALUE_ANTIALIAS_ON);
        
        // Caracteristique du trait
        //
        g2d.setStroke(new BasicStroke(10.0f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        
        // Dessine le trait
        //
        g2d.drawLine((int) x.getX(), (int) x.getY(), (int) y.getX(), (int) y.getY());
    }
    
    /**
     * Obtention du debut du trait
     * 
     * @return x
     * @since 1.1.0
     */
    public Point obtenirX() {
        return x;
    }
    
    /**
     * Obtention de la fin du trait
     * 
     * @return y
     * @since 1.1.0
     */
    public Point obtenirY() {
        return y;
    }
}