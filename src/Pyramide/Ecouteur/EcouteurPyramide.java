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
import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.File;

/**
 * Ecouteur souris permettant de gerer l'aspect visuel du
 * jeu de la pyramide.
 * 
 * @version 1.0.0
 * @author Cedric Hulin
 */
public class EcouteurPyramide implements MouseListener, MouseMotionListener {

	/**
	 * Hamecon sur un jeu de la pyramide.
	 * 
	 * @since 1.0.0
	 */
	private Pyramide hamecon;

	/**
	 * Constructeur normal.
	 * 
	 * @param hamecon
	 *            sur un jeu de la pyramide.
	 * @since 1.0.0
	 */
	public EcouteurPyramide(Pyramide hamecon) {
		this.hamecon = hamecon;
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
		if (hamecon.obtenirClavier().obtenirNombreElement() == 0)
			return;

		// Traiter l'action du joueur dans la pyramide.
		//
		hamecon.actionJoueur(evenement.getX(), evenement.getY());
	}

	public void mouseMoved(MouseEvent e) {

		// Verifier la fin de partie.
		//
		if (hamecon.obtenirClavier().obtenirNombreElement() == 0)
			return;

		// Obtenir la designation de la cellule cible
		//
		GrilleG gr = hamecon.obtenirGrille();

		// Obtenir la position de la cellule
		//
		Dimension dim = gr.obtenirPositionCelluleCible(e.getX(), e.getY());
		if (dim.getHeight() > gr.obtenirNbColonnes())
			dim.setSize(dim.getWidth(), gr.obtenirNbColonnes());
		if (dim.getWidth() > gr.obtenirNbLignes())
			dim.setSize(gr.obtenirNbLignes(), dim.getHeight());

		// Verifier si un aliment est selectionner
		//
		if (hamecon.obtenirCheminImage() != null) {

			// Recuperer les informations de l'image
			// selectionner
			//
			File fichier = new File(hamecon.obtenirCheminImage());
			String nomAliment = (String) fichier.getName().subSequence(0, fichier.getName().length() - 4);

			// Afficher un message d'aide
			//
			hamecon.obtenirClavier().modifierTexteEcran(
					nomAliment + Texte.load("../_Textes/Jeux/Pyramide/Question") + hamecon.categorie(dim.width, dim.height) + " ?");
		} else
			// Afficher un message d'aide
			//
			hamecon.obtenirClavier().modifierTexteEcran(hamecon.categorie(dim.width, dim.height));
	}

	public void mouseDragged(MouseEvent e) {
	}

	public void mousePressed(MouseEvent e) {
	}

	public void mouseReleased(MouseEvent e) {
	}

	public void mouseEntered(MouseEvent e) {
	}

	public void mouseExited(MouseEvent e) {
	}
}