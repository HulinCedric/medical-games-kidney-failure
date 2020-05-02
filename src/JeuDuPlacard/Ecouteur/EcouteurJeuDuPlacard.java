/**
 * IUT de Nice / Departement informatique / Stage Archet
 * Annee 2009_2010 - Ecouteur souris
 * 
 * @edition A : edition initiale.
 * 
 * @version 1.0.0
 * 
 *          version initiale.
 */
import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.File;

import javax.swing.JFrame;

/**
 * Ecouteur souris permettant de gerer l'aspect visuel du
 * jeu du placard
 * 
 * @author Cedric Hulin, Charles Fouco
 * @version 1.0.0
 */
public class EcouteurJeuDuPlacard implements MouseListener, MouseMotionListener {

	/**
	 * Hamecon sur une grille
	 * 
	 * @since 1.0.0
	 */
	private JFrame hamecon;

	/**
	 * Hamecon sur le jeu du placard courant
	 * 
	 * @since 1.0.0
	 */
	private JeuDuPlacard jeu;

	/**
	 * Constructeur normal
	 * 
	 * @param hamecon
	 * @since 1.0.0
	 */
	public EcouteurJeuDuPlacard(JFrame hamecon, JeuDuPlacard jeu) {
		this.hamecon = hamecon;
		this.jeu = jeu;
	}

	/**
	 * Implementation de l'evenement mouseClicked
	 * 
	 * @param evenement
	 *            capture.
	 * @since 1.0.0
	 */
	public void mouseClicked(MouseEvent evenement) {

		// Verifier la fin de partie.
		//
		if (jeu.obtenirClavier().obtenirNombreElement() == 0)
			return;

		// Traiter l'action du joueur dans la pyramide.
		//
		jeu.actionJoueur(hamecon, evenement.getX(), evenement.getY());
	}

	public void mouseEntered(MouseEvent e) {

		// Modifier la couleur de l'ecran clavier
		//
		jeu.obtenirClavier().modifierCouleurEcran(Color.white);

		// Verifier si un aliment est selectionner
		//
		if (jeu.obtenirCheminImage() != null) {

			// Recuperer les informations de l'image
			// selectionner
			//
			File fichier = new File(jeu.obtenirCheminImage());
			String nomAliment = (String) fichier.getName().subSequence(0, fichier.getName().length() - 4);

			// Afficher un message d'aide
			//
			jeu.obtenirClavier().modifierTexteEcran(nomAliment + Texte.load("../_Textes/Jeux/JeuDuPlacard/Question") + hamecon.getTitle() + " ?");
		} else
			// Afficher un message d'aide
			//
			jeu.obtenirClavier().modifierTexteEcran(hamecon.getTitle());
	}

	public void mouseMoved(MouseEvent e) {
	}

	public void mouseDragged(MouseEvent e) {
	}

	public void mousePressed(MouseEvent e) {
	}

	public void mouseReleased(MouseEvent e) {
	}

	public void mouseExited(MouseEvent e) {
	}
}