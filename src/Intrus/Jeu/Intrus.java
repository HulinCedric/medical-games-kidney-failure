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
import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.io.File;

import org.jdom.Element;

/**
 * Regroupement d'objet necessaire dans une classe pour
 * construire le jeu de l'intrus
 * 
 * @version 1.0.0
 * @author Charles Fouco, Cedric Hulin
 */
public class Intrus {

	/**
	 * Hamecon sur un jeux de grille
	 * 
	 * @since 1.0.0
	 */
	private static JeuxGrille jeu;

	/**
	 * Solution du jeu de l'intrus.
	 * 
	 * @since 1.0.0
	 */
	private static String intrus = null;

	/**
	 * Nombre de partie gagnee.
	 * 
	 * @since 1.0.0
	 */
	private static int score = 0;

	/**
	 * Nombre de partie jouee.
	 * 
	 * @since 1.0.0
	 */
	private static int partieJouee = 0;

	/**
	 * Constructeur normal
	 * 
	 * @param configurationG
	 *            de la grille.
	 * @param configurationP
	 *            du panneau d'information.
	 * @since 1.0.0
	 */
	public Intrus(Element configurationG, Element configurationP) {

		// Creer un nouveau jeu de grille.
		//
		jeu = new JeuxGrille(configurationG);
		if (jeu.obtenirGrille() == null)
			System.exit(0);

		// Ajouter un panneau information.
		//
		jeu.ajouterPanneauInformation(configurationP);
		jeu.obtenirPanneauInformations().ajouterPanneauSud(Color.white);

		// Mise en place des ecouteurs de difficulte.
		//
		jeu.obtenirBoutonGauche().addMouseListener(new EcouteurIntrusChoixNiveau(obtenirJeuxGrille()));
		jeu.obtenirBoutonCentre().addMouseListener(new EcouteurIntrusChoixNiveau(obtenirJeuxGrille()));
		jeu.obtenirBoutonDroite().addMouseListener(new EcouteurIntrusChoixNiveau(obtenirJeuxGrille()));

		// Ajout de l'ecouteur du jeu.
		//
		jeu.obtenirGrille().ajouterEcouteur(new EcouteurIntrus(obtenirJeuxGrille()));

		// Supprimer le bandeau du sud.
		//
		jeu.retirerPanneauSud();

		// Modifier le titre de fenetre.
		//
		jeu.modifierTitreFenetre("Intrus");

		// Simuler un clic sur la difficulte facile.
		//
		for (int i = 0; i < jeu.obtenirBoutonGauche().getMouseListeners().length; i++)
			if (jeu.obtenirBoutonGauche().getMouseListeners()[i].getClass().equals(EcouteurIntrusChoixNiveau.class))
				(jeu.obtenirBoutonGauche().getMouseListeners())[i].mouseClicked(new MouseEvent(jeu.obtenirBoutonGauche(), 0, 0, 0, 0, 0, 0, false));

		// Rendre visible le jeux.
		//
		jeu.setVisible(true);
	}

	/**
	 * Programme principal
	 * 
	 * @param arguments
	 * @since 1.0.0
	 */
	public static void main(String[] arguments) {

		// Charger les fichier de configuration
		//
		Element configGrille = (Element) ConfigXML.load("../Intrus/Configuration/ConfigGrilleG", "1.0.0");
		Element configPanneauInfo = (Element) ConfigXML.load("../Intrus/Configuration/ConfigPanneauInformations", "1.0.0");

		// Instancier un jeu
		//
		Intrus jeu = new Intrus(configGrille, configPanneauInfo);

		// Appliquer un style au jeu
		//
		if (arguments == null)
			jeu.styleArcEnCiel();
		else if (arguments.length <= 0)
			jeu.styleArcEnCiel();
		else if (arguments[0].equals("styleMasculin"))
			jeu.styleMasculin();
		else if (arguments[0].equals("styleFeminin"))
			jeu.styleFeminin();
		else
			jeu.styleArcEnCiel();
	}

	/**
	 * Applique un style feminin au design du jeu
	 * 
	 * @since 1.0.0
	 */
	public void styleFeminin() {
		jeu.styleFeminin();
	}

	/**
	 * Applique un style masculin au design du jeu
	 * 
	 * @since 1.0.0
	 */
	public void styleMasculin() {
		jeu.styleMasculin();
	}

	/**
	 * Applique un style arc en ciel au design du jeu
	 * 
	 * @since 1.0.0
	 */
	public void styleArcEnCiel() {
		jeu.styleArcEnCiel();
	}

	/**
	 * Obtention du jeu de grille support.
	 * 
	 * @return jeu de grille support.
	 * @since 1.0.0
	 */
	public JeuxGrille obtenirJeuxGrille() {
		return jeu;
	}

	/**
	 * Obtention de la solution du jeu de l'intrus
	 * 
	 * @return solution du jeu.
	 * @since 1.0.0
	 */
	public static String obtenirIntrus() {
		return intrus;
	}

	/**
	 * Modification de la solution du jeu de l'intrus
	 * 
	 * @param intrus
	 * @since 1.0.0
	 */
	public static void modificationIntrus(String newIntrus) {
		intrus = newIntrus;
	}

	/**
	 * Obtention du score de la partie.
	 * 
	 * @return score de la partie.
	 * @since 1.0.0
	 */
	public static int obtenirScore() {
		return score;
	}

	/**
	 * Augmente le score de partie jouee.
	 * 
	 * @since 1.0.0
	 */
	public static void augmenterScore() {
		score++;
	}

	/**
	 * Augmente le nombre de partie jouee.
	 * 
	 * @since 1.0.0
	 */
	public static void nouvellePartie() {
		partieJouee++;
	}

	/**
	 * Obtention du nombre de partie jouee.
	 * 
	 * @return nombre de partie jouee.
	 * @since 1.0.0
	 */
	public static int obtenirNombrePartieJouee() {
		return partieJouee;
	}

	/**
	 * Traitement d'une action du joueur.
	 * 
	 * @param ligne
	 *            selectionner par le joueur.
	 * @param colonne
	 *            selectionner par le joueur.
	 * @since 1.0.0
	 */
	public static void actionJoueur(int ligne, int colonne) {

		// Obtenir la grille courante.
		//
		GrilleG gr = jeu.obtenirGrille();

		// Obtenir la position de la cellule.
		//
		Dimension dim = gr.obtenirPositionCelluleCible(ligne, colonne);
		if (dim.getHeight() > gr.obtenirNbColonnes())
			dim.setSize(dim.getWidth(), gr.obtenirNbColonnes());
		if (dim.getWidth() > gr.obtenirNbLignes())
			dim.setSize(gr.obtenirNbLignes(), dim.getHeight());

		// Obtenir la designation de la cellule cible.
		//
		if (gr.obtenirCellule(dim.width, dim.height) == null)
			return;

		// Verifier la presence d'image dans la cellule
		// cible.
		//
		if (gr.obtenirCellule(dim.width, dim.height).presenceImage()) {

			// Recuperer l'image contenu dans la cellule.
			//
			File file = new File(gr.obtenirCellule(dim.width, dim.height).obtenirCheminImage());
			String aliment = file.getName().substring(0, file.getName().length() - 4);

			// Verifier la correspondance entre l'intrus et
			// l'image selectionnee.
			//
			if (aliment.equals(obtenirIntrus())) {

				// Augmenter le score.
				//
				augmenterScore();

				// Afficher un message de felicitation.
				//
				jeu.obtenirPanneauInformations().bulleVerte();
				jeu.obtenirPanneauInformations().ajouterTexteAide(
						Texte.load("../_Textes/Jeux/Intrus/Bravo") + Intrus.obtenirIntrus() + "<BR>" + Texte.load("../_Textes/Jeux/Intrus/Score")
								+ (int) ((double) Intrus.obtenirScore() / (double) Intrus.obtenirNombrePartieJouee() * (double) 20) + " / 20");
			} else {

				// Afficher un message de correction.
				//
				jeu.obtenirPanneauInformations().bulleRouge();
				jeu.obtenirPanneauInformations().ajouterTexteAide(
						Texte.load("../_Textes/Jeux/Intrus/Dommage") + Intrus.obtenirIntrus() + "<BR>" + Texte.load("../_Textes/Jeux/Intrus/Score")
								+ (int) ((double) Intrus.obtenirScore() / (double) Intrus.obtenirNombrePartieJouee() * (double) 20) + " / 20");
			}

			// Afficher l'information visuel de la categorie
			// de l'aliment dans le panneau d'information.
			//
			jeu.obtenirPanneauInformations().ajouterImageSud("../_Images/Pyramide/3D/" + file.getParentFile().getName() + ".jpg");

			// Specifier la fin de partie.
			//
			modificationIntrus(null);
		}
	}
}