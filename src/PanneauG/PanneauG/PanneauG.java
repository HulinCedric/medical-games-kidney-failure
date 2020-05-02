//
// IUT de Nice / Departement informatique / Module APO-Java
// Annee 2009_2010 - Package SWING
//
// Classe PanneauG - Panneau generique de visualisation
//
// Edition A : Cours_10
// + Version 1.0.0 : classe externalisee de l'application
// FenetreImage,
// avec une taille d'image ajustee a celle du panneau
// + Version 1.1.0 : mise en place d'un dictionnaire de
// configuration
// + Version 1.2.0 : introduction d'un titre pour le panneau
// (option)
// + Version 1.3.0 ; gestion du titre deplacee dans une
// classe externe
// + ajout methode publique presenceImage
// + Version 1.4.0 : gestion de l'image deplacee dans une
// classe externe
//
// Edition B : introduction d'un ecouteur souris du panneau
// (option)
// + Version 2.0.0 : implementation des deux interfaces
// standards par la
// classe PanneauG
// + Version 2.1.0 ; gestion de la souris deplacee dans une
// classe interne
// + ajout des methodes publiques ajouterEcouteur et
// retirerEcouteur
// + Version 2.2.0 ; gestion de la souris deplacee dans une
// classe externe
// + Version 2.3.0 : externalisation de la creation de
// l'ecouteur
//
// Edition C : introduction des exigences des applications
// cibles des TP
// + Version 3.0.0 : introduction d'un maillage
// rectangulaire, regulier et
// et couvrant du panneau, reactif aux actions operateur
// (option / classe externe)
// + Version 3.1.0 : possibilite de designer une maille au
// moyen d'un clic
// souris (avec EcouteurPanneauG - V 1.1.0)
// + Version 3.2.0 : modification du type de l'attribut
// hamecon pour
// pouvoir inclure un panneau generique dans un autre
// panneau generique
// + Version 3.3.0 : ajout accesseurs de consultation
// suivants :
// + obtenirCheminImage,
// + obtenirTexteTitre,
// + obtenirCouleurTitre,
// + obtenirPoliceTitre
// + Version 3.4.0 : gestion des clis souris en l'absence de
// maillage
// (correction bug dans methode obtenirPositionCible)
// + Version 3.5.0 : ajout de presenceTitre
//
// Edition D : mise en place de la gestion en configuration
// de fichiers xml
// + version 4.0.0 : methodes set
// 
// Auteur : A. Thuaire, C. Fouco, C. Hulin
//

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.HashMap;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;

import org.jdom.Element;

public class PanneauG extends JPanel {
	private Object hamecon;
	private TitrePanneauG titre;
	private ImagePanneauG image;
	private Object ecouteur;
	private MaillagePanneauG maillage;

	private ImagePanneauG[] images;

	// ------ *** Constructeur normal

	public PanneauG(Object hamecon, Object config) {

		// Memoriser l'hamecon vers le conteneur amont
		//
		this.hamecon = hamecon;

		// Verifier la presence de configuration
		//
		if (config == null)
			return;

		// Configurer la partie statique
		//
		if (config.getClass().getSimpleName().equals("HashMap")) {
			setBackground((HashMap) config);
			setForeground((HashMap) config);
			setFont((HashMap) config);
		} else if (config.getClass().getSimpleName().equals("Element")) {
			setBackground((Element) config);
			setForeground((Element) config);
			setFont((Element) config);
			// setNombreImages((Element) config);
		}

	}

	// ------ *** Methode paint

	public void paint(Graphics g) {

		// Transmettre le contexte courant
		//
		super.paint(g);

		// Dessiner le titre du panneau
		//
		if (titre != null)
			titre.dessiner(g);

		// Dessiner le maillage du panneau
		//
		if (maillage != null)
			maillage.dessiner(g);
	}

	/**
	 * Surcharge de la methode paintComponent permet a l'image d'etre en
	 * background
	 * 
	 * @since 3.6.0
	 */
	protected void paintComponent(Graphics g) {

		super.paintComponent(g);

		// Dessiner l'image de fond
		//
		if (image != null)
			image.dessiner(g);

		// Dessiner l'image de fond
		//
		if (images != null) {
			for (int i = 0; i < images.length; i++)
				if (images[i] != null)
					images[i].dessiner(g);
		}
	}

	// ------ *** Methode main

	public static void main(String[] args) {

		// Creer un cadre support
		//   	
		JFrame f = new JFrame();
		f.setTitle("(SWING) Classe PanneauG - V 3.3.0");
		f.setSize(500, 300);
		f.setLocation(350, 350);

		// Charger le dictionnaire de configuration d'un
		// panneau
		//   	
		// HashMap config = (HashMap)
		// Config.load("ConfigPanneauG", "1.0.0");
		Element config = (Element) ConfigXML.load("ConfigPanneauG", "1.0.0");

		// Creer et configurer un panneau
		//
		PanneauG panneau = new PanneauG(f, config);

		// Ajouter une image de fond
		//
		panneau.ajouterImage("Campanules.jpg");

		// Creer et ajouter un ecouteur de souris par defaut
		//
		panneau.ajouterEcouteur(new EcouteurPanneauG(panneau));

		// Montrer le cadre support et son panneau interne
		//
		f.getContentPane().add(panneau);
		f.setVisible(true);
	}

	// ------ *** Accesseurs de consultation

	public String obtenirCheminImage() {
		return image.obtenirCheminImage();
	}

	public String obtenirTexteTitre() {
		return titre.obtenirTexteTitre();
	}

	public Color obtenirCouleurTitre() {
		return titre.obtenirCouleurTitre();
	}

	public Font obtenirPoliceTitre() {
		return titre.obtenirPoliceTitre();
	}

	public int obtenirNombreImagesAllocation() {

		if (images == null)
			return 0;

		return images.length;
	}

	public int obtenirNombreImagesPresentent() {

		int resultat = 0;
		for (int i = 0; i < obtenirNombreImagesAllocation(); i++) {
			if (images[i] != null)
				resultat++;
		}
		return resultat;

	}

	// ------ *** Methodes setBackground

	private void setBackground(HashMap config) {
		Object w;

		if (config == null) {
			setBackground(Color.black);
			return;
		}
		w = config.get("arrierePlan");
		if (w == null) {
			setBackground(Color.black);
			return;
		}
		setBackground((Color) w);
	}

	private void setBackground(Element config) {
		String StringCouleur;

		if (config == null) {
			setBackground(Color.black);
			return;
		}

		if (config.getChild("ArrierePlan") == null) {
			setBackground(Color.black);
			return;
		}
		StringCouleur = config.getChild("ArrierePlan").getTextTrim();

		this.setBackground(XML.obtenirColorFont(StringCouleur));
	}

	// ------ *** Methodes setForeground

	private void setForeground(HashMap config) {
		Object w;

		if (config == null) {
			setForeground(Color.yellow);
			return;
		}
		w = config.get("avantPlan");
		if (w == null) {
			setForeground(Color.yellow);
			return;
		}
		setForeground((Color) w);
	}

	private void setForeground(Element config) {
		String StringCouleur;

		// Verifier le parametre
		//
		if (config == null) {
			setForeground(Color.YELLOW);
			return;
		}

		// Verifier la presence de la cle
		//
		if (config.getChild("AvantPlan") == null) {
			setForeground(Color.YELLOW);
			return;
		}

		// Recuperer la valeur associee
		//
		StringCouleur = config.getChild("AvantPlan").getTextTrim();

		// Affecter la nouvelle couleur
		//
		setForeground(XML.obtenirColorFont(StringCouleur));
	}

	// ------ *** Methodes setFont

	private void setFont(HashMap config) {
		Object w;

		if (config == null)
			return;
		w = config.get("police");
		if (w == null)
			return;
		setFont((Font) w);
	}

	private void setFont(Element config) {
		List noeudFils = null;
		Element elementCourant;

		// Verifier le parametre
		//
		if (config == null) {
			return;
		}

		// Verifier la presence de la cle
		//
		if (config.getChild("Police") == null) {
			return;
		}

		// Recuperer le noeud fils
		//
		noeudFils = config.getChildren("Police");
		if (noeudFils == null) {
			return;
		}
		elementCourant = (Element) noeudFils.iterator().next();

		// Verifier la presence des elements
		//
		if (elementCourant.getChild("Famille") == null
				|| elementCourant.getChild("Style") == null
				|| elementCourant.getChild("Taille") == null)
			return;

		// Recuperer les valeurs associees
		//
		String Famille = elementCourant.getChild("Famille").getTextTrim();
		String Style = elementCourant.getChild("Style").getTextTrim();
		String Taille = elementCourant.getChild("Taille").getTextTrim();

		// Creer la nouvelle font
		//
		Font f = new Font(Famille, XML.obtenirStyleFont(Style), (int) Integer
				.valueOf(Taille).intValue());
		if (f == null)
			return;

		// Affecter la nouvelle font
		//
		setFont(f);
	}

	private void setNombreImages(Element config) {
		int nombreImages;

		// Verifier le parametre
		//
		if (config == null) {
			return;
		}

		// Verifier la presence de la cle
		//
		if (config.getChild("NombreImages") == null) {
			return;
		}

		// Recuperer la valeur associee
		//
		nombreImages = Integer.valueOf(
				config.getChild("NombreImages").getTextTrim()).intValue();

		// Verifier la valeur recepurer
		//
		if (nombreImages <= 0)
			return;

		// Affecter la nouvelle couleur
		//
		images = new ImagePanneauG[nombreImages];
	}

	public boolean modifierNombreImages(int nombreImages) {

		// Verifier la validite du parametre
		//
		if (nombreImages <= 0)
			return false;

		images = new ImagePanneauG[nombreImages];
		return true;
	}

	// ------ *** Methode ajouterTitre

	public boolean ajouterTitre(String texte, Color couleur, Font police) {

		// Controler la validite des parametres
		//
		if (texte == null)
			return false;
		if (couleur == null)
			return false;

		// Creer une instance de la classe externe
		//
		titre = new TitrePanneauG(this, texte, couleur, police);

		// Repeindre le panneau
		//
		repaint();
		return true;
	}

	// ------ *** Methode retirerTitre

	public void retirerTitre() {
		titre = null;
		repaint();
	}

	// ------ *** Methodes ajouterImage

	public void ajouterImage(String chemin) {

		image = new ImagePanneauG(this, chemin);
		repaint();
	}

	public void ajouterImage(String chemin, int index, int decalageEntreImages,
			int decalageHauteur, int decalageCoter) {

		images[index] = new ImagePanneauG(this, chemin, index,
				decalageEntreImages, decalageHauteur, decalageCoter);
		repaint();
	}

	// ------ *** Methode retirerImage

	public void retirerImage() {
		image = null;
		repaint();
	}

	public void retirerImages() {
		images = null;
		repaint();
	}

	// ------ *** Methode presenceImage

	public boolean presenceImage() {

		if (image == null)
			return false;
		return true;
	}

	/**
	 * 
	 * @return flag de presence
	 * @since 3.5.0
	 */
	public boolean presenceTitre() {

		if (titre == null)
			return false;
		return true;
	}

	// --- *** Methode ajouterEcouteur

	public void ajouterEcouteur(Object ecouteur) {

		addMouseListener((MouseListener) ecouteur);
		addMouseMotionListener((MouseMotionListener) ecouteur);
	}

	// --- *** Methode retirerEcouteur

	public void retirerEcouteur() {

		removeMouseListener((MouseListener) ecouteur);
		removeMouseMotionListener((MouseMotionListener) ecouteur);
	}

	// ------ *** Methode ajouterMaillage

	public boolean ajouterMaillage(Color couleurTrait, Color couleurFond,
			int nbLignes, int nbColonnes) {

		// Controler la validite des parametres
		//
		if (couleurTrait == null)
			return false;
		if (nbLignes <= 0)
			return false;
		if (nbColonnes <= 0)
			return false;

		// Construire et memoriser le maillage
		//
		maillage = new MaillagePanneauG(this, couleurTrait, couleurFond,
				nbLignes, nbColonnes);

		// Repeindre le panneau
		//
		repaint();
		return true;
	}

	// ------ *** Methode retirerMaillage

	public void retirerMaillage() {
		maillage = null;
		repaint();
	}

	// ------ *** Methode obtenirMaillage

	public MaillagePanneauG obtenirMaillage() {
		return maillage;
	}

	// ------ *** Methode presenceMaillage

	public boolean presenceMaillage() {

		if (maillage == null)
			return false;
		return true;
	}

	// ------ *** Methode obtenirPositionCible

	public Dimension obtenirPositionCible(int x, int y) {

		if (!presenceMaillage())
			return new Dimension(x, y);
		return maillage.obtenirPositionCible(x, y);
	}
}
