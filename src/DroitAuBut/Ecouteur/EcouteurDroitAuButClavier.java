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
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.File;

/**
 * Ecouteur souris permettant de selectionner une image dans le clavier
 * 
 * @author Charles Fouco
 * @version 1.0.0
 */
public class EcouteurDroitAuButClavier implements MouseListener, MouseMotionListener {

	/**
	 * Hamecon suport
	 * 
	 * @since 1.1.0
	 */
	private DroitAuBut hamecon;

	

	/**
	 * Constructeur normal
	 * 
	 * @param hamecon
	 * @since 1.0.0
	 */
	public EcouteurDroitAuButClavier(Object hamecon) {
		this.hamecon = (DroitAuBut) hamecon;
	}

	/**
	 * MouseClicked
	 * 
	 * @since 1.0.0
	 */
	public void mouseClicked(MouseEvent e) {

		
		// Verifier le statut du jeu
		//
		if (!hamecon.obtenirStatutJeu())
			return;

		// Recuperer le texte du bouton clic
		//
		File cheminImage = new File(((PanneauG) e.getComponent()).obtenirCheminImage());
		String nomImage = cheminImage.getName().substring(0, cheminImage.getName().length() - 4);

		// Cas d'une bonne reponse
		//
		if (hamecon.obtenirSolution().equals(nomImage)) {

			// Recuperer la position du joueur
			//
			int ligne = (int) hamecon.obtenirPositionJoueur().getWidth();
			int colonne = (int) hamecon.obtenirPositionJoueur().getHeight();

			// Traiter le cas d'un debordement de la grille
			//
			if (colonne != hamecon.obtenirJeuxGrille().obtenirGrille().obtenirNbColonnes()) {
				hamecon.obtenirJeuxGrille().obtenirGrille().obtenirCellule(ligne, colonne).retirerImage();
				hamecon.modifierPositionJoueur(new Dimension(ligne, colonne + 1));
				hamecon.obtenirJeuxGrille().obtenirGrille().obtenirCellule((int) hamecon.obtenirPositionJoueur().getWidth(),
						(int) hamecon.obtenirPositionJoueur().getHeight()).ajouterImage(hamecon.obtenirCheminImage());
				hamecon.obtenirJeuxGrille().obtenirGrille().repaint();

				// Afficher un message d'encouragement
				//
				hamecon.obtenirJeuxGrille().obtenirPanneauInformations().bulleBlanche();
				hamecon.obtenirJeuxGrille().obtenirPanneauInformations().ajouterTexteAide(Texte.load("../_Textes/Jeux/DroitAuBut/Encouragement"));

				// Afficher une nouvelle devinette
				//
				hamecon.chargerDevinette();
			}
			// Traiter le cas ou le joueur a gagne
			//
			else {

				// Affichage d'une bulle verte ainsi qu'un
				// message de reussite
				//
				hamecon.obtenirJeuxGrille().obtenirPanneauInformations().bulleVerte();
				hamecon.obtenirJeuxGrille().obtenirPanneauInformations().ajouterTexteAide(Texte.load("../_Textes/Jeux/DroitAuBut/Bravo"));
				
				// Rendre le clavier inutilisable
				//
				hamecon.finPartie();
			}
		} else {

			// Affichage d'une bulle rouge ainsi qu'un
			// texte
			//
			hamecon.obtenirJeuxGrille().obtenirPanneauInformations().bulleRouge();
			hamecon.obtenirJeuxGrille().obtenirPanneauInformations().ajouterTexteAide(Texte.load("../_Textes/Jeux/DroitAuBut/Dommage"));
		}

	}

	public void mouseEntered(MouseEvent e) {

		// Changer le curseur
		//
		((PanneauG) e.getComponent()).setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

		// Recuperer le texte du bouton 
		//
		File cheminImage = new File(((PanneauG) e.getComponent()).obtenirCheminImage());
		String nomImage = cheminImage.getName().substring(0, cheminImage.getName().length() - 4);

		// Afficher le nom de l'image dans le clavier
		//
		hamecon.obtenirJeuxGrille().obtenirClavier().modifierTexteEcran(nomImage);
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

	public void mouseDragged(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	public void mouseMoved(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

}
