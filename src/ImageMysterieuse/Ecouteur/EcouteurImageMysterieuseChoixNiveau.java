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
import javax.swing.JButton;

/**
 * Ecouteur souris permettant de definir une nouvelle partie suivant un niveau
 * de difficulte
 * 
 * @author Charles Fouco
 * @version 1.0.0
 */
public class EcouteurImageMysterieuseChoixNiveau implements MouseListener {

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
	public EcouteurImageMysterieuseChoixNiveau(Object hamecon) {
		this.hamecon = (ImageMysterieuse) hamecon;
	}

	/**
	 * mouseClicked
	 * 
	 * @since 1.0.0
	 */
	public void mouseClicked(MouseEvent e) {

		// Recuperer le texte du bouton clic
		//
		String niveau = ((JButton) e.getComponent()).getText();
		int nbCellules = 10;

		// Definir le nombre de mailles
		// en fonction du niveau
		//
		if (niveau.equals("Facile")) {
			hamecon.modifierNiveau("Facile");
			nbCellules = 10;
		} else if (niveau.equals("Moyen")) {
			hamecon.modifierNiveau("Moyen");
			nbCellules = 15;
		} else if (niveau.equals("Difficile")) {
			hamecon.modifierNiveau("Difficile");
			nbCellules = 20;
		}

		// Ajouter une nouvelle liste d'elements
		//
		hamecon.obtenirJeu().obtenirPanneauInformations().styleZoneAffichageJList(hamecon.obtenirListChoix());
		hamecon.obtenirJeu().obtenirPanneauInformations().obtenirList().addMouseListener(new EcouteurImageMysterieusePanneauInformations(hamecon));

		// Re afficher les regles du jeu
		//
		hamecon.obtenirJeu().obtenirPanneauInformations().bulleBlanche();
		hamecon.obtenirJeu().obtenirPanneauInformations().ajouterTexteAide(Texte.load(("../_Textes/Jeux/ImageMysterieuse/Regle")));

		// Retirer la pyramide
		//
		if (hamecon.obtenirJeu().obtenirPanneauInformations().obtenirPanneauSud() != null) {
			hamecon.obtenirJeu().obtenirPanneauInformations().retirerPanneauSud();
			hamecon.obtenirJeu().obtenirPanneauInformations().obtenirPanneauSud().repaint();
		}

		// Ajouter une nouvelle maille en
		// fonction du niveau
		//
		hamecon.obtenirJeu().obtenirPanneauCentral().retirerMaillage();
		hamecon.obtenirJeu().obtenirPanneauCentral().ajouterMaillage(Color.PINK, Color.BLACK, nbCellules, nbCellules);

		// Reinitialiser le chronometre
		//
		hamecon.obtenirChronometre().stoper();
		hamecon.obtenirChronometre().initialiser();
		hamecon.obtenirChronometre().demarer();
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