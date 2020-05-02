import java.awt.event.MouseEvent;
import java.util.Vector;

public class EcouteurMotsCroisesChoixNiveau extends EcouteurChoixNiveau {
	
	MotsCroises jeu;
    
    public EcouteurMotsCroisesChoixNiveau(MotsCroises hamecon) {
        super(hamecon.obtenirJeuxGrille());
        
        this.jeu = hamecon;
    }
    
    public void mouseClicked(MouseEvent e) {
        super.mouseClicked(e);
        
        if(this.obtenirFlagReussite() == false)
            return;
        
        // Charger le contenu et sa solution
        //
        jeu.ajouterSolution((Vector) Data.load(this.obtenirNomFichier() + "$Solution", "1.0.0"));
        obtenirHamecon().obtenirGrille().ajouterDonnees((Vector) Data.load(this.obtenirNomFichier() + "$Contenu", "1.0.0"));
        
        obtenirHamecon().obtenirGrille().remplirGrille();
        
        // Retirer le texte de la grille chargee
        //
        if ((jeu.obtenirSolution().equals(obtenirHamecon().obtenirGrille().obtenirContenu()))) {        
            obtenirHamecon().obtenirGrille().retirerTexte();
        }
        
        // Repaindre la grille
        //
        obtenirHamecon().paintAll(obtenirHamecon().obtenirGrille().getGraphics());
    }
}
