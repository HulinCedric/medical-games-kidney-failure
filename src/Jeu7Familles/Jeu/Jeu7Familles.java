/**
 * IUT de Nice / Departement informatique / Module APO-Java
 * Annee 2009_2010 - Applications graphiques
 * 
 * @edition A : edition initiale
 * 
 * @version 1.0.0 :
 * 
 *          version initiale gestion de l'affichage des
 *          cartes
 * 
 * @version 2.0.0 :
 * 
 *          mise en place de la gestion du jeu
 */
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Vector;

import javax.swing.JFrame;

import org.jdom.Element;

/**
 * Regroupement d'objets necessaires dans une classe pour
 * construire le jeu des 7 familles
 * 
 * @version 2.0.0
 * @author Charles Fouco, Cedric Hulin
 */
public class Jeu7Familles {

	/**
	 * Fenetre support
	 * 
	 * @since 1.0.0
	 */
	private JFrame fenetreSupport;

	/**
	 * Grille de disposition des aliments
	 * 
	 * @since 1.0.0
	 */
	private GrilleG grille;

	/**
	 * Image selectionnee
	 * 
	 * @since 1.0.0
	 */
	private String cheminImageSelectionnee;

	/**
	 * Jeu de 42 cartes 7 familles - 6 cartes chacune
	 * 
	 * @since 2.0.0
	 */
	private Vector cartes;

	/**
	 * Boolean definissant l'etat du jeu
	 * 
	 * @since 2.0.0
	 */
	private boolean etatJeu;

	/**
	 * Panneau sud
	 * 
	 * @since 2.1.0
	 */
	private GrilleG grilleSud;

	/**
	 * Panneau central
	 * 
	 * @since 2.1.0
	 */
	private PanneauG grilleCentrale;

	/**
	 * HashMap permettant de faire l'association entre une
	 * colonne et une categorie (famille)
	 * 
	 * @since 3.0.0
	 */
	private HashMap familles;

	/**
	 * Constructeur normal
	 * 
	 * @param configPanneauInfo
	 * @param configClavier
	 * 
	 * @since 1.0.0
	 */
	public Jeu7Familles(Element configGrilleNord, Element configGrilleCentrale, Element configGrilleSud) {

		// Obtention de variable de calcule de
		// dimensionnement des fenetre selon l'environement
		// de lancement
		//
		Dimension tailleEcran = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
		double largeur = tailleEcran.getWidth();

		// Creation de la fenetre support
		//
		fenetreSupport = new JFrame();
		fenetreSupport.setTitle("Jeu des 7 Familles");
		// fenetreSupport.setResizable(false);
		fenetreSupport.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// Disposition de la fenetre sur l'ecran
		//
		fenetreSupport.setSize(1024, 768);
		fenetreSupport.setLocation((int) (largeur - fenetreSupport.getWidth()) / 2, 0);

		// Definir le jeu comme actif
		//
		etatJeu = true;

		// Charger les images du jeu de cartes
		//
		if (!setJeuCartes())
			return;

		// Faire l'association colonnes-familles
		//
		setFamille();

		// Ajouter le panneau nord comportant
		// les 7 familles
		//
		ajouterPanneauNord(configGrilleNord);

		// Ajouter la grille centrale
		//
		ajouterPanneauSud(configGrilleSud);

		// Ajouter la grille centrale
		//
		ajouterPanneauCentral(configGrilleCentrale);

		// Afficher le tas de cartes
		//
		afficherCarteJouer();

		// Rendre visible la fenetre support
		//
		fenetreSupport.setVisible(true);

		// Afficher le jeu
		//
		fenetreSupport.setVisible(true);
	}

	/**
	 * Programme principal
	 * 
	 * @param args
	 * 
	 * @since 1.0.0
	 */
	public static void main(String[] args) {

		// Charger les fichier de configuration
		//
		Element configGrilleNord = (Element) ConfigXML.load("Configuration/ConfigGrilleNord", "1.0.0");
		Element configGrilleCentrale = (Element) ConfigXML.load("Configuration/ConfigGrilleCentrale", "1.0.0");
		Element configGrilleSud = (Element) ConfigXML.load("Configuration/ConfigGrilleSud", "1.0.0");

		// Instancier un jeu
		//
		new Jeu7Familles(configGrilleNord, configGrilleCentrale, configGrilleSud);
	}

	/**
	 * Obtenir chemin image selectionnee
	 * 
	 * @since 1.0.0
	 */
	public String obtenirCheminImageSelectionnee() {
		return cheminImageSelectionnee;
	}

	/**
	 * Modifier l'image selectionnee
	 * 
	 * @since 1.0.0
	 */
	public void modifierImageSelectionnee(String cheminNouvelleImage) {
		cheminImageSelectionnee = cheminNouvelleImage;
	}

	/**
	 * Obtenir grille support
	 * 
	 * @since 1.0.0
	 */
	public GrilleG obtenirGrilleNord() {
		return grille;
	}

	/**
	 * Affichage du tas de cartes restantes
	 * 
	 * @since 2.0.0
	 */
	private void afficherTasDeCartes() {

		// Verifier qu'un jeu de carte est present
		//
		if (cartes == null)
			return;

		// Verifier le nombre de cartes restantes
		//
		if (cartes.size() < 0)
			return;

		// Verifier l'existence de la grille sud
		//
		if (grilleSud == null)
			return;

		// Declarer les variables de travail
		//
		Iterator i = cartes.iterator();
		int compteur = 0;
		String imageEnCours;

		// Retirer toutes les images du lot de cartes
		//
		grilleSud.obtenirCellule(1, 3).retirerImages();
		grilleSud.obtenirCellule(1, 3).modifierNombreImages(42);
		grilleSud.repaint();

		// Afficher le nouveau tas de cartes
		//
		while (i.hasNext()) {
			imageEnCours = (String) i.next();
			grilleSud.ajouterImage(1, 3, imageEnCours, compteur, 1, 10, 10);
			compteur++;
		}

		grilleSud.repaint();
	}

	/**
	 * Charger un jeu de 42 cartes
	 * 
	 * @since 2.0.0
	 */
	private boolean setJeuCartes() {

		// Creer le vecteur de travail
		//
		Vector listeDossier = new Vector();

		// Remplir le vecteur avec les elements
		// de chaque repertoire
		//
		listeDossier.add("../_Images/Jeu7Familles/Legumes");
		listeDossier.add("../_Images/Jeu7Familles/Fruits");
		listeDossier.add("../_Images/Jeu7Familles/Feculents");
		listeDossier.add("../_Images/Jeu7Familles/Matieres Grasses");
		listeDossier.add("../_Images/Jeu7Familles/Produits Laitiers");
		listeDossier.add("../_Images/Jeu7Familles/Produits Sucres");
		listeDossier.add("../_Images/Jeu7Familles/Viande Poisson Oeuf");

		// Obtenir 6 images de chaque repertoire
		//
		cartes = Dossier.obtenirVecteur(listeDossier, 6, ".png");

		// Verifier le nombre d'images chargee
		//
		if (cartes.size() != 42)
			return false;
		return true;
	}

	/**
	 * Alouer l'espace necessaire pour l'affichage de 6
	 * cartes pour les 7 familles (cellules)
	 * 
	 * @since 2.0.0
	 */
	private boolean setAllocationImagesCellules(int nombreImages) {

		// Verifier le parametre
		//
		if (nombreImages <= 0)
			return false;

		// Verifier la presence de la grille
		//
		if (grille == null)
			return false;

		// Effectuer l'allocation
		//
		for (int i = 0; i < 7; i++)
			grille.obtenirCellule(1, i + 1).modifierNombreImages(nombreImages);

		return true;
	}

	/**
	 * Afficher la carte a jouer
	 * 
	 * @since 2.0.0
	 */
	public void afficherCarteJouer() {

		// Verifier l'etat du jeu
		//
		if (!etatJeu)
			return;

		// Verifier la presence d'un jeu de cartes
		//
		if (cartes == null)
			return;

		// Il n'y a plus de carte a jouer
		// mettre fin a la partie
		//
		if (cartes.size() - 1 < 0) {
			etatJeu = false;
			cartes = null;

			Son.play("../_Sons/applaudi-foule.wav");

			grilleSud.obtenirCellule(1, 4).retirerImage();
			grilleSud.obtenirCellule(1, 5).retirerTexte();

			// Afficher un message de reussite
			//
			grilleSud.ajouterTexte(1, 2, "B");
			grilleSud.ajouterTexte(1, 3, "R");
			grilleSud.ajouterTexte(1, 4, "A");
			grilleSud.ajouterTexte(1, 5, "V");
			grilleSud.ajouterTexte(1, 6, "O");

			grilleCentrale.repaint();
			grilleSud.repaint();

			return;
		}

		// Afficher le nombre de carte restante a jouer
		//
		grilleSud.ajouterTexte(1, 5, String.valueOf(cartes.size()));

		// Recuperer la derniere carte du jeu
		//
		String derniereImage = (String) cartes.get(cartes.size() - 1);

		// Supprimer l'image recuperee du tas de cartes
		//
		cartes.remove(cartes.size() - 1);

		// Modifier l'image de la carte a jouer
		//
		grilleSud.ajouterImage(1, 4, derniereImage);
		grilleSud.repaint();

		// Definir l'image selectionnee
		//
		cheminImageSelectionnee = derniereImage;

		// Afficher le tas de cartes restantes
		//
		afficherTasDeCartes();
	}

	/**
	 * Obtenir l'etat du jeu
	 * 
	 * @since 2.0.0
	 */
	public boolean obtenirEtatJeu() {
		return etatJeu;
	}

	/**
	 * Ajouter panneau nord
	 * 
	 * @since 2.1.0
	 */
	private void ajouterPanneauNord(Element configPanneauNord) {

		// Creation de la grille de disposition
		//
		grille = new GrilleG(fenetreSupport, configPanneauNord);

		// Permettre l'affichage de 7 cartes
		// dans les 7 cellules
		//
		if (!setAllocationImagesCellules(7))
			return;

		// Ajouter un ecouteur a la grille
		// permettant le placement des images
		//
		grille.ajouterEcouteur(new EcouteurPlacerImage(this));

		// Rendre la grille transparente a l'oeil
		//
		grille.modifierCouleurCellules(new Color(0, 0, 0, 0));
		grille.setBackground(new Color(0, 0, 0, 0));

		// Ajouter un fond a chaque cellule pour faire
		// les 7 familles
		//
		Iterator i = familles.keySet().iterator();
		Integer index = new Integer(0);
		String nomFamille = "";
		while (i.hasNext()) {
			// Recuperer la position dans la grille de la
			// famille
			//
			index = (Integer) i.next();

			// Recuperer le nom de la famille
			//
			nomFamille = (String) familles.get(index);

			// Ajouter l'image correspondante a la famille
			// a l'index indique
			//
			grille.ajouterImage(1, index.intValue(), "../_Images/Jeu7Familles/" + nomFamille + ".png", 0, 20, 10, 19);

			// Ajouter une couleur de fond correspondant a
			// la famille
			//
			grille.obtenirCellule(1, index.intValue()).setBackground(obtenirCouleurFamille(nomFamille));
		}

		grille.setPreferredSize(new Dimension(fenetreSupport.getWidth(), fenetreSupport.getHeight() * 300 / 768));

		// Ajout du panneau au cadre support
		//
		fenetreSupport.getContentPane().add(grille, BorderLayout.NORTH);
	}

	/**
	 * Ajouter un panneau sud
	 * 
	 * @since 2.1.0
	 */
	private void ajouterPanneauSud(Element configPanneauSud) {

		// Creation de la grille de disposition
		//
		grilleSud = new GrilleG(fenetreSupport, configPanneauSud);

		// Ajout de l'image de fond
		//
		grilleSud.ajouterImage("../_Images/Jeu7Familles/Fond.jpg");

		// Enlever les bordures de la grilleCentrale
		//
		grilleSud.modifierCouleurCellules(new Color(0, 0, 0, 0));
		grilleSud.setBackground(new Color(0, 0, 0, 0));
		grilleSud.modifierBordure(null);

		// Ajout de la grille au cadre support
		//
		grilleSud.setPreferredSize(new Dimension(fenetreSupport.getWidth(), fenetreSupport.getHeight() * 200 / 768));
		fenetreSupport.getContentPane().add(grilleSud, BorderLayout.SOUTH);
	}

	/**
	 * Ajouter un panneau central
	 * 
	 * @since 2.1.0
	 */
	private void ajouterPanneauCentral(Element configPanneauCentral) {

		// Creation de la grille de disposition
		//
		grilleCentrale = new PanneauG(fenetreSupport, configPanneauCentral);

		// Ajout de l'image de fond
		//
		grilleCentrale.ajouterImage("../_Images/Jeu7Familles/Fond titre.jpg");

		// Enlever les brodure de la grilleCentrale
		//

		// Ajout de la grille au cadre support
		//
		fenetreSupport.getContentPane().add(grilleCentrale, BorderLayout.CENTER);
	}

	/**
	 * Obtenir grille centrale
	 * 
	 * @since 2.1.0
	 */
	public PanneauG obtenirGrilleCentrale() {
		return grilleCentrale;
	}

	/**
	 * Obtenir grille sud
	 * 
	 * @since 2.1.0
	 */
	public GrilleG obtenirGrilleSud() {
		return grilleSud;
	}

	/**
	 * setFamille Permet de faire l'association entre les
	 * colonnes de la grille nord et les 7 familles
	 * 
	 * @since 3.0.0
	 */
	private void setFamille() {

		familles = new HashMap();

		familles.put(new Integer(7), "Produits Sucres");
		familles.put(new Integer(6), "Matieres Grasses");
		familles.put(new Integer(5), "Produits Laitiers");
		familles.put(new Integer(4), "Viande Poisson Oeuf");
		familles.put(new Integer(3), "Fruits");
		familles.put(new Integer(2), "Legumes");
		familles.put(new Integer(1), "Feculents");
	}

	/**
	 * Obtenir liste familles
	 * 
	 * @since 3.0.0
	 */
	public HashMap obtenirFamilles() {
		return familles;
	}

	/**
	 * Obtenir la couleur associe a la famille
	 * 
	 * @since 3.0.0
	 */
	private Color obtenirCouleurFamille(String nomFamille) {

		Color couleur;

		if (nomFamille.equals("Produits Sucres"))
			couleur = new Color(224, 59, 180);
		else if (nomFamille.equals("Matieres Grasses"))
			couleur = new Color(255, 204, 3);
		else if (nomFamille.equals("Produits Laitiers"))
			couleur = new Color(82, 136, 196);
		else if (nomFamille.equals("Viande Poisson Oeuf"))
			couleur = new Color(255, 3, 2);
		else if (nomFamille.equals("Fruits"))
			couleur = new Color(11, 250, 11);
		else if (nomFamille.equals("Legumes"))
			couleur = new Color(2, 131, 2);
		else if (nomFamille.equals("Feculents"))
			couleur = new Color(255, 160, 76);
		else
			couleur = new Color(255, 255, 255);

		return couleur;
	}
}