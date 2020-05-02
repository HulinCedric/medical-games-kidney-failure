/**
 * IUT de Nice / Departement informatique / Module APO-Java
 * Annee 2009_2010 - Ecouteur
 * 
 * @version 1.0.0
 * 
 *          version initiale
 * 
 * @version 1.1.0
 * 
 *          Ajout de plusieurs methodes protegees :
 *          obtenirClic ; obtenirDernierTrait ;
 *          obtenirGrilleG ; obtenirHamecon ;
 *          retirerDernierTrait
 * 
 * @version 1.2.0
 * 
 *          Ajout de redimensionnement des trait en cas de
 *          redimensionnement de la grille support
 * 
 * @version 1.3.0
 * 
 *          Ajout d'un constructeur permettant de regenerer
 *          des traits precedemment enregistrer ; Ajout d'un
 *          accesseur de consultation des proprietes utiles
 *          pour l'enregistrement
 */
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.HashMap;
import java.util.Vector;

/**
 * Ecouteur souris permettant le tracage de trait sur une
 * GrilleG
 * 
 * @author Cedric Hulin, Charles Fouco
 * @version 1.2.0
 */
public class EcouteurGrilleDrawLine implements MouseListener, MouseMotionListener {
    /**
     * Hamecon sur une grilleG
     * 
     * @since 1.0.0
     */
    private Object hamecon;
    
    /**
     * Drapeau situant le clic dans le temps
     * 
     * @since 1.0.0
     */
    private boolean premierClic;
    
    /**
     * Vecteur contenant les trait sur la grille
     * 
     * @since 1.0.0
     */
    private Vector listeTrace;
    
    /**
     * Vecteur contenant les coordonnees de debut de chaque
     * trait
     * 
     * @since 1.2.0
     */
    private HashMap listeCoordonneesDebut;
    
    /**
     * Vecteur contenant les coordonnees de fin de chaque
     * trait
     * 
     * @since 1.2.0
     */
    private HashMap listeCoordonneesFin;
    
    /**
     * Constructeur par defaut
     * 
     * @param hamecon
     * @since 1.0.0
     */
    public EcouteurGrilleDrawLine(Object hamecon) {
        this.hamecon = hamecon;
        this.listeTrace = new Vector();
        this.listeCoordonneesDebut = new HashMap();
        this.listeCoordonneesFin = new HashMap();
        this.premierClic = true;
    }
    
    /**
     * Constructeur normal gestion de l'enregistrement
     * 
     * @param hamecon
     * @param listeTrace
     * @param listeCoordonneesDebut
     * @param listeCoordonneesFin
     * @since 1.3.0
     */
    public EcouteurGrilleDrawLine(Object hamecon, HashMap listeCoordonneesDebut, HashMap listeCoordonneesFin) {
        this.hamecon = hamecon;
        this.listeTrace = new Vector();
        this.listeCoordonneesDebut = listeCoordonneesDebut;
        this.listeCoordonneesFin = listeCoordonneesFin;
        this.premierClic = true;
    }
    
    /**
     * Implementation de l'evenement mouseMoved
     * 
     * @since 1.0.0
     */
    public void mouseMoved(MouseEvent e) {
        if (!premierClic) {
            Trace t = (Trace) listeTrace.get(listeTrace.size() - 1);
            t.modifierY(e.getX(), e.getY());
        }
        if (hamecon.getClass().getSimpleName().equals("JeuxGrille"))
            ((JeuxGrille) hamecon).obtenirGrille().repaint();
        else ((GrilleG) hamecon).repaint();
    }
    
    /**
     * Implementation de l'evenement mousePressed
     * 
     * @since 1.0.0
     */
    public void mousePressed(MouseEvent e) {
        
        // Recuperer la position de la cellule cliquer
        //
        GrilleG gr;
        if (hamecon.getClass().getSimpleName().equals("JeuxGrille"))
            gr = ((JeuxGrille) hamecon).obtenirGrille();
        else gr = ((GrilleG) hamecon);
        
        // Obtenir la position de la cellule
        //
        Dimension dim = gr.obtenirPositionCelluleCible(e.getX(), e.getY());
        if (dim.getHeight() > gr.obtenirNbColonnes()) dim.setSize(dim.getWidth(), gr.obtenirNbColonnes());
        if (dim.getWidth() > gr.obtenirNbLignes()) dim.setSize(gr.obtenirNbLignes(), dim.getHeight());
        
        // Calculer la dimension d'une cellule
        //
        float largeur = gr.getSize().width / gr.obtenirNbColonnes();
        float hauteur = gr.getSize().height / gr.obtenirNbLignes();
        
        // Calculer les coordonnees du centre de la
        // cellule
        //
        float x = largeur * dim.height - largeur / 2;
        float y = hauteur * dim.width - hauteur / 2;
        
        // Differencier le premier clic, du deuxieme
        //
        if (premierClic) {
            
            // Ajouter le debut de trait a la liste
            //
            listeTrace.add(new Trace(obtenirCouleurAleatoire(), (int) x, (int) y, e.getX(), e.getY()));
            listeCoordonneesDebut.put(Integer.valueOf(listeCoordonneesDebut.size()), dim);
            premierClic = false;
        }
        else {
            
            // Modifier la fin du trait
            //
            Trace t = (Trace) listeTrace.get(listeTrace.size() - 1);
            t.modifierY((int) x, (int) y);
            listeCoordonneesFin.put(Integer.valueOf(listeCoordonneesFin.size()), dim);
            gr.repaint();
            premierClic = true;
        }
    }
    
    /**
     * Dessine la totalite des figures
     * 
     * @since 1.0.0
     */
    public void dessiner(Graphics g) {
        
        // Recuperer la position de la cellule cliquer
        //
        GrilleG gr;
        if (hamecon.getClass().getSimpleName().equals("JeuxGrille"))
            gr = ((JeuxGrille) hamecon).obtenirGrille();
        else gr = ((GrilleG) hamecon);
        
        int i = 0;
        int j = 0;
        int k = 0;
        while (i < listeCoordonneesDebut.size()) {
            
            Trace f = null;
            
            // Recuperation de la position du debut du trait
            //
            Dimension caseDebut = (Dimension) listeCoordonneesDebut.get(Integer.valueOf(i++));
            
            // Recuperation du trait
            //
            if (listeTrace.size() > k) f = (Trace) listeTrace.get(k++);
            
            // Calculer la dimension d'une cellule
            //
            float largeur = gr.getSize().width / gr.obtenirNbColonnes();
            float hauteur = gr.getSize().height / gr.obtenirNbLignes();
            
            // Calculer les coordonnees du centre de la
            // cellule de debut de trait
            //
            float x = largeur * caseDebut.height - largeur / 2;
            float y = hauteur * caseDebut.width - hauteur / 2;
            
            // Modifier le debut du trait
            //
            if (f != null)
                f.modifierX((int) x, (int) y);
            else {
                f = new Trace(obtenirCouleurAleatoire(), (int) x, (int) y, (int) x, (int) y);
                listeTrace.add(f);
            }
            
            if (premierClic) {
                
                // Recuperation de la position de fin du
                // trait
                //
                Dimension caseFin = (Dimension) listeCoordonneesFin.get(Integer.valueOf(j++));
                
                // Calculer les coordonnees du centre de la
                // cellule de fin de trait
                //
                x = largeur * caseFin.height - largeur / 2;
                y = hauteur * caseFin.width - hauteur / 2;
                
                // Modifier la fin du trait
                //
                f.modifierY((int) x, (int) y);
            }
            // Dessiner le trait
            //
            f.dessiner(g);
        }
    }
    
    /**
     * Retirer le dernier trait de la liste
     * 
     * @since 1.1.0
     */
    protected void retirerDernierTrait() {
        if (listeTrace.size() > 0) {
            listeTrace.remove(listeTrace.lastElement());
            listeCoordonneesDebut.remove(Integer.valueOf(listeCoordonneesDebut.size() - 1));
            listeCoordonneesFin.remove(Integer.valueOf(listeCoordonneesFin.size() - 1));
        }
    }
    
    /**
     * Obtention du dernier trait de la liste
     * 
     * @return dernier trait
     * @since 1.1.0
     */
    protected Trace obtenirDernierTrait() {
        return (Trace) listeTrace.lastElement();
    }
    
    /**
     * Obtention du drapeau situant le clic dans le temps
     * 
     * @return drapeau clic
     * @since 1.1.0
     */
    protected boolean obtenirClic() {
        return premierClic;
    }
    
    /**
     * Obtention de la grille
     * 
     * @return grille
     * @since 1.1.0
     */
    protected GrilleG obtenirGrilleG() {
        GrilleG gr;
        if (hamecon.getClass().getSimpleName().equals("JeuxGrille"))
            gr = ((JeuxGrille) hamecon).obtenirGrille();
        else gr = ((GrilleG) hamecon);
        
        return gr;
    }
    
    /**
     * Obtention de l'hamecon
     * 
     * @return hamecon
     * @since 1.1.0
     */
    protected Object obtenirHamecon() {
        return hamecon;
    }
    
    /**
     * Obtention des proprietes utile pour l'enregistrement
     * 
     * @return list des proprietes
     * @since 1.3.0
     */
    public HashMap obtenirProprietes() {
        HashMap dicoProprietes = new HashMap();
        dicoProprietes.put("listeCoordonneesDebut", listeCoordonneesDebut);
        dicoProprietes.put("listeCoordonneesFin", listeCoordonneesFin);
        return dicoProprietes;
    }
    
    /**
     * Obtention d'une couleur aleatoire
     * 
     * @return couleur aleatoire
     * @since 1.0.0
     */
    private Color obtenirCouleurAleatoire() {
        Color[] colors = { Color.blue, Color.cyan, Color.green, Color.magenta, Color.orange, Color.red, Color.yellow };
        
        int random = (int) (Math.random() * colors.length);
        
        return colors[random];
    }
    
    public void mouseClicked(MouseEvent e) {}
    
    public void mouseDragged(MouseEvent e) {}
    
    public void mouseReleased(MouseEvent e) {}
    
    public void mouseEntered(MouseEvent e) {}
    
    public void mouseExited(MouseEvent e) {}
}