/**
 * IUT de Nice / Departement informatique / Stage Archet
 * Annee 2009_2010 - Composant générique
 * 
 * @edition A : edition de base
 * 
 * @version 1.0.0
 * 
 *          version initial
 * 
 * @version 1.1.0
 * 
 *          Ajout d'ecouteur sur chaque boutons
 * 
 * @version 1.2.0
 * 
 *          Ajout d'accesseur de modification
 *          setImageBackground Ajout d'un ecran au clavier
 *          avec sa methode modifierTexteEcran
 * 
 * @version 1.3.0
 * 
 *          ajouterEcran, presenceEcran, retirerEcran
 *          styleClavierLettres retirerElement obtenirJFrame
 *          setVisible obtenirNombreElement
 * 
 * @version 1.4.0
 * 
 *          styleClavierImages : param Vector ou String
 *          modifierBordure, bordureBlack retirerElements
 * 
 * @version 1.5.0
 * 
 *          modifierCouleurElement
 * 
 * @edition B : mise en place de la gestion XML
 * 
 * @version 2.0.0
 * 
 *          modification du type de l'attribut config
 *          setFontStyleClavierLettres
 *          setForegroundStyleClavierLettres
 *          setBackgroundStyleClavierLettres
 * 
 * @version 2.1.0
 * 
 *          le clavier virtuel devient une fenetre et non
 *          plus un panneau ajout d'un panneauSupport ajout
 *          d'une methode ajouterImage
 * 
 */
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.File;
import java.util.List;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.border.Border;

import org.jdom.Element;

/**
 * Composant graphique permettant d'afficher un clavier
 * virtuel
 * 
 * @author Charles Fouco, Cedric Hulin
 * @version 2.1.0
 * 
 */
public class ClavierG extends JFrame {

	/**
	 * Configuration du panneau
	 * 
	 * @since 1.0.0
	 */
	private Element config;

	/**
	 * Ensemble des boutons
	 * 
	 * @since 1.0.0
	 */
	private JButton[] tabBoutons;

	/**
	 * Ecran
	 * 
	 * @since 1.1.0
	 */
	private JLabel ecran;

	/**
	 * panneauVisualisation de l'ecran
	 * 
	 * @since 1.2.0
	 */
	private PanneauG panneauVisualisation;

	/**
	 * tabPanneauG tableau d'images
	 * 
	 * @since 1.3.0
	 */
	private PanneauG[] tabPanneauG;

	/**
	 * Dimension des elements configurable
	 * 
	 * @since 2.0.0
	 */
	private Dimension dimensionElements;

	/**
	 * Panneau support
	 * 
	 * @since 2.1.0
	 */
	private PanneauG panneauSupport;

	/**
	 * Constructeur normal
	 * 
	 * @param hamecon
	 * @param config
	 * @since 1.0.0
	 */
	public ClavierG(Object hamecon, Element config) {

		// Memoriser les information du jeux
		//
		this.config = config;

		// Calculer les coordonnees de positionnement
		//
		int centreX = 0;
		int bottomY = 250;
		if (hamecon != null) {
			if (hamecon.getClass().getSimpleName().equals("JeuxGrille")) {
				centreX = ((JeuxGrille) hamecon).getX() + (((JeuxGrille) hamecon).getWidth());
				bottomY = ((JeuxGrille) hamecon).getY() + ((JeuxGrille) hamecon).getHeight();
			}
		}

		// Définir les proprietes de la fenetre
		//
		setTitle("Clavier virtuel");
		setSize(424, 250);
		setLocation(centreX, bottomY - 250);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// Creer le panneau support
		//
		panneauSupport = new PanneauG(hamecon, config);

		// Ajout d'une bordure au panneau principal
		//
		panneauSupport.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLoweredBevelBorder(), BorderFactory.createMatteBorder(1, 1,
				0, 0, Color.black)));

		// Ajouter le panneau support a la fenetre
		//
		this.getContentPane().add(panneauSupport, BorderLayout.CENTER);

		// Rendre visible le clavier
		//
		setVisible(true);
	}

	/**
	 * Ajout d'ecouteur sur chaque boutons
	 * 
	 * @param ecouteur
	 * @since 1.1.0
	 */
	public void ajouterEcouteur(Object ecouteur) {
		if (tabBoutons != null) {
			for (int i = 0; i < tabBoutons.length; i++) {
				tabBoutons[i].addMouseListener((MouseListener) ecouteur);
				tabBoutons[i].addMouseMotionListener((MouseMotionListener) ecouteur);
			}
		} else {
			if (tabPanneauG != null) {
				for (int i = 0; i < tabPanneauG.length; i++) {
					tabPanneauG[i].addMouseListener((MouseListener) ecouteur);
					tabPanneauG[i].addMouseMotionListener((MouseMotionListener) ecouteur);
				}
			}
		}
	}

	/**
	 * Modifier le texte de l'ecran
	 * 
	 * @param texte
	 *            texte a afficher sur l'ecran
	 * @return flag de reussite de la modification
	 * @since 1.2.0
	 */
	public boolean modifierTexteEcran(String texte) {
		if (texte == null)
			return false;
		if (ecran == null)
			return false;

		ecran.setText(texte);
		return true;
	}

	/**
	 * Modifier la taille des elements
	 * 
	 * @param dimension
	 *            Dimension a affecter aux boutons ou aux
	 *            images
	 * @return flag de reussite de la modification
	 * @since 1.2.0
	 */
	public boolean modifierTailleElements(Dimension dimension) {
		if (dimension == null)
			return false;
		if (tabBoutons != null)
			for (int i = 0; i < tabBoutons.length; i++)
				tabBoutons[i].setPreferredSize(dimension);
		if (tabPanneauG != null)
			for (int i = 0; i < tabPanneauG.length; i++)
				tabPanneauG[i].setPreferredSize(dimension);

		return true;
	}

	/**
	 * Modifier la couleur de l'ecran
	 * 
	 * @param couleur
	 *            Couleur de fond de l'ecran de
	 *            visualisation
	 * @return flag de reussite de la modification
	 * @since 1.2.0
	 */
	public boolean modifierCouleurEcran(Color couleur) {
		if (couleur == null)
			return false;

		panneauVisualisation.setBackground(couleur);
		return true;
	}

	/**
	 * Ajoute un ecran de visualisation au clavier
	 * 
	 * @since 1.3.0
	 */
	public void ajouterEcran() {

		// Creer un panneau qui servira d'ecran au clavier
		//
		panneauVisualisation = new PanneauG(this, config);
		panneauVisualisation.setLayout(new FlowLayout());

		// Definir un fond d'ecran blanc par defaut
		//
		panneauVisualisation.setBackground(Color.WHITE);

		// Ajout d'une bordure au panneau
		//
		panneauVisualisation.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLoweredBevelBorder(), BorderFactory.createMatteBorder(
				2, 2, 0, 0, Color.black)));

		// Definir les proprietes de l'ecran
		//
		ecran = new JLabel("Bienvenue sur le clavier virtuel");
		ecran.setBackground(Color.WHITE);
		ecran.setPreferredSize(new Dimension(800, 40));
		ecran.setHorizontalAlignment(SwingConstants.CENTER);
		ecran.setFont(new Font("Comic sans ms", Font.BOLD, 26));

		// Attacher le bouton ecran au panneau ecran
		//
		panneauVisualisation.add(ecran);

		// Ajouter le panneau ecran au frame
		//
		getContentPane().add(panneauVisualisation, BorderLayout.NORTH);
	}

	/**
	 * Determine la presence d'un ecran de visualisation
	 * 
	 * @return true si un ecran est present
	 * @since 1.3.0
	 */
	public boolean presenceEcran() {
		if (panneauVisualisation == null)
			return false;
		return true;
	}

	/**
	 * Retirer l'ecran de visualisation
	 * 
	 * @since 1.3.0
	 */
	public void retirerEcran() {
		if (presenceEcran())
			getContentPane().remove(this.panneauVisualisation);
	}

	/**
	 * Ajoute a la fenetre un clavier de lettres
	 * 
	 * @since 1.3.0
	 */
	public void styleClavierLettres() {

		// Initialiser les multiples boutons alphabet
		//
		tabBoutons = new JButton[27];

		for (int i = 0; i < 26; i++) {
			tabBoutons[i] = new JButton();
			tabBoutons[i].setPreferredSize(new Dimension(50, 30));
			panneauSupport.add(tabBoutons[i]);
			tabBoutons[i].addMouseListener(new EcouteurBouton(tabBoutons[i]));
		}

		int i = 0;
		for (char car = 'A'; car <= 'Z'; car++) {
			tabBoutons[i].setText(String.valueOf(car));
			i++;
		}

		// Ajouter un bouton Gomme
		//
		tabBoutons[26] = new JButton("", (Icon) new ImageIcon("../_Images/Bouton/Gomme.png"));
		tabBoutons[26].setPreferredSize(new Dimension(50, 30));
		panneauSupport.add(tabBoutons[26]);

		// Appliquer la configuration au clavier
		//
		this.setFontStyleClavierLettres(config);
		this.setForegroundStyleClavierLettres(config);
		this.setBackgroundStyleClavierLettres(config);
	}

	/**
	 * Ajouter a la fenetre un clavier d'images
	 * 
	 * @param cheminRepertoire
	 *            chemin vers le repertoire d'images
	 * @return flag de reussite a l'ajout du clavier
	 * @since 1.4.0
	 */
	public boolean styleClavierImages(String cheminRepertoire) {
		File repertoireImages = null;

		// Retirer les elements presents
		//
		retirerElements();

		// Definir la taille des elements
		//
		setDimensionElements(config);

		// Creer le repertoire abstrait cible
		//
		if (cheminRepertoire == null)
			repertoireImages = new File(".");
		else
			repertoireImages = new File(cheminRepertoire);

		// Controler l'existence du repertoire cible
		//
		if (repertoireImages == null)
			return false;

		// Controler l'existence du repertoire cible

		if (!repertoireImages.exists() && !repertoireImages.isDirectory()) {
			JOptionPane.showMessageDialog(null, Texte.load("../_Textes/Erreur/Dossier images"));
			return false;
		}

		// Obtenir la liste de tous les fichiers du
		// repertoire cible
		//
		File[] files = repertoireImages.listFiles();

		// Remplir un vecteur temporaire ordonnee
		//
		Vector fichiersImage = new Vector();
		for (int i = 0; i < files.length; i++)
			if (files[i].getName().indexOf(".jpg") > 0)
				fichiersImage.add(files[i].getAbsolutePath());

		tabPanneauG = new PanneauG[fichiersImage.size()];

		for (int i = 0; i < fichiersImage.size(); i++) {

			tabPanneauG[i] = new PanneauG(this, config);
			tabPanneauG[i].setBackground(new Color(0, 0, 0, 0));
			tabPanneauG[i].setPreferredSize(dimensionElements);
			tabPanneauG[i].ajouterImage((String) fichiersImage.get(i));
			panneauSupport.add(tabPanneauG[i]);
		}

		return true;
	}

	/**
	 * Ajouter a la fenetre un clavier d'images
	 * 
	 * @param donnees
	 *            vecteur comportant les liens vers les
	 *            images
	 * @return flag de reussite a l'ajout du clavier
	 * @since 1.4.0
	 */
	public boolean styleClavierImages(Vector donnees) {

		// Verifier le parametre
		//
		if (donnees == null)
			return false;

		if (donnees.size() == 0)
			return false;

		// Retirer les elements presents
		//
		retirerElements();

		// Definir la taille des elements
		//
		setDimensionElements(config);

		// Initialiser la taille du panneau d'images
		//
		tabPanneauG = new PanneauG[donnees.size()];

		// Ajouter le clavier d'images
		//
		for (int i = 0; i < donnees.size(); i++) {

			tabPanneauG[i] = new PanneauG(this, config);
			tabPanneauG[i].setBackground(new Color(0, 0, 0, 0));
			tabPanneauG[i].setPreferredSize(dimensionElements);
			tabPanneauG[i].ajouterImage((String) donnees.get(i));
			panneauSupport.add(tabPanneauG[i]);
		}

		return true;
	}

	/**
	 * Retire l'image du clavier passee en parametre
	 * 
	 * @param chemin
	 *            chemin vers l'image cible
	 * @since 1.3.0
	 */
	public void retirerElement(String chemin) {
		for (int i = 0; i < tabPanneauG.length; i++)
			if (tabPanneauG[i] != null)
				if (tabPanneauG[i].presenceImage())
					if (tabPanneauG[i].obtenirCheminImage().equals(chemin)) {
						panneauSupport.remove(tabPanneauG[i]);
					}
	}

	/**
	 * Obtenir la JFrame support
	 * 
	 * @return frameSupport
	 * @since 1.3.0
	 */
	public JFrame obtenirJFrame() {
		return this;
	}

	/**
	 * Rend visible ou non le panneau support
	 * 
	 * @param value
	 *            true ou false
	 * @since 1.3.0
	 */
	public void setVisible(boolean value) {
		super.setVisible(value);
		if (panneauSupport != null)
			panneauSupport.setVisible(value);
	}

	/**
	 * Obtenir le nombre d'elements
	 * 
	 * @return nombreElement
	 * @since 1.3.0
	 */
	public int obtenirNombreElement() {
		return panneauSupport.getComponentCount();
	}

	/**
	 * Modification des bordures pour le clavier d'images
	 * 
	 * @param bordure
	 *            nouvelle bordure a mettre en place
	 * @since 1.4.0
	 */
	public void modifierBordure(Border bordure) {
		if (tabPanneauG != null) {
			for (int i = 0; i < tabPanneauG.length; i++)
				tabPanneauG[i].setBorder(bordure);
		}
	}

	/**
	 * Definir une bordure simple noir autour des images
	 * 
	 * @since 1.4.0
	 */
	public void bordureBlack() {
		if (tabPanneauG != null) {
			for (int i = 0; i < tabPanneauG.length; i++)
				tabPanneauG[i].setBorder(BorderFactory.createCompoundBorder(BorderFactory.createMatteBorder(3, 3, 0, 0, Color.black), BorderFactory
						.createLoweredBevelBorder()));
		}
	}

	/**
	 * Retirer tous les elements du clavier
	 * 
	 * @since 1.4.0
	 */
	public void retirerElements() {

		if (tabBoutons != null) {
			for (int i = 0; i < tabBoutons.length; i++)
				if (tabBoutons[i] != null)
					panneauSupport.remove(tabBoutons[i]);
			tabBoutons = null;
		}

		if (tabPanneauG != null) {
			for (int i = 0; i < tabPanneauG.length; i++)
				if (tabPanneauG[i] != null)
					panneauSupport.remove(tabPanneauG[i]);
			tabPanneauG = null;
		}
	}

	/**
	 * Modification de la couleur de fond de chaque element
	 * du clavier
	 * 
	 * @param color
	 *            Couleur de fond a appliquer
	 * @since 1.5.0
	 */
	public void modifierCouleurElement(Color color) {
		if (tabBoutons != null)
			for (int i = 0; i < tabBoutons.length; i++)
				tabBoutons[i].setBackground(color);
		if (tabPanneauG != null)
			for (int i = 0; i < tabPanneauG.length; i++)
				tabPanneauG[i].setBackground(color);
	}

	/**
	 * Applique au clavier de lettres une font passee par
	 * fichier de configuration
	 * 
	 * @param config
	 *            fichier de configuration XML contenant la
	 *            police a appliquer
	 * @since 2.0.0
	 */
	private void setFontStyleClavierLettres(Element config) {

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
		for (int i = 0; i < tabBoutons.length; i++)
			tabBoutons[i].setFont(f);
	}

	/**
	 * Applique au clavier lettres une couleur de font via
	 * fichier de configuration
	 * 
	 * @param config
	 *            fichier de configuration XML contenant la
	 *            couleur de font a appliquer
	 * @since 2.0.0
	 */
	private void setForegroundStyleClavierLettres(Element config) {
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
		for (int i = 0; i < tabBoutons.length; i++)
			tabBoutons[i].setForeground(XML.obtenirColorFont(StringCouleur));
	}

	/**
	 * Applique au clavier lettres une couleur de fond pour
	 * les boutons
	 * 
	 * @param config
	 *            fichier de configuration XML contenant la
	 *            couleur de fond a appliquer
	 * @since 2.0.0
	 */
	private void setBackgroundStyleClavierLettres(Element config) {
		String StringCouleur;

		if (config == null) {
			panneauSupport.setBackground(Color.black);
			return;
		}

		if (config.getChild("ArrierePlan") == null) {
			panneauSupport.setBackground(Color.black);
			return;
		}
		StringCouleur = config.getChild("ArrierePlan").getTextTrim();

		for (int i = 0; i < tabBoutons.length; i++)
			tabBoutons[i].setBackground(XML.obtenirColorFont(StringCouleur));
	}

	/**
	 * Recupere la dimension du fichier XML la configuration
	 * 
	 * @param config
	 *            fichier de configuration XML contenant la
	 *            dimension
	 * @since 2.0.0
	 */
	private void setDimensionElements(Element config) {
		List noeudFils = null;
		Element elementCourant;

		// Verifier le parametre
		//
		if (config == null) {
			dimensionElements = new Dimension(64, 64);
			return;
		}

		// Verifier la presence de la cle
		//
		if (config.getChild("DimensionElements") == null) {
			dimensionElements = new Dimension(64, 64);
			return;
		}

		// Recuperer le noeud fils
		//
		noeudFils = config.getChildren("DimensionElements");
		if (noeudFils == null) {
			return;
		}
		elementCourant = (Element) noeudFils.iterator().next();

		// Verifier la presence des elements
		//
		if (elementCourant.getChild("x") == null || elementCourant.getChild("y") == null) {
			dimensionElements = new Dimension(64, 64);
			return;
		}

		// Recuperer les valeurs associees
		//
		String x = elementCourant.getChild("x").getTextTrim();
		String y = elementCourant.getChild("y").getTextTrim();

		// Recuperer la dimension
		//
		dimensionElements = new Dimension(Integer.valueOf(x).intValue(), Integer.valueOf(y).intValue());
		if (dimensionElements == null) {
			dimensionElements = new Dimension(64, 64);
			return;
		}
	}

	/**
	 * Obtention de la taille de l'ecran de visualisation
	 * 
	 * @return taille de l'ecran
	 * @since 2.0.0
	 */
	public Dimension obtenirTailleEcran() {
		if (!presenceEcran())
			return null;
		return ecran.getSize();
	}

	/**
	 * Modifier la taille de l'ecran de visualisation
	 * 
	 * @param dimension
	 *            Dimension a appliquer a l'ecran
	 * @since 2.0.0
	 */
	public void modifierTailleEcran(Dimension dimension) {
		if (!presenceEcran())
			return;
		ecran.setPreferredSize(dimension);
		ecran.setBounds(0, 0, (int) dimension.getWidth(), (int) dimension.getHeight());
	}

	/**
	 * Programme principal
	 * 
	 * @param args
	 * @since 2.0.0
	 */
	public static void main(String[] args) {
		Element configClavier = (Element) ConfigXML.load("Configuration/ConfigClavier", "1.0.0");
		if (configClavier == null)
			return;

		ClavierG c = new ClavierG(null, configClavier);
		c.ajouterEcran();
		c.styleClavierLettres();
		// c.styleClavierImages("../_Images/Produits Sucres");
	}

	/**
	 * Ajouter une image de fond au panneauSupport
	 * 
	 * @param chemin
	 *            chemin vers l'image de fond a appliquer
	 * @since 2.1.0
	 */
	public void ajouterImage(String chemin) {
		panneauSupport.ajouterImage(chemin);
	}
}
