import java.awt.event.MouseEvent;
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
import java.awt.event.MouseListener;
import javax.swing.JButton;

/**
 * Ecouteur souris permettant de choisir un nouveau joueur
 * 
 * @author Charles Fouco
 * @version 1.0.0
 */
public class EcouteurDroitAuButChoixJoueur implements MouseListener {

	/**
	 * Hamecon
	 * 
	 * @since 1.0.0
	 */
	private DroitAuBut hamecon;

	/**
	 * Constructeur normal
	 * 
	 * @since 1.0.0
	 */
	public EcouteurDroitAuButChoixJoueur(Object hamecon) {
		this.hamecon = (DroitAuBut) hamecon;
	}

	/**
	 * mouseClicked
	 * 
	 * @since 1.0.0
	 */
	public void mouseClicked(MouseEvent e) {

		// Recuperer le texte du bouton clic
		//
		String niveau = ((JButton) e.getComponent()).getIcon().toString();

		// Definir le nombre de mailles
		// en fonction du niveau
		//

		hamecon.modifierImageJoueur(niveau);

		// Afficher le nouveau joueur
		//
		hamecon.obtenirJeuxGrille().obtenirGrille().obtenirCellule((int) hamecon.obtenirPositionJoueur().getWidth(),
				(int) hamecon.obtenirPositionJoueur().getHeight()).ajouterImage(hamecon.obtenirCheminImage());
		hamecon.obtenirJeuxGrille().obtenirGrille().repaint();
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
