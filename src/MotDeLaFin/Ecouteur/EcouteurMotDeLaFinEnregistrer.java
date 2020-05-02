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
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JOptionPane;

/**
 * Ecouteur souris permettant d'enregistrer une partie du jeu Mot De La Fin.
 * 
 * @author Cedric Hulin
 * @version 1.0.0
 */
public class EcouteurMotDeLaFinEnregistrer extends EcouteurJeuxGrilleEnregistrer {

	/**
	 * Constructeur normal.
	 * 
	 * @param hamecon
	 *            sur le jeu de grille.
	 * @since 1.0.0
	 */
	public EcouteurMotDeLaFinEnregistrer(Object hamecon) {
		super(hamecon);
	}

	/**
	 * Implementation de l'evenement mouseClicked.
	 * 
	 * @param evenement
	 *            capture.
	 * @since 1.0.0
	 */
	public void mouseClicked(MouseEvent evenement) {
		super.mouseClicked(evenement);

		// Verifier qu'un chemin a bien ete specifier.
		//
		if (obtenirCheminEnregistrement() == null)
			return;

		// Verifier qu'il y a bien une grille a enregistrer.
		//
		if (obtenirHamecon().obtenirGrille() != null) {

			// Enregistrement de la configuration de la grille
			//
			if (Data.store(obtenirHamecon().obtenirGrille().obtenirContenu(), obtenirCheminEnregistrement() + "$Contenu", "1.0.0"))
				JOptionPane.showMessageDialog(obtenirHamecon(), Texte.load("../_Textes/Confirmations/Enregistrement grille contenu"));

			// Si l'enregistrement ne s'est pas bien passe
			//
			else
				JOptionPane.showMessageDialog(obtenirHamecon(), Texte.load("../_Textes/Erreurs/Enregistrement grille contenu"));

			// Recuperation de l'ecouteur
			//
			MouseListener[] m = obtenirHamecon().obtenirGrille().getMouseListeners();
			EcouteurMotDeLaFin ecouteur = null;
			for (int i = 0; i < m.length; i++) {
				if (m[i].getClass().getSimpleName().equals("EcouteurMotDeLaFin"))
					ecouteur = (EcouteurMotDeLaFin) m[i];
			}

			// Verification de la recuperation de l'ecouteur
			//
			if (ecouteur == null) {
				JOptionPane.showMessageDialog(obtenirHamecon(), Texte.load("../_Textes/Erreurs/Enregistrement ecouteur"));
				return;
			}

			// Enregistrer la liste des coordonnees des debut de trait et
			// verifier l'enregistrement.
			//
			if (!Data.store(ecouteur.obtenirProprietes().get("listeCoordonneesDebut"), obtenirCheminEnregistrement() + "$PartieDeb", "1.0.0"))
				JOptionPane.showMessageDialog(obtenirHamecon(), Texte.load("../_Textes/Erreurs/Probleme partie") + " "
						+ obtenirCheminEnregistrement() + "$PartieDeb");

			// Enregistrer la liste des coordonnees des debut de trait et
			// verifier l'enregistrement.
			//
			if (!Data.store(ecouteur.obtenirProprietes().get("listeCoordonneesFin"), obtenirCheminEnregistrement() + "$PartieFin", "1.0.0"))
				JOptionPane.showMessageDialog(obtenirHamecon(), Texte.load("../_Textes/Erreurs/Probleme partie") + " "
						+ obtenirCheminEnregistrement() + "$PartieFin");
			else
				JOptionPane.showMessageDialog(obtenirHamecon(), Texte.load("../_Textes/Confirmations/Enregistrement partie"));
		}
	}
}