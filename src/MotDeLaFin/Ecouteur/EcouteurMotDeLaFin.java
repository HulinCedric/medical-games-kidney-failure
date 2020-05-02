/**
 * IUT de Nice / Departement informatique / Stage Archet
 * Annee 2009_2010 - Ecouteur souris
 * 
 * @edition A : edition initiale.
 * 
 * @version 1.0.0
 * 
 *          version initiale.
 *          
 * @version 1.1.0
 *          
 *          gestion de l'enregistrement de partie.
 */
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;

import javax.swing.JOptionPane;

/**
 * Specialisation de l'ecouteurGrilleDrawLine dans le controle et l'effacement
 * de trait mal positionner pour le jeux du mot de la fin.
 * 
 * @version 1.1.0
 * @author Cedric Hulin
 */
public class EcouteurMotDeLaFin extends EcouteurGrilleDrawLine {

	/**
	 * Liste des mots de la partie.
	 * 
	 * @since 1.0.0
	 */
	private LinkedList liste;

	/**
	 * Flag de fin de partie.
	 * 
	 * @since 1.0.0
	 */
	private boolean finDePartie;

	/**
	 * Constructeur normal.
	 * 
	 * @param hamecon
	 *            sur le jeu de grille support.
	 * @since 1.0.0
	 */
	public EcouteurMotDeLaFin(Object hamecon) {
		super(hamecon);

		// Verifier que l'hamecon soit bien un instance de jeux grille
		//
		if (!hamecon.getClass().getSimpleName().equals("JeuxGrille")) {
			JOptionPane.showMessageDialog(null, Texte.load("../_Textes/Jeux/MotDeLaFin/Erreur hamecon"));
			return;
		}

		// Instancier une nouvelle liste.
		//
		liste = new LinkedList();
		String ligne = "";

		// Recuperer le contenu du panneau d'information.
		//
		String mots = ((JeuxGrille) obtenirHamecon()).obtenirPanneauInformations().obtenirZoneAffichage().getText();

		// Parcourir le texte du panneau d'information.
		//
		for (int i = 0; i < mots.length(); i++) {

			// Recuperer les mots dans le texte du panneau d'information.
			//
			if (mots.charAt(i) == '\n' || (i + 1) == mots.length()) {
				if ((i + 1) == mots.length())
					ligne += String.valueOf(mots.charAt(i));
				liste.add(ligne);
				ligne = "";
			} else
				ligne += String.valueOf(mots.charAt(i));
		}

		// Debuter la partie.
		//
		finDePartie = false;
	}

	/**
	 * Constructeur normal avec gestion de l'enregistrement
	 * 
	 * @param hamecon
	 *            sur le jeu de grille support.
	 * @param listeD
	 *            des coordonnees des debuts traits enregistrer.
	 * @param listeF
	 *            des coordonnees des fin de traits enregistrer.
	 * @since 1.1.0
	 */
	public EcouteurMotDeLaFin(Object hamecon, HashMap listeD, HashMap listeF, MotDeLaFin jeu) {
		super(hamecon, listeD, listeF);

		// Verifier que l'hamecon soit bien un instance de jeux grille
		//
		if (!hamecon.getClass().getSimpleName().equals("JeuxGrille")) {
			JOptionPane.showMessageDialog(null, Texte.load("../_Textes/Jeux/MotDeLaFin/Erreur hamecon"));
			return;
		}

		// Instance une nouvelle liste.
		//
		liste = new LinkedList();
		String ligne = "";

		// Recuperer le contenu du panneau d'information.
		//
		String mots = ((JeuxGrille) obtenirHamecon()).obtenirPanneauInformations().obtenirZoneAffichage().getText();

		// Parcourir le texte du panneau d'information.
		//
		for (int i = 0; i < mots.length(); i++) {

			// Recuperer les mots dans le texte du panneau d'information.
			//
			if (mots.charAt(i) == '\n' || (i + 1) == mots.length()) {
				if ((i + 1) == mots.length())
					ligne += String.valueOf(mots.charAt(i));
				liste.add(ligne);
				ligne = "";
			} else
				ligne += String.valueOf(mots.charAt(i));
		}

		// Debuter la partie.
		//
		finDePartie = false;
	}

	/**
	 * Implementation de l'evenement mousePressed
	 * 
	 * @param evenement
	 *            capture.
	 * @since 1.0.0
	 */
	public void mousePressed(MouseEvent evenement) {

		// Verifier si la partie est fini.
		//
		if (!finDePartie)
			super.mousePressed(evenement);
		else
			return;

		// Differencier le deuxieme clic
		//
		if (obtenirClic()) {

			// Obtention des points de debut et de fin
			// du trait
			//
			Point x = obtenirDernierTrait().obtenirX();
			Point y = obtenirDernierTrait().obtenirY();

			// Obtention de la position des cellules
			//
			Dimension celluleDebut = obtenirGrilleG().obtenirPositionCelluleCible((int) x.getX(), (int) x.getY());
			Dimension celluleFin = obtenirGrilleG().obtenirPositionCelluleCible((int) y.getX(), (int) y.getY());

			// Determiner l'orientation du trait
			//
			String motRaye = recuperationMotRaye(celluleDebut, celluleFin);

			// Verifier si le mot rayer est dans la
			// liste
			//
			boolean contient = true;
			if (!liste.contains(motRaye)) {

				// Retourner le mot.
				//
				StringBuffer motRayerReverse = (new StringBuffer(motRaye)).reverse();

				// Verifier si le mot a ete rayer a l'envers
				//
				if (!liste.contains(motRayerReverse.toString())) {
					retirerDernierTrait();
					contient = false;
				} else
					liste.remove(motRayerReverse.toString());

			} else
				liste.remove(motRaye);

			// Enlever le mot de la liste, si trouve.
			//
			if (contient)
				refresh();

			// Repeindre la grille.
			//
			obtenirGrilleG().repaint();

			// Controler la fin de partie
			//
			if (liste.size() == 0) {
				JOptionPane.showMessageDialog(null, Texte.load("../_Textes/Jeux/MotDeLaFin/Bravo"));
				finDePartie = true;
			}
		}
	}

	/**
	 * Rafraichie le panneau d'informations avec les mots a trouves
	 * 
	 * @since 1.0.0
	 */
	private void refresh() {

		// Vider le panneau d'information.
		//
		((JeuxGrille) obtenirHamecon()).obtenirPanneauInformations().modifierZoneAffichage("");

		// Remplir avec la liste rafraichie.
		//
		Iterator i = liste.iterator();
		while (i.hasNext())
			((JeuxGrille) obtenirHamecon()).obtenirPanneauInformations().ajouterTexteEnQueue((String) i.next());
	}

	/**
	 * Recuperation du mot rayer.
	 * 
	 * @param celluleD
	 *            clique en debut de trait.
	 * @param celluleF
	 *            clique en fin de trait.
	 * @return mot raye.
	 * @since 1.0.0
	 */
	public String recuperationMotRaye(Dimension celluleD, Dimension celluleF) {
		String motRaye = "";

		// Determiner l'orientation du trait
		//
		if (celluleD.getWidth() == celluleF.getWidth() && celluleD.getHeight() < celluleF.getHeight()) {
			for (int j = (int) celluleD.getHeight(); j <= (int) celluleF.getHeight(); j++)
				motRaye += obtenirGrilleG().obtenirCellule((int) celluleF.getWidth(), j).obtenirTexteTitre();
		}
		if (celluleD.getWidth() == celluleF.getWidth() && celluleD.getHeight() > celluleF.getHeight()) {
			for (int j = (int) celluleD.getHeight(); j >= (int) celluleF.getHeight(); j--)
				motRaye += obtenirGrilleG().obtenirCellule((int) celluleF.getWidth(), j).obtenirTexteTitre();
		}
		if (celluleD.getWidth() < celluleF.getWidth() && celluleD.getHeight() == celluleF.getHeight()) {
			for (int i = (int) celluleD.getWidth(); i <= (int) celluleF.getWidth(); i++)
				motRaye += obtenirGrilleG().obtenirCellule(i, (int) celluleF.getHeight()).obtenirTexteTitre();
		}
		if (celluleD.getWidth() > celluleF.getWidth() && celluleD.getHeight() == celluleF.getHeight()) {
			for (int i = (int) celluleD.getWidth(); i >= (int) celluleF.getWidth(); i--)
				motRaye += obtenirGrilleG().obtenirCellule(i, (int) celluleF.getHeight()).obtenirTexteTitre();
		}
		if (celluleD.getWidth() < celluleF.getWidth() && celluleD.getHeight() < celluleF.getHeight()) {
			if (celluleF.getWidth() - celluleD.getWidth() == celluleF.getHeight() - celluleD.getHeight()) {
				int j = (int) celluleD.getHeight();
				for (int i = (int) celluleD.getWidth(); i <= (int) celluleF.getWidth(); i++)
					motRaye += obtenirGrilleG().obtenirCellule(i, j++).obtenirTexteTitre();
			}
		}
		if (celluleD.getWidth() > celluleF.getWidth() && celluleD.getHeight() < celluleF.getHeight()) {
			if (celluleD.getWidth() - celluleF.getWidth() == celluleF.getHeight() - celluleD.getHeight()) {
				int j = (int) celluleD.getHeight();
				for (int i = (int) celluleD.getWidth(); i >= (int) celluleF.getWidth(); i--)
					motRaye += obtenirGrilleG().obtenirCellule(i, j++).obtenirTexteTitre();
			}
		}
		if (celluleD.getWidth() < celluleF.getWidth() && celluleD.getHeight() > celluleF.getHeight()) {
			if (celluleF.getWidth() - celluleD.getWidth() == celluleD.getHeight() - celluleF.getHeight()) {
				int j = (int) celluleD.getHeight();
				for (int i = (int) celluleD.getWidth(); i <= (int) celluleF.getWidth(); i++)
					motRaye += obtenirGrilleG().obtenirCellule(i, j--).obtenirTexteTitre();
			}
		}
		if (celluleD.getWidth() > celluleF.getWidth() && celluleD.getHeight() > celluleF.getHeight()) {
			if (celluleD.getWidth() - celluleF.getWidth() == celluleD.getHeight() - celluleF.getHeight()) {
				int j = (int) celluleD.getHeight();
				for (int i = (int) celluleD.getWidth(); i >= (int) celluleF.getWidth(); i--)
					motRaye += obtenirGrilleG().obtenirCellule(i, j--).obtenirTexteTitre();
			}
		}

		// Restituer le resultat
		//
		return motRaye;
	}
}