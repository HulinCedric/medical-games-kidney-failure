/**
 * IUT de Nice / Departement informatique / Module APO-Java
 * Annee 2009_2010 - Ecouteur
 */
import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JOptionPane;

/**
 * Ecouteur souris permettant de remplir les cellule d'une grille de jeux
 * 
 * @author Cedric Hulin, Charles Fouco
 * @version 1.2.0
 */
public class EcouteurJeuxGrilleMemo implements MouseListener, MouseMotionListener {

	/**
	 * Hamecon sur un jeux de grille
	 * 
	 * @since 1.0.0
	 */
	private Memo hamecon;

	/**
	 * Drapeau determinant le nombre de click
	 * 
	 * @since 1.1.0
	 */
	private boolean premierClic;

	/**
	 * Variables servant a sauvegarder les donnees du premier clic
	 * 
	 * @since 1.1.0
	 */
	private int ligneCelluleCiblePremierClic = 1;
	private int colonneCelluleCiblePremierClic = 1;
	private String imageCelluleCiblePremierClic = "Default";

	/**
	 * Compteur permettant de determiner une partie gagnee
	 * 
	 * @since 1.0.0
	 */
	private int nombreCelluleDecouverte = 0;

	/**
	 * Temps de Recouvrement des images
	 * 
	 * @since 1.2.0
	 */
	private int tempsRecouvrement;

	/**
	 * Constructeur normal
	 * 
	 * @param hamecon
	 * @since 1.0.0
	 */
	public EcouteurJeuxGrilleMemo(Object hamecon) {
		this.hamecon = (Memo) hamecon;
		this.premierClic = true;
		this.tempsRecouvrement = ((Memo) hamecon).obtenirTempsRecouvrement();
	}

	/**
	 * Implementation de l'evenement mouseClicked
	 * 
	 * @since 1.0.0
	 */
	public void mouseClicked(MouseEvent e) {

		// Verifier la presence d'un maillage
		//
		if (!hamecon.obtenirJeuxGrille().obtenirGrille().presenceMaillage())
			return;

		// Obtenir les coordonnees absolues du clic courant
		//
		int x = e.getX();
		int y = e.getY();

		// Obtenir la designation de la cellule cible
		//
		Dimension positionCellule = hamecon.obtenirJeuxGrille().obtenirGrille().obtenirPositionCelluleCible(x, y);
		if (positionCellule == null)
			return;

		// Extraire les coordonnees (ligne, colonne) de
		// cette cellule
		//
		int ligneCellule = (int) positionCellule.getWidth();
		int colonneCellule = (int) positionCellule.getHeight();

		// Obtenir la cellule cible
		//
		CelluleG celluleCible = hamecon.obtenirJeuxGrille().obtenirGrille().obtenirCellule(ligneCellule, colonneCellule);

		// Traitement lors de la decouverte
		// de la premiere image
		//
		if (premierClic) {

			// Retirer la maille de la cellule cible
			// si une maille est presente
			//
			if (hamecon.obtenirJeuxGrille().obtenirGrille().obtenirMaillage().retirerMaille(ligneCellule, colonneCellule)) {

				// Repeindre la celulle decouverte
				//
				hamecon.obtenirJeuxGrille().obtenirGrille().obtenirCellule(ligneCellule, colonneCellule).paintAll(
						hamecon.obtenirJeuxGrille().obtenirGrille().obtenirCellule(ligneCellule, colonneCellule).getGraphics());

				// Memoriser la cellule decouverte
				//
				ligneCelluleCiblePremierClic = ligneCellule;
				colonneCelluleCiblePremierClic = colonneCellule;

				// Memoriser l'image decouverte
				//
				if (celluleCible.presenceImage()) {
					imageCelluleCiblePremierClic = celluleCible.obtenirCheminImage();
				}

				// Donner la main pour le traitement
				// du second clic
				//
				premierClic = false;
			}
		} else {

			// Verifier qu'il ne s'agit pas la meme
			// cellule
			//
			if (ligneCellule != ligneCelluleCiblePremierClic || colonneCellule != colonneCelluleCiblePremierClic) {

				// Retirer la maille de la cellule cible
				// si une maille est presente
				//
				if (hamecon.obtenirJeuxGrille().obtenirGrille().obtenirMaillage().retirerMaille(ligneCellule, colonneCellule)) {

					// Repeindre la cellule decouverte
					//
					hamecon.obtenirJeuxGrille().obtenirGrille().obtenirCellule(ligneCellule, colonneCellule).paintAll(
							hamecon.obtenirJeuxGrille().obtenirGrille().obtenirCellule(ligneCellule, colonneCellule).getGraphics());

					// Verifier la correspondance entre
					// deux images identiques
					//
					// - cas de deux images differentes
					//
					if (!imageCelluleCiblePremierClic.equals(celluleCible.obtenirCheminImage())) {

						// Atendre un delai specifie par fichier de conf
						// 		
						Chrono.attendre(tempsRecouvrement);

						// Recouvrir les deux images decouvertes
						//
						hamecon.obtenirJeuxGrille().obtenirGrille().obtenirMaillage().ajouterMaille(ligneCelluleCiblePremierClic,
								colonneCelluleCiblePremierClic);
						hamecon.obtenirJeuxGrille().obtenirGrille().obtenirMaillage().ajouterMaille(ligneCellule, colonneCellule);
					}
					// - cas de deux images identiques
					//
					else {

						// Augmenter le nombre d'images decouvertes
						//
						nombreCelluleDecouverte++;

						// Cas ou la partie est terminee
						//
						if (nombreCelluleDecouverte == hamecon.obtenirJeuxGrille().obtenirGrille().obtenirNbCellules() / 2) {

							// Stoper le chronometre
							//
							hamecon.obtenirChronometre().stoper();

							// Afficher un message de reussite
							//
							JOptionPane.showMessageDialog(null, Texte.load("../_Textes/Jeux/Memo/Bravo"));
							return;
						}
					}

					// Rendre la main pour le traitement
					// du premier clic
					//
					premierClic = true;
				}
			}
		}

	}

	// ------------------------------------- *** Classe interne privee Chrono
	// 

	private static class Chrono {

		private static void attendre(int tms) {

			try {

				Thread.currentThread();
				Thread.sleep(tms);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
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
