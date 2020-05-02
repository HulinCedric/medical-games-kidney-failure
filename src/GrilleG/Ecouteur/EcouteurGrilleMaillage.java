/**
 * IUT de Nice / Departement informatique / Module APO-Java
 * Annee 2009_2010 - Ecouteur
 */
import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

/**
 * Ecouteur souris permettant de remplir les cellule d'une
 * grille de jeux
 * 
 * @author Cedric Hulin, Charles Fouco
 * @version 1.0.0
 */
public class EcouteurGrilleMaillage implements MouseListener, MouseMotionListener {
    
    /**
     * Hamecon sur une grille
     * 
     * @since 1.0.0
     */
    private Object hamecon;
    
    /**
     * Constructeur normal
     * 
     * @param hamecon
     * @since 1.0.0
     */
    public EcouteurGrilleMaillage(Object hamecon) {
        this.hamecon = hamecon;
    }
    
    /**
     * Implementation de l'evenement mouseClicked
     * 
     * @since 1.0.0
     */
    public void mouseClicked(MouseEvent e) {
        
        // Obtenir les coordonnees absolues du clic courant
        //
        int x = e.getX();
        int y = e.getY();
        
        // Obtenir la designation de la cellule cible
        //
        GrilleG gr = null;
        if (hamecon.getClass().getSimpleName().equals("JeuxGrille"))
            gr = ((JeuxGrille) hamecon).obtenirGrille();
        else gr = ((GrilleG) hamecon);
        
        Dimension positionCellule = gr.obtenirPositionCelluleCible(x, y);
        if (positionCellule == null) return;
        
        // Extraire les coordonnees (ligne, colonne) de
        // cette cellule
        //
        int ligneCellule = (int) positionCellule.getWidth();
        int colonneCellule = (int) positionCellule.getHeight();
        
        // Obtenir la position relative du clic dans la
        // cellule cible
        //
        Dimension cible = gr.obtenirPositionRelativeCible(x, y);
        if (cible == null) return;

        // Gerer Maillage
        //
        if (gr.presenceMaillage()) gr.obtenirMaillage().retirerMaille(ligneCellule, colonneCellule);
    }
    
    public void mouseMoved(MouseEvent e) {}
    
    public void mouseDragged(MouseEvent e) {}
    
    public void mousePressed(MouseEvent e) {}
    
    public void mouseReleased(MouseEvent e) {}
    
    public void mouseEntered(MouseEvent e) {}
    
    public void mouseExited(MouseEvent e) {}
}
