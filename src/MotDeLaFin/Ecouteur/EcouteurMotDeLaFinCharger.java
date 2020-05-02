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
 * 			mise en place de chargement de sauvegarde.
 */
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Vector;

/**
 * Specialisation de l'ecouteur de chargement d'un jeu de grille avec la mise en
 * place du placement des mots sur la grille et de reprise de partie.
 * 
 * @version 1.1.0
 * @author Cedric Hulin
 */
public class EcouteurMotDeLaFinCharger extends EcouteurJeuxGrilleCharger {

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
	public EcouteurMotDeLaFinCharger(Object hamecon, MotDeLaFin jeu) {
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
	public void mouseClicked(MouseEvent evenement) {
		super.mouseClicked(evenement);

		// Verifie sur le chargement c'est bien effectuer en amont.
		//
		if (obtenirFlagReussite() == false)
			return;

		// Verifier qu'un fichier a bien ete selectionne.
		//
		if (obtenirNomFichier() == null)
			return;

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
				if (!mots.equals("\n")) {
					if ((i + 1) == mots.length())
						ligne += String.valueOf(mots.charAt(i));
					listeMot.add(ligne);
				}
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

		// Traiter le cas d'une partie enregistree.
		//
		if (chargementPartie())
			return;

		// Verifier la possibilite de jeu
		//
		if (!jeu.verificationListeMot(listeMot))
			return;

		// Placer les mots dans la grille
		//
		jeu.placement(listeMot);
	}

	/**
	 * Traitement d'une partie sauvegardee.
	 * 
	 * @return flag de partie sauvegardee.
	 * @since 1.1.0
	 */
	private boolean chargementPartie() {

		// Recuperer la grille a charger.
		//
		Vector donnees = (Vector) Data.load(this.obtenirNomFichier() + "$Contenu", "1.0.0");

		// Recuperer la partie sauvegardee.
		//
		HashMap listeCoordonneesDebut = (HashMap) Data.load(obtenirNomFichier() + "$PartieDeb", obtenirVersionFichier());
		HashMap listeCoordonneesFin = (HashMap) Data.load(obtenirNomFichier() + "$PartieFin", obtenirVersionFichier());
		modifierNomFichier();

		// Verifier si une partie a bien ete enregistrer
		//
		if (listeCoordonneesDebut == null && listeCoordonneesFin == null)

			return false;

		// Remplir la grille avec les donnees charger
		//
		obtenirHamecon().obtenirGrille().ajouterDonnees(donnees);
		obtenirHamecon().obtenirGrille().remplirGrille();

		// Suppression de l'ecouteur
		//
		MouseListener[] m = obtenirHamecon().obtenirGrille().getMouseListeners();
		MouseMotionListener[] p = obtenirHamecon().obtenirGrille().getMouseMotionListeners();

		for (int i = 0; i < m.length; i++)
			if (m[i].getClass().getSimpleName().equals("EcouteurMotDeLaFin"))
				obtenirHamecon().obtenirGrille().removeMouseListener(m[i]);

		for (int i = 0; i < m.length; i++)
			if (p[i].getClass().getSimpleName().equals("EcouteurMotDeLaFin"))
				obtenirHamecon().obtenirGrille().removeMouseMotionListener(p[i]);

		// Ajouter l'ecouteur enregistrer
		//
		obtenirHamecon().obtenirGrille().ajouterEcouteur(new EcouteurMotDeLaFin(obtenirHamecon(), listeCoordonneesDebut, listeCoordonneesFin, jeu));
		obtenirHamecon().obtenirGrille().setVisible(false);
		obtenirHamecon().obtenirGrille().setVisible(true);

		// Restituer le resultat
		//
		return true;
	}
}
