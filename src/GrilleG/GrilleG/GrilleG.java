/**
 * IUT de Nice / Departement informatique / Stage Archet
 * Annee 2009_2010 - Composant generique
 * 
 * @edition A : Cours_10
 * 
 * @version 1.0.0 :
 * 
 *          chaque cellule supporte les homotheties de
 *          taille du cadre support, avec une repartition
 *          (ligne, colonne) constante
 * 
 * @version 1.1.0 :
 * 
 *          ajout de la methode permuterImages
 * 
 * @version 1.2.0 :
 * 
 *          ajout des methodes suivantes : +
 *          obtenirPositionPCL, + obtenirPositionCLS, +
 *          obtenirPositionPCO, + obtenirPositionCOS
 * 
 * @version 1.3.0 :
 * 
 *          ajout de la methode rassemblerImages
 * 
 * @edition B : introduction d'un ecouteur de souris dans la
 *          mosaique
 * 
 * @version 2.0.0 :
 * 
 *          ecouteur par defaut du panneau principal (classe
 *          EcouteurPanneauG - V 1.0.0)
 * 
 * @version 2.1.0 :
 * 
 *          l'ecouteur designe la position de la cellule
 *          cible : ecouteur dedie (classe EcouteurMosaiqueG
 *          - V 1.0.0) + ajout de la methode
 *          obtenirPositionCelluleCible
 * 
 * @version 2.2.0 :
 * 
 *          l'ecouteur designe la position relative du clic
 *          dans la cellule cible (classe EcouteurMosaiqueG
 *          - V 1.1.0) + ajout de la methode
 *          obtenirPositionRelativeCible
 * 
 * @version 2.3.0 :
 * 
 *          introduction d'un maillage rectangulaire,
 *          regulier, uniforme et couvrant (option) de toute
 *          la mosaique, reactif aux actions operateur
 *          (option)
 * 
 * @edition C : introduction au grille de jeux generique
 * 
 * @version 3.0.0
 * 
 *          ajout d'un dictionnaire contenant les images ;
 *          ajout d'une methode de chargement d'images via
 *          un repertoire cible ; ajout d'une methode de
 *          chargement de donnees via un fichier data ;
 *          ajout d'une methode de remplissage de grille
 *          avec des images et caractere
 * 
 * @version 3.1.0
 * 
 *          mise a disposition de styles predefinis selon le
 *          type de jeu de grille, styleSudoku,
 *          styleNumeroter et calibrerGrille, bordureRelief,
 *          bordureBlack
 * 
 * @version 3.2.0
 * 
 *          ajout de methodes de remplissage des cellules
 *          vide : pas d'image ni de texte ; chargerLotImage
 *          : affichage d'un message d'erreur lors d'un
 *          chargement d'un repertoire inexistant ; ajout
 *          d'une methode permettant l'ajout de texte
 * 
 * @version 3.3.0
 * 
 *          ajout de deux accesseur de consultation sur les
 *          donnees et le style ; ajout d'un accesseur de
 *          consultation generant la configuration de la
 *          grille ; ajout accesseur de modification prive
 *          pour le maillage
 * 
 * @version 3.4.0
 * 
 *          surcharge de la methode paint permettant de
 *          dessiner de multiple trait sur la grille ; ajout
 *          d'un accesseur de consultation permettant
 *          l'enregistrement du contenu d'une grille ; mise
 *          a jour de l'accesseur obtenirProprietes
 * 
 * @version 3.5.0
 * 
 *          ajout de la methode chargerLotImagesPaires ;
 *          ajout de la methode obtenirCellule
 * 
 * @version 3.6.0
 * 
 *          ajout de la methode modifierBordue ajout d'un
 *          vecteur solution ainsi que ses methodes
 * 
 * @edition B : gestion de fichier XML
 * 
 * @version 4.0.0
 * 
 *          Gestion du fichier de configuration XML
 *          
 * @version 4.1.0
 * 
 *          setForeground, setFont
 *          ajouterImage pour plusieurs images dans une cellule
 *          ajouterImage pour toutes les cellules
 */
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.LayoutManager;
import java.awt.event.MouseListener;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.border.Border;

import org.jdom.Document;
import org.jdom.Element;

/**
 * Matrice de cellules (panneaux) generiques
 * 
 * @author Alain Thuaire, Charles Fouco, Cedric Hulin
 * @version 4.1.0
 */
public class GrilleG extends PanneauG {

	/**
	 * Hamecon de l'objet graphique support.
	 * 
	 * @since 1.0.0
	 */
	private Object cadreSupport;

	/**
	 * Dictionnaire de configuration de la grille.
	 * 
	 * @since 1.0.0
	 */
	private Object config;

	/**
	 * Gestionnaire de placement des cellules.
	 * 
	 * @since 1.0.0
	 */
	private LayoutManager gestPlacement;

	/**
	 * Nombre de cellules en verticale.
	 * 
	 * @since 1.0.0
	 */
	private int nombreL;

	/**
	 * Nombre de cellules en horizontale.
	 * 
	 * @since 1.0.0
	 */
	private int nombreC;

	/**
	 * Matrice des cellules.
	 * 
	 * @since 1.0.0
	 */
	private CelluleG[][] matriceCellules;

	/**
	 * Vecteur de donnees.
	 * 
	 * @since 3.0.0
	 */
	private Vector donnees;

	/**
	 * Defini le style numeroter.
	 * 
	 * @since 3.1.0
	 */
	private boolean styleNum;

	/**
	 * Defini le style sudoku.
	 * 
	 * @since 3.1.0
	 */
	private boolean styleSudoku;

	/**
	 * Defini le maillage.
	 * 
	 * @since 3.3.0
	 */
	private boolean presenceMaillage;

	/**
	 * Vecteur de donnees representant une solution de jeu.
	 * 
	 * @since 3.6.0
	 */
	private Vector solution;

	/**
	 * Font utilisee dans la grille.
	 * 
	 * @since 4.0.0
	 */
	private Font font;

	/**
	 * Couleur de la police.
	 * 
	 * @since 4.1.0
	 */
	private Color couleurPolice;

	/**
	 * Constructeur normal.
	 * 
	 * @param hamecon
	 *            support.
	 * @param configuration
	 *            de la grille.
	 * @since 2.3.0
	 */
	public GrilleG(Object hamecon, Object configuration) {
		super(hamecon, configuration);

		// Controler la validite des parametres.
		//
		if (hamecon == null)
			return;
		if (configuration == null)
			return;

		// Memoriser les attributs transmis par parametre
		//
		this.cadreSupport = hamecon;
		this.config = configuration;

		// Definir la partie statique
		//
		if (configuration.getClass().getSimpleName().equals("Element")) {
			setNbLignes((Element) configuration);
			setNbColonnes((Element) configuration);
			setMaillage((Element) configuration);
			setStyle((Element) configuration);
			setFont((Element) configuration);
			setForeground((Element) configuration);
		} else if (configuration.getClass().getSimpleName().equals("HashMap")) {
			setNbLignes((HashMap) configuration);
			setNbColonnes((HashMap) configuration);
			setMaillage((HashMap) configuration);
			setStyle((HashMap) configuration);
		}

		// Installer le gestionnaire de placement du panneau
		// principal
		//
		this.gestPlacement = new GridLayout(nombreL, nombreC);
		setLayout(gestPlacement);

		// Calibrage de la grille
		//
		calibrerGrille(nombreL, nombreC);

		// Appliquer le style de la grille
		//
		if (styleNum) {
			styleNum = false;
			styleNumeroter();
		}
		if (styleSudoku) {
			styleSudoku = false;
			styleSudoku();
		}
	}

	/**
	 * setNbLignes
	 * 
	 * @param configuration
	 *            de la grille.
	 * @since 1.0.0
	 */
	private void setNbLignes(HashMap configuration) {
		int defaultValue = 9;
		int value = defaultValue;
		Object w;

		if (configuration == null) {
			nombreL = defaultValue;
			return;
		}
		w = configuration.get("Lignes");
		if (w != null)
			value = ((Integer) w).intValue();
		nombreL = value;
	}

	/**
	 * setNbLignes
	 * 
	 * @param configuration
	 *            de la grille.
	 * @since 4.0.0
	 */
	private void setNbLignes(Element configuration) {
		int defaultValue = 9;
		int value = defaultValue;

		if (configuration == null) {
			nombreL = defaultValue;
			return;
		}

		if (configuration.getChild("Lignes") == null) {
			nombreL = defaultValue;
			return;
		}
		value = Integer.valueOf(configuration.getChild("Lignes").getTextTrim()).intValue();

		nombreL = value;
	}

	/**
	 * setNbColonnes
	 * 
	 * @param configuration
	 *            de la grille.
	 * @since 1.0.0
	 */
	private void setNbColonnes(HashMap configuration) {
		int defaultValue = 9;
		int value = defaultValue;
		Object w;

		if (configuration == null) {
			nombreC = defaultValue;
			return;
		}
		w = configuration.get("Colonnes");
		if (w != null)
			value = ((Integer) w).intValue();
		nombreC = value;
	}

	/**
	 * setNbColonnes
	 * 
	 * @param configuration
	 *            de la grille.
	 * @since 4.0.0
	 */
	private void setNbColonnes(Element configuration) {
		int defaultValue = 9;
		int value = defaultValue;

		if (configuration == null) {
			nombreC = defaultValue;
			return;
		}

		if (configuration.getChild("Colonnes") == null) {
			nombreC = defaultValue;
			return;
		}
		value = Integer.valueOf(configuration.getChild("Colonnes").getTextTrim()).intValue();

		nombreC = value;
	}

	/**
	 * setStyle
	 * 
	 * @param configuration
	 *            de la grille.
	 * @since 3.1.0
	 */
	private void setStyle(HashMap configuration) {
		boolean defaultValueSudoku = false;
		boolean defaultValueNumeroter = false;
		boolean valueSudoku = defaultValueSudoku;
		boolean valueNumeroter = defaultValueNumeroter;
		Object w;

		if (configuration == null) {
			styleSudoku = defaultValueSudoku;
			styleNum = defaultValueNumeroter;
			return;
		}
		w = configuration.get("Style");
		if (w != null) {
			valueSudoku = ((String) w).equals("Sudoku") ? true : defaultValueSudoku;
			valueNumeroter = ((String) w).equals("Numeroter") ? true : defaultValueNumeroter;
			if (valueSudoku && valueNumeroter) {
				valueSudoku = defaultValueSudoku;
				valueNumeroter = defaultValueNumeroter;
			}
		}
		styleSudoku = valueSudoku;
		styleNum = valueNumeroter;
	}

	/**
	 * setStyle
	 * 
	 * @param configuration
	 *            de la grille.
	 * @since 4.0.0
	 */
	private void setStyle(Element configuration) {
		boolean defaultValueSudoku = false;
		boolean defaultValueNumeroter = false;
		boolean valueSudoku = defaultValueSudoku;
		boolean valueNumeroter = defaultValueNumeroter;
		Object w;

		if (configuration == null) {
			styleSudoku = defaultValueSudoku;
			styleNum = defaultValueNumeroter;
			return;
		}

		if (configuration.getChild("Style") == null) {
			styleSudoku = defaultValueSudoku;
			styleNum = defaultValueNumeroter;
			return;
		}

		w = configuration.getChild("Style").getTextTrim();
		if (w != null) {
			valueSudoku = ((String) w).equals("Sudoku") ? true : defaultValueSudoku;
			valueNumeroter = ((String) w).equals("Numeroter") ? true : defaultValueNumeroter;
			if (valueSudoku && valueNumeroter) {
				valueSudoku = defaultValueSudoku;
				valueNumeroter = defaultValueNumeroter;
			}
		}
		styleSudoku = valueSudoku;
		styleNum = valueNumeroter;
	}

	/**
	 * setMaillage
	 * 
	 * @param configuration
	 *            de la grille.
	 * @since 3.3.0
	 */
	private void setMaillage(HashMap configuration) {
		boolean defaultValue = false;
		boolean value = defaultValue;
		Object w;

		if (configuration == null) {
			presenceMaillage = defaultValue;
			return;
		}
		w = configuration.get("Maillage");
		if (w != null)
			value = ((Boolean) w).booleanValue();

		presenceMaillage = value;

		if (presenceMaillage)
			ajouterMaillage(Color.YELLOW, Color.BLACK, nombreL, nombreC);
	}

	/**
	 * setMaillage
	 * 
	 * @param configuration
	 *            de la grille.
	 * @since 4.0.0
	 */
	private void setMaillage(Element configuration) {
		boolean defaultValue = false;
		boolean value = defaultValue;
		String w;

		if (configuration == null) {
			presenceMaillage = defaultValue;
			return;
		}

		if (configuration.getChild("Maillage") == null) {
			presenceMaillage = defaultValue;
			return;
		}

		w = configuration.getChild("Maillage").getTextTrim();
		value = Boolean.valueOf(w).booleanValue();

		presenceMaillage = value;

		if (presenceMaillage)
			ajouterMaillage(Color.YELLOW, Color.BLACK, nombreL, nombreC);
	}

	/**
	 * setFont
	 * 
	 * @param configuration
	 *            de la grille.
	 * @since 4.1.0
	 */
	private void setFont(Element configuration) {
		List noeudFils = null;
		Element elementCourant;

		// Verifier le parametre
		//
		if (configuration == null) {
			return;
		}

		// Verifier la presence de la cle
		//
		if (configuration.getChild("Police") == null) {
			return;
		}

		// Recuperer le noeud fils
		//
		noeudFils = configuration.getChildren("Police");
		if (noeudFils == null) {
			return;
		}
		elementCourant = (Element) noeudFils.iterator().next();

		// Verifier la presence des elements
		//
		if (elementCourant.getChild("Famille") == null || elementCourant.getChild("Style") == null || elementCourant.getChild("Taille") == null)
			return;

		// Recuperer les valeurs associees
		//
		String Famille = elementCourant.getChild("Famille").getTextTrim();
		String Style = elementCourant.getChild("Style").getTextTrim();
		String Taille = elementCourant.getChild("Taille").getTextTrim();

		// Creer la nouvelle font
		//
		Font f = new Font(Famille, XML.obtenirStyleFont(Style), (int) Integer.valueOf(Taille).intValue());
		if (f == null)
			return;

		// Affecter la nouvelle font
		//
		font = f;
	}

	/**
	 * setForeground
	 * 
	 * @param configuration
	 *            de la grille.
	 * @since 4.1.0
	 */
	private void setForeground(Element configuration) {
		String StringCouleur;

		// Verifier le parametre
		//
		if (configuration == null) {
			setForeground(Color.YELLOW);
			return;
		}

		// Verifier la presence de la cle
		//
		if (configuration.getChild("AvantPlan") == null) {
			setForeground(Color.YELLOW);
			return;
		}

		// Recuperer la valeur associee
		//
		StringCouleur = configuration.getChild("AvantPlan").getTextTrim();

		// Affecter la nouvelle couleur
		//
		couleurPolice = XML.obtenirColorFont(StringCouleur);
	}

	/**
	 * Main
	 * 
	 * @param arguments
	 *            du programme.
	 * @since 3.0.0
	 */
	public static void main(String[] arguments) {

		// Creer un cadre support
		//      
		JFrame fenetre = new JFrame();
		fenetre.setTitle("Classe GrilleG - V 3.3.0");
		fenetre.setSize(600, 600);
		fenetre.setLocation(350, 50);
		fenetre.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// Charger la configuration de la grille
		//      
		Element configuration = (Element) ConfigXML.load("Configuration/ConfigGrille", "1.0.0");
		if (configuration == null)
			return;

		// Creer une grille
		//
		GrilleG grille = new GrilleG(fenetre, configuration);

		// Modifier la bordure
		//
		grille.modifierBordure(null);

		// Ajout d'images
		//
		grille.chargerLotImages("../_Images/Legumes");

		// Ajouter la grille au cadre support
		//
		fenetre.getContentPane().add(grille);

		// Montrer le cadre support et sa grille interne
		//
		fenetre.setVisible(true);
	}

	/**
	 * Surcharge de la methode paint permettant l'affichage de trait.
	 * 
	 * @param graphique
	 *            support.
	 * @since 3.4.0
	 */
	public void paint(Graphics graphique) {
		super.paint(graphique);

		// Peint les traits
		//
		MouseListener[] m = getMouseListeners();
		for (int i = 0; i < m.length; i++) {
			if (m[i].getClass().getSimpleName().equals("EcouteurGrilleDrawLine"))
				((EcouteurGrilleDrawLine) m[i]).dessiner(graphique);
			if (m[i].getClass().getSuperclass().getSimpleName().equals("EcouteurGrilleDrawLine"))
				((EcouteurGrilleDrawLine) m[i]).dessiner(graphique);
		}
	}

	/**
	 * Obtention du nombre de lignes de la grille.
	 * 
	 * @return nombre de lignes.
	 * @since 1.0.0
	 */
	public int obtenirNbLignes() {
		return nombreL;
	}

	/**
	 * Obtention du nombre de colonnes dela grille.
	 * 
	 * @return nombre de colonnes.
	 * @since 1.0.0
	 */
	public int obtenirNbColonnes() {
		return nombreC;
	}

	/**
	 * Obtention du nombre de cellules de la grille.
	 * 
	 * @return nombre de cellules.
	 * @since 1.0.0
	 */
	public int obtenirNbCellules() {
		return nombreL * nombreC;
	}

	/**
	 * Obtention du nombre d'images dans la grille.
	 * 
	 * @return nombre de d'images.
	 * @since 1.0.0
	 */
	public int obtenirNbImages() {
		CelluleG cellule;
		int nbImages = 0;

		// Initialiser les indexes de parcours.
		//
		int i = 1;
		int j = 1;
		if (styleNum) {
			i = 2;
			j = 2;
		}

		// Parcourir l'ensemble des cellules
		//
		for (; i <= obtenirNbLignes(); i++) {
			j = styleNum ? 2 : 1;
			for (; j <= obtenirNbColonnes(); j++) {

				// Obtenir la cellule courante
				//
				cellule = obtenirCellule(i, j);

				// Determiner la presence d'une image
				//
				if (cellule.presenceImage())
					nbImages++;
			}
		}

		// Restituer le resultat
		//
		return nbImages;
	}

	/**
	 * Obtention des donnees de la grille.
	 * 
	 * @return donnees de la grille.
	 * @since 3.3.0
	 */
	public Vector obtenirDonnees() {
		return donnees;
	}

	/**
	 * Obtention du style de la grille.
	 * 
	 * @return style de la grille.
	 * @since 3.3.0
	 */
	public String obtenirStyle() {
		String style = "Defaut";

		// Verifier le style de la grille.
		//
		if (styleNum)
			style = "Numeroter";
		if (styleSudoku)
			style = "Sudoku";

		// Restituer le resultat.
		//
		return style;
	}

	/**
	 * Obtention du contenu de la grille.
	 * 
	 * @return contenu grille.
	 * @since 3.4.0
	 */
	public Vector obtenirContenu() {
		Vector contenu = new Vector();
		CelluleG cellule;

		// Initialiser les indexes de parcours.
		//
		int i = 1;
		int j = 1;
		if (styleNum) {
			i = 2;
			j = 2;
		}

		// Parcourir l'ensemble des cellules.
		//
		for (; i <= obtenirNbLignes(); i++) {
			j = styleNum ? 2 : 1;
			for (; j <= obtenirNbColonnes(); j++) {

				// Obtenir la cellule courante.
				//
				cellule = obtenirCellule(i, j);

				// Verifier la presence d'une image.
				//
				if (cellule.presenceImage())
					contenu.add(cellule.obtenirCheminImage());

				// Verifier la presence d'un titre.
				//
				else if (cellule.presenceTitre())
					contenu.add(cellule.obtenirTexteTitre());

				// Assigner une valeur par defaut.
				//
				else
					contenu.add("@");
			}
		}

		// Restituer le resultat.
		//
		return contenu;
	}

	/**
	 * Obtention de la cellule cible.
	 * 
	 * @param ligne
	 *            de la cellule.
	 * @param cellule
	 *            de la cellule.
	 * @return cellule cible.
	 * @since 3.5.0
	 */
	public CelluleG obtenirCellule(int abscisse, int ordonnee) {

		// Verification des parametres.
		//
		if (abscisse > obtenirNbLignes() || ordonnee > obtenirNbColonnes()) {
			JOptionPane.showMessageDialog(null, Texte.load("../_Textes/Erreurs/Coordonnees"));
			return null;
		}

		// Gestion du style de grille.
		//
		if (styleNum) {
			abscisse++;
			ordonnee++;
		}

		// Verifier les indexes.
		//
		if (abscisse > obtenirNbLignes())
			abscisse--;
		if (ordonnee > obtenirNbColonnes())
			ordonnee--;

		// Restituer le resultat.
		//
		return matriceCellules[abscisse - 1][ordonnee - 1];
	}

	/**
	 * Obtention de la matrice de cellules.
	 * 
	 * @return ensemble des cellules.
	 * @since 3.3.0
	 */
	public CelluleG[][] obtenirMatriceCellules() {
		return matriceCellules;
	}

	/**
	 * Obtention de la configuration de la grille.
	 * 
	 * @return proprietes de la grille.
	 * @since 3.4.0
	 */
	public HashMap obtenirProprietes() {
		HashMap config = new HashMap();

		// Recuperer la totalite des proprietes de la grille.
		//
		config.put("arrierePlan", this.getBackground());
		config.put("avantPlan", this.getForeground());
		config.put("police", this.getFont());
		config.put("Donnees", this.obtenirDonnees());
		config.put("Style", this.obtenirStyle());
		config.put("Maillage", Boolean.valueOf(presenceMaillage()));
		config.put("Solution", this.obtenirSolution());
		config.put("Donnees", obtenirContenu());
		if (styleNum) {
			config.put("Lignes", Integer.valueOf(obtenirNbLignes() - 1));
			config.put("Colonnes", Integer.valueOf(this.obtenirNbColonnes() - 1));
		} else {
			config.put("Lignes", Integer.valueOf(obtenirNbLignes()));
			config.put("Colonnes", Integer.valueOf(this.obtenirNbColonnes()));
		}

		// Restituer le resultat.
		//
		return config;
	}

	/**
	 * Obtention de la configuration de la grille.
	 * 
	 * @return proprietes de la grille.
	 * @since 4.0.0
	 */
	public Document obtenirProprietesXML() {
		Element racine = new Element("Configuration");
		Document document = new Document(racine);
		Element police = new Element("Police");

		// Renseigner l'avant et l'arriere plan.
		//
		ajoutElement(racine, "ArrierePlan", XML.obtenirColorFont(this.getBackground()));
		ajoutElement(racine, "AvantPlan", XML.obtenirColorFont(this.getForeground()));

		// Renseigner le nombre de lignes et de colonnes.
		//
		if (styleNum) {
			ajoutElement(racine, "Lignes", String.valueOf(obtenirNbLignes() - 1));
			ajoutElement(racine, "Colonnes", String.valueOf(this.obtenirNbColonnes() - 1));
		} else {
			ajoutElement(racine, "Lignes", String.valueOf(obtenirNbLignes()));
			ajoutElement(racine, "Colonnes", String.valueOf(this.obtenirNbColonnes()));
		}

		// Renseigner le style et le maillage.
		//
		ajoutElement(racine, "Maillage", String.valueOf(presenceMaillage()));
		ajoutElement(racine, "Style", obtenirStyle());

		// Renseigner la police de la grille.
		//
		ajoutElement(police, "Famille", this.getFont().getName());
		ajoutElement(police, "Style", XML.obtenirStyleFont(this.getFont()));
		ajoutElement(police, "Taille", String.valueOf(this.getFont().getSize()));
		racine.addContent(police);

		// Restituer le resultat.
		//
		return document;
	}

	/**
	 * Ajoute un element a une configuration XML.
	 * 
	 * @param titre
	 *            de l'element auquel ajouter.
	 * @param element
	 *            a ajouter.
	 * @param valeur
	 *            de l'element.
	 * @since 4.0.0
	 */
	private static void ajoutElement(Element titre, String element, String valeur) {
		Element elementAjoute = new Element(element);
		elementAjoute.setText(valeur);
		titre.addContent(elementAjoute);
	}

	/**
	 * Colorer toutes les cellules vide de la grille.
	 * 
	 * @param couleur
	 *            de remplissage.
	 * @return flag de reussite.
	 * @since 3.2.0
	 */
	public boolean modifierCouleurCellules(Color couleur) {
		CelluleG cellule;

		// Verifier le parametre.
		//
		if (couleur == null)
			return false;

		// Initialiser les indexes de parcours.
		//
		int i = 1;
		int j = 1;
		if (styleNum) {
			i = 2;
			j = 2;
		}

		// Parcours de l'ensemble des cellules.
		//
		for (; i <= obtenirNbLignes(); i++) {
			j = styleNum ? 2 : 1;
			for (; j <= obtenirNbColonnes(); j++) {

				// Obtenir la cellule courante.
				//
				cellule = obtenirCellule(i, j);

				// Verifier la presence d'un contenu.
				//
				if (!cellule.presenceTitre() && !cellule.presenceImage())
					modifierCouleurCellule(i, j, couleur);
			}
		}

		// Blanchir la cellule nord-ouest.
		//
		if (styleNum)
			modifierCouleurCellule(1, 1, Color.white);

		// Restituer le flag de reussite.
		//
		return true;
	}

	/**
	 * Griser une cellule.
	 * 
	 * @param ligne
	 *            de la cellule.
	 * @param colonne
	 *            de la cellule.
	 * @param couleur
	 *            a appliquer.
	 * @return flag de reussite.
	 * @since 3.2.0
	 */
	public boolean modifierCouleurCellule(int ligne, int colonne, Color couleur) {

		// Verifier la validite des parametres
		//
		if (ligne < 1 || ligne > obtenirNbLignes())
			return false;
		if (colonne < 1 || colonne > obtenirNbColonnes())
			return false;
		if (couleur == null)
			return false;

		// Recuperer la cellule cible.
		//
		CelluleG cellule = obtenirCellule(ligne, colonne);
		if (cellule == null)
			return false;

		// Colorer la cellule cible.
		//
		cellule.setBackground(couleur);

		// Restituer le flag de reussite.
		//
		return true;
	}

	/**
	 * Ajout d'une image a une cellule cible.
	 * 
	 * @param ligne
	 *            de la cellule.
	 * @param colonne
	 *            de la cellule.
	 * @param chemin
	 *            de l'image.
	 * @return flag de reussite.
	 * @since 1.0.0
	 */
	public boolean ajouterImage(int ligne, int colonne, String chemin) {

		// Verifier la validite des parametres
		//
		if (ligne < 1 || ligne > obtenirNbLignes())
			return false;
		if (colonne < 1 || colonne > obtenirNbColonnes())
			return false;
		if (chemin == null)
			return false;

		// Recuperer la cellule cible.
		//
		CelluleG cellule = obtenirCellule(ligne, colonne);
		if (cellule == null)
			return false;

		// Ajouter l'image a la cellule cible.
		//
		cellule.ajouterImage(chemin);

		// Restituer le flag de reussite.
		//
		return true;
	}

	/**
	 * Retrait d'une image a une cellule cible.
	 * 
	 * @param ligne
	 *            de la cellule.
	 * @param colonne
	 *            de la cellule.
	 * @return flag de reussite.
	 * @since 1.0.0
	 */
	public boolean retirerImage(int ligne, int colonne) {

		// Verifier la validite des parametres
		//
		if (ligne < 1 || ligne > obtenirNbLignes())
			return false;
		if (colonne < 1 || colonne > obtenirNbColonnes())
			return false;

		// Recuperer la cellule cible.
		//
		CelluleG cellule = obtenirCellule(ligne, colonne);
		if (cellule == null)
			return false;

		// Retirer l'image de la cellule cible.
		//
		cellule.retirerImage();

		// Restituer le flag de reussite.
		//
		return true;
	}

	/**
	 * Ajout d'un texte a une cellule cible.
	 * 
	 * @param ligne
	 *            de la cellule.
	 * @param colonne
	 *            de la cellule.
	 * @param texte
	 *            a inserer.
	 * @return flag de reussite.
	 * @since 3.2.0
	 */
	public boolean ajouterTexte(int ligne, int colonne, String texte) {

		// Verifier la validite des parametres
		//
		if (ligne < 1 || ligne > obtenirNbLignes())
			return false;
		if (colonne < 1 || colonne > obtenirNbColonnes())
			return false;
		if (texte == null)
			return false;

		// Recuperer la cellule cible.
		//
		CelluleG cellule = obtenirCellule(ligne, colonne);
		if (cellule == null)
			return false;

		// Ajouter une texte a la cellule cible.
		//
		return cellule.ajouterTexte(texte, couleurPolice, font);
	}

	/**
	 * Retrait du texte d'une cellule cible.
	 * 
	 * @param ligne
	 *            de la cellule.
	 * @param colonne
	 *            de la cellule.
	 * @return flag de reussite.
	 * @since 3.2.0
	 */
	public boolean retirerTexte(int ligne, int colonne) {

		// Verifier la validite des parametres
		//
		if (ligne < 1 || ligne > obtenirNbLignes())
			return false;
		if (colonne < 1 || colonne > obtenirNbColonnes())
			return false;

		// Recuperer la cellule cible.
		//
		CelluleG cellule = obtenirCellule(ligne, colonne);
		if (cellule == null)
			return false;

		// Retirer le texte de la cellule cible.
		//
		cellule.retirerTexte();

		// Restituer le flag de reussite.
		//
		return true;
	}

	/**
	 * Retrait du texte de toutes les cellules.
	 * 
	 * @since 3.5.0
	 */
	public void retirerTexte() {

		// Parcourir l'ensemble des cellules.
		//
		for (int i = 1; i <= obtenirNbLignes(); i++)
			for (int j = 1; j <= obtenirNbColonnes(); j++)
				obtenirCellule(i, j).retirerTexte();
	}

	/**
	 * Remplissage de la grille avec les donnees
	 * 
	 * @since 3.0.0
	 */
	public void remplirGrille() {
		String cle = null;
		Iterator iterator = donnees.iterator();

		// Initialiser les indexes de parcours.
		//
		int i = 1;
		int j = 1;
		if (styleNum) {
			i = 2;
			j = 2;
		}

		// Parcours de l'ensemble des cellules.
		//
		for (; i <= obtenirNbLignes(); i++) {
			j = styleNum ? 2 : 1;
			for (; j <= obtenirNbColonnes(); j++) {

				// Parcourir les donnees.
				//
				if (iterator.hasNext()) {

					// Acquerir la donnee courante.
					//
					cle = (String) iterator.next();

					// Ajouter la donnee a la cellule courante
					//
					if (cle.equals("@"))
						modifierCouleurCellule(i, j, Color.gray);
					else {
						if (cle.indexOf(".jpg") > 0 || cle.indexOf(".png") > 0)
							ajouterImage(i, j, cle);
						else
							ajouterTexte(i, j, cle);
					}
				} else
					modifierCouleurCellule(i, j, Color.gray);
			}
		}
	}

	/**
	 * Remplissage de la grille avec les solutions
	 * 
	 * @since 3.0.0
	 */
	public void remplirGrilleSolution() {
		String cle = null;
		Iterator iterator = solution.iterator();

		// Initialiser les indexes de parcours.
		//
		int i = 1;
		int j = 1;
		if (styleNum) {
			i = 2;
			j = 2;
		}

		// Parcours de l'ensemble des cellules.
		//
		for (; i <= obtenirNbLignes(); i++) {
			j = styleNum ? 2 : 1;
			for (; j <= obtenirNbColonnes(); j++) {

				// Parcourir les donnees.
				//
				if (iterator.hasNext()) {

					// Acquerir la donnee courante.
					//
					cle = (String) iterator.next();

					// Ajouter la donnee a la cellule courante
					//
					if (cle.equals("@"))
						modifierCouleurCellule(i, j, Color.gray);
					else {
						if (cle.indexOf(".jpg") > 0 || cle.indexOf(".png") > 0)
							ajouterImage(i, j, cle);
						else {
							ajouterTexte(i, j, cle);
							modifierCouleurCellule(i, j, Color.white);
						}
					}
				}
			}
		}
	}

	/**
	 * Remplissage de la grille avec une sauvegarde
	 * 
	 * @since 3.0.0
	 */
	public void remplirGrilleSauvegarde() {
		String cle = null;
		Iterator iterator = solution.iterator();

		// Remplir la grille des donnees
		//
		remplirGrille();

		// Initialiser les indexes de parcours.
		//
		int i = 1;
		int j = 1;
		if (styleNum) {
			i = 2;
			j = 2;
		}

		// Parcours de l'ensemble des cellules.
		//
		for (; i <= obtenirNbLignes(); i++) {
			j = styleNum ? 2 : 1;
			for (; j <= obtenirNbColonnes(); j++) {

				// Parcourir les donnees.
				//
				if (iterator.hasNext()) {

					// Acquerir l'image courante
					//
					cle = (String) iterator.next();

					// Ajouter la donnee a la cellule courante
					//
					if (cle.equals("@"))
						modifierCouleurCellule(i, j, Color.gray);
					else {
						if (cle.indexOf(".jpg") > 0 || cle.indexOf(".png") > 0)
							ajouterImage(i, j, cle);
						else {
							modifierCouleurCellule(i, j, Color.white);
						}
					}
				}
			}
		}
	}

	/**
	 * Verifier la presence de contenu dans une grille.
	 * 
	 * @return flag de presence de contenu.
	 * @since 3.0.0
	 */
	public boolean grilleVide() {

		// Initialiser les indexes de parcours.
		//
		int i = 1;
		int j = 1;
		if (styleNum) {
			i = 2;
			j = 2;
		}

		// Parcourir l'ensemble des cellules
		//
		for (; i <= obtenirNbLignes(); i++)
			for (; j <= obtenirNbColonnes(); j++)
				if (obtenirCellule(i, j).presenceTexte())
					return false;

		// Restituer le resutlat
		//
		return true;
	}

	/**
	 * Corriger le contenu de la grille.
	 * 
	 * @since 3.0.0
	 */
	public void correction() {
		String cleSolution = null;
		String cleContenu = null;
		Iterator iteratorSolution = solution.iterator();
		Iterator iteratorContenu = obtenirContenu().iterator();

		// Initialiser les indexes de parcours.
		//
		int i = 1;
		int j = 1;
		if (styleNum) {
			i = 2;
			j = 2;
		}

		// Parcours de l'ensemble des cellules.
		//
		for (; i <= obtenirNbLignes(); i++) {
			j = styleNum ? 2 : 1;
			for (; j <= obtenirNbColonnes(); j++) {

				// Parcourir les donnees.
				//
				if (iteratorSolution.hasNext() && iteratorContenu.hasNext()) {

					// Acquerir l'image courante
					//
					cleSolution = (String) iteratorSolution.next();
					cleContenu = (String) iteratorContenu.next();

					if (cleSolution.equals(cleContenu))
						if (!obtenirCellule(i - 1, j - 1).getBackground().equals(Color.GRAY))
							modifierCouleurCellule(i, j, Color.GREEN);
				}
			}
		}
	}

	/**
	 * Vider la grille.
	 * 
	 * @since 3.0.0
	 */
	public void viderGrille() {

		// Initialiser les indexes de parcours.
		//
		int i = 1;
		int j = 1;
		if (styleNum) {
			i = 2;
			j = 2;
		}

		// Parcours de l'ensemble des cellules.
		//
		for (; i <= obtenirNbLignes(); i++) {
			j = styleNum ? 2 : 1;
			for (; j <= obtenirNbColonnes(); j++) {
				retirerImage(i, j);
				retirerTexte(i, j);
			}
		}

		// Griser les cellules
		//
		modifierCouleurCellules(Color.GRAY);
	}

	/**
	 * Chargement d'un lot d'images ou de caractere via un fichier Data en
	 * gardant l'ordre.
	 * 
	 * @param nom
	 *            de fichier.
	 * @param version
	 *            du fichier.
	 * @return flag de reussite.
	 * @since 3.0.0
	 */
	public boolean chargerDonnees(String nom, String version) {

		// Recuperation des donnees
		//
		LinkedHashMap cheminsImages = (LinkedHashMap) Data.load(nom, version);

		// Remplissage du vecteur de donnees
		//
		donnees = new Vector();
		Iterator i = cheminsImages.keySet().iterator();
		while (i.hasNext())
			donnees.add(i.next());

		if (donnees != null) {
			remplirGrille();
			return true;
		} else
			return false;
	}

	/**
	 * Chargement d'un lot d'images via un repertoire cible en les ordonnant de
	 * maniere aleatoire
	 * 
	 * @param cheminRepertoire
	 * @since 3.3.0
	 */
	public void chargerLotImages(String cheminRepertoire) {

		donnees = Dossier.chargerRepertoire(cheminRepertoire, ".jpg");

		// Remplir la grille des images
		//
		remplirGrille();
	}

	/**
	 * Chargement d'un lot d'images via un repertoire cible en les ordonnant de
	 * maniere aleatoire Chaque image chargé est contenu 2 fois dans le vecteur
	 * de donnees
	 * 
	 * @param cheminRepertoire
	 * @since 3.5.0
	 */
	public void chargerLotImagesPaires(Vector dossierImages) {

		// Construire le vecteur des donnees
		//
		donnees = new Vector();
		int random;

		while (donnees.size() < this.obtenirNbCellules() / 2) {
			random = (int) (Math.random() * (dossierImages.size()));

			if (!donnees.contains(dossierImages.get(random)))
				donnees.add(dossierImages.get(random));
		}

		Vector temp = new Vector();

		while (temp.size() < donnees.size() * 2) {
			random = (int) (Math.random() * (donnees.size()));
			if (!temp.contains(donnees.get(random)) || this.obtenirNombreOccurence(temp, (String) donnees.get(random)) < 2)
				temp.add(donnees.get(random));
		}

		donnees = temp;

		// Remplir la grille des images
		//
		remplirGrille();
	}

	private int obtenirNombreOccurence(Vector donnees, String nom) {
		Iterator iterator = donnees.iterator();
		String cle;
		int nombreOccurence = 0;

		while (iterator.hasNext()) {
			cle = (String) iterator.next();
			if (cle.equals(nom))
				nombreOccurence++;
		}

		return nombreOccurence;
	}

	/**
	 * Permutation des images de deux cellules
	 * 
	 * @param p1
	 * @param p2
	 * @since 1.1.0
	 */
	public void permuterImages(Dimension p1, Dimension p2) {
		int i1, i2, j1, j2;

		// Detailler les coordonnees des deux cellules
		// cibles
		//
		i1 = (int) p1.getWidth() - 1;
		j1 = (int) p1.getHeight() - 1;
		i2 = (int) p2.getWidth() - 1;
		j2 = (int) p2.getHeight() - 1;

		// Obtenir les cellules cibles
		//
		CelluleG cellule_1 = matriceCellules[i1][j1];
		CelluleG cellule_2 = matriceCellules[i2][j2];

		// Determiner la presence d'images dans les cellules
		// cibles
		//
		boolean presenceImage_1 = cellule_1.presenceImage();
		boolean presenceImage_2 = cellule_2.presenceImage();

		// Recueillir les chemins d'acces aux images
		// presentes
		//
		String cheminImage_1 = null, cheminImage_2 = null;
		if (presenceImage_1)
			cheminImage_1 = cellule_1.obtenirCheminImage();
		if (presenceImage_2)
			cheminImage_2 = cellule_2.obtenirCheminImage();

		// Retirer les anciennes images des panneaux sous
		// jacents
		//
		if (presenceImage_1)
			matriceCellules[i1][j1].retirerImage();
		if (presenceImage_2)
			matriceCellules[i2][j2].retirerImage();

		// Ajouter les nouvelles images dans les panneaux
		// sous jacents
		//
		if (presenceImage_2)
			matriceCellules[i1][j1].ajouterImage(cheminImage_2);
		if (presenceImage_1)
			matriceCellules[i2][j2].ajouterImage(cheminImage_1);
	}

	/**
	 * Applique un style numeroter a la grille
	 * 
	 * @since 3.1.0
	 */
	public void styleNumeroter() {

		// Controle du style actuel
		//
		if (styleNum)
			return;

		// Calibrer la dimension de la grille
		//
		calibrerGrille(nombreL + 1, nombreL + 1);

		// Affecter les numerotation au lignes
		//
		for (int i = 1; i < nombreL; i++) {
			matriceCellules[i][0].ajouterTexte(Integer.toString(i), Color.black, new Font("Times Roman", Font.BOLD, 12));
			matriceCellules[i][0].setBorder(null);
		}

		// Affecter les numerotation au colonnes
		//
		for (int i = 1; i < nombreC; i++) {
			matriceCellules[0][i].ajouterTexte(Integer.toString(i), Color.black, new Font("Times Roman", Font.BOLD, 12));
			matriceCellules[0][i].setBorder(null);
		}

		// Enlever la bordure de la premiere case
		//
		matriceCellules[0][0].setBorder(null);

		// Mettre la flag numeroter
		//
		styleNum = true;
		styleSudoku = false;
	}

	/**
	 * Applique un style numeroter a la grille
	 * 
	 * @since 3.1.0
	 */
	public void styleSudoku() {

		// Controle du style actuel
		//
		if (styleSudoku)
			return;

		// Calibrer la dimension de la grille
		//
		calibrerGrille(3, 3);

		// Modifier le style de bordure
		//
		bordureBlack();

		// Modifier la bordure du contour de la grille
		//
		for (int i = 1; i < nombreL - 1; i++) {
			matriceCellules[i][0].setBorder(BorderFactory.createCompoundBorder(BorderFactory.createMatteBorder(3, 5, 0, 0, Color.black),
					BorderFactory.createLoweredBevelBorder()));
			matriceCellules[i][nombreC - 1].setBorder(BorderFactory.createCompoundBorder(BorderFactory.createMatteBorder(3, 3, 0, 5, Color.black),
					BorderFactory.createLoweredBevelBorder()));
			matriceCellules[0][i].setBorder(BorderFactory.createCompoundBorder(BorderFactory.createMatteBorder(5, 3, 0, 0, Color.black),
					BorderFactory.createLoweredBevelBorder()));
			matriceCellules[nombreL - 1][i].setBorder(BorderFactory.createCompoundBorder(BorderFactory.createMatteBorder(3, 3, 5, 0, Color.black),
					BorderFactory.createLoweredBevelBorder()));
		}

		// Modifier la bordure des coins de la grille
		//
		matriceCellules[0][0].setBorder(BorderFactory.createCompoundBorder(BorderFactory.createMatteBorder(5, 5, 0, 0, Color.black), BorderFactory
				.createLoweredBevelBorder()));
		matriceCellules[nombreL - 1][0].setBorder(BorderFactory.createCompoundBorder(BorderFactory.createMatteBorder(3, 5, 5, 0, Color.black),
				BorderFactory.createLoweredBevelBorder()));
		matriceCellules[0][nombreC - 1].setBorder(BorderFactory.createCompoundBorder(BorderFactory.createMatteBorder(5, 3, 0, 5, Color.black),
				BorderFactory.createLoweredBevelBorder()));
		matriceCellules[nombreL - 1][nombreC - 1].setBorder(BorderFactory.createCompoundBorder(BorderFactory.createMatteBorder(3, 3, 5, 5,
				Color.black), BorderFactory.createLoweredBevelBorder()));

		// Mettre la flag sudoku
		//
		styleSudoku = true;
		styleNum = false;
	}

	/**
	 * 
	 * @since 3.1.0
	 */
	public void bordureRelief() {
		for (int i = 0; i < nombreL; i++)
			for (int j = 0; j < nombreC; j++)
				matriceCellules[i][j].setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLoweredBevelBorder(), BorderFactory
						.createMatteBorder(3, 3, 0, 0, Color.black)));
	}

	/**
	 * @since 3.1.0
	 */
	public void bordureBlack() {
		for (int i = 0; i < nombreL; i++)
			for (int j = 0; j < nombreC; j++)
				matriceCellules[i][j].setBorder(BorderFactory.createCompoundBorder(BorderFactory.createMatteBorder(3, 3, 0, 0, Color.black),
						BorderFactory.createLoweredBevelBorder()));
	}

	/**
	 * Modification des bordures des cellules
	 * 
	 * @param bordure
	 * @since 3.6.0
	 */
	public void modifierBordure(Border bordure) {
		for (int i = 0; i < nombreL; i++)
			for (int j = 0; j < nombreC; j++)
				matriceCellules[i][j].setBorder(bordure);
	}

	/**
	 * Calibrage d'une grille
	 * 
	 * @param nbLignes
	 * @param nbColonnes
	 * @since 3.1.0
	 */
	private void calibrerGrille(int nbLignes, int nbColonnes) {

		// Calibrage de la grille
		//
		this.nombreL = nbLignes;
		this.nombreC = nbColonnes;

		// Enlever l'ancienne grille
		//
		removeAll();

		// Augmenter la taille du LayoutManager
		//
		((GridLayout) gestPlacement).setRows(nbLignes);
		((GridLayout) gestPlacement).setColumns(nbColonnes);

		// Creer la matrice des cellules internes
		//
		matriceCellules = new CelluleG[nbLignes][nbColonnes];
		CelluleG celluleCible;
		for (int i = 0; i < nbLignes; i++) {
			for (int j = 0; j < nbColonnes; j++) {

				// Construire la cellule cible
				//
				celluleCible = new CelluleG(this, config, new Dimension(i + 1, j + 1));

				// Affecter la nouvelle cellule a la matrice
				//
				this.matriceCellules[i][j] = celluleCible;
			}
		}
	}

	/**
	 * Obtention de la position de la premiere cellule libre
	 * 
	 * @return position
	 * @since 1.2.0
	 */
	public Position obtenirPositionPCL() {
		return obtenirPositionCLS(new Position(1, 1));
	}

	/**
	 * Obtention de la position de la cellule CLS
	 * 
	 * @param positionDepart
	 * @return position
	 * @since 1.2.0
	 */
	public Position obtenirPositionCLS(Dimension positionDepart) {
		CelluleG celluleCourante;

		// Controler la validite du parametre
		//
		if (positionDepart == null)
			return null;

		// Controler la validite de la ligne de depart
		//
		int ligneDepart = (int) positionDepart.getWidth();
		if (ligneDepart < 1 || ligneDepart > nombreL)
			return null;

		// Controler la validite de la colonne de depart
		//
		int colonneDepart = (int) positionDepart.getHeight();
		if (colonneDepart < 1 || colonneDepart > nombreC)
			return null;

		// Parcourir la matrice depuis la position de depart
		//
		int xDepart = ligneDepart - 1;
		int yDepart = colonneDepart - 1;

		for (int i = xDepart; i < nombreL; i++) {
			for (int j = yDepart; j < nombreC; j++) {

				// Obtenir la cellule courante
				//
				celluleCourante = matriceCellules[i][j];

				// Determiner presence ou pas d'une image
				// dans la cellule courante
				//
				if (!celluleCourante.presenceImage())
					return new Position(i + 1, j + 1);
			}
		}

		// Restituer le resultat en cas de saturation
		//
		return new Position(0, 0);
	}

	/**
	 * Obtention de la position de la premiere cellule occupees
	 * 
	 * @return position
	 * @since 1.2.0
	 */
	public Position obtenirPositionPCO() {
		return obtenirPositionCOS(new Position(1, 1));
	}

	/**
	 * Obtention de la position de la cellule COS
	 * 
	 * @param positionDepart
	 * @return position
	 * @since 1.2.0
	 */
	public Position obtenirPositionCOS(Position positionDepart) {
		CelluleG celluleCourante;

		// Controler la validite du parametre
		//
		if (positionDepart == null)
			return null;

		// Controler la validite de la ligne de depart
		//
		int ligneDepart = (int) positionDepart.getWidth();
		if (ligneDepart < 1 || ligneDepart > nombreL)
			return null;

		// Controler la validite de la colonne de depart
		//
		int colonneDepart = (int) positionDepart.getHeight();
		if (colonneDepart < 1 || colonneDepart > nombreC)
			return null;

		// Parcourir la matrice depuis la position de depart
		//
		int xDepart = ligneDepart - 1;
		int yDepart = colonneDepart - 1;

		for (int i = xDepart; i < nombreL; i++) {
			for (int j = yDepart; j < nombreC; j++) {

				// Obtenir la cellule courante
				//
				celluleCourante = matriceCellules[i][j];

				// Determiner presence ou pas d'une image
				// dans la cellule courante
				//
				if (celluleCourante.presenceImage())
					return new Position(i + 1, j + 1);
			}
		}

		// Restituer le resultat d'un solde vide
		//
		return new Position(0, 0);
	}

	/**
	 * Rassemblage de toutes les images sans trou
	 */
	public void rassemblerImages() {
		Position positionCOS;

		// Traiter le cas particulier d'une mosaique vide de
		// toute image
		//
		if (obtenirNbImages() == 0)
			return;

		// Traiter le cas particulier d'une saturation de la
		// mosaique
		//
		if (obtenirNbCellules() == obtenirNbImages())
			return;

		// Obtenir la position de la premiere cellule libre
		//
		Position positionPCL = obtenirPositionPCL();

		// Obtenir la position de depart des permutations
		//
		Position positionDepart = positionPCL.suivante();

		// Traiter toutes les cellules occupees non encore
		// rassemblees
		//
		while (positionDepart.valide()) {

			// Obtenir la position de la premiere cellule
			// occupee au dela de la premiere cellule libre
			//
			positionCOS = obtenirPositionCOS(positionPCL.suivante());
			if (!positionCOS.valide())
				return;

			// Permuter la premiere cellule libre et la
			// cellule occupee suivante
			//
			permuterImages(positionPCL, positionCOS);

			// Obtenir la position de la premiere cellule
			// libre
			//
			positionPCL = obtenirPositionPCL();

			// Obtenir la nouvelle position de depart des
			// permutations
			//
			positionDepart = positionPCL.suivante();
		}
	}

	/**
	 * Obtention de la position de la cellule par rapport au autres
	 * 
	 * @param x
	 * @param y
	 * @return dimension
	 * @since 2.1.0
	 */
	public Dimension obtenirPositionCelluleCible(int x, int y) {

		// Obtenir les dimensions du panneau principal
		//
		int largeur = (int) getWidth();
		int hauteur = (int) getHeight();

		// Calculer la largeur et la hauteur de chaque
		// cellule
		//
		int largeurPanneau = (int) largeur / nombreC;
		int hauteurPanneau = (int) hauteur / nombreL;

		// Calculer les coordonnees de la cellule cible
		//
		int i = (int) y / hauteurPanneau + 1;
		int j = (int) x / largeurPanneau + 1;

		// Restituer le resultat
		//
		return new Dimension(i, j);
	}

	/**
	 * Obtention de la position d'une coordonnee dans une cellule
	 * 
	 * @param x
	 * @param y
	 * @return dimension
	 * @since 2.2.0
	 */
	public Dimension obtenirPositionRelativeCible(int x, int y) {

		// Obtenir les dimensions du panneau principal
		//
		int largeur = (int) getWidth();
		int hauteur = (int) getHeight();

		// Calculer la largeur et la hauteur de chaque
		// cellule
		//
		int largeurPanneau = (int) largeur / nombreC;
		int hauteurPanneau = (int) hauteur / nombreL;

		// Calculer les coordonnees relatives du clic dans
		// la cellule cible
		//
		int i = (int) y % hauteurPanneau;
		int j = (int) x % largeurPanneau;

		// Restituer le resultat
		//
		return new Dimension(i, j);
	}

	/**
	 * ajouterSolution
	 * 
	 * @param v
	 * @since 3.6.0
	 */
	public void ajouterSolution(Vector v) {
		solution = v;
	}

	/**
	 * obtenirSolution
	 * 
	 * @return solution
	 * @since 3.6.0
	 */
	public Vector obtenirSolution() {
		return solution;
	}

	/**
	 * ajouterDonnees
	 * 
	 * @param v
	 * @since 4.0.0
	 */
	public void ajouterDonnees(Vector v) {
		donnees = v;
	}

	/**
	 * Ajout d'une image a une cellule cible
	 * 
	 * @param ligne
	 * @param colonne
	 * @param cheminImage
	 * @return flag de reussite
	 * @since 4.1.0
	 */
	public boolean ajouterImage(int ligne, int colonne, String cheminImage, int index, int decalageEntreImages, int decalageHauteur, int decalageCoter) {

		// Controler la validite des parametres
		//
		if (ligne < 1 || ligne > nombreL)
			return false;
		if (colonne < 1 || colonne > nombreC)
			return false;
		if (cheminImage == null)
			return false;

		// Ajouter l'image a la cellule cible
		//
		CelluleG celluleCible = matriceCellules[ligne - 1][colonne - 1];
		celluleCible.ajouterImage(cheminImage, index, decalageEntreImages, decalageHauteur, decalageCoter);

		return true;
	}

	/**
	 * Ajout d'une image a toute les cellules
	 * 
	 * @param ligne
	 * @param colonne
	 * @param cheminImage
	 * @return flag de reussite
	 * @since 4.1.0
	 */
	public void ajouterImageCellules(String cheminImage) {

		// Controler la validite des parametres
		//
		if (cheminImage == null)
			return;

		int i = 1;
		int j = 1;
		if (styleNum) {
			i = 2;
			j = 2;
		}

		// Ajouter l'image au cellules
		//
		for (; i <= nombreL; i++) {
			j = styleNum ? 2 : 1;
			for (; j <= nombreC; j++)
				matriceCellules[i - 1][j - 1].ajouterImage(cheminImage);
		}
	}

	/**
	 * Position d'une cellule dans son contexte
	 * 
	 * @author Alain Thuaire, Charles Fouco, Cedric Hulin
	 * @version 1.0.0
	 */
	public class Position extends Dimension {

		/**
		 * Designation d'une position coherente
		 */
		private boolean status;

		/**
		 * Constructeur normal
		 * 
		 * @param ligne
		 * @param colonne
		 * @since 1.0.0
		 */
		public Position(int ligne, int colonne) {

			super(ligne, colonne);

			// Controler la validite des parametres
			//
			if (ligne <= 0 || ligne > nombreL) {
				status = false;
				return;
			}
			if (colonne <= 0 || colonne > nombreC) {
				status = false;
				return;
			}

			// Valider le status
			//
			status = true;
		}

		/**
		 * Validation d'une position
		 * 
		 * @return statuts
		 * @since 1.0.0
		 */
		public boolean valide() {
			return status;
		}

		/**
		 * Obtention de la position de la cellule suivante
		 * 
		 * @return position de la cellule suivante
		 * @since 1.0.0
		 */
		public Position suivante() {

			// Obtenir les coordonnees de la position
			// support
			//
			int x = (int) getWidth();
			int y = (int) getHeight();

			// Restituer le resultat
			//
			if (x == nombreL && y == nombreC)
				return new Position(0, 0);
			if (y < nombreC)
				return new Position(x, y + 1);
			return new Position(x + 1, 1);
		}
	}

	public boolean styleNum() {
		return styleNum;
	}

	public Color obtenirCouleurPolice() {
		return couleurPolice;
	}

	public Font obtenirFont() {
		return font;
	}
}