import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.io.File;
import java.util.Iterator;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextPane;
import javax.swing.ListSelectionModel;
import javax.swing.text.MutableAttributeSet;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

import org.jdom.Element;

/**
 * Liste des recettes
 * 
 * @version 1.0.0
 * @author Cedric Hulin, Charles Fouco
 */
public class Recettes {

	/**
	 * Panneau support contenant les differentes informations relatives a une
	 * recette
	 * 
	 * @since 1.0.0
	 */
	private PanneauG panneauLivre;

	/**
	 * Panneau contenant l'image de la recette
	 * 
	 * @since 1.0.0
	 */
	private PanneauG panneauImage;

	/**
	 * Titre de la recette selectionnee
	 * 
	 * @since 1.0.0
	 */
	private JScrollPane scrollTitre;
	private JTextPane titre;

	/**
	 * Ingredients demandes dans la recette selectionnee
	 * 
	 * @since 1.0.0
	 */
	private JScrollPane scrollIngredients;
	private JTextArea ingredients;

	/**
	 * Description de la recette selectionnee
	 * 
	 * @since 1.0.0
	 */
	private JScrollPane scrollDescription;
	private JTextArea description;

	/**
	 * Liste contenant les titres des recettes proposees
	 * 
	 * @since 1.0.0
	 */
	private JScrollPane scrollRecettes;
	private JList listeRecettes;

	/**
	 * Chemin des repertoire sur les recettes
	 * 
	 * @since 1.0.0
	 */
	private Vector repertoireRecettes;

	/**
	 * Panneau contenant les informations relative au recettes
	 * 
	 * @since 1.0.0
	 */
	private PanneauG panneauRecettes;

	private Demarrer hamecon;

	private PanneauG decorationGauche;
	private PanneauG decorationDroite;

	/**
	 * Constructeur normal
	 * 
	 * @param hamecon
	 * @since 1.0.0
	 */
	public Recettes(Demarrer hamecon, Element configGrille) {

		// Verifier les parametres
		//
		if (hamecon == null || configGrille == null)
			return;
		this.hamecon = hamecon;

		// Renommer la fenetre
		//
		hamecon.obtenirFenetreSupport().setTitle("Recettes");

		// Charger les liens contenants les informations
		// relatives a chaque centre
		//
		if (!setRepertoiresRecettes())
			return;

		// Creer le panneau support contenant les informations
		// relatives a chaque centre
		//
		panneauLivre = new PanneauG(this, configGrille);

		// Ajouter une image de fond a la grille support
		//
		panneauLivre.ajouterImage("../_Images/Recettes/Fond/Livre.png");

		// Creer les differents champs du panneau d'informations
		//
		titre = creerTextePane("", panneauLivre.getFont());
		scrollTitre = new JScrollPane(titre);
		scrollTitre.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		scrollTitre.setBackground(new Color(0, 0, 0, 0));
		scrollTitre.setBorder(null);
		// titre.setBorder(BorderFactory.createMatteBorder(5, 5, 5, 5,
		// Color.yellow));

		ingredients = creerTexteArea("", panneauLivre.getFont());
		scrollIngredients = new JScrollPane(ingredients);
		scrollIngredients.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		scrollIngredients.setBackground(new Color(0, 0, 0, 0));
		scrollIngredients.setBorder(null);

		description = creerTexteArea("", panneauLivre.getFont());
		scrollDescription = new JScrollPane(description);
		scrollDescription.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		scrollDescription.setBackground(new Color(0, 0, 0, 0));
		scrollDescription.setBorder(null);

		ajouterPanneauRecette(configGrille);

		panneauImage = new PanneauG(this, configGrille);
		panneauImage.setBackground(new Color(0, 0, 0, 0));
		decorationDroite = new PanneauG(this, configGrille);
		decorationDroite.ajouterImage("../_Images/Recettes/Fond/Droite.png");
		decorationDroite.setBackground(new Color(253, 252, 232, 255));
		decorationGauche = new PanneauG(this, configGrille);
		decorationGauche.ajouterImage("../_Images/Recettes/Fond/Gauche.png");
		decorationGauche.setBackground(new Color(253, 252, 232, 255));

		// Definir une nouvelle disposition pour les champs
		//
		int largeur = hamecon.obtenirFenetreSupport().getWidth();
		int hauteur = hamecon.obtenirFenetreSupport().getHeight();

		panneauLivre.setLayout(null);
		scrollRecettes.setBounds(40 * largeur / 1024, 20 * hauteur / 768, 450 * largeur / 1024, 480 * hauteur / 768);
		panneauRecettes.setBounds(540 * largeur / 1024, 20 * hauteur / 768, 420 * largeur / 1024, 630 * hauteur / 768);
		scrollTitre.setBounds(0, 0, panneauRecettes.getWidth(), 100 * hauteur / 800);
		scrollIngredients.setBounds(0, scrollTitre.getHeight(), panneauRecettes.getWidth(), 200 * hauteur / 800);
		scrollDescription.setBounds(0, scrollTitre.getHeight() + scrollIngredients.getHeight(), panneauRecettes.getWidth(), panneauRecettes
				.getHeight()
				- (scrollTitre.getHeight() + scrollIngredients.getHeight()));
		panneauRecettes.setBackground(new Color(0, 0, 0, 0));
		panneauRecettes.ajouterImage("../_Images/Recettes/Fond/Page Livre.png");

		int xalign = scrollRecettes.getX() + (scrollRecettes.getWidth() / 2);
		int yalign = scrollRecettes.getY() + scrollRecettes.getHeight();

		panneauImage.setBounds(xalign - (75 * largeur / 1024), yalign, 150 * largeur / 1024, 150 * hauteur / 768);

		panneauRecettes.setBackground(new Color(253, 252, 232, 255));

		// Definir la police a employe
		//
		listeRecettes.setFont(panneauLivre.getFont());

		EcouteurRecettes ecouteur = new EcouteurRecettes(this);

		listeRecettes.addMouseListener(ecouteur);
		listeRecettes.addMouseMotionListener(ecouteur);

		// Attacher la grille et le panneau a la fenetre support
		//
		panneauLivre.add(scrollRecettes);
		panneauLivre.add(panneauRecettes);
		panneauLivre.add(panneauImage);
		panneauLivre.add(decorationDroite);
		panneauLivre.add(decorationGauche);
		hamecon.obtenirFenetreSupport().getContentPane().add(panneauLivre, BorderLayout.CENTER);
		hamecon.obtenirFenetreSupport().getContentPane().repaint();
	}

	public JList obtenirListeCentres() {
		return listeRecettes;
	}

	private void ajouterPanneauRecette(Element configGrille) {
		panneauRecettes = new PanneauG(this, configGrille);

		panneauRecettes.setLayout(null);

		panneauRecettes.add(scrollTitre);
		titre.setFont(new Font(titre.getFont().getFamily(), titre.getFont().getStyle(), 30));

		panneauRecettes.add(scrollIngredients);
		ingredients.setFont(new Font(ingredients.getFont().getFamily(), ingredients.getFont().getStyle(), 20));

		panneauRecettes.add(scrollDescription);
		description.setFont(new Font(description.getFont().getFamily(), description.getFont().getStyle(), 20));
	}

	private boolean setRepertoiresRecettes() {

		listeRecettes = new JList();
		listeRecettes.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		repertoireRecettes = Dossier.chargerDirectory("../_Textes/Accueil/Recettes/");
		if (repertoireRecettes == null)
			return false;
		File fichier;
		Iterator i = repertoireRecettes.iterator();
		Vector nomRecettes = new Vector();
		while (i.hasNext()) {
			fichier = new File((String) i.next());
			nomRecettes.add((String) fichier.getName());
		}

		listeRecettes.setListData(nomRecettes);

		scrollRecettes = new JScrollPane(listeRecettes);
		scrollRecettes.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		scrollRecettes.setBackground(new Color(0, 0, 0, 0));
		scrollRecettes.setBorder(null);

		listeRecettes.setBackground(new Color(253, 252, 232, 255));

		return true;
	}

	private JTextArea creerTexteArea(String titre, Font font) {

		JTextArea resultat = new JTextArea(titre);
		resultat.setBackground(new Color(0, 0, 0, 0));
		resultat.setOpaque(true);

		resultat.setFont(font);
		resultat.setWrapStyleWord(true);
		resultat.setLineWrap(true);
		resultat.setEditable(false);
		return resultat;

	}

	private JTextPane creerTextePane(String titre, Font font) {

		JTextPane resultat = new JTextPane();
		resultat.setBackground(new Color(0, 0, 0, 0));
		resultat.setOpaque(true);

		StyledDocument doc = resultat.getStyledDocument();
		MutableAttributeSet center = new SimpleAttributeSet();
		StyleConstants.setAlignment(center, StyleConstants.ALIGN_CENTER);
		doc.setParagraphAttributes(0, 0, center, false);

		resultat.setFont(font);
		resultat.setEditable(false);
		return resultat;
	}

	public void afficherInfoCentre(int index) {

		if (index == -1)
			return;
		String repertoirRecette = (String) repertoireRecettes.get(index);
		Vector dossierCourant;

		if (panneauRecettes.presenceImage()) {
			panneauRecettes.retirerImage();

			titre.setBackground(new Color(253, 252, 232, 255));
			description.setBackground(new Color(253, 252, 232, 255));
			ingredients.setBackground(new Color(253, 252, 232, 255));
		}
		// Ouvrir le dossier contenant les informations du centre
		//
		dossierCourant = Dossier.chargerRepertoire(repertoirRecette, ".txt");
		if (dossierCourant != null) {

			// Verifier que le dossier contient trois fichiers
			//
			if (dossierCourant.size() != 3) {
				JOptionPane.showMessageDialog(null, Texte.load("../_Textes/Erreurs/Nombre fichiers"));
				return;
			}

			// Lister les differents fichiers textes du dossier
			//
			String cle = null;
			File file = null;
			String nomFichier = null;
			String cheminFichier = null;
			Iterator i = dossierCourant.iterator();
			while (i.hasNext()) {

				cle = (String) i.next();
				file = new File(cle);

				nomFichier = file.getName().substring(0, file.getName().length() - 4);
				cheminFichier = file.getAbsolutePath().substring(0, file.getAbsolutePath().length() - 4);

				if (nomFichier.equals("Titre"))
					titre.setText(Texte.load(cheminFichier));
				else if (nomFichier.equals("Ingredients"))
					ingredients.setText(Texte.load(cheminFichier));
				else if (nomFichier.equals("Description"))
					description.setText(Texte.load(cheminFichier));

			}
			titre.setCaretPosition(0);
			ingredients.setCaretPosition(0);
			description.setCaretPosition(0);

			hamecon.obtenirFenetreSupport().repaint();
		}
		
	}

	public String obtenirCheminImage(int index) {

		if (index == -1)
			return null;
		String repertoirRecette = (String) repertoireRecettes.get(index);
		Vector dossierCourant;

		// Ouvrir le dossier contenant les informations du centre
		//
		dossierCourant = Dossier.chargerRepertoire(repertoirRecette, ".jpg");
		if (dossierCourant != null) {

			// Verifier que le dossier contient une image
			//
			if (dossierCourant.size() < 1)
				return null;

			// Retourner
			//
			return (String) dossierCourant.iterator().next();
		}
		return null;
	}

	public void ajouterImage(String cheminImage) {
		panneauImage.ajouterImage(cheminImage);
		ImageIcon icon = new ImageIcon(cheminImage);

		int xalign = scrollRecettes.getX() + (scrollRecettes.getWidth() / 2);
		int yalign = scrollRecettes.getY() + scrollRecettes.getHeight();

		int hauteur = hamecon.obtenirFenetreSupport().getHeight();

		panneauImage.setBounds(xalign - (icon.getIconWidth() * (150 * hauteur / 768) / icon.getIconHeight() / 2), yalign, icon.getIconWidth()
				* (150 * hauteur / 768) / icon.getIconHeight(), (150 * hauteur / 768));

		decorationGauche.setBounds(scrollRecettes.getX(), yalign + (38 * hauteur / 768), panneauImage.getX() - scrollRecettes.getX(),
				(75 * hauteur / 768));

		decorationDroite.setBounds(xalign + (panneauImage.getWidth() / 2), yalign + (38 * hauteur / 768),
				panneauImage.getX() - scrollRecettes.getX(), (75 * hauteur / 768));
	}
}