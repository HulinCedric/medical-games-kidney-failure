/**
 * IUT de Nice / Departement informatique / Module APO-Java
 * Annee 2009_2010 - Projet l'Archet
 * 
 * 
 * @edition A 
 * 
 * @version 1.0.0
 * 
 * 				mise en place du fond
 * 
 */

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.MouseListener;

import javax.swing.JFrame;

import org.jdom.Element;

/**
 * Page d'accueil du CD
 * 
 * @author Charles Fouco, Cedric Hulin
 * @version 1.0.0
 */
public class Demarrer {

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
	 * Panneau sud
	 * 
	 * @since 1.1.0
	 */
	private GrilleG grilleSud;

	/**
	 * Page en cours
	 * 
	 * @since 1.3.0
	 */
	private String pageEnCours;

	private Accueil pageAccueil;

	/**
	 * Constructeur normal
	 * 
	 * @since 1.0.0
	 */
	public Demarrer(Element configGrille, Element configGrilleSud) {

		// Creation de la fenetre support
		//
		fenetreSupport = new JFrame();
		fenetreSupport.setTitle("Accueil");
		// fenetreSupport.setResizable(false);
		fenetreSupport.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// Afficher le jeu
		//
		// fenetreSupport.setUndecorated(true);
		 fenetreSupport.setSize(java.awt.Toolkit.getDefaultToolkit().getScreenSize());
		//fenetreSupport.setSize(1024, 768);

		// Ajouter un panneau sud permettant la navigation
		// entre les pages
		//
		ajouterGrilleSud(configGrilleSud);

		// Afficher la page d'accueil
		//
		pageAccueil(configGrille);

		fenetreSupport.setLocationRelativeTo(null);
		fenetreSupport.validate();
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
		Element configGrille = (Element) ConfigXML.load("Configuration/Accueil/ConfigGrille", "1.0.0");
		Element configGrilleSud = (Element) ConfigXML.load("Configuration/Accueil/ConfigGrilleSud", "1.0.0");

		new Demarrer(configGrille, configGrilleSud);
	}

	/**
	 * Obtenir grille
	 * 
	 * @since 1.0.0
	 */
	public GrilleG obtenirGrille() {
		return grille;
	}

	/**
	 * Ajouter panneau nord
	 * 
	 * @since 1.1.0
	 */
	public void pageAccueil(Element configGrille) {

		// Verifier la validite des parametres
		//
		if (configGrille == null)
			return;

		// Effacer le contenu de la fenetre support
		//
		fenetreSupport.getContentPane().removeAll();

		// Definir la page en cours
		//
		pageEnCours = "Accueil";

		// Stoper le rafraichissement de la page
		//
		if (pageAccueil != null) {
			pageAccueil.arreterChrono();
			pageAccueil = null;
		}

		// Afficher une nouvelle page d'accueil
		//
		pageAccueil = new Accueil(this, configGrille);

		// Verifier la presence de la grille sud
		//
		if (grilleSud == null)
			return;
		else {

			// Attacher la grille sud a la fenetre support
			//
			fenetreSupport.getContentPane().add(grilleSud, BorderLayout.SOUTH);
		}

		// Ajouter une image de fond dans la grille sud
		//
		grilleSud.ajouterImage("../_Images/Accueil/Fond/Bottom.jpg");

		// Retirer le bouton retour
		//
		retirerBouton(grilleSud.obtenirCellule(1, 1), "EcouteurRetour");

		// Ajouter un bouton quitter
		//
		ajouterBoutonQuitter();
	}

	/**
	 * Obtenir panneau sud
	 * 
	 * @since 1.1.0
	 */
	public GrilleG obtenirGrilleSud() {
		return grilleSud;
	}

	/**
	 * Afficher la page de jeux
	 * 
	 * @param configGrille
	 * @since 1.2.0
	 */
	public void pageJeux(Element configGrille, Element configPanneauNord) {

		// Verifier la validite des parametres
		//
		if (configGrille == null || configPanneauNord == null)
			return;

		// Supprimer les pages presentent
		//
		fenetreSupport.getContentPane().removeAll();

		// Definir la page en cours
		//
		pageEnCours = "Jeux";

		// Stoper le rafraichissement de la page
		//
		if (pageAccueil != null) {
			pageAccueil.arreterChrono();
			pageAccueil = null;
		}

		// Afficher la page de jeux
		//
		new Jeux(this, configGrille, configPanneauNord);

		// Verifier la presence de la grille sud
		//
		if (grilleSud == null)
			return;
		else {

			// Attacher la grille sud a la fenetre support
			//
			fenetreSupport.getContentPane().add(grilleSud, BorderLayout.SOUTH);
		}

		// Ajouter un bouton de retour permettant la navigation
		//
		ajouterBoutonRetour();

		// Retirer le bouton quitter
		//
		retirerBouton(grilleSud.obtenirCellule(1, 13), "EcouteurQuitter");

		// Ajouter une image de fond dans la grille sud
		//
		grilleSud.ajouterImage("../_Images/Jeux/Fond/Bottom.jpg");
	}

	/**
	 * obtenir le frame support
	 * 
	 * @return fenetreSupport
	 * @since 1.2.0
	 */
	public JFrame obtenirFenetreSupport() {
		return fenetreSupport;
	}

	/**
	 * Page Realisateurs
	 * 
	 * @since 1.3.0
	 */
	public void pageRealisateurs() {

		// Effacer le contenu de la fenetre support
		//
		fenetreSupport.getContentPane().removeAll();

		// Definir la page en cours
		//
		pageEnCours = "Realisateurs";

		// Stoper le rafraichissement de la page
		//
		if (pageAccueil != null) {
			pageAccueil.arreterChrono();
			pageAccueil = null;
		}

		// Afficher la page des realisateurs
		//
		new Realisateurs(this);

		// Verifier la presence de la grille sud
		//
		if (grilleSud == null)
			return;
		else {

			// Attacher la grille sud a la fenetre support
			//
			fenetreSupport.getContentPane().add(grilleSud, BorderLayout.SOUTH);
		}

		// Ajouter un bouton de retour permettant la navigation
		//
		ajouterBoutonRetour();

		// Retirer le bouton quitter
		//
		retirerBouton(grilleSud.obtenirCellule(1, 13), "EcouteurQuitter");

		// Ajouter une image de fond dans la grille sud
		//
		grilleSud.ajouterImage("../_Images/Realisateurs/Fond/Bottom.jpg");
	}

	/**
	 * Obtenir le nom de la page en cours
	 * 
	 * @since 1.3.0
	 */
	public String obtenirNomPageEnCours() {
		return pageEnCours;
	}

	/**
	 * Page des Centres
	 * 
	 * @since 1.4.0
	 */
	public void pageCentres(Element configGrille) {

		// Verifier la validite des parametres
		//
		if (configGrille == null)
			return;

		// Supprimer les pages presentent
		//
		fenetreSupport.getContentPane().removeAll();

		// Definir la page en cours
		//
		pageEnCours = "Centres";

		// Stoper le rafraichissement de la page
		//
		if (pageAccueil != null) {
			pageAccueil.arreterChrono();
			pageAccueil = null;
		}

		// Afficher la page de jeux
		//
		new Centres(this, configGrille);

		// Verifier la presence de la grille sud
		//
		if (grilleSud == null)
			return;
		else {

			// Attacher la grille sud a la fenetre support
			//
			fenetreSupport.getContentPane().add(grilleSud, BorderLayout.SOUTH);
		}

		// Ajouter un bouton de retour permettant la navigation
		//
		ajouterBoutonRetour();

		// Retirer le bouton quitter
		//
		retirerBouton(grilleSud.obtenirCellule(1, 13), "EcouteurQuitter");

		// Ajouter une image de fond dans la grille sud
		//
		grilleSud.ajouterImage("");
	}

	private void ajouterGrilleSud(Element configGrilleSud) {

		// Creation de la grille sud
		//
		grilleSud = new GrilleG(this, configGrilleSud);

		// Enlever les brodure de la grille
		//
		grilleSud.modifierCouleurCellules(new Color(0, 0, 0, 0));
		grilleSud.setBackground(new Color(0, 0, 0, 0));
		grilleSud.modifierBordure(null);

		// Definir une taille pour le panneau sud
		//
		grilleSud.setPreferredSize(new Dimension(fenetreSupport.getWidth(), (fenetreSupport.getHeight() * 311 / 1600) / 2));

		// Permettre l'affichage d'une image de fond
		//
		grilleSud.modifierCouleurCellules(new Color(0, 0, 0, 0));
		grilleSud.setBackground(new Color(0, 0, 0, 0));
	}

	/**
	 * Page Bonus
	 * 
	 * @since 1.5.0
	 */
	public void pageBonus(Element configPanneauNord, Element configGrille) {

		// Verifier la validite des parametres
		//
		if (configGrille == null || configPanneauNord == null)
			return;

		// Effacer le contenu de la fenetre support
		//
		fenetreSupport.getContentPane().removeAll();

		// Definir la page en cours
		//
		pageEnCours = "Bonus";

		// Stoper le rafraichissement de la page
		//
		if (pageAccueil != null) {
			pageAccueil.arreterChrono();
			pageAccueil = null;
		}

		// Afficher la page des realisateurs
		//
		new Bonus(this, configPanneauNord, configGrille);

		// Verifier la presence de la grille sud
		//
		if (grilleSud == null)
			return;
		else {

			// Attacher la grille sud a la fenetre support
			//
			fenetreSupport.getContentPane().add(grilleSud, BorderLayout.SOUTH);
		}

		// Ajouter un bouton de retour permettant la navigation
		//
		ajouterBoutonRetour();

		// Retirer le bouton quitter
		//
		retirerBouton(grilleSud.obtenirCellule(1, 13), "EcouteurQuitter");

		// Ajouter une image de fond dans la grille sud
		//
		grilleSud.ajouterImage("");
	}

	/**
	 * Ajouter un bouton retour
	 * 
	 */
	private void ajouterBoutonRetour() {

		// Verifier la presence de la grille support
		//
		if (grilleSud == null)
			return;

		// Ajouter l'image du bouton ainsi que son ecouteur dedie
		//
		grilleSud.obtenirCellule(1, 1).ajouterImage("../_Images/Accueil/Boutons/Retour.png");
		grilleSud.obtenirCellule(1, 1).addMouseListener((new EcouteurRetour(this)));

	}

	/**
	 * Retire l'image de la cellule cible ainsi que son ecouteur dedie
	 * 
	 * @param celluleCible
	 * @param nomEcouteur
	 * 
	 */
	private void retirerBouton(CelluleG celluleCible, String ecouteurASupprimer) {

		// Verifier la presence de la grille support
		//
		if (grilleSud == null)
			return;

		// Retirer l'image du bouton ainsi que son ecouteur dedie
		//
		celluleCible.retirerImage();

		// Recuperer la liste des ecouteurs
		//
		MouseListener[] listeEcouteurs = celluleCible.getMouseListeners();

		// Supprimer l'ecouteur cible
		// 
		for (int i = 0; i < listeEcouteurs.length; i++) {
			if (listeEcouteurs[i].getClass().getSimpleName().equals(ecouteurASupprimer))
				celluleCible.removeMouseListener(listeEcouteurs[i]);
		}
	}

	/**
	 * Retire l'ecouteur du bouton retour
	 * 
	 * @param nomEcouteur
	 */
	public void retirerEcouteurBoutonRetour(String ecouteurASupprimer) {

		// Verifier la presence de la grille support
		//
		if (grilleSud == null)
			return;

		// Recuperer la liste des ecouteurs
		//
		MouseListener[] listeEcouteurs = grilleSud.obtenirCellule(1, 1).getMouseListeners();

		// Supprimer l'ecouteur cible
		// 
		for (int i = 0; i < listeEcouteurs.length; i++) {
			if (listeEcouteurs[i].getClass().getSimpleName().equals(ecouteurASupprimer))
				grilleSud.obtenirCellule(1, 1).removeMouseListener(listeEcouteurs[i]);
		}
	}

	/**
	 * Ajouter ecouteur au bouton retour
	 * 
	 * @param ecouteur
	 */
	public void ajouterEcouteurBoutonRetour(MouseListener ecouteur) {

		// Verifier la presence de la grille support
		//
		if (grilleSud == null)
			return;

		// Recuperer la liste des ecouteurs
		//
		grilleSud.obtenirCellule(1, 1).addMouseListener(ecouteur);
	}

	/**
	 * Ajouter un bouton quitter
	 * 
	 */
	private void ajouterBoutonQuitter() {

		// Verifier la presence de la grille support
		//
		if (grilleSud == null)
			return;

		// Ajouter l'image du bouton ainsi que son ecouteur dedie
		//
		grilleSud.obtenirCellule(1, 13).ajouterImage("../_Images/Accueil/Boutons/Quitter.png");
		grilleSud.obtenirCellule(1, 13).addMouseListener(new EcouteurAfficherTitre(this, grilleSud.obtenirCellule(1, 13)));
		grilleSud.obtenirCellule(1, 13).addMouseListener(new EcouteurQuitter());

	}

	/**
	 * Page des Recettes
	 * 
	 * @since 1.5.0
	 */
	public void pageRecettes(Element configGrille) {

		// Verifier la validite des parametres
		//
		if (configGrille == null)
			return;

		// Effacer le contenu de la fenetre support
		//
		fenetreSupport.getContentPane().removeAll();

		// Definir la page en cours
		//
		pageEnCours = "Recettes";

		// Stoper le rafraichissement de la page
		//
		if (pageAccueil != null) {
			pageAccueil.arreterChrono();
			pageAccueil = null;
		}

		// Afficher la page des recettes
		//
		new Recettes(this, configGrille);

		// Verifier la presence de la grille sud
		//
		if (grilleSud == null)
			return;
		else {

			// Attacher la grille sud a la fenetre support
			//
			fenetreSupport.getContentPane().add(grilleSud, BorderLayout.SOUTH);
		}

		// Ajouter un bouton de retour permettant la navigation
		//
		ajouterBoutonRetour();

		// Retirer le bouton quitter
		//
		retirerBouton(grilleSud.obtenirCellule(1, 13), "EcouteurQuitter");

		// Ajouter une image de fond dans la grille sud
		//
		grilleSud.ajouterImage("../_Images/Recettes/Fond/Bottom.png");
	}

	/**
	 * Page de l'Abecedaire
	 * 
	 * @since 1.5.0
	 */
	public void pageAbecedaire(Element configGrille) {

		// Verifier la validite des parametres
		//
		if (configGrille == null)
			return;

		// Effacer le contenu de la fenetre support
		//
		fenetreSupport.getContentPane().removeAll();

		// Definir la page en cours
		//
		pageEnCours = "Abecedaire";

		// Stoper le rafraichissement de la page
		//
		if (pageAccueil != null) {
			pageAccueil.arreterChrono();
			pageAccueil = null;
		}

		// Afficher la page de l'abecedaire
		//
		new Abecedaire(this, configGrille);

		// Verifier la presence de la grille sud
		//
		if (grilleSud == null)
			return;
		else {

			// Attacher la grille sud a la fenetre support
			//
			fenetreSupport.getContentPane().add(grilleSud, BorderLayout.SOUTH);
		}

		// Ajouter un bouton de retour permettant la navigation
		//
		ajouterBoutonRetour();

		// Retirer le bouton quitter
		//
		retirerBouton(grilleSud.obtenirCellule(1, 13), "EcouteurQuitter");

		// Ajouter une image de fond dans la grille sud
		//
		grilleSud.ajouterImage("../_Images/Abecedaire/Fond/Bottom.jpg");
	}
}