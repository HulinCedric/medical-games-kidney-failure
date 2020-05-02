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
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.util.LinkedList;

/**
 * Ecouteur souris permettant de charger dynamiquement des grilles de difficulte
 * differente et aleatoirement choisi
 * 
 * @version 1.0.0
 * @author Cedric Hulin
 */
public class EcouteurMotDeLaFinChoixNiveau extends EcouteurChoixNiveau {

	/**
	 * Hamecon sur le mot de la fin.
	 * 
	 * @since 1.0.0
	 */
	private MotDeLaFin jeu;

	/**
	 * Constructeur normal.
	 * 
	 * @param hamecon
	 *            sur le jeu de grille support.
	 * @param jeu
	 *            du mot de la fin courant.
	 * @since 1.0.0
	 */
	public EcouteurMotDeLaFinChoixNiveau(Object hamecon, MotDeLaFin jeu) {
		super(hamecon);
		this.jeu = jeu;
	}

	/**
	 * Implementation de l'evenement mouseClicked
	 * 
	 * @param evenement
	 *            capture.
	 * @since 1.0.0
	 */
	public void mouseClicked(MouseEvent e) {
		super.mouseClicked(e);

		// Verifie sur le chargement c'est bien effectuer en amont.
		//
		if (obtenirFlagReussite() == false)
			return;

		// Verifier le mode de jeu courant.
		//
		if (!obtenirHamecon().obtenirModeAdmin()) {

			// Instancier une nouvelle liste.
			//
			LinkedList listeMot = new LinkedList();
			String ligne = "";

			// Recuperer le contenu du panneau d'information.
			//
			String mots = obtenirHamecon().obtenirPanneauInformations().obtenirZoneAffichage().getText();

			// Parcourir le texte du panneau d'information.
			//
			for (int i = 0; i < mots.length(); i++) {

				// Recuperer les mots dans le texte du panneau d'information.
				//
				if (mots.charAt(i) == '\n' || (i + 1) == mots.length()) {
					if ((i + 1) == mots.length())
						ligne += String.valueOf(mots.charAt(i));
					listeMot.add(ligne);
					ligne = "";
				} else
					ligne += String.valueOf(mots.charAt(i));
			}

			// Supprimer les caracteres superflus dans le panneau
			//
			while (listeMot.contains(""))
				listeMot.remove("");
			while (listeMot.contains("\n"))
				listeMot.remove("\n");

			// Verifier la possibilite de jeu
			//
			if (!jeu.verificationListeMot(listeMot))
				return;

			// Placer les mots dans la grille
			//
			jeu.placement(listeMot);

		} else {

			// Actionner l'ecouteur du panneau d'information.
			//
			KeyListener[] key = obtenirHamecon().obtenirPanneauInformations().obtenirZoneAffichage().getKeyListeners();
			for (int i = 0; i < key.length; i++)
				if (key[i].getClass().getSimpleName().equals(EcouteurMotDeLaFinPanneauInformations.class.getSimpleName()))
					((EcouteurMotDeLaFinPanneauInformations) key[i]).keyTyped(null);

			// Repeindre l'interface.
			//
			obtenirHamecon().paintAll(obtenirHamecon().getGraphics());
		}
	}
}