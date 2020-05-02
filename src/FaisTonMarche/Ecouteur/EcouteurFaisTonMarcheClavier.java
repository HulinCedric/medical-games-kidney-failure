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
 * @version 1.0.0
 * @author Cedric Hulin
 */
public class EcouteurFaisTonMarcheClavier implements MouseListener, MouseMotionListener {

	/**
	 * Hamecon sur le jeu du placard courant
	 * 
	 * @since 1.0.0
	 */
	private FaisTonMarche jeu;

	/**
	 * Constructeur normal
	 * 
	 * @param jeu
	 *            du placard courant.
	 * @since 1.0.0
	 */
	public EcouteurFaisTonMarcheClavier(FaisTonMarche jeu) {
		this.jeu = jeu;
	}

	/**
	 * Implementation de l'evenement mousseClicked
	 * 
	 * @since 1.0.0
	 */
	public void mouseClicked(MouseEvent e) {

		// Modifier la couleur de l'ecran clavier
		//
		jeu.obtenirClavier().modifierCouleurEcran(Color.white);

		// Verifier la presence d'une image
		//
		if (((PanneauG) e.getComponent()).presenceImage()) {

			// Specifier la presence d'une image au jeu
			//
			jeu.modifierCheminImage(((PanneauG) e.getComponent()).obtenirCheminImage());

			// Afficher le nom de l'aliment selectionne dans
			// le clavier
			//
			File fichier = new File(jeu.obtenirCheminImage());
			jeu.obtenirClavier().modifierTexteEcran(fichier.getName().substring(0, fichier.getName().length() - 4));

			// Specifier l'element selectionne
			//
			jeu.obtenirClavier().modifierCouleurElement(new Color(0, 0, 0, 0));
			((PanneauG) e.getComponent()).setBackground(Color.green);
			jeu.obtenirClavier().repaint();
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
