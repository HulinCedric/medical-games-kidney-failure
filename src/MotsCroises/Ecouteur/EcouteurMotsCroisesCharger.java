import java.awt.event.MouseEvent;
import java.util.Vector;

public class EcouteurMotsCroisesCharger extends EcouteurJeuxGrilleCharger {

	MotsCroises jeu;
	
	public EcouteurMotsCroisesCharger(MotsCroises hamecon) {
		super(hamecon.obtenirJeuxGrille());
		jeu = hamecon;
	}

	public void mouseClicked(MouseEvent e) {
		super.mouseClicked(e);

		if (this.obtenirFlagReussite() == false)
			return;
		if (this.obtenirNomFichier() == null)
			return;

		// Charger le contenu et sa solution
		//
		jeu.ajouterSolution((Vector) Data.load(this.obtenirNomFichier() + "$Solution", "1.0.0"));
		obtenirHamecon().obtenirGrille().ajouterDonnees((Vector) Data.load(this.obtenirNomFichier() + "$Contenu", "1.0.0"));

		// Remplir la grille avec les nouvelles valeurs
		//
		obtenirHamecon().obtenirGrille().remplirGrille();

		// S'il le joueur charge une nouvelle partie
		//
		if ((jeu.obtenirSolution().equals(obtenirHamecon().obtenirGrille().obtenirContenu()))) {
			obtenirHamecon().obtenirGrille().retirerTexte();
			System.out.println("pppp");
		}
		// Cas d'un chargement d'une sauvegarde
		//
		else {
			jeu.remplirGrilleSauvegarde();
		}

		// Repaindre la grille
		//
		obtenirHamecon().paintAll(obtenirHamecon().obtenirGrille().getGraphics());
		this.modifierNomFichier();
	}
}
