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

/**
 * Ecouteur souris permettant de connaitre quelle touche du
 * clavier virtuel a ete selectionne
 * 
 * @author Cedric Hulin, Charles Fouco
 * @version 1.0.0
 */
public class EcouteurJeuDuPlacardClavier implements MouseListener, MouseMotionListener {

	/**
	 * Hamecon sur le jeu du placard courant
	 * 
	 * @since 1.0.0
	 */
	private JeuDuPlacard jeuDuPlacard;

	/**
	 * Constructeur normal
	 * 
	 * @param hamecon
	 * @since 1.0.0
	 */
	public EcouteurJeuDuPlacardClavier(JeuDuPlacard jeuDuPlacard) {
		this.jeuDuPlacard = jeuDuPlacard;
	}

	/**
	 * Implementation de l'evenement mousseClicked
	 * 
	 * @since 1.0.0
	 */
	public void mouseClicked(MouseEvent e) {

		// Modifier la couleur de l'ecran clavier
		//
		jeuDuPlacard.obtenirClavier().modifierCouleurEcran(Color.white);

		// Verifier la presence d'une image
		//
		if (((PanneauG) e.getComponent()).presenceImage()) {

			// Specifier la presence d'une image au jeu
			//
			jeuDuPlacard.modifierCheminImage(((PanneauG) e.getComponent()).obtenirCheminImage());

			// Afficher le nom de l'aliment selectionne dans
			// le clavier
			//
			File fichier = new File(jeuDuPlacard.obtenirCheminImage());
			jeuDuPlacard.obtenirClavier().modifierTexteEcran(fichier.getName().substring(0, fichier.getName().length() - 4));

			// Specifier l'element selectionne
			//
			jeuDuPlacard.obtenirClavier().modifierCouleurElement(new Color(0, 0, 0, 0));
			((PanneauG) e.getComponent()).setBackground(Color.green);
			jeuDuPlacard.obtenirClavier().repaint();
		}
	}

	public void mouseMoved(MouseEvent e) {
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