/**
 * IUT de Nice / Departement informatique / Module APO-Java
 * Annee 2009_2010 - Ecouteur
 */
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JButton;

/**
 * Ecouteur souris permettant de connaitre quelle touche du
 * clavier virtuel a ete selectionne
 * 
 * @author Cedric Hulin, Charles Fouco
 * @version 1.0.0
 */
public class EcouteurJeuxGrilleClavier implements MouseListener, MouseMotionListener {
    
    /**
     * Hamecon sur un jeux de grille
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
    public EcouteurJeuxGrilleClavier(Object hamecon) {
        this.hamecon = hamecon;
    }
    
    /**
     * Implementation de l'evenement mousseClicked
     * 
     * @since 1.0.0
     */
    public void mouseClicked(MouseEvent e) {
        ((JeuxGrille) hamecon).modifierBoutonSelectionne(((JButton) e.getComponent()));
        
        if ((((JeuxGrille) hamecon).obtenirBoutonSelectionne().getText()).equals(""))
            ((JeuxGrille) hamecon).obtenirClavier().modifierTexteEcran("Retirer une lettre d'une case");
        
        else ((JeuxGrille) hamecon).obtenirClavier().modifierTexteEcran(((JButton) e.getComponent()).getText());
    }
    
    public void mouseMoved(MouseEvent e) {}
    
    public void mouseDragged(MouseEvent e) {}
    
    public void mousePressed(MouseEvent e) {}
    
    public void mouseReleased(MouseEvent e) {}
    
    public void mouseEntered(MouseEvent e) {}
    
    public void mouseExited(MouseEvent e) {}
}
