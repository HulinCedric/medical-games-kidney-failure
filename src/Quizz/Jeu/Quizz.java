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
 *          calcul du score de la partie
 */
import java.io.File;
import java.util.Iterator;
import java.util.Vector;

import javax.swing.JList;
import javax.swing.JOptionPane;

import org.jdom.Element;

/**
 * Regroupement d'objet necessaire dans une classe pour construire le jeu du
 * Quizz
 * 
 * @version 1.1.0
 * @author Charles Fouco, Cedric Hulin
 */
public class Quizz {

	/**
	 * Jeu support - interface
	 * 
	 * @since 1.0.0
	 */
	private JeuxGrille jeuGrille;

	/**
	 * Liste de questions
	 * 
	 * @since 1.0.0
	 */
	private JList listQuestions;

	/**
	 * Contenu de la list de questions
	 * 
	 * @since 1.0.0
	 */
	private Vector contenuJList;

	/**
	 * Lot d'images
	 * 
	 * @since 1.0.0
	 */
	private Vector lotImages;

	/**
	 * Vecteur compose des differents dossiers qui composent le quizz
	 * 
	 * @since 1.0.0
	 */
	private Vector repertoireQuizz;

	/**
	 * Solution de la question
	 * 
	 * @since 1.0.0
	 */
	private String solution;

	/**
	 * Question
	 * 
	 * @since 1.0.0
	 */
	private String question;

	/**
	 * Nomre de bonnes reponses
	 * 
	 * @since 1.1.0
	 */
	private int bonnesReponses;

	/**
	 * Nomre de movaises reponses
	 * 
	 * @since 1.1.0
	 */
	private int movaisesReponses;

	/**
	 * Indiquer le statut d'une partie
	 * 
	 * @since 1.1.0
	 */
	private boolean statut;

	/**
	 * Chronometre du jeu
	 * 
	 * @since 1.2.0
	 */
	private Chronometre chrono;

	/**
	 * Chronometre du jeu
	 * 
	 * @since 1.3.0
	 */
	private int niveau;

	/**
	 * Constructeur normal
	 * 
	 * @param configPanneauCentral
	 * @param configPanneauInfo
	 * @param configClavier
	 * @param configChrono
	 * 
	 * @since 1.0.0
	 */
	public Quizz(Element configPanneauCentral, Element configPanneauInfo, Element configClavier, Element configChrono) {

		// Verifier la validite des parametres
		//
		if (configClavier == null || configPanneauInfo == null) {
			JOptionPane.showMessageDialog(null, Texte.load("../_Textes/Erreurs/Movais parametre"));
			System.exit(0);
		}

		// Creer un nouveau jeu
		//
		jeuGrille = new JeuxGrille(null);
		if (jeuGrille.obtenirGrille() == null)
			System.exit(0);

		// Modifier le titre de la fenetre
		//
		jeuGrille.modifierTitreFenetre("Jeu du Quizz");

		// Ajouter un clavier au jeu
		//
		jeuGrille.ajouterClavier(configClavier);
		jeuGrille.obtenirClavier().ajouterEcran();

		// Ajouter un panneau d'informations
		//
		jeuGrille.ajouterPanneauInformation(configPanneauInfo);

		// Afficher les regles du jeu
		//
		jeuGrille.obtenirPanneauInformations().ajouterTexteAide(Texte.load("../_Textes/Jeux/Quizz/Regle"));

		// Retirer le panneau central pour la JList
		//
		jeuGrille.retirerPanneauCentral();

		// Retirer les boutons du panneau sud
		//
		jeuGrille.retirerBoutonPanneauSud();

		// Ajouter le chronometre au panneau sud
		//
		chrono = new Chronometre(configChrono);
		jeuGrille.obtenirPanneauSud().add(chrono);
		chrono.demarer();

		// Mise en place d'un ecouteur de niveau facile
		//
		jeuGrille.obtenirBoutonGauche().addMouseListener(new EcouteurChoixNiveau(this));

		// Mise en place d'un ecouteur de niveau moyen
		//
		jeuGrille.obtenirBoutonCentre().addMouseListener(new EcouteurChoixNiveau(this));

		// Mise en place d'un ecouteur de niveau difficile
		//
		jeuGrille.obtenirBoutonDroite().addMouseListener(new EcouteurChoixNiveau(this));

		// Creer le nouveau vecteur pour
		// ajouter la liste de question
		//
		contenuJList = new Vector();

		// Ajouter la JList au panneau support
		//
		listQuestions = new JList();
		jeuGrille.getContentPane().add(listQuestions);

		// Definir la partie statique
		//
		setLotImages();
		setRepertoireQuizz();

		// Indiquer une nouvelle partie
		//
		niveau = 5;
		bonnesReponses = 0;
		movaisesReponses = 0;
		statut = true;

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
		Element configPanneauInfo = (Element) ConfigXML.load("Configuration/ConfigPanneauInfo", "1.0.0");
		Element configPanneauCentral = (Element) ConfigXML.load("Configuration/ConfigPanneauCentral", "1.0.0");
		Element configClavier = (Element) ConfigXML.load("Configuration/ConfigClavier", "1.0.0");
		Element configChrono = (Element) ConfigXML.load("Configuration/ConfigChrono", "1.0.0");

		// Instancier un jeu
		//
		Quizz jeu = new Quizz(configPanneauCentral, configPanneauInfo, configClavier, configChrono);

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

		// Charger une nouvelle question
		//
		jeu.chargerNouvelleQuestion();
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
	 * Obtenir le jeu support
	 * 
	 * @since 1.0.0
	 */
	public JeuxGrille obtenirJeu() {
		return jeuGrille;
	}

	/**
	 * Charger le lot d'images
	 * 
	 * @since 1.0.0
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
	 * setRepertoireQuizz Vecteur contenant les dossiers du quizz
	 * 
	 * @since 1.0.0
	 */
	private void setRepertoireQuizz() {

		// Remplir un vecteur contenant les
		// differents dossier du quizz
		//
		repertoireQuizz = Dossier.chargerRepertoire("../_Textes/Jeux/Quizz/Questions/", ".txt");
	}

	/**
	 * Chargement d'une nouvelle question
	 * 
	 * @since 1.0.0
	 */
	public void chargerNouvelleQuestion() {

		// Verifier qu'il reste des questions a charger
		//
		if (contenuJList.size() == repertoireQuizz.size()) {
			finPartie();
			return;
		}

		// Verifier qu'il reste des questions a charger
		// en fonction du niveau
		//
		if (contenuJList.size() == niveau) {
			finPartie();
			return;
		}

		// Variables concernant les
		// fichiers du dossier
		//
		String nomFichier;
		String cheminNomFichier;

		// Variables de travail
		//
		File file;
		int random;
		boolean condition = false;

		// Boucler tant que l'on a pas
		// de nouvelle question
		//
		do {

			random = (int) (Math.random() * (repertoireQuizz.size()));

			// Recuperer le nom du repertoire courant
			//

			file = new File((String) repertoireQuizz.get(random));
			cheminNomFichier = file.getAbsolutePath().substring(0, file.getAbsolutePath().length() - 4);
			nomFichier = file.getName().substring(0, file.getName().length() - 4);
			System.out.println("\nNom fichier -> " + nomFichier);

			// Verifier la presence de l'image dans
			// le lot d'image que nous avons charge
			//
			if (!presenceImage(nomFichier)) {
				JOptionPane.showMessageDialog(null, Texte.load("../_Textes/Erreurs/Image non presente"));
				return;
			}

			// Verifier que cette question n'a pas
			// deja ete jouee
			//
			if (!contenuJList.contains("Vrai   " + Texte.load(cheminNomFichier) + " Reponse : " + nomFichier)
					&& !contenuJList.contains("   Faux   " + Texte.load(cheminNomFichier) + " Reponse : " + nomFichier)) {

				// Enregistre la question
				//
				this.question = Texte.load(cheminNomFichier);

				// Afficher le nom des deux fichiers
				// dans la console
				//
				System.out.println("\tQuestion : " + this.question);

				// Ajouter la question dans la liste
				//
				contenuJList.add(this.question);

				// Charger un lot d'image comprenant
				// la reponse dans le clavier
				//
				chargerLotImagesClavier(nomFichier);

				// Charger l'indice de la solution
				// dans le panneau d'informations :
				// (voir Abecedaire)
				//
				// jeuGrille.obtenirPanneauInformations().modifierZoneAffichage(Texte.load(cheminDescription));
				file = new File("../_Textes/Accueil/Abecedaire/" + nomFichier + "/Indice.txt");
				if (file.exists())
					jeuGrille.obtenirPanneauInformations().modifierZoneAffichage(
							Texte.load("../_Textes/Accueil/Abecedaire/" + nomFichier + "/Indice"));
				else
					jeuGrille.obtenirPanneauInformations().modifierZoneAffichage(Texte.load("../_Textes/Jeux/Quizz/Pas d'indice"));

				// Enregistrer la solution de la
				// question
				//
				solution = nomFichier;

				condition = true;
				break;
			}

		} while (!condition);
		listQuestions.setListData(contenuJList);
		listQuestions.setSelectedIndex(contenuJList.size() - 1);
		System.out.println("FIN");
	}

	/**
	 * Verifie la presence du nom de l'image passee en parametre dans le lot
	 * d'image chargee
	 * 
	 * @param image
	 * @return flag de reussite
	 * 
	 * @since 1.0.0
	 */
	private boolean presenceImage(String image) {

		// Creer les variables de travail
		//
		File file;
		String nomImage;

		// Parcourir le lot d'images
		//
		Iterator i = lotImages.iterator();
		while (i.hasNext()) {

			file = new File((String) i.next());
			nomImage = file.getName().substring(0, file.getName().length() - 4);

			if (nomImage.equals(image))
				return true;
		}
		return false;
	}

	/**
	 * Charger un lot d'images contenant la solution
	 * 
	 * @param solution
	 * @since 1.0.0
	 */
	private boolean chargerLotImagesClavier(String solution) {

		// Creer les variables de travail
		//
		int random = 0;
		Vector donnees = new Vector();
		File file;
		String elementEnCours;

		// Charger un lot d'images contenant la solution
		//
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
			elementEnCours = file.getName().substring(0, file.getName().length() - 4);

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
		jeuGrille.obtenirClavier().styleClavierImages(donnees);
		jeuGrille.obtenirClavier().bordureBlack();
		jeuGrille.obtenirClavier().ajouterEcouteur(new EcouteurQuizzClavier(this));
		return true;
	}

	/**
	 * Obtenir la solution de la question
	 * 
	 * @return solution
	 * @since 1.0.0
	 */
	public String obtenirSolution() {
		return solution;
	}

	/**
	 * Obtenir question
	 * 
	 * @return question
	 * @since 1.0.0
	 */
	public String obtenirQuestion() {
		return question;
	}

	/**
	 * ObtenirContenuJList
	 * 
	 * @return contenuJList
	 * @since 1.0.0
	 */
	public Vector obtenirContenuJList() {
		return contenuJList;
	}

	/**
	 * ModifierContenuJList
	 * 
	 * @param nouvellePhrase
	 * @since 1.0.0
	 */
	public void modifierContenuJList(String nouvellePhrase) {
		contenuJList.set(contenuJList.size() - 1, nouvellePhrase);
		listQuestions.repaint();
	}

	/**
	 * Modifier nombre de bonnes reponses
	 * 
	 * @since 1.1.0
	 */
	public void ajouterBonneReponse() {
		bonnesReponses++;
	}

	/**
	 * Modifier nombre de movaises reponses
	 * 
	 * @since 1.1.0
	 */
	public void ajouterMovaisesReponse() {
		movaisesReponses++;
	}

	/**
	 * Mettre fin a une partie et affichage du score
	 * 
	 * @since 1.1.0
	 */
	private void finPartie() {

		// Indiquer la fin de la partie
		//
		statut = false;

		// Stop le chronometre
		//
		chrono.stoper();

		// Calculer la note finale
		//
		int nombreReponses = bonnesReponses + movaisesReponses;
		int note = bonnesReponses * 20 / nombreReponses;

		// Afficher le score de la partie
		//
		jeuGrille.obtenirPanneauInformations().bulleBlanche();
		jeuGrille.obtenirPanneauInformations().ajouterTexteAide(Texte.load("../_Textes/Jeux/Quizz/Score") + note + "/20");
	}

	/**
	 * Obtenir le statut de la partie
	 * 
	 * @since 1.1.0
	 */
	public boolean obtenirStatutPartie() {
		return statut;
	}

	/**
	 * Definir le nombre de question a jouer
	 * 
	 * @since 1.3.0
	 */
	public void modifierNiveau(int nombreQuestion) {
		niveau = nombreQuestion;
	}

	/**
	 * Nouvelle partie
	 * 
	 * @since 1.3.0
	 */
	public void nouvellePartie() {

		// Indiquer la fin de la partie
		//
		statut = true;

		// Stop le chronometre
		//
		chrono.initialiser();
		chrono.demarer();

		// Reinitialiser les variables
		//
		bonnesReponses = 0;
		movaisesReponses = 0;
		contenuJList = new Vector();

		// Afficher le score de la partie
		//
		jeuGrille.obtenirPanneauInformations().bulleBlanche();
		jeuGrille.obtenirPanneauInformations().ajouterTexteAide(Texte.load("../_Textes/Jeux/Quizz/Regle"));
	}

}
