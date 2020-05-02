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
 * construire le jeu du placard.
 * 
 * @author Cedric Hulin
 * @version 1.1.0
 */
public class JeuDuPlacard {

	/**
	 * Fenetre support contenant le panneau du placard
	 * 
	 * @since 1.0.0
	 */
	private JFrame placard;

	/**
	 * Panneau contenant le placard
	 * 
	 * @since 1.0.0
	 */
	private PanneauG fondPlacard;

	/**
	 * Grille de rangement aliments pour le placard
	 * 
	 * @since 1.0.0
	 */
	private GrilleG grillePlacard;

	/**
	 * Fenetre support contenant le panneau du frigo
	 * 
	 * @since 1.0.0
	 */
	private JFrame frigo;

	/**
	 * Panneau contenant le frigo
	 * 
	 * @since 1.0.0
	 */
	private PanneauG fondFrigo;

	/**
	 * Grille de rangement aliments pour le frigo
	 * 
	 * @since 1.0.0
	 */
	private GrilleG grilleFrigo;

	/**
	 * Panneau contenant les images clavier
	 * 
	 * @since 1.0.0
	 */
	private ClavierG clavier = null;

	/**
	 * Chemin de l'image selectionnee
	 * 
	 * @since 1.0.0
	 */
	private String cheminImage = null;

	/**
	 * Score d'une partie jouee
	 * 
	 * @since 1.0.0
	 */
	private int score = 0;

	/**
	 * Nombre d'element pour calculez la moyenne du scores
	 * 
	 * @since 1.0.0
	 */
	private int nombreElementDepart = 0;

	/**
	 * Constructeur normal
	 * 
	 * @param configGrilleG
	 * @param configClavierG
	 * @since 1.0.0
	 */
	public JeuDuPlacard(Element configGrilleG, Element configClavierG) {

		// Obtention de variable de calcule de
		// dimensionnement des fenetre selon l'environement
		// de lancement
		//
		Dimension tailleEcran = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
		double largeur = tailleEcran.getWidth();
		double hauteur = tailleEcran.getHeight();
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		GraphicsDevice gd = ge.getDefaultScreenDevice();
		GraphicsConfiguration gc = gd.getDefaultConfiguration();
		Insets I = Toolkit.getDefaultToolkit().getScreenInsets(gc);

		// Creation de la fenetre du frigo
		//
		frigo = new JFrame();
		frigo.setTitle("Frigo");
		frigo.setResizable(false);
		frigo.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// Disposition de la fenetre sur l'ecran
		//
		frigo.setSize(512, (int) hauteur - I.bottom - 160);
		frigo.setLocation((int) (largeur / 2) - frigo.getWidth(), 0);

		// Creation du panneau au fond de frigo
		//
		fondFrigo = new PanneauG(frigo, null);
		fondFrigo.setLayout(null);

		// Ajout de l'image de fond frigo
		//
		fondFrigo.ajouterImage("../_Images/JeuDuPlacard/Fond/Frigo.jpg");

		// Creation de la grille du frigo
		//
		grilleFrigo = new GrilleG(fondFrigo, configGrilleG);

		// Rendre la grille transparente a l'oeil
		//
		grilleFrigo.modifierCouleurCellules(new Color(0, 0, 0, 0));
		grilleFrigo.setBackground(new Color(0, 0, 0, 0));
		grilleFrigo.modifierBordure(null);

		// Disposition de la grille dans le panneau
		//
		int largeurFen = frigo.getSize().width;
		int hauteurFen = frigo.getSize().height;
		grilleFrigo.setLocation((int) ((largeurFen * 110) / 512), (int) ((hauteurFen * 34) / 571));
		grilleFrigo.setSize((int) ((largeurFen * 296) / 512), (int) ((hauteurFen * 390) / 571));

		// Rendre visible le frigo
		//
		fondFrigo.add(grilleFrigo);
		frigo.getContentPane().add(fondFrigo);
		frigo.setVisible(true);

		// Creation du placard
		//
		placard = new JFrame();
		placard.setTitle("Placard");
		placard.setResizable(false);
		placard.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// Disposition de la fenetre sur l'ecran
		//
		placard.setLocation((int) (largeur / 2), 0);
		placard.setSize(512, (int) hauteur - I.bottom - 160);

		// Creation du panneau au fond de placard
		//
		fondPlacard = new PanneauG(placard, null);
		fondPlacard.setLayout(null);

		// Ajout de l'image de fond placard
		//
		fondPlacard.ajouterImage("../_Images/JeuDuPlacard/Fond/Placard.jpg");

		// Creation de la grille du placard
		//
		grillePlacard = new GrilleG(fondPlacard, configGrilleG);

		// Rendre la grille transparente a l'oeil
		//
		grillePlacard.modifierCouleurCellules(new Color(0, 0, 0, 0));
		grillePlacard.setBackground(new Color(0, 0, 0, 0));
		grillePlacard.modifierBordure(null);

		// Disposition de la grille dans le panneau
		//
		largeurFen = placard.getSize().width;
		hauteurFen = placard.getSize().height;
		grillePlacard.setLocation((int) ((largeurFen * 110) / 512), (int) ((hauteurFen * 28) / 571));
		grillePlacard.setSize((int) ((largeurFen * 296) / 512), (int) ((hauteurFen * 460) / 571));

		// Rendre visible le placard
		//
		fondPlacard.add(grillePlacard);
		placard.getContentPane().add(fondPlacard);
		placard.setVisible(true);

		// Creation du clavier
		//
		clavier = new ClavierG(new JFrame(), configClavierG);

		// Ajout d'un ecran
		//
		clavier.ajouterEcran();

		// Regler la disposition du clavier
		//
		clavier.obtenirJFrame().setLocation(frigo.getLocation().x, frigo.getSize().height);
		clavier.obtenirJFrame().setSize(frigo.getSize().width + placard.getSize().width, 160);
		clavier.obtenirJFrame().setResizable(false);

		// Remplissage du clavier avec des images aleatoires
		//
		Vector cheminsRepertoire = new Vector();
		cheminsRepertoire.add("../_Images/JeuDuPlacard/Placard/");
		cheminsRepertoire.add("../_Images/JeuDuPlacard/Frigo/");

		// Creation du vecteur d'images
		//
		Vector donnees = Dossier.obtenirVecteur(cheminsRepertoire, grilleFrigo.obtenirNbCellules(), ".png");
		if (donnees == null)
			System.exit(0);

		// Mise en style images
		//
		clavier.styleClavierImages(donnees);

		// Ajout d'un ecouteur de jeu clavier
		//
		clavier.ajouterEcouteur(new EcouteurJeuDuPlacardClavier(this));

		// Ajouter un ecouteur de jeu
		//
		grilleFrigo.ajouterEcouteur(new EcouteurJeuDuPlacard(frigo, this));

		// Ajout d'un ecouteur de jeu
		//
		fondFrigo.ajouterEcouteur(new EcouteurJeuDuPlacard(frigo, this));

		// Ajout d'un ecouteur de jeu
		//
		grillePlacard.ajouterEcouteur(new EcouteurJeuDuPlacard(placard, this));

		// Ajout d'un ecouteur de jeu
		//
		fondPlacard.ajouterEcouteur(new EcouteurJeuDuPlacard(placard, this));

		// Definir le nombre d'element du clavier
		//
		nombreElementDepart = clavier.obtenirNombreElement();

		// Rendre visible le clavier
		//
		clavier.setVisible(true);
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
		JeuDuPlacard jeu = new JeuDuPlacard(configGrilleG, configClavierG);

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
	 * Obtention de la grille de rangement d'aliments pour
	 * le frigo
	 * 
	 * @return grille du frigo
	 * @since 1.0.0
	 */
	public GrilleG obtenirGrilleFrigo() {
		return grilleFrigo;
	}

	/**
	 * Obtention de la grille de rangement d'aliments pour
	 * le placard
	 * 
	 * @return grille du placard
	 * @since 1.0.0
	 */
	public GrilleG obtenirGrillePlacard() {
		return grillePlacard;
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
		clavier.ajouterImage("../_Images/Banniere/BanniereClavierFaisTonMarcheGirl.png");
	}

	/**
	 * Applique un style masculin au design du jeu
	 * 
	 * @since 1.0.0
	 */
	public void styleMasculin() {
		clavier.ajouterImage("../_Images/Banniere/BanniereClavierFaisTonMarcheBoy.png");
	}

	/**
	 * Applique un style masculin au design du jeu
	 * 
	 * @since 1.0.0
	 */
	public void styleArcEnCiel() {
		clavier.ajouterImage("../_Images/Banniere/BanniereClavierFaisTonMarcheArcEnCiel.png");
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
	 * @param fenetre
	 *            demandeuse de verification.
	 * @param fichier
	 *            a verifier.
	 * @return flag de bon placement
	 * @since 1.0.0
	 */
	public boolean verificationJeu(String fenetre, File fichier) {
		boolean victoire = false;

		// Verifier si l'aliment peu etre mis dans la
		// fenetre demandeuse
		//
		if (fenetre.equals(fichier.getParentFile().getName()))
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
		GrilleG gr = null;
		if (fenetre.getTitle().equals("Frigo"))
			gr = obtenirGrilleFrigo();
		else
			gr = obtenirGrillePlacard();

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
					JOptionPane.showMessageDialog(null, Texte.load("../_Textes/Jeux/JeuDuPlacard/Gagner") + scoreSurVingt + "/20");
					obtenirClavier().modifierTexteEcran(Texte.load("../_Textes/Jeux/JeuDuPlacard/Gagner") + scoreSurVingt + "/20");
				}
			} else {
				// Afficher un message de correction
				//
				obtenirClavier().modifierTexteEcran(nomAliment + Texte.load("../_Textes/Jeux/JeuDuPlacard/Mauvais") + fenetre.getTitle());
				obtenirClavier().modifierCouleurEcran(Color.red);
			}
		} else
			// Afficher un message de selection
			//
			obtenirClavier().modifierTexteEcran("Choisissez un aliment");
	}
}