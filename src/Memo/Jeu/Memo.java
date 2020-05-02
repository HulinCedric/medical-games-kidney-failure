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
 *          
 * @version 1.2.0 :
 * 
 * 			gestion du temps de recouvrement des images
 * 			selon le niveau de difficulte
 */
import java.util.Vector;
import javax.swing.JFrame;
import org.jdom.Element;

/**
 * Regroupement d'objet necessaire dans une classe pour construire le jeu du
 * Memo
 * 
 * @version 1.2.0
 * @author Charles Fouco, Cedric Hulin
 */
public class Memo {

	/**
	 * jeuGrille
	 * 
	 * @since 1.0.0
	 */
	JeuxGrille jeuGrille;

	/**
	 * Chronometre du jeu
	 * 
	 * @since 1.1.0
	 */
	Chronometre chrono;

	/**
	 * tempsRecouvrement temps de recouvrement des images selon le niveau de
	 * difficulte
	 * 
	 * @since 1.2.0
	 */
	int tempsRecouvrement;

	/**
	 * Constructeur normal
	 * 
	 * @param configGrille
	 * @param configChrono
	 * 
	 * @since 1.0.0
	 */
	public Memo(Element configGrille, Element configChrono) {

		// Creer un nouveau jeu
		//
		jeuGrille = new JeuxGrille(configGrille);
		if (jeuGrille.obtenirGrille() == null)
			System.exit(0);

		// Modifier le titre de la fenetre
		//
		jeuGrille.modifierTitreFenetre("Jeu du Memo");

		// Definir le temps de recouvrement des images
		//
		setTempsRecouvrement(configGrille);

		// Ajouter un ecouteur dedie a la grille pour la gestion du maillage
		//
		jeuGrille.obtenirGrille().ajouterEcouteur(new EcouteurJeuxGrilleMemo(this));

		// Ajouter des ecouteurs de niveau
		//
		jeuGrille.obtenirBoutonGauche().addMouseListener(new EcouteurJeuxGrilleMemoChoixNiveau(this));
		jeuGrille.obtenirBoutonCentre().addMouseListener(new EcouteurJeuxGrilleMemoChoixNiveau(this));
		jeuGrille.obtenirBoutonDroite().addMouseListener(new EcouteurJeuxGrilleMemoChoixNiveau(this));

		// Retirer les boutons du panneau sud
		//
		jeuGrille.retirerBoutonPanneauSud();

		// Ajouter le chronometre au panneau sud
		//
		chrono = new Chronometre(configChrono);
		jeuGrille.obtenirPanneauSud().add(chrono);
		chrono.demarer();

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
		Vector tousLesDossier = Dossier.obtenirVecteur(listeDossier, ".jpg");

		// Charger un lot d'images paires
		//
		jeuGrille.obtenirGrille().chargerLotImagesPaires(tousLesDossier);

		// Fermer la fenetre support lors d'un click sur la
		// croix
		//
		jeuGrille.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// Montrer le cadre support et ses panneaux internes
		//
		jeuGrille.setVisible(true);
	}

	public static void main(String[] args) {

		// Charger les fichier de configuration
		//
		Element configGrille = (Element) ConfigXML.load("Configuration/ConfigMemoGrilleMoyen", "1.0.0");
		Element configChrono = (Element) ConfigXML.load("Configuration/ConfigMemoChrono", "1.0.0");

		// Creer un nouveau jeu
		//
		Memo jeu = new Memo(configGrille, configChrono);

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
	 * Obtenir le chronometre du jeu
	 * 
	 * @since 1.1.0
	 */
	public Chronometre obtenirChronometre() {
		return chrono;
	}

	/**
	 * Obtenir le jeu grille support
	 * 
	 * @since 1.1.0
	 */
	public JeuxGrille obtenirJeuxGrille() {
		return jeuGrille;
	}

	/**
	 * setTempsRecouvrement
	 * 
	 * @since 1.2.0
	 */
	public void setTempsRecouvrement(Element config) {
		int temps;

		// Verifier le parametre
		//
		if (config == null) {
			return;
		}

		// Verifier la presence de la cle
		//
		if (config.getChild("TempsRecouvrement") == null) {
			return;
		}

		// Recuperer la valeur associee
		//
		temps = Integer.valueOf(config.getChild("TempsRecouvrement").getTextTrim()).intValue();

		// Verifier la valeur recepurer
		//
		if (temps <= 0)
			return;

		// Affecter la nouvelle couleur
		//
		tempsRecouvrement = temps;
	}

	/**
	 * Obtenir temps recouvrement
	 * 
	 * @since 1.2.0
	 */
	public int obtenirTempsRecouvrement() {
		return tempsRecouvrement;
	}
}
