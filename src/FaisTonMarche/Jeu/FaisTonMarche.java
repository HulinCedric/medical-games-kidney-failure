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
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Insets;
import java.awt.Toolkit;
import java.io.File;
import java.util.HashMap;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import org.jdom.Element;

/**
 * Regroupement d'objet necessaire dans une classe pour
 * construire le jeu fais ton marche
 * 
 * @version 1.0.0
 * @author Cedric Hulin
 */
public class FaisTonMarche {

	/**
	 * Dictionnaire des grilles.
	 * 
	 * @since 1.0.0
	 */
	private HashMap grilles;

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
	 *            des grilles.
	 * @param configurationC
	 *            du clavier.
	 * @since 1.0.0
	 */
	public FaisTonMarche(Element configurationG, Element configurationC) {

		// Obtention de variable de calcule de
		// dimensionnement des fenetre selon l'environement
		// de lancement
		//
		Dimension tailleEcran = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
		int largeurEcran = (int) tailleEcran.getWidth();
		int hauteurEcran = (int) tailleEcran.getHeight();
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		GraphicsDevice gd = ge.getDefaultScreenDevice();
		GraphicsConfiguration gc = gd.getDefaultConfiguration();
		Insets I = Toolkit.getDefaultToolkit().getScreenInsets(gc);

		// Creation du clavier
		//
		clavier = new ClavierG(new JFrame(), configurationC);

		// Ajout d'un ecran
		//
		clavier.ajouterEcran();

		// Regler la disposition du clavier
		//
		int locationX = largeurEcran * 128 / 1280;
		int hauteurClavier = hauteurEcran * 150 / 800;
		if ((double) largeurEcran / (double) hauteurEcran == 4.0 / 3.0) {
			clavier.obtenirJFrame().setSize(largeurEcran, hauteurClavier);
			clavier.obtenirJFrame().setLocation(largeurEcran / 1024, hauteurEcran - hauteurClavier - I.bottom);
		} else {
			clavier.obtenirJFrame().setSize(largeurEcran - locationX * 2, hauteurClavier);
			clavier.obtenirJFrame().setLocation(locationX, hauteurEcran - hauteurClavier - I.bottom);
		}
		clavier.obtenirJFrame().setResizable(false);

		// Remplissage du clavier avec des images aleatoires
		//
		Vector cheminsRepertoire = new Vector();
		cheminsRepertoire.add("../_Images/FaisTonMarche/Printemps/");
		cheminsRepertoire.add("../_Images/FaisTonMarche/Ete/");
		cheminsRepertoire.add("../_Images/FaisTonMarche/Automne/");
		cheminsRepertoire.add("../_Images/FaisTonMarche/Hiver/");

		// Creation du vecteur d'images
		//
		int value = Integer.valueOf(configurationG.getChild("Lignes").getTextTrim()).intValue();
		value *= Integer.valueOf(configurationG.getChild("Colonnes").getTextTrim()).intValue();
		Vector donnees = Dossier.obtenirVecteur(cheminsRepertoire, value, ".png");
		if (donnees == null)
			System.exit(0);

		// Mise en style images
		//
		clavier.styleClavierImages(donnees);
		clavier.modifierTailleEcran(new Dimension((int) clavier.obtenirTailleEcran().getWidth(), hauteurEcran * 40 / 800));
		clavier.modifierTailleElements(new Dimension(largeurEcran * 64 / 1280, hauteurEcran * 64 / 800));

		// Ajout d'un ecouteur de jeu clavier
		//
		clavier.ajouterEcouteur(new EcouteurFaisTonMarcheClavier(this));

		// Creation de l'environnement de chez Cedric
		//
		grilles = new HashMap();
		grilles.put("Printemps", creationEnvironnement("Printemps", configurationG));

		// Creation de l'environnement de chez Cedric
		//
		grilles.put("Ete", creationEnvironnement("Ete", configurationG));

		// Creation de l'environnement de chez Cedric
		//
		grilles.put("Automne", creationEnvironnement("Automne", configurationG));

		// Creation de l'environnement de chez Charles
		//
		grilles.put("Hiver", creationEnvironnement("Hiver", configurationG));

		// Definir le nombre d'element du clavier
		//
		nombreElementDepart = clavier.obtenirNombreElement();

		// Rendre visible le clavier
		//
		clavier.setVisible(true);
		clavier.ajouterImage("../_Images/Banniere/BanniereClavierFaisTonMarche.png");
	}

	/**
	 * Main
	 * 
	 * @param args
	 * @since 1.0.0
	 */
	public static void main(String[] args) {

		// Charger les fichier de configuration
		//
		Element configGrilleG = (Element) ConfigXML.load("Configuration/ConfigGrilleG", "4.0.0");
		Element configClavierG = (Element) ConfigXML.load("Configuration/ConfigClavier", "2.0.0");

		// Instancier un jeu
		//
		FaisTonMarche jeu = new FaisTonMarche(configGrilleG, configClavierG);

		// Appliquer un style au jeu
		//
		if (args == null)
			jeu.styleArcEnCiel();
		else if (args.length <= 0)
			jeu.styleArcEnCiel();
		else if (args[0].equals("styleMasculin"))
			jeu.styleMasculin();
		else if (args[0].equals("styleFeminin"))
			jeu.styleFeminin();
		else
			jeu.styleArcEnCiel();
	}

	/**
	 * Obtention de la grille de rangement d'aliments selon
	 * la fenetre
	 * 
	 * @return grille du placard
	 * @since 1.0.0
	 */
	public GrilleG obtenirGrille(String nomGrille) {
		return (GrilleG) grilles.get(nomGrille);
	}

	/**
	 * Obtention du chemin de l'image d'aliment selectionnee
	 * 
	 * @return chemin image
	 * @since 1.0.0
	 */
	public String obtenirCheminImage() {
		return cheminImage;
	}

	/**
	 * Modification du chemin de l'image selectionnee
	 * 
	 * @param chemin
	 * @since 1.0.0
	 */
	public void modifierCheminImage(String chemin) {
		cheminImage = chemin;
	}

	/**
	 * Obtention du clavier
	 * 
	 * @return clavier
	 * @since 1.0.0
	 */
	public ClavierG obtenirClavier() {
		return clavier;
	}

	/**
	 * Applique un style feminin au design du jeu
	 * 
	 * @since 1.0.0
	 */
	public void styleFeminin() {
		clavier.ajouterImage("../_Images/Banniere/BanniereClavierJeuDuPlacardGirl.png");
	}

	/**
	 * Applique un style masculin au design du jeu
	 * 
	 * @since 1.0.0
	 */
	public void styleMasculin() {
		clavier.ajouterImage("../_Images/Banniere/BanniereClavierJeuDuPlacardBoy.png");
	}

	/**
	 * Applique un style arc en ciel au design du jeu
	 * 
	 * @since 1.0.0
	 */
	public void styleArcEnCiel() {
		clavier.ajouterImage("../_Images/Banniere/BanniereClavierJeuDuPlacardArcEnCiel.png");
	}

	/**
	 * Obtention du score de la partie en cours
	 * 
	 * @return score
	 * @since 1.0.0
	 */
	public int obtenirScore() {
		return score;
	}

	/**
	 * Augmente le score lors d'une bonne action du joueur
	 * 
	 * @since 1.0.0
	 */
	private void augmenterScore() {
		score += 2;
	}

	/**
	 * Diminue le score lors d'une mauvaise action du joueur
	 * 
	 * @since 1.0.0
	 */
	private void diminuerScore() {
		score -= 2;
		if (score < 0)
			score = 0;
	}

	/**
	 * Obtention du nombre d'element au depart de la partie
	 * 
	 * @return nombre d'element au depart de la partie
	 * @since 1.0.0
	 */
	public int obtenirNombreElementDepart() {
		return nombreElementDepart;
	}

	/**
	 * Analyse le placement d'un aliment dans le bon endroit
	 * 
	 * @param frame
	 * @param fichier
	 * @return flag de bon placement
	 * @since 1.0.0
	 */
	public boolean verificationJeu(String frame, File fichier) {
		boolean victoire = false;

		// Controler si la fenetre a visualiser est le frigo
		//
		if (frame.equals(fichier.getParentFile().getName()))
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

	private GrilleG creationEnvironnement(String nomFenetre, Element configGrilleG) {

		// Obtention de variable de calcul de
		// dimensionnerent des fenetre selon l'environement
		// de lancement
		//
		Dimension tailleEcran = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
		int largeur = (int) tailleEcran.getWidth();
		int hauteur = (int) tailleEcran.getHeight();
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		GraphicsDevice gd = ge.getDefaultScreenDevice();
		GraphicsConfiguration gc = gd.getDefaultConfiguration();
		Insets I = Toolkit.getDefaultToolkit().getScreenInsets(gc);

		// Creation de la fenetre.
		//
		JFrame fenetre = new JFrame();
		fenetre.setTitle(nomFenetre);
		fenetre.setResizable(false);
		fenetre.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// Disposition de la fenetre sur l'ecran.
		//
		if ((double) largeur / (double) hauteur == 4.0 / 3.0) {
			fenetre.setSize(largeur * 256 / 1024, hauteur - (hauteur * 150 / 800) - I.bottom);
			fenetre.setLocation(fenetre.getWidth() * grilles.size(), 0);
		} else {
			fenetre.setSize(largeur * 256 / 1280, hauteur - (hauteur * 150 / 800) - I.bottom);
			fenetre.setLocation(fenetre.getWidth() * grilles.size() + (largeur * 128 / 1280), 0);
		}

		// Creation du fond de la fenetre.
		//
		PanneauG fond = new PanneauG(fenetre, configGrilleG);
		fond.ajouterImage("../_Images/FaisTonMarche/Baniere/" + nomFenetre + ".jpg");
		fond.setLayout(null);

		// Creation 
		//
		PanneauG personnage = new PanneauG(fond, configGrilleG);

		// Ajout de l'image de fond placard
		//
		personnage.ajouterImage("../_Images/FaisTonMarche/Fond/" + nomFenetre + ".png");
		personnage.setBackground(new Color(0, 0, 0, 0));
		personnage.setBounds(0, 0, fenetre.getWidth(), fenetre.getWidth());

		// Ajout d'un ecouteur de jeu
		//
		personnage.ajouterEcouteur(new EcouteurFaisTonMarche(fenetre, this));

		// Creation de la grille du placard
		//
		GrilleG grilleFond = new GrilleG(fond, configGrilleG);

		// Rendre la grille transparente a l'oeil
		//
		grilleFond.modifierCouleurCellules(new Color(0, 0, 0, 0));
		grilleFond.setBackground(new Color(0, 0, 0, 0));
		grilleFond.ajouterImageCellules("../_Images/FaisTonMarche/Fond/Cagette.png");
		grilleFond.modifierBordure(null);
		grilleFond.setBounds(0, fenetre.getWidth(), fenetre.getWidth(), fenetre.getHeight() - fenetre.getWidth() - I.bottom);

		// Creation de la grille du placard
		//
		GrilleG grillePlacement = new GrilleG(fond, configGrilleG);

		// Rendre la grille transparente a l'oeil
		//
		grillePlacement.modifierCouleurCellules(new Color(0, 0, 0, 0));
		grillePlacement.setBackground(new Color(0, 0, 0, 0));
		grillePlacement.modifierBordure(null);
		grillePlacement.setPreferredSize(new Dimension(fenetre.getWidth(), fenetre.getHeight() - fenetre.getWidth()));
		grillePlacement.setBounds(0, fenetre.getWidth(), fenetre.getWidth(), fenetre.getHeight() - fenetre.getWidth() - I.bottom);

		// Ajout d'un ecouteur de jeu
		//
		grillePlacement.ajouterEcouteur(new EcouteurFaisTonMarche(fenetre, this));

		// Rendre visible le placard
		//
		fond.add(personnage, BorderLayout.NORTH);
		fond.add(grillePlacement);
		fond.add(grilleFond);
		fenetre.getContentPane().add(fond);
		fenetre.setVisible(true);

		return grillePlacement;
	}

	/**
	 * Traitement d'une action du joueur.
	 * 
	 * @param fenetre
	 *            demandeuse.
	 * @param ligne
	 *            selectionner par le joueur.
	 * @param colonne
	 *            selectionner par le joueur.
	 * @since 1.1.0
	 */
	public void actionJoueur(JFrame fenetre, int ligne, int colonne) {

		// Obtenir la designation de la cellule cible
		//
		GrilleG gr = obtenirGrille(fenetre.getTitle());
		if (gr == null)
			return;

		// Verifier qu'un aliment a bien ete selectionne
		//
		if (obtenirCheminImage() != null) {

			// Recuperer les informations de l'image
			// selectionner
			//
			File fichier = new File(obtenirCheminImage());
			String nomAliment = (String) fichier.getName().subSequence(0, fichier.getName().length() - 4);
			Dimension p = gr.obtenirPositionPCL();

			// Verifier que le joueur a bien jouez ou non
			//
			if (verificationJeu(fenetre.getTitle(), fichier)) {

				// Ajout de l'image dans la grille
				// correspondante
				//
				gr.ajouterImage(p.width, p.height, obtenirCheminImage());

				// Retirer la meme image du clavier
				//
				obtenirClavier().retirerElement(obtenirCheminImage());

				// Specifier que l'image a ete placer
				//
				modifierCheminImage(null);

				// Afficher un message de felicitation
				//
				obtenirClavier().modifierTexteEcran(nomAliment + Texte.load("../_Textes/Jeux/JeuDuPlacard/Bon") + fenetre.getTitle());
				obtenirClavier().modifierCouleurEcran(Color.green);

				// Repeindre
				//
				obtenirClavier().repaint();
				fenetre.repaint();

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
					JOptionPane.showMessageDialog(null, Texte.load("../_Textes/Jeux/FaisTonMarche/Gagner") + scoreSurVingt + "/20");
					obtenirClavier().modifierTexteEcran(Texte.load("../_Textes/Jeux/FaisTonMarche/Gagner") + scoreSurVingt + "/20");
				}
			} else {
				// Afficher un message de correction
				//
				obtenirClavier().modifierTexteEcran(nomAliment + Texte.load("../_Textes/Jeux/FaisTonMarche/Mauvais") + fenetre.getTitle());
				obtenirClavier().modifierCouleurEcran(Color.red);
			}
		} else
			// Afficher un message de selection
			//
			obtenirClavier().modifierTexteEcran("Choisissez un aliment");
	}
}