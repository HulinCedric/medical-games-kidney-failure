import java.awt.event.MouseEvent;

import javax.swing.JOptionPane;

public class EcouteurMotsCroisesEnregistrer extends EcouteurJeuxGrilleEnregistrer {
    
	MotsCroises jeu;
	
    public EcouteurMotsCroisesEnregistrer(MotsCroises hamecon) {
        super(hamecon.obtenirJeuxGrille());
        
        jeu = hamecon;
    }
    
    public void mouseClicked(MouseEvent e) {
        super.mouseClicked(e);
        
        if (obtenirHamecon().obtenirGrille() != null) {
            
            // Enregistrement du contenu de la
            // grille
            //
            if (!Data.store(obtenirHamecon().obtenirGrille().obtenirContenu(), obtenirCheminEnregistrement() + "$Contenu", "1.0.0"))
               JOptionPane.showMessageDialog(obtenirHamecon(), Texte.load("../_Textes/Erreurs/Enregistrement grille contenu"));
            
            // Enregistrer la grille en tant que solution
            // si on est en mode creation
            //
            if (obtenirHamecon().obtenirModeAdmin()) {
                
                // Enregistrement de la configuration de la
                // grille
                //
                if (!Data.store(obtenirHamecon().obtenirGrille().obtenirContenu(), obtenirCheminEnregistrement() + "$Solution", "1.0.0"))
                   JOptionPane.showMessageDialog(obtenirHamecon(), Texte.load("../_Textes/Erreurs/Enregistrement grille solution"));
            }
            else {
                
             // Enregistrement de la configuration de la
                // grille
                //
                if (!Data.store(jeu.obtenirSolution(), obtenirCheminEnregistrement() + "$Solution", "1.0.0"))
                   JOptionPane.showMessageDialog(obtenirHamecon(), Texte.load("../_Textes/Erreurs/Enregistrement grille solution"));
            }
        }
    }
}
