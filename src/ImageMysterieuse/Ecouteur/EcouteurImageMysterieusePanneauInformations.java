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
import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;

/**
 * Ecouteur souris permettant de definir si l'element
 * selectionne est la solution
 * 
 * @author Charles Fouco
 * @version 1.0.0
 */
public class EcouteurImageMysterieusePanneauInformations implements MouseListener {
    
    /**
     * Hamecon
     * 
     * @since 1.0.0
     */
    private ImageMysterieuse hamecon;
    
    /**
     * Constructeur normal
     * 
     * @param hamecon
     * 
     * @since 1.0.0
     */
    public EcouteurImageMysterieusePanneauInformations(Object hamecon) {
        this.hamecon = (ImageMysterieuse) hamecon;
    }
    
    /**
     * mouseClicked
     * 
     * @since 1.0.0
     */
    public void mouseClicked(MouseEvent e) {
        
        // Recuperer le nom de l'item selectionne
        //
        String itemSelectionne = (String) hamecon.obtenirJeu().obtenirPanneauInformations().obtenirList().getSelectedValue();
        
        // Recuperer le nom de la solution ainsi que son
        // chemin image
        //
        String solution = hamecon.obtenirSolution();
        File file = new File(hamecon.obtenirJeu().obtenirPanneauCentral().obtenirCheminImage());
        
        // Cas ou l'item selectionne correspond
        // a la reponse
        //
        if (solution.equals(itemSelectionne)) {
            
            // Rendre la liste d'item inactive
            //
            hamecon.obtenirJeu().obtenirPanneauInformations().obtenirList().setEnabled(false);
            
            // Affichage d'une bulle verte ainsi qu'un
            // message de reussite
            //
            hamecon.obtenirJeu().obtenirPanneauInformations().bulleVerte();
            hamecon.obtenirJeu().obtenirPanneauInformations().ajouterTexteAide(Texte.load("../_Textes/Jeux/ImageMysterieuse/Bravo"));
            
            // Decouvrir l'image
            //
            hamecon.obtenirSupport().retirerMaillage();
            
            // Decouvrir via la pyramide
            // la categorie de l'aliment
            //
            hamecon.obtenirJeu().obtenirPanneauInformations().ajouterPanneauSud(Color.white);
            hamecon.obtenirJeu().obtenirPanneauInformations().ajouterImageSud("../_Images/Pyramide/3D/" + file.getParentFile().getName() + ".jpg");
            
            // Stop le chronometre
            //
            hamecon.obtenirChronometre().stoper();
        }
        else {
            
            // Affichage d'une bulle rouge ainsi qu'un
            // texte
            //
            hamecon.obtenirJeu().obtenirPanneauInformations().bulleRouge();
            hamecon.obtenirJeu().obtenirPanneauInformations().ajouterTexteAide(Texte.load("../_Textes/Jeux/ImageMysterieuse/Dommage"));
        }
    }
    
    public void mouseEntered(MouseEvent arg0) {
    // TODO Auto-generated method stub
    
    }
    
    public void mouseExited(MouseEvent arg0) {
    // TODO Auto-generated method stub
    
    }
    
    public void mousePressed(MouseEvent arg0) {
    // TODO Auto-generated method stub
    
    }
    
    public void mouseReleased(MouseEvent arg0) {
    // TODO Auto-generated method stub
    
    }
}