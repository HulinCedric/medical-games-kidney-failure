/**
 * IUT de Nice / Departement informatique / Module APO-Java
 * Annee 2009_2010 - Applications graphiques
 * 
 * @edition A : edition initiale
 * 
 * @version 1.0.0 :
 * 
 *          version initiale
 * 
 * @version 1.1.0 :
 * 
 *          poissibilite de changer de joueur en cours de
 *          jeu
 * 
 */
import java.awt.Color;
import java.awt.Dimension;
import java.io.File;
import java.util.Iterator;
import java.util.Vector;

import javax.swing.JOptionPane;

import org.jdom.Element;

/**
 * Regroupement d'objet necessaire dans une classe pour construire le jeu Droit
 * Au But
 * 
 * @version 1.1.0
 * @author Charles Fouco, Cedric Hulin
 */
public class DroitAuBut {

	/**
	 * Jeu support - interface
	 * 
	 * @since 1.0.0
	 */
	private JeuxGrille jeuGrille;

	/**
	 * Solution de la devinette
	 * 
	 * @since 1.0.0
	 */
	private String solution;

	/**
	 * Position joueur
	 * 
	 * @since 1.0.0
	 */
	private Dimension positionJoueur;

	/**
	 * Image du joueur
	 * 
	 * @since 1.1.0
	 */
	private String cheminImage;

	/**
	 * Lot d'images
	 * 
	 * @since 1.0.0
	 */
	private Vector lotImages;
	
	/**
	 * Statut du jeu
	 * 
	 * @since 1.0.0
	 */
	private boolean statutJeu;
	
	/**
	 * Vecteur contenant les devinettes
	 * 
	 * @ince 1.0.0
	 */
	private Vector dossierDevinettes;
	
	/**
	 * Index de la devinette en cours
	 * 
	 * @since 1.0.0
	 */
	private int indexDevinette;

	/**
	 * Constructeur normal
	 * 
	 * @param configGrille
	 * @param configPanneau
	 * 
	 * @since 1.0.0
	 */
	public DroitAuBut(Element configGrille, Element configPanneauInfo, Element configClavier) {

		// Verifier la validite des parametres
		//
		if (configGrille == null || configClavier == null || configPanneauInfo == null) {
			JOptionPane.showMessageDialog(null, Texte.load("../_Textes/Erreurs/Movais parametre"));
			System.exit(0);
		}

		// Creer un nouveau jeu
		//
		jeuGrille = new JeuxGrille(configGrille);
		if (jeuGrille.obtenirGrille() == null)
			System.exit(0);

		// Modifier le titre de la fenetre
		//
		jeuGrille.modifierTitreFenetre("Jeu Droit Au But");
		
		// Charge le lot d'images pour le jeu
		//
		setLotImages();
		
		// Charger le dossier contenant les devinettes
		//
		setDevinettes();
		indexDevinette = 0;

		// Ajouter un clavier au jeu
		//
		jeuGrille.ajouterClavier(configClavier);
		jeuGrille.obtenirClavier().ajouterEcran();

		// Ajouter un panneau d'informations
		//
		jeuGrille.ajouterPanneauInformation(configPanneauInfo);

		// Afficher les règles du jeu
		//
		jeuGrille.obtenirPanneauInformations().ajouterTexteAide(Texte.load(("../_Textes/Jeux/DroitAuBut/Regle")));

		// Rendre la grille transparente
		//
		jeuGrille.obtenirGrille().setBackground(new Color(0, 0, 0, 0));
		jeuGrille.obtenirGrille().modifierCouleurCellules(new Color(0, 0, 0, 0));
		jeuGrille.obtenirGrille().modifierBordure(null);
		
		// Ajouter l'image de fond
		//
		jeuGrille.obtenirGrille().ajouterImage("../_Images/DroitAuBut/Terrain.jpg");
		jeuGrille.retirerPanneauSud();


		// Ajouter le joueur au jeu
		//
		cheminImage = jeuGrille.obtenirBoutonGauche().getIcon().toString();
		
		positionJoueur = new Dimension(4, 1);
		jeuGrille.obtenirGrille().obtenirCellule((int) positionJoueur.getWidth(), (int) positionJoueur.getHeight()).ajouterImage(cheminImage);

		// Modifier le texte des boutons
		//
		//jeuGrille.modifierTexteBouton(jeuGrille.obtenirBoutonGauche(), "Yoshi");
		//jeuGrille.modifierTexteBouton(jeuGrille.obtenirBoutonCentre(), "Mario");
		//jeuGrille.modifierTexteBouton(jeuGrille.obtenirBoutonDroite(), "Bowser");

		// Mise en place d'un ecouteur de niveau facile
		//
		jeuGrille.obtenirBoutonGauche().addMouseListener(new EcouteurDroitAuButChoixJoueur(this));

		// Mise en place d'un ecouteur de niveau moyen
		//
		jeuGrille.obtenirBoutonCentre().addMouseListener(new EcouteurDroitAuButChoixJoueur(this));

		// Mise en place d'un ecouteur de niveau difficile
		//
		jeuGrille.obtenirBoutonDroite().addMouseListener(new EcouteurDroitAuButChoixJoueur(this));

		// Activer le jeu
		//
		statutJeu = true;
		
		// Afficher le jeu
		//
		jeuGrille.setVisible(true);
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
		Element configGrille = (Element) ConfigXML.load("Configuration/ConfigGrille", "1.0.0");
		Element configPanneauInfo = (Element) ConfigXML.load("Configuration/ConfigPanneauInfo", "1.0.0");
		Element configClavier = (Element) ConfigXML.load("Configuration/ConfigClavier", "1.0.0");

		// Instancier un jeu
		//
		DroitAuBut jeu = new DroitAuBut(configGrille, configPanneauInfo, configClavier);

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
		
		// Charger une devinette
		//
		jeu.chargerDevinette();
	}

	/**
	 * Chargement d'une devinette
	 * 
	 * @since 1.0.0
	 */
	public void chargerDevinette() {

		if(dossierDevinettes.size()< jeuGrille.obtenirGrille().obtenirNbColonnes()) {
			JOptionPane.showMessageDialog(null, Texte.load("../_Textes/Jeux/DroitAuBut/Nombre devinettes"));
			return;
		}
					
		// Definir un element du vecteur
		// en tant que solution
		//
		//int random = (int) (Math.random() * (dossierDevinettes.size()));
		File file = new File((String) dossierDevinettes.get(indexDevinette));
		solution = file.getName().substring(0, file.getName().length() - 4);
		String cheminSolution = dossierDevinettes.get(indexDevinette).toString();

		// Verifier la presence de l'image dans
		// le lot d'image que nous avons charge
		//
		if (!presenceImage(solution)) {
			JOptionPane.showMessageDialog(null, Texte.load("../_Textes/Erreurs/Image non presente"));
			return;
		}

		// Afficher la devinette dans
		// le panneau informations
		//
		String contenu = cheminSolution.substring(0, cheminSolution.length() - 4);
		jeuGrille.obtenirPanneauInformations().modifierZoneAffichage(Texte.load(contenu));

		Vector donnees = new Vector();

		// Charger un lot d'images contenant la solution
		//
		int random = 0;
		boolean presenceSolution = false;
		boolean condition = false;
		while (!condition) {

			// Recuperer un element aleatoire
			//
			random = (int) (Math.random() * (lotImages.size()));

			// Ajouter cet element au vecteur de donnees
			//
			if (!donnees.contains(lotImages.get(random)))
				donnees.add(lotImages.get(random));
			else
				continue;

			// Recuperer le nom de cet element
			//
			file = new File((String) lotImages.get(random));
			String elementEnCours = file.getName().substring(0, file.getName().length() - 4);

			// Tester si l'element correspond
			// a la solution
			//
			if (elementEnCours.equals(solution))
				presenceSolution = true;

			// Tester la presence de la solution
			// et la taille du vecteur de donnees
			//
			if (presenceSolution && donnees.size() == 10)
				condition = true;

			// Le vecteur de donnees ne contient
			// pas la solution
			//
			else {
				if (!presenceSolution && donnees.size() == 10) {
					donnees.clear();
					donnees = new Vector();
				}
			}
		}

		// Ajouter les images du vecteur de donnees
		// au clavier
		//
		//System.out.println(donnees);
		jeuGrille.obtenirClavier().styleClavierImages(donnees);
		jeuGrille.obtenirClavier().bordureBlack();
		jeuGrille.obtenirClavier().ajouterEcouteur(new EcouteurDroitAuButClavier(this));
		
		// Passer a la devinette suivante
		//
		indexDevinette++;
	}

	/**
	 * Applique un style feminin au design du jeu
	 * 
	 * @since 1.0.0
	 */
	public void styleFeminin() {
		jeuGrille.styleFeminin();
	}

	/**
	 * Applique un style masculin au design du jeu
	 * 
	 * @since 1.0.0
	 */
	public void styleMasculin() {
		jeuGrille.styleMasculin();
	}

	/**
	 * Applique un style masculin au design du jeu
	 * 
	 * @since 1.0.0
	 */
	public void styleArcEnCiel() {
		jeuGrille.styleArcEnCiel();
	}

	/**
	 * Obtenir jeu support
	 * 
	 * @return jeuGrille
	 * @since 1.0.0
	 */
	public JeuxGrille obtenirJeuxGrille() {
		return jeuGrille;
	}

	/**
	 * Obtenir solution
	 * 
	 * @return solution
	 * @since 1.0.0
	 */
	public String obtenirSolution() {
		return solution;
	}

	/**
	 * Obtenir position joueur
	 * 
	 * @return positionJoueur
	 * @since 1.0.0
	 */
	public Dimension obtenirPositionJoueur() {
		return positionJoueur;
	}

	/**
	 * Modifier position joueur
	 * 
	 * @since 1.0.0
	 */
	public void modifierPositionJoueur(Dimension position) {
		positionJoueur = position;
	}

	/**
	 * Modifier l'image du joueur
	 * 
	 * @since 1.1.0
	 */
	public void modifierImageJoueur(String chemin) {
		cheminImage = chemin;
	}

	/**
	 * Obtenir image joueur
	 * 
	 * @since 1.1.0
	 */
	public String obtenirCheminImage() {
		return cheminImage;
	}

	/**
	 * Charger le lot d'images
	 * 
	 * @since 1.1.0
	 */
	private void setLotImages() {

		// Creer le vecteur de travail
		//
		Vector listeDossier = new Vector();

		// Remplir le vecteur avec les elements
		// de chaque repertoire
		//
		listeDossier.add("../_Images/Legumes");
		listeDossier.add("../_Images/Fruits");
		listeDossier.add("../_Images/Feculents");
		listeDossier.add("../_Images/Matieres Grasses");
		listeDossier.add("../_Images/Produits Laitiers");
		listeDossier.add("../_Images/Produits Sucres");
		listeDossier.add("../_Images/Viande Poisson Oeuf");

		// Obtenir toutes les images des repertoires
		//
		lotImages = Dossier.obtenirVecteur(listeDossier, ".jpg");
	}

	/**
	 * Verifie la presence du nom de l'image passee en parametre dans le lot
	 * d'image chargee
	 * 
	 * @param image
	 * @return flag de reussite
	 * 
	 * @since 1.1.0
	 */
	private boolean presenceImage(String image) {

		
		// Creer les variables de travail
		//
		File file;
		String nomImage;
		
		//System.out.println("PARAMETRE : " + image);

		// Parcourir le lot d'images
		//
		Iterator i = lotImages.iterator();
		while (i.hasNext()) {

			file = new File((String) i.next());
			nomImage = file.getName().substring(0, file.getName().length() - 4);
			//System.out.println(nomImage);
			if (nomImage.equals(image))
				return true;
		}
		return false;
	}
	
	/**
	 * Modifier le statut du jeu
	 * 
	 * @since 1.0.0
	 */
	public void finPartie() {
		statutJeu = false;
		
		// Afficher une image de fond dans la grille
		//
		jeuGrille.obtenirGrille().ajouterImage("../_Images/DroitAuBut/Terrain GOAL.jpg");
	}
	
	/**
	 * Obtenir statut jeu
	 * 
	 * @since 1.0.0
	 */
	public boolean obtenirStatutJeu() {
		return statutJeu;
	}
	
	/**
	 * Charger les devinettes
	 * 
	 * @since 1.0.0
	 */
	private void setDevinettes() {
		
		// Remplir un vecteur temporaire ordonnee
		//
		dossierDevinettes = Dossier.chargerRepertoire("../_Textes/Jeux/DroitAuBut/Devinettes/", ".txt");
		
		// Melanger ce vecteur
		//
		Dossier.melanger(dossierDevinettes);
	}

}
