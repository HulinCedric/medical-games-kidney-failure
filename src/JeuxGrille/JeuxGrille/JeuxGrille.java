/**
 * IUT de Nice / Departement informatique / Module APO-Java
 * Annee 2010_2011 - Package SWING
 * 
 * @edition A : edition initial
 * 
 * @version 1.0.0 :
 * 
 *          regroupement d'objet necessaire dans une classe
 *          pour construire un jeu de grille
 * 
 * @version 1.1.0 :
 * 
 *          attachement d'un ecouteur au clavier et a la
 *          grille
 * 
 * @version 1.2.0 :
 * 
 *          ajout d'une methode private setTailleEcran
 * 
 * @version 1.3.0 :
 * 
 *          ajout d'un attribut panneauCentral et ses
 *          methodes
 * 
 * @version 1.4.0 :
 * 
 *          ajout d'un nouveau stylle arc en ciel
 *          possibilite de retirer le panneau sud ainsi que
 *          les boutons du panneau
 *          
 * @version 1.5.0 :
 * 
 *          modifierTitreFenetre
 * 
 * @author Charles Fouco, Cedric Hulin
 */

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.MouseListener;
import java.util.HashMap;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;

import org.jdom.Element;

/**
 * Conteneur et service pour les jeux de grille
 * 
 * @author Charles Fouco, Cedric Hulin
 * @version 1.5.0
 */
public class JeuxGrille extends JFrame {

	/**
	 * Configuration de la grille
	 * 
	 * @since 1.0.0
	 */
	private Object configGrille;

	/**
	 * Grille du jeu
	 * 
	 * @since 1.0.0
	 */
	private GrilleG grille;

	/**
	 * Clavier du jeu
	 * 
	 * @since 1.0.0
	 */
	private ClavierG clavier;

	/**
	 * Panneau d'informations du jeu
	 * 
	 * @since 1.0.0
	 */
	private PanneauInformations panneauInformations;

	/**
	 * Bouton selectionne
	 * 
	 * @since 1.0.0
	 */
	private JButton boutonSelectionne;

	/**
	 * Panneau situe au Nord
	 * 
	 * @since 1.1.0
	 */
	private PanneauG panneauNord;

	/**
	 * Panneau situe au Sud
	 * 
	 * @since 1.1.0
	 */
	private PanneauG panneauSud;

	/**
	 * Panneau situe au centre
	 * 
	 * @since 1.3.0
	 */
	private PanneauG panneauCentral;

	/**
	 * Bouton situe a gauche dans le panneau nord
	 * 
	 * @since 1.1.0
	 */
	private JButton boutonGauche;

	/**
	 * Bouton situe au centre dans le panneau nord
	 * 
	 * @since 1.1.0
	 */
	private JButton boutonCentre;

	/**
	 * Bouton situe a droite dans le panneau nord
	 * 
	 * @since 1.1.0
	 */
	private JButton boutonDroite;

	/**
	 * Bouton situe dans le panneau sud
	 * 
	 * @since 1.1.0
	 */
	private JButton boutonEnregistrer;

	/**
	 * Bouton situe dans le panneau sud
	 * 
	 * @since 1.1.0
	 */
	private JButton boutonCharger;

	/**
	 * Bouton situe dans le panneau sud
	 * 
	 * @since 1.1.0
	 */
	private JButton boutonSolution;

	/**
	 * Bouton situe dans le panneau sud
	 * 
	 * @since 1.1.0
	 */
	private JButton boutonCorrection;

	/**
	 * Bouton qui gere le mode d'affichage
	 * 
	 * @since 1.3.0
	 */
	private boolean admin;

	/**
	 * Constructeur normal
	 * 
	 * @param configGrille
	 * @since 1.0.0
	 */
	public JeuxGrille(Object configGrille) {

		// Choix d'un mode :
		// - Creation
		// - Joueur
		//
		// Demander le type de chargement voulu :
		// - Nouvelle grille
		// - Partie sauvegardee
		//
		Object[] options = { "Creation", "Joueur" };
		String retour = null;
		retour = (String) JOptionPane.showInputDialog(this, "Choisissez un mode : ", "Choix du mode", JOptionPane.QUESTION_MESSAGE, null, options,
				options[0]);

		// Definir le repertoire a charger
		// en fonction du l'option selectionnee
		//
		if (retour != null) {

			if (retour.equals(options[0]))
				admin = true;
			else
				admin = false;

		} else
			return;

		this.configGrille = configGrille;

		grille = new GrilleG(this, configGrille);

		boutonSelectionne = null;

		Dimension tailleEcran = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
		double largeur = tailleEcran.getWidth();
		double hauteur = tailleEcran.getHeight();
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		GraphicsDevice gd = ge.getDefaultScreenDevice();
		GraphicsConfiguration gc = gd.getDefaultConfiguration();
		Insets I = Toolkit.getDefaultToolkit().getScreenInsets(gc);

		if (largeur / hauteur == 4.0 / 3.0) {
			setSize((int) (largeur * 600 / 1024), (int) tailleEcran.getHeight() - I.bottom);
			setLocation(0, 0);
		} else {
			setSize((int) (650), (int) tailleEcran.getHeight() - I.bottom);
			setLocation((int) (largeur - (this.getWidth() + 424)) / 2, 0);
		}

		setTitle("Jeu de grille");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// Ajouter un panneau nord et un panneau sud a la
		// fenetre de jeu
		//
		ajouterPanneauNord(Color.DARK_GRAY);
		ajouterPanneauSud(Color.DARK_GRAY);
		ajouterPannneauCentral(Color.DARK_GRAY);
	}

	/**
	 * setTailleFenetre
	 * 
	 * @param config
	 * 
	 * @since 1.2.0
	 */
	private void setTailleFenetre(Element config) {
		Object w;

		if (config == null) {
			setSize(550, 665);
			return;
		}
		if (config.getChild("TailleFenetre") == null) {
			setSize(550, 665);
			return;
		}

		// setSize((Dimension) w);

		/*
		 * value =
		 * Integer.valueOf(config.getChild("Colonnes").getTextTrim()).intValue
		 * ();
		 * 
		 * nbColonnes = value;
		 */
	}

	/**
	 * setTailleFenetre
	 * 
	 * @param config
	 * 
	 * @since 1.2.0
	 */
	private void setTailleFenetre(HashMap config) {
		Object w;

		if (config == null) {
			setSize(550, 665);
			return;
		}
		w = config.get("TailleFenetre");
		if (w == null) {
			setSize(550, 665);
			return;
		}

		setSize((Dimension) w);
	}

	/**
	 * setModeAdmin
	 * 
	 * @param config
	 * 
	 * @since 1.2.0
	 */
	private void setModeAdmin(HashMap config) {
		Object w;

		if (config == null) {
			admin = false;
			return;
		}
		w = config.get("ModeAdmin");
		if (w == null) {
			admin = false;
			return;
		}

		admin = true;
	}

	/**
	 * Obtenir le Clavier
	 * 
	 * @return clavier
	 * @since 1.0.0
	 */
	public ClavierG obtenirClavier() {
		return clavier;
	}

	/**
	 * Obtenir la grille
	 * 
	 * @return grille
	 * @since 1.0.0
	 */
	public GrilleG obtenirGrille() {
		return grille;
	}

	/**
	 * Obtenir le panneau d'informations
	 * 
	 * @return panneau d'informations
	 * @since 1.0.0
	 */
	public PanneauInformations obtenirPanneauInformations() {
		return panneauInformations;
	}

	/**
	 * Modifier la grille de jeu
	 * 
	 * @param grille
	 * @since 1.0.0
	 */
	public void modifierGrille(GrilleG grille) {
		this.grille = grille;
	}

	/**
	 * Modifier le bouton selectionne
	 * 
	 * @param bouton
	 * @since 1.0.0
	 */
	public void modifierBoutonSelectionne(JButton bouton) {
		boutonSelectionne = bouton;
	}

	/**
	 * Obtenir le bouton selectionne
	 * 
	 * @return bouton selectionne
	 * @since 1.0.0
	 */
	public JButton obtenirBoutonSelectionne() {
		return boutonSelectionne;
	}

	/**
	 * Obtenir la configuration de la grille
	 * 
	 * @return configuration de la grill
	 * @since 1.0.0
	 */
	public HashMap obtenirConfigurationGrille() {
		return (HashMap) configGrille;
	}

	/**
	 * Obtenir la configuration de la grille
	 * 
	 * @return configuration de la grill
	 * @since 2.0.0
	 */
	public Element obtenirConfigurationGrilleXML() {
		return (Element) configGrille;
	}

	/**
	 * Ajouter un clavier
	 * 
	 * @param configClavier
	 * @since 1.0.0
	 */
	public void ajouterClavier(Element configClavier) {
		clavier = new ClavierG(this, configClavier);
	}

	/**
	 * Ajouter un panneau d'information
	 * 
	 * @param configPanneauInfo
	 * @since 1.0.0
	 */
	public void ajouterPanneauInformation(Element configPanneauInfo) {
		panneauInformations = new PanneauInformations(this, configPanneauInfo);

		if (admin)
			panneauInformations.editable();
	}

	/**
	 * Ajout d'un panneau au nord contenant divers boutons et images
	 * 
	 * @param couleur
	 * @since 1.0.0
	 */
	private void ajouterPanneauNord(Color couleur) {

		// Creation du panneau nord
		//
		panneauNord = new PanneauG(this, configGrille);
		panneauNord.setBackground(couleur);
		panneauNord.ajouterImage("../_Images/Banniere/BanniereNordBoy.png");

		// Ajout d'une grille de disposition
		//
		panneauNord.setLayout(new FlowLayout());

		// Creation des boutons
		//
		boutonGauche = creerBouton("../_Images/Bouton/Yoshi.png", "Facile", "Comic sans ms", 28, Color.black);
		boutonCentre = creerBouton("../_Images/Bouton/Mario.png", "Moyen", "Comic sans ms", 28, Color.black);
		boutonDroite = creerBouton("../_Images/Bouton/Bowser.png", "Difficile", "Comic sans ms", 28, Color.black);

		boutonGauche.addMouseListener(new EcouteurBouton(boutonGauche));
		boutonCentre.addMouseListener(new EcouteurBouton(boutonCentre));
		boutonDroite.addMouseListener(new EcouteurBouton(boutonDroite));

		/*
		 * boutonGauche.addMouseListener(new EcouteurChoixNiveau(this));
		 * boutonCentre.addMouseListener(new EcouteurChoixNiveau(this));
		 * boutonDroite.addMouseListener(new EcouteurChoixNiveau(this));
		 */

		// Ajout des boutons au panneau
		//        
		panneauNord.add(boutonGauche);
		panneauNord.add(boutonCentre);
		panneauNord.add(boutonDroite);

		// Ajout d'une bordure au panneau
		//
		panneauNord.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLoweredBevelBorder(), BorderFactory.createMatteBorder(1, 1, 0,
				0, Color.black)));

		// Ajout du panneau au cadre support
		//
		getContentPane().add(panneauNord, BorderLayout.NORTH);
	}

	/**
	 * Ajouter un panneau au sud contenant divers bouton et images
	 * 
	 * @param couleur
	 * @since 1.0.0
	 */
	private void ajouterPanneauSud(Color couleur) {

		// Creation du panneau sud
		//
		panneauSud = new PanneauG(this, configGrille);
		panneauSud.setBackground(couleur);
		panneauSud.ajouterImage("../_Images/Banniere/BanniereSudBoy.png");

		// Ajout d'une grille de disposition
		//
		// panneauSud.setLayout(new FlowLayout());

		// Creer le bouton enregistrer
		//
		boutonEnregistrer = creerBouton("../_Images/Bouton/Enregistrer.png", "Enregistrer", "Comic sans ms", 15, Color.black);
		boutonEnregistrer.addMouseListener(new EcouteurJeuxGrilleEnregistrer(this));
		boutonEnregistrer.addMouseListener(new EcouteurBouton(boutonEnregistrer));

		// L'ajouter au panneau sud
		//
		panneauSud.add(boutonEnregistrer);

		// Affichage selon le mode admin
		//
		if (!admin) {

			boutonCharger = creerBouton("../_Images/Bouton/Charger.png", "Charger", "Comic sans ms", 15, Color.black);
			boutonSolution = creerBouton("../_Images/Bouton/Solution.png", "Solution", "Comic sans ms", 15, Color.black);
			boutonCorrection = creerBouton("../_Images/Bouton/Correction.png", "Correction", "Comic sans ms", 15, Color.black);

			boutonCharger.addMouseListener(new EcouteurJeuxGrilleCharger(this));

			boutonSolution.addMouseListener(new EcouteurBouton(boutonSolution));
			boutonCorrection.addMouseListener(new EcouteurBouton(boutonCorrection));
			boutonCharger.addMouseListener(new EcouteurBouton(boutonCharger));

			panneauSud.add(boutonSolution);
			panneauSud.add(boutonCorrection);
			panneauSud.add(boutonCharger);
		}

		panneauSud.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLoweredBevelBorder(), BorderFactory.createMatteBorder(1, 1, 0, 0,
				Color.black)));
		this.getContentPane().add(panneauSud, BorderLayout.SOUTH);
	}

	/**
	 * Ajouter un panneau centrale contenant la grille
	 * 
	 * @param couleur
	 * @since 1.0.0
	 */
	private void ajouterPannneauCentral(Color couleur) {

		// Ajout d'une bordure a la grille
		//
		obtenirGrille().setBorder(
				BorderFactory.createCompoundBorder(BorderFactory.createLoweredBevelBorder(), BorderFactory.createMatteBorder(0, 0, 1, 1, couleur)));

		// Ajout de la grille au cadre support
		//
		getContentPane().add(obtenirGrille(), BorderLayout.CENTER);
	}

	public boolean ajouterBouton(PanneauG panneauCible, MouseListener ecouteur, String cheminImage, String texteBouton, String policeTexte,
			int tailleTexte, Color couleurTexte) {
		// Verification des parametre
		//
		if (!panneauCible.equals(panneauSud) && !panneauCible.equals(panneauNord))
			return false;

		JButton bouton = creerBouton(cheminImage, texteBouton, policeTexte, tailleTexte, couleurTexte);

		bouton.addMouseListener(ecouteur);
		bouton.addMouseListener(new EcouteurBouton(bouton));

		panneauCible.add(bouton);

		return true;
	}

	/**
	 * Modification du texte d'un bouton
	 * 
	 * @param bouton
	 * @param texte
	 * @return flag de reussite
	 * @since 1.1.0
	 */
	public boolean modifierTexteBouton(JButton bouton, String texte) {
		if (bouton == null)
			return false;
		if (texte == null)
			return false;

		bouton.setText(texte);

		return true;
	}

	/**
	 * Modification de la position du texte d'un bouton
	 * 
	 * @param bouton
	 * @param position
	 * @return flag de reussite
	 * @since 1.1.0
	 */
	public boolean modifierPositionTexteBouton(JButton bouton, int position) {
		if (bouton == null)
			return false;
		if (position != SwingConstants.CENTER && position != SwingConstants.BOTTOM && position != SwingConstants.TOP)
			return false;

		bouton.setVerticalTextPosition(position);
		bouton.setHorizontalTextPosition(SwingConstants.CENTER);

		return true;
	}

	/**
	 * Modification du la police d'un bouton
	 * 
	 * @param bouton
	 * @param font
	 * @return flag de reussite
	 * @since 1.1.0
	 */
	public boolean modifierPoliceBouton(JButton bouton, String font) {
		if (bouton == null)
			return false;
		if (font == null)
			return false;

		bouton.setFont(new Font(font, Font.BOLD, 24));
		return true;
	}

	/**
	 * Modification de la police detailles d'un bouton
	 * 
	 * @param bouton
	 * @param font
	 * @return flag de reussite
	 * @since 1.1.0
	 */
	public boolean modifierPoliceBouton(JButton bouton, Font font) {
		if (bouton == null)
			return false;
		if (font == null)
			return false;

		bouton.setFont(font);
		return true;
	}

	/**
	 * Modification de la couleur du texte d'un bouton
	 * 
	 * @param bouton
	 * @param couleur
	 * @return flag de reussite
	 * @since 1.1.0
	 */
	public boolean modifierCouleurTexteBouton(JButton bouton, Color couleur) {
		if (bouton == null)
			return false;
		if (couleur == null)
			return false;

		bouton.setForeground(couleur);
		return true;
	}

	/**
	 * Obtention du bouton de gauche du panneau nord
	 * 
	 * @return bouton de gauche
	 * @since 1.1.0
	 */
	public JButton obtenirBoutonGauche() {
		return boutonGauche;
	}

	/**
	 * Obtention du bouton central du panneau nord
	 * 
	 * @return bouton central
	 * @since 1.1.0
	 */
	public JButton obtenirBoutonCentre() {
		return boutonCentre;
	}

	/**
	 * Obtention du bouton de droite du panneau nord
	 * 
	 * @return bouton de droite
	 * @since 1.1.0
	 */
	public JButton obtenirBoutonDroite() {
		return boutonDroite;
	}

	/**
	 * Obtention du bouton enregistrer du panneau sud
	 * 
	 * @return bouton nouvelle
	 * @since 1.1.0
	 */
	public JButton obtenirBoutonEnregistrer() {
		return boutonEnregistrer;
	}

	/**
	 * Obtention du bouton charger du panneau sud
	 * 
	 * @return bouton charger
	 * @since 1.1.0
	 */
	public JButton obtenirBoutonCharger() {
		return boutonCharger;
	}

	/**
	 * Obtention du bouton solution du panneau sud
	 * 
	 * @return bouton solution
	 * @since 1.1.0
	 */
	public JButton obtenirBoutonSolution() {
		return boutonSolution;
	}

	/**
	 * Obtention du bouton correction du panneau sud
	 * 
	 * @return bouton correction
	 * @since 1.1.0
	 */
	public JButton obtenirBoutonCorrection() {
		return boutonCorrection;
	}

	/**
	 * Modification de l'image du bouton
	 * 
	 * @param bouton
	 * @param chemin
	 * @return flag de reussite
	 * @since 1.1.0
	 */
	public boolean modifierImageBouton(JButton bouton, String chemin) {
		if (bouton == null)
			return false;
		if (chemin == null)
			return false;

		bouton.setIcon((Icon) new ImageIcon(chemin));
		return true;
	}

	public boolean supprimerEcouteurBouton(JButton bouton, MouseListener[] listeEcouteurs, String ecouteurASuppriper) {
		if (bouton == null)
			return false;
		if (listeEcouteurs == null)
			return false;
		// if(nouvelEcouteur == null) return false;

		for (int i = 0; i < listeEcouteurs.length; i++) {
			if (listeEcouteurs[i].getClass().getSimpleName().equals(ecouteurASuppriper))
				bouton.removeMouseListener(listeEcouteurs[i]);
		}

		return true;

	}

	/**
	 * Creation de bouton a l'initialisation d'un panneau
	 * 
	 * @param icone
	 * @param texte
	 * @param font
	 * @param couleur
	 * @return bouton
	 * @since 1.1.0
	 */
	public static JButton creerBouton(String icone, String texte, String font, int tailleFont, Color couleur) {

		// Creer le bouton resultat
		//
		JButton bouton = new JButton("Bouton");

		// Ajout d'une icon
		//
		Icon icon = null;
		icon = new ImageIcon(icone);
		if (icon != null)
			bouton.setIcon(icon);

		// Ajout du texte
		//
		if (texte != null)
			bouton.setText(texte);

		// Modification de la police
		//
		if (font != null)
			bouton.setFont(new Font(font, Font.BOLD, tailleFont));

		// Definition de la position du texte
		//
		bouton.setVerticalTextPosition(SwingConstants.BOTTOM);
		bouton.setHorizontalTextPosition(SwingConstants.CENTER);

		// Definir la couleur du texte
		//
		bouton.setForeground(couleur);

		// Enlever le contour du bouton
		//
		bouton.setFocusPainted(false);
		bouton.setBorderPainted(false);
		bouton.setContentAreaFilled(false);

		// Retourner le bouton resultant
		//
		return bouton;
	}

	/**
	 * Application d'un style feminin au jeu de grille
	 * 
	 * @since 1.1.0
	 */
	public void styleFeminin() {

		modifierImageBouton(obtenirBoutonGauche(), "../_Images/Bouton/Kitty.png");
		modifierImageBouton(obtenirBoutonCentre(), "../_Images/Bouton/Hamtaro.png");
		modifierImageBouton(obtenirBoutonDroite(), "../_Images/Bouton/Peach.png");

		modifierFondPanneauNord("../_Images/Banniere/BanniereNordGirl.png");
		modifierFondPanneauSud("../_Images/Banniere/BanniereSudGirl.png");
		modifierFondClavier("../_Images/Banniere/BanniereClavierGirl.png");
		modifierFondPanneauInformations("../_Images/Banniere/BannierePanneauInformationsGirl.png");
	}

	/**
	 * Application d'un style masculin au jeu de grille
	 * 
	 * @since 1.1.0
	 */
	public void styleMasculin() {

		modifierImageBouton(obtenirBoutonGauche(), "../_Images/Bouton/Yoshi.png");
		modifierImageBouton(obtenirBoutonCentre(), "../_Images/Bouton/Mario.png");
		modifierImageBouton(obtenirBoutonDroite(), "../_Images/Bouton/Bowser.png");

		modifierCouleurTexteBouton(this.obtenirBoutonCharger(), Color.LIGHT_GRAY);
		modifierCouleurTexteBouton(this.obtenirBoutonCorrection(), Color.LIGHT_GRAY);
		modifierCouleurTexteBouton(this.obtenirBoutonEnregistrer(), Color.LIGHT_GRAY);
		modifierCouleurTexteBouton(this.obtenirBoutonSolution(), Color.LIGHT_GRAY);

		modifierCouleurTexteBouton(this.obtenirBoutonGauche(), Color.BLACK);
		modifierCouleurTexteBouton(this.obtenirBoutonCentre(), Color.DARK_GRAY);
		modifierCouleurTexteBouton(this.obtenirBoutonDroite(), Color.LIGHT_GRAY);

		modifierFondPanneauNord("../_Images/Banniere/BanniereNordBoy.png");
		modifierFondPanneauSud("../_Images/Banniere/BanniereSudBoy.png");
		modifierFondClavier("../_Images/Banniere/BanniereClavierBoy.png");
		modifierFondPanneauInformations("../_Images/Banniere/BannierePanneauInformationsBoy.png");
	}

	/**
	 * Main
	 * 
	 * @param args
	 * @since 1.1.0
	 */
	public static void main(String[] args) {

		// Charger les fichier de configuration
		//
		Element configGrille = (Element) ConfigXML.load("Configuration/ConfigGrille", "1.0.0");
		if (configGrille == null)
			return;

		Element configPanneauInfo = (Element) ConfigXML.load("Configuration/ConfigPanneauInformations", "1.0.0");
		if (configPanneauInfo == null)
			return;

		Element configClavier = (Element) ConfigXML.load("Configuration/ConfigClavier", "1.0.0");
		if (configPanneauInfo == null)
			return;

		// Creer un nouveau jeu
		//
		JeuxGrille jeu = new JeuxGrille(configGrille);

		// Ajout d'un clavier
		//
		jeu.ajouterClavier(configClavier);
		jeu.obtenirClavier().ajouterEcran();
		jeu.obtenirClavier().styleClavierLettres();

		// Ajout d'un panneau d'information
		//
		jeu.ajouterPanneauInformation(configPanneauInfo);
		jeu.obtenirPanneauInformations().ajouterTexteAide("Bravo, tu as bien joué !");
		jeu.obtenirPanneauInformations().bulleVerte();
		
		jeu.obtenirPanneauInformations().ajouterTexteEnQueue("Elle est née du père Clément, un moine qui la");
		jeu.obtenirPanneauInformations().ajouterTexteEnQueue("créa en croisant un mandarinier et un oranger");
		jeu.obtenirPanneauInformations().ajouterTexteEnQueue("amer.");

		// Ajouter un ecouteur qui permet de tracer des
		// trait a la souris sur les cellules
		//
		jeu.obtenirGrille().ajouterEcouteur(new EcouteurGrilleDrawLine(jeu.obtenirGrille()));

		// Montrer le cadre support et ses panneaux internes
		//
		jeu.styleFeminin();
		jeu.setVisible(true);
	}

	/**
	 * Obtention du panneau nord
	 * 
	 * @return panneau nord
	 * @since 1.1.0
	 */
	public PanneauG obtenirPanneauNord() {
		return panneauNord;
	}

	/**
	 * Obtention du panneau sud
	 * 
	 * @return panneau sud
	 * @since 1.1.0
	 */
	public PanneauG obtenirPanneauSud() {
		return panneauSud;
	}

	public boolean obtenirModeAdmin() {
		return admin;
	}

	/**
	 * Modification du fond du panneau sud
	 * 
	 * @since 1.1.0
	 */
	public void modifierFondPanneauSud(String chemin) {
		panneauSud.ajouterImage(chemin);
	}

	/**
	 * Modification du fond du panneau nord
	 * 
	 * @since 1.1.0
	 */
	public void modifierFondPanneauNord(String chemin) {
		panneauNord.ajouterImage(chemin);
	}

	/**
	 * Modification du fond du clavier
	 * 
	 * @since 1.1.0
	 */
	public void modifierFondClavier(String chemin) {
		if (clavier != null)
			clavier.ajouterImage(chemin);
	}

	/**
	 * Modification du fond panneau informations
	 * 
	 * @since 1.1.0
	 */
	public void modifierFondPanneauInformations(String chemin) {
		if (panneauInformations != null)
			panneauInformations.obtenirPanneauNord().ajouterImage(chemin);
	}

	/**
	 * Retirer panneau central
	 * 
	 * @since 1.3.0
	 */
	public void retirerPanneauCentral() {
		if (grille != null) {
			this.getContentPane().remove(grille);
		} else if (panneauCentral != null) {
			panneauCentral = null;
			this.getContentPane().remove(panneauCentral);
		}
	}

	/**
	 * Ajouter un nouveau panneau central
	 * 
	 * @param panneau
	 * @since 1.3.0
	 */
	public void ajouterPanneauCentral(PanneauG panneau) {
		panneauCentral = panneau;
		this.getContentPane().add(panneauCentral);
	}

	/**
	 * Obtenir panneau central
	 * 
	 * @return panneauCentral
	 * @since 1.3.0
	 */
	public PanneauG obtenirPanneauCentral() {
		return panneauCentral;
	}

	/**
	 * Retirer panneau sud
	 * 
	 * @since 1.4.0
	 */
	public void retirerPanneauSud() {
		if (panneauSud != null)
			this.getContentPane().remove(panneauSud);
	}

	/**
	 * Application d'un style arc en ciel
	 * 
	 * @since 1.4.0
	 */
	public void styleArcEnCiel() {

		modifierImageBouton(obtenirBoutonGauche(), "../_Images/Bouton/Yoshi.png");
		modifierImageBouton(obtenirBoutonCentre(), "../_Images/Bouton/Mario.png");
		modifierImageBouton(obtenirBoutonDroite(), "../_Images/Bouton/Bowser.png");

		modifierCouleurTexteBouton(this.obtenirBoutonCharger(), Color.WHITE);

		modifierCouleurTexteBouton(this.obtenirBoutonGauche(), Color.WHITE);
		modifierCouleurTexteBouton(this.obtenirBoutonCentre(), Color.WHITE);
		modifierCouleurTexteBouton(this.obtenirBoutonDroite(), Color.WHITE);

		modifierFondPanneauNord("../_Images/Banniere/BanniereNordArcEnCiel.png");
		modifierFondPanneauSud("../_Images/Banniere/BanniereSudArcEnCiel.png");
		modifierFondClavier("../_Images/Banniere/BanniereClavierArcEnCiel.png");
		modifierFondPanneauInformations("../_Images/Banniere/BannierePanneauInformationsArcEnCiel.png");
	}

	/**
	 * Retirer tous les boutons du panneau sud
	 * 
	 * @since 1.4.0
	 */
	public void retirerBoutonPanneauSud() {
		if (admin) {
			if (panneauSud != null) {
				panneauSud.remove(obtenirBoutonEnregistrer());
			}
		} else {
			if (panneauSud != null) {
				panneauSud.remove(obtenirBoutonEnregistrer());
				panneauSud.remove(obtenirBoutonCharger());
				panneauSud.remove(obtenirBoutonSolution());
				panneauSud.remove(obtenirBoutonCorrection());
			}
		}
	}

	/**
	 * Changer le nom de la fenetre
	 * 
	 * @since 1.5.0
	 */
	public void modifierTitreFenetre(String titre) {
		setTitle(titre);
	}

	/**
	 * Retirer un bouton specifique au panneau sud
	 * 
	 * @param bouton
	 * @since 1.5.0
	 */
	public void retirerBoutonPanneauSud(JButton bouton) {
		if (bouton == null)
			return;

		if (panneauSud != null)
			panneauSud.remove(bouton);
	}
}