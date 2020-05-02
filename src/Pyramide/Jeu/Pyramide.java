/**
 * IUT de Nice / Departement informatique / Stage Archet
 * Annee 2009_2010 - Applications graphiques
 * 
 * @edition A : edition initiale.
 * 
 * @version 1.0.0
 * 
 *          version initiale.
 * 
 * @version 1.1.0
 * 
 *          mise en place du traitement de l'action d'un
 *          joueur autrefois fait par l'ecouteur de jeu.
 */
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Insets;
import java.awt.Toolkit;
import java.io.File;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import org.jdom.Element;

/**
 * Regroupement d'objet necessaire dans une classe pour
 * construire le jeu de la pyramide.
 * 
 * @version 1.1.0
 * @author Cedric Hulin
 */
public class Pyramide {

	/**
	 * Fenetre support contenant le panneau de la pyramide.
	 * 
	 * @since 1.0.0
	 */
	private JFrame fenetreSupport;

	/**
	 * Grille de disposition des aliments.
	 * 
	 * @since 1.0.0
	 */
	private GrilleG grille;

	/**
	 * Panneau contenant les images clavier.
	 * 
	 * @since 1.0.0
	 */
	private ClavierG clavier = null;

	/**
	 * Chemin de l'image selectionnee.
	 * 
	 * @since 1.0.0
	 */
	private String cheminImage = null;

	/**
	 * Score d'une partie jouee.
	 * 
	 * @since 1.0.0
	 */
	private int score = 0;

	/**
	 * Nombre d'element pour calculez la moyenne du scores.
	 * 
	 * @since 1.0.0
	 */
	private int nombreElementDepart = 0;

	/**
	 * Constructeur normal.
	 * 
	 * @param configurationG
	 *            de la grilles de jeu.
	 * @param configurationC
	 *            du clavier.
	 * @since 1.0.0
	 */
	public Pyramide(Element configurationG, Element configurationC) {

		// Verifier les parametres
		//
		if (configurationG == null || configurationC == null) {
			JOptionPane.showMessageDialog(null, Texte.load("../_Textes/Erreurs/Mauvais parametre"));
			System.exit(0);
		}

		// Obtention de variable de calcule de
		// dimensionnement des
		// fenetres
		// selon l'environement de lancement
		//
		Dimension tailleEcran = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
		int largeurEcran = (int) tailleEcran.getWidth();
		int hauteurEcran = (int) tailleEcran.getHeight();
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		GraphicsDevice gd = ge.getDefaultScreenDevice();
		GraphicsConfiguration gc = gd.getDefaultConfiguration();
		Insets I = Toolkit.getDefaultToolkit().getScreenInsets(gc);

		// Creation de la fenetre support
		//
		fenetreSupport = new JFrame();
		fenetreSupport.setResizable(false);
		fenetreSupport.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// Modifier le titre de la fenetre
		//
		fenetreSupport.setTitle("Pyramide");

		// Disposition de la fenetre sur l'ecran
		//
		int hauteurClavier = hauteurEcran * 160 / 800;
		if ((double) largeurEcran / (double) hauteurEcran == 4.0 / 3.0) {
			fenetreSupport.setSize(largeurEcran, hauteurEcran - I.bottom - hauteurClavier);
			fenetreSupport.setLocation(0, 0);
		} else {
			fenetreSupport.setSize(largeurEcran * 1024 / 1280, hauteurEcran - I.bottom - hauteurClavier);
			fenetreSupport.setLocation((largeurEcran - fenetreSupport.getWidth()) / 2, 0);
		}

		// Creation de la grille de disposition
		//
		grille = new GrilleG(fenetreSupport, configurationG);
		if (configurationG == null || configurationC == null) {
			JOptionPane.showMessageDialog(null, Texte.load("../_Textes/Erreurs/Chargement grille configuration"));
			System.exit(0);
		}

		// Ajout de l'image de fond
		//
		grille.ajouterImage("../_Images/Pyramide/Fond/Pyramide.jpg");

		// Ajout d'un ecouteur de jeu
		//
		grille.ajouterEcouteur(new EcouteurPyramide(this));

		// Rendre la grille transparente a l'oeil
		//
		grille.modifierCouleurCellules(new Color(0, 0, 0, 0));
		grille.setBackground(new Color(0, 0, 0, 0));
		grille.modifierBordure(null);

		// Rendre visible la pyramide
		//
		fenetreSupport.getContentPane().add(grille);
		fenetreSupport.setVisible(true);

		// Creation du clavier d'images
		//
		clavier = new ClavierG(new JFrame(), configurationC);

		// Ajout d'un ecran
		//
		clavier.ajouterEcran();

		// Redimensionnement du clavier
		//
		if ((double) largeurEcran / (double) hauteurEcran == 4.0 / 3.0) {
			clavier.obtenirJFrame().setSize(largeurEcran, hauteurClavier);
			clavier.obtenirJFrame().setLocation(0, fenetreSupport.getHeight());
		} else {
			clavier.obtenirJFrame().setSize(fenetreSupport.getWidth(), hauteurClavier);
			clavier.obtenirJFrame().setLocation(fenetreSupport.getX(), fenetreSupport.getHeight());
		}
		clavier.obtenirJFrame().setResizable(false);

		// Specification des repertoires d'images
		//
		Vector cheminsImages = new Vector();
		cheminsImages.add("../_Images/Legumes/");
		cheminsImages.add("../_Images/Fruits/");
		cheminsImages.add("../_Images/Feculents/");
		cheminsImages.add("../_Images/Matieres Grasses/");
		cheminsImages.add("../_Images/Produits Laitiers/");
		cheminsImages.add("../_Images/Produits Sucres/");
		cheminsImages.add("../_Images/Viande Poisson Oeuf/");

		// Creation du vecteur d'images
		//
		Vector donnees = Dossier.obtenirVecteur(cheminsImages, 5, ".png");
		if (donnees == null)
			System.exit(0);

		// Mise en style images
		//
		clavier.styleClavierImages(donnees);

		// Ajout d'un ecouteur de jeu clavier
		//
		clavier.ajouterEcouteur(new EcouteurPyramideClavier(this));

		// Definir le nombre d'element du clavier
		//
		nombreElementDepart = clavier.obtenirNombreElement();

		// Rendre visible le clavier
		//
		clavier.setVisible(true);
	}

	/**
	 * Programme principale
	 * 
	 * @param arguments
	 * @since 1.0.0
	 */
	public static void main(String[] arguments) {

		// Charger les fichier de configuration
		//
		Element configGrilleG = (Element) ConfigXML.load("Configuration/ConfigGrilleG", "4.0.0");
		Element configClavierG = (Element) ConfigXML.load("Configuration/ConfigClavier", "2.0.0");

		// Instancier un jeu
		//
		Pyramide jeu = new Pyramide(configGrilleG, configClavierG);

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
	 * Obtention de la grille de disposition d'aliments.
	 * 
	 * @return grille de jeu.
	 * @since 1.0.0
	 */
	public GrilleG obtenirGrille() {
		return grille;
	}

	/**
	 * Obtention du chemin de l'image d'aliment
	 * selectionnee.
	 * 
	 * @return chemin de l'image selectionnee.
	 * @since 1.0.0
	 */
	public String obtenirCheminImage() {
		return cheminImage;
	}

	/**
	 * Modification du chemin de l'image selectionnee.
	 * 
	 * @param chemin
	 *            de l'image voulue.
	 * @since 1.0.0
	 */
	public void modifierCheminImage(String chemin) {
		cheminImage = chemin;
	}

	/**
	 * Obtention du clavier.
	 * 
	 * @return clavier du jeu courant.
	 * @since 1.0.0
	 */
	public ClavierG obtenirClavier() {
		return clavier;
	}

	/**
	 * Applique un style feminin au design du jeu.
	 * 
	 * @since 1.0.0
	 */
	public void styleFeminin() {
		clavier.ajouterImage("../_Images/Banniere/BanniereClavierJeuDuPlacardGirl.png");
	}

	/**
	 * Applique un style masculin au design du jeu.
	 * 
	 * @since 1.0.0
	 */
	public void styleMasculin() {
		clavier.ajouterImage("../_Images/Banniere/BanniereClavierJeuDuPlacardBoy.png");
	}

	/**
	 * Applique un style arc en ciel au design du jeu.
	 * 
	 * @since 1.0.0
	 */
	public void styleArcEnCiel() {
		clavier.ajouterImage("../_Images/Banniere/BanniereClavierJeuDuPlacardArcEnCiel.png");
	}

	/**
	 * Obtention du score de la partie en cours.
	 * 
	 * @return score de la partie.
	 * @since 1.0.0
	 */
	public int obtenirScore() {
		return score;
	}

	/**
	 * Augmente le score lors d'une bonne action du joueur.
	 * 
	 * @since 1.0.0
	 */
	private void augmenterScore() {
		score += 2;
	}

	/**
	 * Diminue le score lors d'une mauvaise action du
	 * joueur.
	 * 
	 * @since 1.0.0
	 */
	private void diminuerScore() {
		score -= 2;
		if (score < 0)
			score = 0;
	}

	/**
	 * Obtention du nombre d'element au depart de la partie.
	 * 
	 * @return nombre d'element au depart de la partie.
	 * @since 1.0.0
	 */
	public int obtenirNombreElementDepart() {
		return nombreElementDepart;
	}

	/**
	 * Analyse le placement d'un aliment dans le bon
	 * endroit.
	 * 
	 * @param categorie
	 *            selectionnee.
	 * @param fichier
	 *            selectionne.
	 * @return flag de bon placement.
	 * @since 1.0.0
	 */
	private boolean verificationJeu(String categorie, File fichier) {
		boolean victoire = false;

		// Verifier si l'aliment peu etre mis dans le
		// la categorie selectionner
		//
		if (fichier.getParentFile().getName().equals(categorie))
			victoire = true;

		// Modifier le score en fonction de l'action
		//
		if (victoire)
			augmenterScore();
		else
			diminuerScore();

		// Restituer le resultat
		//
		return victoire;
	}

	/**
	 * Specifie la categorie designer par la position donnee
	 * en parametre.
	 * 
	 * @param ligne
	 *            de la grille.
	 * @param colonne
	 *            de la grille.
	 * @return nom de la categorie.
	 * @since 1.0.0
	 */
	public String categorie(int ligne, int colonne) {
		String categorie = "";

		switch (ligne) {
		case 1:
			categorie = "Produits Sucres";
			obtenirClavier().modifierCouleurEcran(new Color(224, 59, 180, 255));
			break;
		case 2:
			categorie = "Matieres Grasses";
			obtenirClavier().modifierCouleurEcran(new Color(255, 204, 3, 255));
			break;
		case 3:
			if (colonne <= 5) {
				categorie = "Produits Laitiers";
				obtenirClavier().modifierCouleurEcran(new Color(84, 137, 202, 255));
			} else {
				categorie = "Viande Poisson Oeuf";
				obtenirClavier().modifierCouleurEcran(new Color(255, 3, 2, 255));
			}
			break;
		case 4:
			if (colonne <= 5) {
				categorie = "Legumes";
				obtenirClavier().modifierCouleurEcran(new Color(11, 250, 11, 255));
			} else {
				categorie = "Fruits";
				obtenirClavier().modifierCouleurEcran(new Color(2, 131, 2, 255));
			}
			break;
		case 5:
			categorie = "Feculents";
			obtenirClavier().modifierCouleurEcran(new Color(255, 160, 76, 255));
			break;
		}
		return categorie;
	}

	/**
	 * Traitement d'une action du joueur.
	 * 
	 * @param ligne
	 *            selectionner par le joueur.
	 * @param colonne
	 *            selectionner par le joueur.
	 * @since 1.1.0
	 */
	public void actionJoueur(int ligne, int colonne) {

		// Obtenir la designation de la cellule cible.
		//
		GrilleG gr = obtenirGrille();

		// Modifier la couleur de l'ecran du clavier.
		//
		obtenirClavier().modifierCouleurEcran(Color.white);

		// Verifier qu'un image est selectionner.
		//
		if (obtenirCheminImage() != null) {

			// Recuperer les informations de l'image
			// selectionner.
			//
			File fichier = new File(obtenirCheminImage());
			String nomAliment = (String) fichier.getName().subSequence(0, fichier.getName().length() - 4);

			// Obtenir la position de la cellule.
			//
			Dimension dim = gr.obtenirPositionCelluleCible(ligne, colonne);
			if (dim.getHeight() > gr.obtenirNbColonnes())
				dim.setSize(dim.getWidth(), gr.obtenirNbColonnes());
			if (dim.getWidth() > gr.obtenirNbLignes())
				dim.setSize(gr.obtenirNbLignes(), dim.getHeight());

			// Recuperer la categorie de l'aliment
			// selectionne.
			//
			String categorie = categorie(dim.width, dim.height);

			// Verifier que le joueur ai effectuer une bonne
			// action.
			//
			if (verificationJeu(categorie, fichier)) {
				Dimension p = null;
				int i;

				// Initialiser l'index de placement selon la
				// categorie.
				//
				if (categorie.equals("Fruits") || categorie.equals("Viande Poisson Oeuf"))
					i = 6;
				else
					i = 1;

				// Determiner la position de l'aliment dans
				// la pyramide selon sa categorie.
				//
				for (; i <= obtenirGrille().obtenirNbColonnes(); i += 2) {

					// Verifier la presence d'un aliment.
					//
					if (!obtenirGrille().obtenirCellule(dim.width, i).presenceImage()) {
						p = new Dimension(dim.width, i);
						break;
					}

					// Traiter le cas d'une categorie
					// specifique.
					//
					if (categorie.equals("Fruits") || categorie.equals("Viande Poisson Oeuf") || categorie.equals("Legumes")
							|| categorie.equals("Produits Laitiers"))
						i--;
				}

				// Verifier qu'une position a ete trouver.
				//
				if (p == null)
					return;

				// Ajout de l'image dans la categorie
				// correspondante
				//
				gr.ajouterImage(p.width, p.height, obtenirCheminImage());

				// Retirer la meme image du clavier
				//
				obtenirClavier().retirerElement(obtenirCheminImage());

				// Specifier que l'image a ete placer.
				//
				modifierCheminImage(null);

				// Afficher un message de felicitation.
				//
				obtenirClavier().modifierTexteEcran(nomAliment + Texte.load("../_Textes/Jeux/Pyramide/Bon") + categorie);
				obtenirClavier().modifierCouleurEcran(Color.green);

				// Repeindre l'interface.
				//
				obtenirClavier().repaint();
				obtenirGrille().repaint();

				// Verifier que c'est la fin du jeu
				//
				if (obtenirClavier().obtenirNombreElement() == 0) {

					// Calcul du score sur vingt
					//
					int scoreSurVingt = obtenirScore() / 2;
					scoreSurVingt *= 20;
					scoreSurVingt /= obtenirNombreElementDepart();

					// Afficher un message de felicitation
					//
					JOptionPane.showMessageDialog(null, Texte.load("../_Textes/Jeux/Pyramide/Gagner") + scoreSurVingt + "/20");
					obtenirClavier().modifierTexteEcran(Texte.load("../_Textes/Jeux/Pyramide/Gagner") + scoreSurVingt + "/20");
				}
			} else {
				// Afficher un message de correction
				//
				obtenirClavier().modifierTexteEcran(nomAliment + Texte.load("../_Textes/Jeux/Pyramide/Mauvais") + categorie);
				obtenirClavier().modifierCouleurEcran(Color.red);
			}
		} else
			// Afficher un message de selection
			//
			obtenirClavier().modifierTexteEcran("Choisissez un aliment");
	}
}