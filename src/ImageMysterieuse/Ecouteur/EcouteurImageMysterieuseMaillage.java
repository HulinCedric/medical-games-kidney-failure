/**
 * IUT de Nice / Departement informatique / Module APO-Java
 * Annee 2009_2010 - Applications graphiques
 * 
 * @edition A : edition initiale
 * 
 * @version 1.0.0 :
 * 
 *          version initiale
 */
import java.awt.Dimension;
import java.awt.event.MouseEvent;


/**
 * Ecouteur souris permettant de retirer
 * une maille d'un maillage
 * 
 * @author Charles Fouco
 * @version 1.0.0
 */
public class EcouteurImageMysterieuseMaillage extends EcouteurPanneauG {
    
    /**
     * Constructeur normal
     * 
     * @param hamecon
     * 
     * @since 1.0.0
     */
    public EcouteurImageMysterieuseMaillage(PanneauG hamecon) {
        panneauCible = hamecon;
    }
    
    /**
     * mouseClicked
     * 
     * @since 1.0.0
     */
    public void mouseClicked(MouseEvent e) {
    	
    	// Verifier la presence d'un maillage
    	//
    	if(!panneauCible.presenceMaillage()) return;
        
        // Obtenir les coordonnees absolues du clic courant
        //
        int x = e.getX();
        int y = e.getY();
        
        // Obtenir la designation de la maille cible
        //
        Dimension cible = panneauCible.obtenirPositionCible(x, y);
        if (cible == null) return;
        
        // Extraire les coordonnees (ligne, colonne) de
        // cette maille
        //
        int ligne = (int) cible.getWidth();
        int colonne = (int) cible.getHeight();
        
        // Retirer la maille cible
        //
        panneauCible.obtenirMaillage().retirerMaille(ligne, colonne);
    }
}