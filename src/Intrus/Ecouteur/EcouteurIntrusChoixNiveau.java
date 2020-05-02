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
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.Iterator;
import java.util.Vector;

/**
 * Ecouteur souris permettant de charger dynamiquement des
 * grilles de difficulte differente et choisi aleatoirement.
 * 
 * @author Cedric Hulin
 * @version 1.0.0
 */
public class EcouteurIntrusChoixNiveau extends EcouteurChoixNiveau {

	/**
	 * Constante sur les legumes
	 * 
	 * @since 1.0.0
	 */
	private final int Legumes = 0;

	/**
	 * Constante sur les fruits
	 * 
	 * @since 1.0.0
	 */
	private final int Fruits = 6;

	/**
	 * Constante sur les Feculents
	 * 
	 * @since 1.0.0
	 */
	private final int Feculents = 1;

	/**
	 * Constante sur les Matieres Grasses
	 * 
	 * @since 1.0.0
	 */
	private final int MatieresGrasses = 2;

	/**
	 * Constante sur les Produits Laitiers
	 * 
	 * @since 1.0.0
	 */
	private final int ProduitsLaitiers = 3;

	/**
	 * Constante sur les Produits Sucres
	 * 
	 * @since 1.0.0
	 */
	private final int ProduitsSucres = 4;

	/**
	 * Constante sur les Viandes Poissons Oeufs
	 * 
	 * @since 1.0.0
	 */
	private final int ViandePoissonOeuf = 5;

	/**
	 * Constructeur normal
	 * 
	 * @param hamecon
	 * @since 1.0.0
	 */
	public EcouteurIntrusChoixNiveau(Object hamecon) {
		super(hamecon);
	}

	/**
	 * Implementation de l'evenement mouseClicked
	 * 
	 * @since 1.0.0
	 */
	public void mouseClicked(MouseEvent e) {
		super.mouseClicked(e);

		if (obtenirNomFichier() != null && obtenirDifficulte() != null) {

			// Augmenter le nombre de partie jouee
			//
			Intrus.nouvellePartie();
			obtenirHamecon().obtenirPanneauInformations().bulleBlanche();
			obtenirHamecon().obtenirPanneauInformations().ajouterTexteAide(Texte.load("../_Textes/Jeux/Intrus/Regle"));
			obtenirHamecon().obtenirPanneauInformations().ajouterImageSud("../_Images/Pyramide/3D/Pyramide.jpg");
			obtenirHamecon().obtenirPanneauInformations().modifierZoneAffichage(null);

			// Mettre les case en blanc
			//
			obtenirHamecon().obtenirGrille().modifierCouleurCellules(Color.white);

			// Enlever toute bordure de grille
			//
			obtenirHamecon().obtenirGrille().modifierBordure(null);

			// Determiner le nombre d'image selon la
			// difficulte
			//
			int nombreDonnees = obtenirDifficulte().equals("Facile") ? 4 : obtenirDifficulte().equals("Moyen") ? 9 : obtenirDifficulte().equals(
					"Difficile") ? 14 : 0;

			// Determiner aleatoirement la categorie des
			// aliments majoritaire
			//
			String cheminNonIntrus = obtenirCheminAleatoire();

			// Determiner aleatoirement la categories de
			// l'aliment intrus
			//
			String cheminIntrus = cheminNonIntrus;
			while (cheminNonIntrus.equals(cheminIntrus))
				cheminIntrus = obtenirCheminAleatoire();

			// Obtenir les vecteur de jeu
			//
			Vector nonIntrus = Dossier.obtenirVecteur(cheminNonIntrus, nombreDonnees, ".jpg");
			Vector intrus = Dossier.obtenirVecteur(cheminIntrus, 1, ".jpg");
			if (nonIntrus.size() <= 0)
				System.exit(0);

			// Specifier l'intru au jeu
			//
			File f = new File((String) intrus.get(0));
			Intrus.modificationIntrus(f.getName().substring(0, f.getName().length() - 4));

			// Obtenir le vecteur de donner du jeu
			//       
			Vector donneesAleatoire = new Vector();
			donneesAleatoire.addAll(nonIntrus);
			Dossier.ajouter(donneesAleatoire, intrus, 1);
			Dossier.melanger(donneesAleatoire);

			// Ajouter les nom des aliments dans le panneau
			// d'information.
			//
			Iterator k = donneesAleatoire.iterator();
			obtenirHamecon().obtenirPanneauInformations().modifierZoneAffichage("");
			while (k.hasNext()) {
				File fichier = new File((String) k.next());
				String nonAliment = fichier.getName().substring(0, fichier.getName().length() - 4);
				obtenirHamecon().obtenirPanneauInformations().ajouterTexteEnQueue(nonAliment);
			}

			// Remplir la grille selon la difficulte
			//
			remplirGrille(donneesAleatoire);

			// Mettre a niveau l'ecouteur apres traitement
			//
			modifierNomFichier();
		}
	}

	/**
	 * Obtention d'un chemin choisi aleatoirement.
	 * 
	 * @return chemin aleatoire.
	 * @since 1.0.0
	 */
	private String obtenirCheminAleatoire() {
		int random = (int) (Math.random() * (7));
		String chemin = "";

		// Recuperer un chemin.
		//
		switch (random) {
		case Legumes:
			chemin = "../_Images/Legumes/";
			break;
		case Fruits:
			chemin = "../_Images/Fruits/";
			break;
		case Feculents:
			chemin = "../_Images/Feculents/";
			break;
		case MatieresGrasses:
			chemin = "../_Images/Matieres Grasses/";
			break;
		case ProduitsLaitiers:
			chemin = "../_Images/Produits Laitiers/";
			break;
		case ProduitsSucres:
			chemin = "../_Images/Produits Sucres/";
			break;
		case ViandePoissonOeuf:
			chemin = "../_Images/Viande Poisson Oeuf/";
			break;
		}

		// Restituer le resultat.
		//
		return chemin;
	}

	/**
	 * Remplie la grille selon la difficulte choisi.
	 * 
	 * @param donneesAleatoire
	 * @since 1.0.0
	 */
	private void remplirGrille(Vector donneesAleatoire) {

		// Placer les aliments selon le niveau de
		// difficulte.
		//
		switch (obtenirDifficulte().charAt(0)) {
		case 'F':
			for (int i = 1; i <= obtenirHamecon().obtenirGrille().obtenirNbColonnes(); i++)
				obtenirHamecon().obtenirGrille().ajouterImage(3, i, (String) donneesAleatoire.get(i - 1));
			break;
		case 'M':
			for (int i = 1; i <= obtenirHamecon().obtenirGrille().obtenirNbColonnes(); i++)
				obtenirHamecon().obtenirGrille().ajouterImage(2, i, (String) donneesAleatoire.get(i - 1));
			for (int i = 1; i <= obtenirHamecon().obtenirGrille().obtenirNbColonnes(); i++)
				obtenirHamecon().obtenirGrille().ajouterImage(4, i, (String) donneesAleatoire.get(i - 1 + 5));
			break;
		case 'D':
			for (int i = 1; i <= obtenirHamecon().obtenirGrille().obtenirNbColonnes(); i++)
				obtenirHamecon().obtenirGrille().ajouterImage(1, i, (String) donneesAleatoire.get(i - 1));
			for (int i = 1; i <= obtenirHamecon().obtenirGrille().obtenirNbColonnes(); i++)
				obtenirHamecon().obtenirGrille().ajouterImage(3, i, (String) donneesAleatoire.get(i - 1 + 5));
			for (int i = 1; i <= obtenirHamecon().obtenirGrille().obtenirNbColonnes(); i++)
				obtenirHamecon().obtenirGrille().ajouterImage(5, i, (String) donneesAleatoire.get(i - 1 + 10));
			break;
		default:
			break;
		}
	}
}