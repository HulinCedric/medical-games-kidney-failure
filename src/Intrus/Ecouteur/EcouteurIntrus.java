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

/**
 * Ecouteur souris permettant de definir les regles du jeu
 * de l'intrus
 * 
 * @author Cedric Hulin
 * @version 1.0.0
 */
public class EcouteurIntrus implements MouseListener, MouseMotionListener {

	/**
	 * Hamecon sur un jeux de grille
	 * 
	 * @since 1.0.0
	 */
	private JeuxGrille hamecon;

	/**
	 * Constructeur normal
	 * 
	 * @param hamecon
	 * @since 1.0.0
	 */
	public EcouteurIntrus(Object hamecon) {
		this.hamecon = (JeuxGrille) hamecon;
	}

	/**
	 * Implementation de l'evenement mousePressed
	 * 
	 * @param evenement
	 *            capture.
	 * @since 1.0.0
	 */
	public void mousePressed(MouseEvent evenement) {

		// Traiter le cas ou aucun intrus n'a ete
		// selectionne.
		//
		if (Intrus.obtenirIntrus() == null) {
			hamecon.obtenirPanneauInformations().bulleBlanche();
			hamecon.obtenirPanneauInformations().ajouterTexteAide(Texte.load(("../_Textes/Jeux/Intrus/Recommencer")));
			return;
		}

		// Traiter l'action du joueur dans l'intrus.
		//
		Intrus.actionJoueur(evenement.getX(), evenement.getY());
	}

	public void mouseClicked(MouseEvent e) {
	}

	public void mouseEntered(MouseEvent e) {
	}

	public void mouseExited(MouseEvent e) {
	}

	public void mouseReleased(MouseEvent e) {
	}

	public void mouseDragged(MouseEvent e) {
	}

	public void mouseMoved(MouseEvent e) {
	}
}