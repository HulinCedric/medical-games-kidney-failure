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
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.File;

/**
 * Ecouteur souris permettant de definir si l'element
 * selectionne est la solution
 * 
 * @author Charles Fouco
 * @version 1.0.0
 */
public class EcouteurQuizzClavier implements MouseListener, MouseMotionListener {
    
    /**
     * Hamecon
     * 
     * @since 1.0.0
     */
    private Quizz hamecon;
    
    /**
     * Constructeur normal
     * 
     * @param hamecon
     * 
     * @since 1.0.0
     */
    public EcouteurQuizzClavier(Object hamecon) {
        this.hamecon = (Quizz) hamecon;
    }
    
    /**
     * mouseClicked
     * 
     * @since 1.0.0
     */
    public void mouseClicked(MouseEvent e) {
        
        // Verifier le statut de la partie
        //
        if(!hamecon.obtenirStatutPartie())
            return;
        
        // Recuperer le nom de l'image selectionnee
        //
        File cheminImage = new File(((PanneauG) e.getComponent()).obtenirCheminImage());
        String nomImageSelectionne = cheminImage.getName().substring(0, cheminImage.getName().length() - 4);
        
        // Recuperer le nom de la solution ainsi que son
        // chemin image
        //
        String solution = hamecon.obtenirSolution();
        
        // Indication de reussite
        //
        String reponse;
        
        // Cas ou l'item selectionne correspond
        // a la reponse
        //
        if (solution.equals(nomImageSelectionne)) {
            
            // Affichage d'une bulle rouge ainsi qu'un
            // texte
            //
            hamecon.obtenirJeu().obtenirPanneauInformations().bulleBlanche();
            hamecon.obtenirJeu().obtenirPanneauInformations().ajouterTexteAide(Texte.load("../_Textes/Jeux/Quizz/Bravo"));
            
            // Indiquer une bonne reponse
            //
            reponse = "Vrai   ";
            
            // Augmenter le nombre de bonnes reponses
            //
            hamecon.ajouterBonneReponse();
        }
        else {
            
            // Affichage d'une bulle rouge ainsi qu'un
            // texte
            //
            hamecon.obtenirJeu().obtenirPanneauInformations().bulleRouge();
            hamecon.obtenirJeu().obtenirPanneauInformations().ajouterTexteAide(Texte.load("../_Textes/Jeux/Quizz/Dommage"));
            
            // Indiquer une bonne reponse
            //
            reponse = "   Faux   ";
            
            // Augmenter le nombre de movaises reponses
            //
            hamecon.ajouterMovaisesReponse();
        }
        
        String nouvellePhrase = reponse + hamecon.obtenirQuestion() + " Reponse : " + hamecon.obtenirSolution();
        hamecon.modifierContenuJList(nouvellePhrase);
        
        hamecon.chargerNouvelleQuestion();
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
    
    public void mouseDragged(MouseEvent arg0) {
    // TODO Auto-generated method stub
    
    }
    
    public void mouseMoved(MouseEvent arg0) {
    // TODO Auto-generated method stub
    
    }
}