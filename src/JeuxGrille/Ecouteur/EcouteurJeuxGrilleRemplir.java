/**
 * IUT de Nice / Departement informatique / Module APO-Java
 * Annee 2009_2010 - Ecouteur
 */
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JOptionPane;

/**
 * Ecouteur souris permettant de remplir les cellule d'une
 * grille de jeux
 * 
 * @author Cedric Hulin, Charles Fouco
 * @version 1.0.0
 */
public class EcouteurJeuxGrilleRemplir implements MouseListener, MouseMotionListener {
    
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
    public EcouteurJeuxGrilleRemplir(Object hamecon) {
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
        Dimension positionCellule = ((JeuxGrille) hamecon).obtenirGrille().obtenirPositionCelluleCible(x, y);
        if (positionCellule == null) return;
        
        // Extraire les coordonnees (ligne, colonne) de
        // cette cellule
        //
        int ligneCellule = (int) positionCellule.getWidth();
        int colonneCellule = (int) positionCellule.getHeight();
        
        // Obtenir la position relative du clic dans la
        // cellule cible
        //
        Dimension cible = ((JeuxGrille) hamecon).obtenirGrille().obtenirPositionRelativeCible(x, y);
        if (cible == null) return;
        
        // Visualiser les coordonnees de cette cellule
        //
        System.out.println("[" + ligneCellule + ", " + colonneCellule + "]");
        
        // Gere Caractere
        //
        if (((JeuxGrille) hamecon).obtenirBoutonSelectionne() == null)
            JOptionPane.showMessageDialog(null, Texte.load("../_Textes/Indications/Clavier"));
        else {
            if (!(((JeuxGrille) hamecon).obtenirGrille().obtenirCellule(ligneCellule - 1, colonneCellule - 1).getBackground().equals(Color.GRAY))) {
                
                if ((((JeuxGrille) hamecon).obtenirBoutonSelectionne().getText()).equals("")) {
                    ((JeuxGrille) hamecon).obtenirGrille().retirerTexte(ligneCellule, colonneCellule);
                    ((JeuxGrille) hamecon).obtenirGrille().modifierCouleurCellule(ligneCellule, colonneCellule, Color.WHITE);
                }
                else {
                    ((JeuxGrille) hamecon).obtenirGrille().ajouterTexte(ligneCellule, colonneCellule, ((JeuxGrille) hamecon).obtenirBoutonSelectionne().getText());
                    ((JeuxGrille) hamecon).obtenirGrille().modifierCouleurCellule(ligneCellule, colonneCellule, Color.WHITE);
                }
            }
        }
        
        // Gerer Maillage
        //
        if (((JeuxGrille) hamecon).obtenirGrille().presenceMaillage()) ((JeuxGrille) hamecon).obtenirGrille().obtenirMaillage().retirerMaille(ligneCellule, colonneCellule);
    }
    
    public void mouseMoved(MouseEvent e) {}
    
    public void mouseDragged(MouseEvent e) {}
    
    public void mousePressed(MouseEvent e) {}
    
    public void mouseReleased(MouseEvent e) {}
    
    public void mouseEntered(MouseEvent e) {}
    
    public void mouseExited(MouseEvent e) {}
}
