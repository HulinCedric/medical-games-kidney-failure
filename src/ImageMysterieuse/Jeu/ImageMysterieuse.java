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
 *          ajout d'un chronometre dans le jeu
 */
import java.awt.Color;
import java.io.File;
import java.util.Vector;

import javax.swing.JFrame;

import org.jdom.Element;

/**
 * Regroupement d'objet necessaire dans une classe pour construire le jeu de
 * l'image mysterieuse
 * 
 * @version 1.1.0
 * @author Charles Fouco, Cedric Hulin
 */
public class ImageMysterieuse {

	/**
	 * Panneau support contenant l'image a trouver
	 * 
	 * @since 1.0.0
	 */
	private PanneauG supportImage;

	/**
	 * Jeux grille - interface
	 * 
	 * @since 1.0.0
	 */
	private JeuxGrille jeu;

	/**
	 * Niveau de difficulte du jeu
	 * 
	 * @since 1.0.0
	 */
	private String niveauDifficulte;

	/**
	 * Solution du jeu Nom de l'image du panneau support
	 * 
	 * @since 1.0.0
	 */
	private String solution;

	/**
	 * Chronometre du jeu
	 * 
	 * @since 1.1.0
	 */
	Chronometre chrono;

	/**
	 * Constructeur normal
	 * 
	 * @param configPanneau
	 * 
	 * @since 1.0.0
	 */
	public ImageMysterieuse(Element configPanneau, Element configChrono) {

		// Creer un nouveau jeu
		//
		jeu = new JeuxGrille(null);
		if (jeu.obtenirGrille() == null)
			System.exit(0);

		// Modifier le titre de la fenetre
		//
		jeu.modifierTitreFenetre("Jeu de l'image mysterieuse");

		// Definir un niveau de difficulte
		//
		niveauDifficulte = "Moyen";

		// Retirer les boutons du panneau sud
		//
		jeu.retirerBoutonPanneauSud();

		// Ajouter le chronometre au panneau sud
		//
		chrono = new Chronometre(configChrono);
		jeu.obtenirPanneauSud().add(chrono);
		chrono.demarer();

		// Creer un nouveau panneau pour
		// accueilir l'image de fond
		//
		supportImage = new PanneauG(null, configPanneau);
		supportImage.ajouterMaillage(Color.PINK, Color.BLACK, 15, 15);
		supportImage.ajouterEcouteur(new EcouteurImageMysterieuseMaillage(supportImage));

		// Ajouter le panneau support au jeu
		//
		jeu.retirerPanneauCentral();
		jeu.ajouterPanneauCentral(supportImage);

		// Mise en place d'un ecouteur de niveau facile
		//
		jeu.obtenirBoutonGauche().addMouseListener(new EcouteurImageMysterieuseChoixNiveau(this));

		// Mise en place d'un ecouteur de niveau moyen
		//
		jeu.obtenirBoutonCentre().addMouseListener(new EcouteurImageMysterieuseChoixNiveau(this));

		// Mise en place d'un ecouteur de niveau difficile
		//
		jeu.obtenirBoutonDroite().addMouseListener(new EcouteurImageMysterieuseChoixNiveau(this));

		// Ajouter un panneau informations
		//
		jeu.ajouterPanneauInformation(configPanneau);

		// Afficher les règles du jeu
		//
		jeu.obtenirPanneauInformations().ajouterTexteAide(Texte.load(("../_Textes/Jeux/ImageMysterieuse/Regle")));

		// Lancer une nouvelle partie
		//
		jeu.obtenirPanneauInformations().styleZoneAffichageJList(obtenirListChoix());
		jeu.obtenirPanneauInformations().obtenirList().addMouseListener(new EcouteurImageMysterieusePanneauInformations(this));

		// Fermer la fenêtre support lors d'un click sur la
		// croix
		//
		jeu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// Montrer le cadre support et ses panneaux internes
		//
		jeu.setVisible(true);
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
		Element configPanneauInfo = (Element) ConfigXML.load("Configuration/ConfigPanneauG", "1.0.0");
		Element configChrono = (Element) ConfigXML.load("Configuration/ConfigChrono", "1.0.0");

		// Instancier un jeu
		//
		ImageMysterieuse jeu = new ImageMysterieuse(configPanneauInfo, configChrono);

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
	 * Applique un style feminin au design du jeu
	 * 
	 * @since 1.0.0
	 */
	public void styleFeminin() {
		jeu.styleFeminin();
	}
	
	/**
	 * Applique un style feminin au design du jeu
	 * 
	 * @since 1.0.0
	 */
	public void styleArcEnCiel() {
		jeu.styleArcEnCiel();
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
	 * Obtenir le panneau support de l'image a decouvrir
	 * 
	 * @return supportImage
	 * @since 1.0.0
	 */
	public PanneauG obtenirSupport() {
		return this.supportImage;
	}

	/**
	 * Obtenir une list d'element a afficher dans le panneau d'informations
	 * 
	 * @return Vector nomImages
	 * @since 1.0.0
	 */
	public Vector obtenirListChoix() {

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
		Vector fusionDossier = Dossier.obtenirVecteur(listeDossier, ".jpg");

		// Definir le nombre d'elements
		// a recuperer en fonction du niveau
		//
		int nombreImages = 0;
		if (niveauDifficulte.equals("Facile"))
			nombreImages = 5;
		else if (niveauDifficulte.equals("Moyen"))
			nombreImages = 10;
		else if (niveauDifficulte.equals("Difficile"))
			nombreImages = 15;

		// Verifier que le nombre d'elements a
		// recuperer n'est pas superieur au
		// nombre d'images dans le dossier
		//
		if (nombreImages > fusionDossier.size())
			nombreImages = fusionDossier.size();

		// Recuperer dans un nouveau vecteur
		// le nombre d'elements desire
		//
		Vector nomImages = new Vector();
		for (int i = 0; i < nombreImages; i++) {
			nomImages.add(fusionDossier.get(i));
		}

		// Definir un element du vecteur
		// en tant que solution
		//
		int random = (int) (Math.random() * (nomImages.size()));
		File file = new File((String) nomImages.get(random));
		solution = file.getName().substring(0, file.getName().length() - 4);
		supportImage.ajouterImage(file.getAbsolutePath());

		// Remplir le vecteur avec le nom des
		// images
		//
		nomImages.clear();
		for (int i = 0; i < nombreImages; i++) {
			file = new File((String) fusionDossier.get(i));
			nomImages.add(file.getName().substring(0, file.getName().length() - 4));
		}

		return nomImages;
	}

	/**
	 * Obtenir la solution du jeu
	 * 
	 * @return solution
	 * @since 1.0.0
	 */
	public String obtenirSolution() {
		return solution;
	}

	/**
	 * Obtenir le jeu support - interface
	 * 
	 * @return jeu
	 * 
	 * @since 1.0.0
	 */
	public JeuxGrille obtenirJeu() {
		return jeu;
	}

	/**
	 * Modifier le niveau de difficulte du jeu
	 * 
	 * @param difficulte
	 * @return flag de reussite
	 */
	public boolean modifierNiveau(String difficulte) {

		// Verification du parametre
		//
		if (!difficulte.equals("Facile") && !difficulte.equals("Moyen") && !difficulte.equals("Difficile"))
			return false;

		// Affecter le nouveau niveau
		//
		niveauDifficulte = difficulte;
		return true;
	}

	/**
	 * Obtenir le chronometre du jeu
	 * 
	 * @since 1.1.0
	 */
	public Chronometre obtenirChronometre() {
		return chrono;
	}

}
