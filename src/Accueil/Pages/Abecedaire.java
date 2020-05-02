import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.io.File;
import java.util.Iterator;
import java.util.Vector;

import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import org.jdom.Element;

/**
 * Liste des recettes
 * 
 * @version 1.0.0
 * @author Cedric Hulin, Charles Fouco
 */
public class Abecedaire {

	/**
	 * Configuration appliquee au panneau de l'abecedaire
	 * 
	 * @since 1.0.0
	 */
	private Element configuration;

	/**
	 * Hamecon sur l'instance demarrer
	 * 
	 * @since 1.0.0
	 */
	private Demarrer hamecon;

	/**
	 * Panneau support contenant les differentes
	 * informations relatives a l'abecedaire
	 * 
	 * @since 1.0.0
	 */
	private PanneauG panneauCentral;

	/**
	 * Liste contenant le nom des aliments
	 * 
	 * @since 1.0.0
	 */
	private JList listeAliments;

	/**
	 * Barre de scroll sur la liste des aliments
	 * 
	 * @since 1.0.0
	 */
	private JScrollPane scrollListeAliments;

	/**
	 * Titre de l'element selectionnee
	 * 
	 * @since 1.0.0
	 */
	private PanneauG titre;

	/**
	 * Valeur nutritionnel sur l'aliment selectionnee
	 * 
	 * @since 1.0.0
	 */
	private JTextArea valeurNutritive;

	/**
	 * Description de l'aliment selectionnee
	 * 
	 * @since 1.0.0
	 */
	private JTextArea description;

	/**
	 * Barre de scroll sur la description
	 * 
	 * @since 1.0.0
	 */
	private JScrollPane scrollDescription;

	/**
	 * Indice sur l'aliment selectionnee
	 * 
	 * @since 1.0.0
	 */
	private JTextArea indice;

	/**
	 * Barre de scroll sur l'indice
	 * 
	 * @since 1.0.0
	 */
	private JScrollPane scrollIndice;

	/**
	 * Chemin des images des aliments
	 * 
	 * @since 1.0.0
	 */
	private Vector cheminsImages;

	/**
	 * Chemin des repertoires sur les descriptions
	 * 
	 * @since 1.0.0
	 */
	private Vector cheminsRepertoires;

	/**
	 * Panneau contenant les informations relative au
	 * aliments
	 * 
	 * @since 1.0.0
	 */
	private PanneauG panneauAliments;

	/**
	 * Alphabet sous forme de panneau
	 * 
	 * @since 1.0.0
	 */
	private PanneauG[] tabPanneauG;

	/**
	 * Grille des indices
	 * 
	 * @since 1.0.0
	 */
	private GrilleG grilleValeurNutritive;

	/**
	 * Panneau contenant l'image de l'aliment selectionne
	 * 
	 * @since 1.0.0
	 */
	private PanneauG panneauImage;

	/**
	 * Constructeur normal
	 * 
	 * @param hamecon
	 * @since 1.0.0
	 */
	public Abecedaire(Demarrer hamecon, Element configuration) {

		// Verifier les parametres
		//
		if (hamecon == null || configuration == null)
			return;

		// Stocker la configuration et l'hamecon
		//
		this.configuration = configuration;
		this.hamecon = hamecon;

		// Renommer la fenetre
		//
		hamecon.obtenirFenetreSupport().setTitle("Abecedaire");

		// Specification des repertoires d'images
		//
		Vector cheminsRepertoiresImages = new Vector();
		cheminsRepertoiresImages.add("../_Images/Legumes/");
		cheminsRepertoiresImages.add("../_Images/Fruits/");
		cheminsRepertoiresImages.add("../_Images/Feculents/");
		cheminsRepertoiresImages.add("../_Images/Matieres Grasses/");
		cheminsRepertoiresImages.add("../_Images/Produits Laitiers/");
		cheminsRepertoiresImages.add("../_Images/Produits Sucres/");
		cheminsRepertoiresImages.add("../_Images/Viande Poisson Oeuf/");

		// Recuperer la totalite des chemins des images
		// d'aliments
		//
		cheminsImages = Dossier.obtenirVecteur(cheminsRepertoiresImages, ".png");
		if (cheminsImages == null)
			System.exit(0);

		// Creer le panneau support contenant les
		// informations
		// relatives a chaque centre
		//
		panneauCentral = new PanneauG(this, configuration);

		// Ajouter une image de fond a la grille support
		//
		panneauCentral.setBackground(new Color(253, 252, 232, 255));

		// Afficher un alphabet
		//
		appelAlphabet();

		// Ajouter la page a la fenetre support
		//
		hamecon.obtenirFenetreSupport().getContentPane().add(panneauCentral, BorderLayout.CENTER);
		hamecon.obtenirFenetreSupport().getContentPane().repaint();
	}

	public void appelAlphabet() {

		listeAliments = null;
		cheminsRepertoires = null;
		indice = null;
		grilleValeurNutritive = null;
		panneauAliments = null;
		panneauImage = null;
		scrollDescription = null;
		scrollListeAliments = null;
		titre = null;
		valeurNutritive = null;

		// Ajout d'un ecouteur retour
		//
		hamecon.retirerEcouteurBoutonRetour("EcouteurRetour");
		hamecon.retirerEcouteurBoutonRetour("EcouteurRetourAbecedaire");
		hamecon.ajouterEcouteurBoutonRetour(new EcouteurRetour(hamecon));

		panneauCentral.removeAll();
		panneauCentral.setLayout(new FlowLayout());
		tabPanneauG = new PanneauG[26];

		// Recuperer la taille de la fenetre support
		//
		int largeur = hamecon.obtenirFenetreSupport().getWidth();
		int hauteur = hamecon.obtenirFenetreSupport().getHeight();

		char car = 'A';
		for (int i = 0; i < tabPanneauG.length; i++) {

			tabPanneauG[i] = new PanneauG(this, configuration);
			tabPanneauG[i].setBackground(new Color(0, 0, 0, 0));
			tabPanneauG[i].setPreferredSize(new Dimension(170 * largeur / 1280, 170 * hauteur / 800));
			tabPanneauG[i].ajouterImage("../_Images/Abecedaire/Boutons/" + String.valueOf(car) + ".png");
			tabPanneauG[i].addMouseListener(new EcouteurAlphabet(this, car++));
			panneauCentral.add(tabPanneauG[i]);
		}

		// Attacher la grille et le panneau a la fenetre
		// support
		//
		hamecon.obtenirFenetreSupport().getContentPane().repaint();
		hamecon.obtenirFenetreSupport().paintAll(hamecon.obtenirFenetreSupport().getGraphics());
		panneauCentral.repaint();
		panneauCentral.paintAll(panneauCentral.getGraphics());

		// Ajouter une image de fond dans la grille sud
		//
		hamecon.obtenirGrilleSud().ajouterImage("../_Images/Abecedaire/Fond/Bottom.jpg");
	}

	public void appelFicheAlimentaire(char car) {

		tabPanneauG = null;
		panneauCentral.removeAll();

		// Charger la liste d'aliments de la lettre demandee
		//
		creerListeAliments(car);

		// Creer les differents champs du panneau d'aliment
		//
		creerTitre("Nom Aliment");
		creerValeurNutritive("Potassium :\nSucre :\nPhosphore :\nSel :\nVitamines et Mineraux :");
		creerGrilleDisposition("Configuration/Abecedaire/ConfigurationGrille");
		creerDescription("Description Aliment");
		creerIndice("Indice Alimentaire");
		creerPanneauImage("../_Images/Abecedaire/Fond/Default.jpg");

		// Creer le panneau d'aliments
		//
		creerPanneauAliments(configuration);

		// Recuperer la taille de la fenetre support
		//
		int largeur = hamecon.obtenirFenetreSupport().getWidth();
		int hauteur = hamecon.obtenirFenetreSupport().getHeight();

		// Enlever les layout
		//
		panneauCentral.setLayout(null);
		panneauAliments.setLayout(null);
		panneauAliments.ajouterImage("../_Images/Abecedaire/Fond/fond.jpg");

		// Definir la disposition des champs
		//
		scrollListeAliments.setBounds(0, 0, 200 * largeur / 1280, panneauCentral.getHeight());
		panneauAliments.setBounds(scrollListeAliments.getWidth(), 0, panneauCentral.getWidth() - scrollListeAliments.getWidth(), panneauCentral.getHeight());
		titre.setBounds(0, 0, panneauAliments.getWidth(), 90 * hauteur / 800);
		valeurNutritive.setBounds(50 * largeur / 1024, titre.getHeight(), 360 * largeur / 1280, 150 * hauteur / 800);
		grilleValeurNutritive.setBounds((titre.getWidth() / 2) - valeurNutritive.getHeight() / 2, titre.getHeight(), valeurNutritive.getHeight(), valeurNutritive.getHeight());
		panneauImage.setBounds((panneauAliments.getWidth() - (panneauAliments.getWidth() - (grilleValeurNutritive.getX() + grilleValeurNutritive.getWidth())) / 2) - valeurNutritive.getHeight() / 2, titre.getHeight(), valeurNutritive.getHeight(), valeurNutritive.getHeight());
		scrollIndice.setBounds(20 * largeur / 1024, titre.getHeight() + valeurNutritive.getHeight() + 15 * hauteur / 800, panneauAliments.getWidth() - (40 * largeur / 1024), 65 * hauteur / 800);
		scrollDescription.setBounds(20 * largeur / 1024, titre.getHeight() + valeurNutritive.getHeight() + scrollIndice.getHeight() + 25 * hauteur / 800, panneauAliments.getWidth() - (40 * largeur / 1024), panneauAliments.getHeight() - (titre.getHeight() + valeurNutritive.getHeight() + scrollIndice.getHeight() + (45 * hauteur / 768)));

		panneauAliments.setBackground(new Color(0, 0, 0, 0));

		// Ajouter un ecouteur a la liste
		//
		EcouteurAbecedaire ecouteur = new EcouteurAbecedaire(this);
		listeAliments.addMouseListener(ecouteur);

		// Ajout d'un ecouteur retour
		//
		hamecon.retirerEcouteurBoutonRetour("EcouteurRetour");
		hamecon.ajouterEcouteurBoutonRetour(new EcouteurRetourAbecedaire(hamecon, this));

		// Attacher la grille et le panneau a la fenetre
		// support
		//
		panneauCentral.add(scrollListeAliments);
		panneauCentral.add(panneauAliments);
		hamecon.obtenirFenetreSupport().getContentPane().repaint();
		hamecon.obtenirFenetreSupport().paintAll(hamecon.obtenirFenetreSupport().getGraphics());

		// Ajouter une image de fond dans la grille sud
		//
		hamecon.obtenirGrilleSud().ajouterImage("../_Images/Abecedaire/Fond/Bottom_Fiche.jpg");
	}

	private void creerListeAliments(char car) {

		listeAliments = new JList();

		Vector repertoire = Dossier.chargerDirectory("../_Textes/Accueil/Abecedaire/");
		if (repertoire == null)
			return;
		File fichier;
		Iterator i = repertoire.iterator();
		Vector nomAliments = new Vector();
		cheminsRepertoires = new Vector();
		while (i.hasNext()) {
			fichier = new File((String) i.next());
			if (((String) fichier.getName()).charAt(0) == car) {
				nomAliments.add((String) fichier.getName());
				cheminsRepertoires.add(fichier.getAbsolutePath());
			}
		}

		listeAliments.setListData(nomAliments);
		listeAliments.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

		scrollListeAliments = new JScrollPane(listeAliments);
		scrollListeAliments.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		scrollListeAliments.setBackground(new Color(253, 252, 232, 255));
		scrollListeAliments.setBorder(null);

		// Definir la police a employe
		//
		listeAliments.setFont(panneauCentral.getFont());

		listeAliments.setBackground(new Color(253, 252, 232, 255));
	}

	private void creerTitre(String texte) {
		// titre = creerTextePane(texte,
		// panneauCentral.getFont());
		titre = new PanneauG(panneauAliments, configuration);
		titre.setBackground(new Color(0, 0, 0, 0));
		titre.setForeground(panneauCentral.getForeground());
	}

	private void creerValeurNutritive(String texte) {
		valeurNutritive = creerTexteArea(texte, panneauCentral.getFont());
	}

	private void creerGrilleDisposition(String nom) {
		Element configGrille = (Element) ConfigXML.load(nom, "1.0.0");
		grilleValeurNutritive = new GrilleG(this, configGrille);
		grilleValeurNutritive.setBorder(null);
		grilleValeurNutritive.setBackground(new Color(0, 0, 0, 0));
		grilleValeurNutritive.modifierCouleurCellules(new Color(0, 0, 0, 0));
		grilleValeurNutritive.modifierBordure(null);
	}

	private void creerPanneauImage(String chemin) {
		panneauImage = new PanneauG(this, configuration);
		panneauImage.ajouterImage(chemin);
		panneauImage.setBackground(new Color(0, 0, 0, 0));
	}

	private void creerDescription(String texte) {

		description = creerTexteArea(texte, panneauCentral.getFont());
		description.setForeground(panneauCentral.getForeground());
		scrollDescription = new JScrollPane(description);
		scrollDescription.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		scrollDescription.setBackground(new Color(0, 0, 0, 0));
		scrollDescription.setBorder(null);
		scrollDescription.getVerticalScrollBar().addAdjustmentListener(new EcouteurScroll(hamecon.obtenirFenetreSupport()));
	}

	private void creerIndice(String texte) {

		indice = creerTexteArea(texte, panneauCentral.getFont());
		indice.setForeground(panneauCentral.getForeground());
		scrollIndice = new JScrollPane(indice);
		scrollIndice.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		scrollIndice.setBackground(new Color(0, 0, 0, 0));
		scrollIndice.setBorder(null);
		scrollIndice.getVerticalScrollBar().addAdjustmentListener(new EcouteurScroll(hamecon.obtenirFenetreSupport()));
	}

	private JTextArea creerTexteArea(String texte, Font police) {

		JTextArea resultat = new JTextArea(texte);
		resultat.setBackground(Color.white);
		resultat.setForeground(panneauCentral.getForeground());
		resultat.setOpaque(true);

		resultat.setFont(police);
		resultat.setWrapStyleWord(true);
		resultat.setLineWrap(true);
		resultat.setEditable(false);
		return resultat;

	}

	private void creerPanneauAliments(Element configGrille) {
		panneauAliments = new PanneauG(this, configGrille);

		// Recuperer la taille de la fenetre support
		//
		int hauteur = hamecon.obtenirFenetreSupport().getHeight();

		panneauAliments.add(titre);
		titre.setFont(new Font(titre.getFont().getFamily(), titre.getFont().getStyle(), 40 * hauteur / 800));

		panneauAliments.add(valeurNutritive);
		valeurNutritive.setFont(new Font(valeurNutritive.getFont().getFamily(), valeurNutritive.getFont().getStyle(), 20 * hauteur / 800));

		panneauAliments.add(scrollIndice);
		indice.setFont(new Font(description.getFont().getFamily(), description.getFont().getStyle(), 20 * hauteur / 800));

		panneauAliments.add(scrollDescription);
		description.setFont(new Font(description.getFont().getFamily(), description.getFont().getStyle(), 20 * hauteur / 800));

		panneauAliments.add(grilleValeurNutritive);

		panneauAliments.add(panneauImage);
	}

	public JList obtenirListeAliments() {
		return listeAliments;
	}

	public void afficherInfoAliments(int index) {

		if (panneauAliments != null) {

			String repertoireAliment = (String) cheminsRepertoires.get(index);
			Vector dossierCourant;

			// Ouvrir le dossier contenant les informations
			// du centre
			//
			dossierCourant = Dossier.chargerRepertoire(repertoireAliment, ".txt");
			if (dossierCourant != null) {

				// Verifier que le dossier contient trois
				// fichiers
				//
				if (dossierCourant.size() < 3) {
					JOptionPane.showMessageDialog(null, Texte.load("../_Textes/Erreurs/Nombre fichiers"));
					return;
				}

				grilleValeurNutritive.viderGrille();
				grilleValeurNutritive.modifierCouleurCellules(new Color(0, 0, 0, 0));

				// Recuperer la taille de la fenetre support
				//
				int hauteur = hamecon.obtenirFenetreSupport().getHeight();

				// Lister les differents fichiers textes du
				// dossier
				//
				String Nutriment_1 = Texte.load("../_Textes/Accueil/Abecedaire_Nutriments/Nutriment_1");
				String Nutriment_2 = Texte.load("../_Textes/Accueil/Abecedaire_Nutriments/Nutriment_2");
				String Nutriment_3 = Texte.load("../_Textes/Accueil/Abecedaire_Nutriments/Nutriment_3");
				String Nutriment_4 = Texte.load("../_Textes/Accueil/Abecedaire_Nutriments/Nutriment_4");
				String Nutriment_5 = Texte.load("../_Textes/Accueil/Abecedaire_Nutriments/Nutriment_5");
				Nutriment_1 = Nutriment_1.substring(0, Nutriment_1.length() - 1);
				Nutriment_2 = Nutriment_2.substring(0, Nutriment_2.length() - 1);
				Nutriment_3 = Nutriment_3.substring(0, Nutriment_3.length() - 1);
				Nutriment_4 = Nutriment_4.substring(0, Nutriment_4.length() - 1);
				Nutriment_5 = Nutriment_5.substring(0, Nutriment_5.length() - 1);

				String cle = null;
				File file = null;
				String nomFichier = null;
				String cheminFichier = null;
				Iterator i = dossierCourant.iterator();
				boolean potassium = false;
				boolean sel = false;
				boolean phosphore = false;
				boolean sucre = false;
				boolean vitamine = false;
				while (i.hasNext()) {

					cle = (String) i.next();
					file = new File(cle);

					nomFichier = file.getName().substring(0, file.getName().length() - 4);
					cheminFichier = file.getAbsolutePath().substring(0, file.getAbsolutePath().length() - 4);

					if (nomFichier.equals("Titre"))
						titre.ajouterTitre(Texte.load(cheminFichier), Color.DARK_GRAY, new Font(panneauCentral.getFont().getFamily(), panneauCentral
								.getFont().getStyle(), 40 * hauteur / 800));
					if (nomFichier.equals(Nutriment_1))
						potassium = true;
					if (nomFichier.equals(Nutriment_2))
						sucre = true;
					if (nomFichier.equals(Nutriment_3))
						phosphore = true;
					if (nomFichier.equals(Nutriment_4))
						sel = true;
					if (nomFichier.equals(Nutriment_5))
						vitamine = true;
					if (nomFichier.equals("Description"))
						description.setText(Texte.load(cheminFichier));
					if (nomFichier.equals("Indice"))
						indice.setText(Texte.load(cheminFichier));

					afficherIndice(nomFichier, cheminFichier);
				}

				indice.setCaretPosition(0);
				description.setCaretPosition(0);

				String valeurNutritive = "";
				if (potassium)
					valeurNutritive = Nutriment_1 + " :";
				valeurNutritive += "\n";
				if (sucre)
					valeurNutritive += Nutriment_2 + " :";
				valeurNutritive += "\n";
				if (phosphore)
					valeurNutritive += Nutriment_3 + " :";
				valeurNutritive += "\n";
				if (sel)
					valeurNutritive += Nutriment_4 + " :";
				valeurNutritive += "\n";
				if (vitamine)
					valeurNutritive += Nutriment_5 + " :";

				this.valeurNutritive.setText(valeurNutritive);

				String cheminImage = null;
				File fichier = null;
				String nom;
				for (int r = 0; r < cheminsImages.size(); r++) {
					fichier = new File((String) cheminsImages.get(r));
					nom = fichier.getName().substring(0, fichier.getName().length() - 4);
					if (nom.equals(listeAliments.getSelectedValue())) {
						cheminImage = (String) cheminsImages.get(r);
						break;
					}
				}

				if (cheminImage == null) {
					JOptionPane.showMessageDialog(null, Texte.load("../_Textes/Erreurs/Image non presente"));
					return;
				}

				panneauAliments.ajouterImage("../_Images/Abecedaire/Fond/Fond_" + fichier.getParentFile().getName() + ".jpg");

				panneauImage.ajouterImage(cheminImage);

				hamecon.obtenirFenetreSupport().repaint();
				hamecon.obtenirFenetreSupport().paintAll(hamecon.obtenirFenetreSupport().getGraphics());
			}
		}
	}

	private void afficherIndice(String categorie, String chemin) {
		int nombreIcon = 0;
		String texte = Texte.load(chemin).substring(0, 1);
		int j = 1;
		if (categorieValide(categorie)) {
			nombreIcon = Integer.valueOf(texte).intValue();
		} else
			return;

		String Nutriment_1 = Texte.load("../_Textes/Accueil/Abecedaire_Nutriments/Nutriment_1");
		String Nutriment_2 = Texte.load("../_Textes/Accueil/Abecedaire_Nutriments/Nutriment_2");
		String Nutriment_3 = Texte.load("../_Textes/Accueil/Abecedaire_Nutriments/Nutriment_3");
		String Nutriment_4 = Texte.load("../_Textes/Accueil/Abecedaire_Nutriments/Nutriment_4");
		String Nutriment_5 = Texte.load("../_Textes/Accueil/Abecedaire_Nutriments/Nutriment_5");
		Nutriment_1 = Nutriment_1.substring(0, Nutriment_1.length() - 1);
		Nutriment_2 = Nutriment_2.substring(0, Nutriment_2.length() - 1);
		Nutriment_3 = Nutriment_3.substring(0, Nutriment_3.length() - 1);
		Nutriment_4 = Nutriment_4.substring(0, Nutriment_4.length() - 1);
		Nutriment_5 = Nutriment_5.substring(0, Nutriment_5.length() - 1);

		System.out.println(categorie);
		if (categorie.equals(Nutriment_1))
			j = 1;
		if (categorie.equals(Nutriment_2))
			j = 2;
		if (categorie.equals(Nutriment_3))
			j = 3;
		if (categorie.equals(Nutriment_4))
			j = 4;
		if (categorie.equals(Nutriment_5))
			j = 5;

		for (int i = 1; i <= nombreIcon; i++)
			grilleValeurNutritive.ajouterImage(j, i, "../_Images/Abecedaire/Fond/" + categorie + ".png");
	}

	private boolean categorieValide(String categorie) {
		String Nutriment_1 = Texte.load("../_Textes/Accueil/Abecedaire_Nutriments/Nutriment_1");
		String Nutriment_2 = Texte.load("../_Textes/Accueil/Abecedaire_Nutriments/Nutriment_2");
		String Nutriment_3 = Texte.load("../_Textes/Accueil/Abecedaire_Nutriments/Nutriment_3");
		String Nutriment_4 = Texte.load("../_Textes/Accueil/Abecedaire_Nutriments/Nutriment_4");
		String Nutriment_5 = Texte.load("../_Textes/Accueil/Abecedaire_Nutriments/Nutriment_5");
		Nutriment_1 = Nutriment_1.substring(0, Nutriment_1.length() - 1);
		Nutriment_2 = Nutriment_2.substring(0, Nutriment_2.length() - 1);
		Nutriment_3 = Nutriment_3.substring(0, Nutriment_3.length() - 1);
		Nutriment_4 = Nutriment_4.substring(0, Nutriment_4.length() - 1);
		Nutriment_5 = Nutriment_5.substring(0, Nutriment_5.length() - 1);

		if (categorie.equals(Nutriment_1) || categorie.equals(Nutriment_2) || categorie.equals(Nutriment_3) || categorie.equals(Nutriment_4)
				|| categorie.equals(Nutriment_5))
			return true;
		return false;
	}
}