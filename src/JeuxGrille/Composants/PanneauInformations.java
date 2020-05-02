/** IUT de Nice / Departement informatique / Stage Archet
 * Annee 2009_2010 - Composant générique
 * 
 * @edition A : edition de base
 * 
 * @version 1.0.0
 * 
 * 			version de base
 * 			ajout d'une JTextArea editable ou non
 * 			obtenir JTextArea
 * 			modifierZoneAffichage
 * 
 * @version 1.1.0
 * 
 * 			ajout d'un text en queue a la JTextArea
 * 			appliquer un fond a la JTextArea
 * 			appliquer une font a la JTextArea
 * 			appliquer une couleur de font a la JTexArea
 * 			gestion par fichier XML
 * 
 * @version 1.2.0
 * 
 * 			ajout d'un panneau nord avec bulle
 * 			ajouterTextAide
 * 			obtenirPanneauNord
 * 
 * @version 1.3.0
 * 
 * 			ajout d'un panneau sud
 * 			possibilite de remplacer la JTextArea par une JList
 * 			ajouter une image dans le panneau sud
 * 			changement de la couleur de la bulle d'aide
 * 			obtenir le panneau sud
 * 			retirer le panneau sud
 */
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.util.List;
import java.util.Vector;
import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;

import org.jdom.Element;

/**
 * Composant graphique permettant d'afficher et de rentrer des informations
 * 
 * @author Cedric Hulin, Charles Fouco
 * @version 1.3.0
 */
public class PanneauInformations extends JFrame {

	/**
	 * Configuration du panneau
	 * 
	 * @since 1.0.0
	 */
	private Element config;

	/**
	 * Zone d'affichage d'informations
	 * 
	 * @since 1.0.0
	 */
	private JTextArea zoneAffichage;

	/**
	 * Panneau situe au Nord
	 * 
	 * @since 1.2.0
	 */
	private PanneauG panneauNord;

	/**
	 * Panneau situe au Sud
	 * 
	 * @since 1.3.0
	 */
	private PanneauG panneauSud;

	/**
	 * Panneau faisant guise de bulle d'aide
	 * 
	 * @since 1.2.0
	 */
	private JButton bulleAide;

	/**
	 * Zone d'affichage JList
	 * 
	 * @since 1.3.0
	 */
	private JList list;

	/**
	 * Constructeur normal
	 * 
	 * @param hamecon
	 *            hamecon permettant un positionnement relatif
	 * 
	 * @param config
	 *            fichier de configuration XML
	 * @since 1.0.0
	 */
	public PanneauInformations(JFrame hameconPlacement, Element config) {

		// Memoriser les information
		//
		this.config = config;

		// Definir les coordonnees de positionnement
		//
		int eastX = 0;
		int topY = 0;
		int sizeY = 600;
		if (hameconPlacement != null) {

			// Positionnement relatif par rapport a l'hamecon
			//
			eastX = ((JeuxGrille) hameconPlacement).getX() + ((JeuxGrille) hameconPlacement).getWidth();
			topY = ((JeuxGrille) hameconPlacement).getY();

			// Definir la taille de la fenetre
			//			
			sizeY = ((JeuxGrille) hameconPlacement).getHeight() - (((JeuxGrille) hameconPlacement).obtenirClavier() == null ? 0 : 250);
		}

		// Definir la partie statique
		//
		setTitle("Informations");
		setSize(424, sizeY);
		setLocation(eastX, topY);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// Ajouter une textArea
		//
		zoneAffichage = new JTextArea();
		zoneAffichage.setLineWrap(true);
		zoneAffichage.setWrapStyleWord(true);
		nonEditable();

		// Definir la partie statique
		//
		setBackground(config);
		setFont(config);
		setForeground(config);

		// Ajouter la zone de texte a la fenetre support
		//
		getContentPane().add(zoneAffichage, BorderLayout.CENTER);

		// Ajouter un panneau au Nord avec un bulle
		// d'informations
		//
		ajouterPanneauNord(Color.black);

		// Rendre visible la fenetre support
		//
		setVisible(true);
	}

	/**
	 * Obtention de la zone d'affichage
	 * 
	 * @return zone d'affichage (JTextArea)
	 * @since 1.0.0
	 */
	public JTextArea obtenirZoneAffichage() {
		return zoneAffichage;
	}

	/**
	 * Modification du texte de la zone d'affichage
	 * 
	 * @param texte
	 *            Nouveau texte a afficher
	 * @since 1.0.0
	 */
	public void modifierZoneAffichage(String texte) {
		zoneAffichage.setText(texte);
	}

	/**
	 * Ajouter un texte a la suite de ce qu'il y a deja dans la zone d'affichage
	 * 
	 * @param texte
	 *            Texte a ajouter en queue
	 * @since 1.1.0
	 */
	public void ajouterTexteEnQueue(String texte) {
		if (zoneAffichage.getText().length() == 0)
			zoneAffichage.setText(texte);
		else
			zoneAffichage.setText(zoneAffichage.getText() + "\n" + texte);
	}

	/**
	 * Rendre non editable la zone d'affichage
	 * 
	 * @since 1.0.0
	 */
	public void nonEditable() {
		zoneAffichage.setEditable(false);
		zoneAffichage.setBackground(Color.WHITE);
		zoneAffichage.setFocusable(false);
		zoneAffichage.setCursor(null);
	}

	/**
	 * Rendre editable la zone d'affichage
	 * 
	 * @since 1.0.0
	 */
	public void editable() {
		zoneAffichage.setEditable(true);
		zoneAffichage.setBackground(Color.WHITE);
		zoneAffichage.setFocusable(true);
		zoneAffichage.setCursor(Cursor.getDefaultCursor());
	}

	/**
	 * Appliquer une couleur de fond a la JTextArea
	 * 
	 * @param config
	 *            fichier de configuration XML
	 * @since 1.1.0
	 */
	private void setBackground(Element config) {
		String StringCouleur;

		if (config == null) {
			zoneAffichage.setBackground(Color.WHITE);
			return;
		}

		if (config.getChild("ArrierePlan") == null) {
			zoneAffichage.setBackground(Color.WHITE);
			return;
		}
		StringCouleur = config.getChild("ArrierePlan").getTextTrim();

		if (zoneAffichage != null)
			zoneAffichage.setBackground(XML.obtenirColorFont(StringCouleur));
		else if (list != null)
			list.setBackground(XML.obtenirColorFont(StringCouleur));
	}

	/**
	 * Appliquer une font a la JTextArea
	 * 
	 * @param config
	 *            fichier de configuration XML
	 * @since 1.1.0
	 */
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
		if (zoneAffichage != null)
			zoneAffichage.setFont(f);
		else if (list != null)
			list.setFont(f);
	}

	/**
	 * Appliquer une couleur de font a la JTextArea
	 * 
	 * @param config
	 *            fichier de configuration XML
	 * @since 1.1.0
	 */
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
		if (zoneAffichage != null)
			zoneAffichage.setForeground(XML.obtenirColorFont(StringCouleur));
		else if (list != null)
			list.setForeground(XML.obtenirColorFont(StringCouleur));
	}

	/**
	 * Ajout d'un panneau nord contenant une bulle d'aide
	 * 
	 * @param couleur
	 *            couleur de fond a appliquer
	 * @since 1.2.0
	 */
	public void ajouterPanneauNord(Color couleur) {

		// Creation du panneau nord
		//
		panneauNord = new PanneauG(this, config);
		panneauNord.setBackground(couleur);
		panneauNord.ajouterImage("../_Images/Banniere/BannierePanneauInformationsBoy.png");

		// Ajout d'une grille de disposition
		//
		panneauNord.setLayout(new FlowLayout());

		// Creation des boutons
		//
		bulleAide = JeuxGrille.creerBouton("../_Images/Bouton/bulle_blanche.png", "", "Comic sans ms", 12, Color.black);
		bulleAide.setPreferredSize(new Dimension(400, 185));

		// Definition de la position du texte
		//
		bulleAide.setVerticalTextPosition(SwingConstants.CENTER);
		bulleAide.setHorizontalTextPosition(SwingConstants.CENTER);

		// Ajout des boutons au panneau
		//        
		panneauNord.add(bulleAide);

		// Ajout d'une bordure au panneau
		//
		panneauNord.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLoweredBevelBorder(), BorderFactory.createMatteBorder(1, 1, 0,
				0, Color.black)));

		// Ajout du panneau au cadre support
		//
		getContentPane().add(panneauNord, BorderLayout.NORTH);
	}

	/**
	 * Ajout d'un panneau au sud contenant des images educatives et autres
	 * 
	 * @param couleur
	 * @since 1.3.0
	 */
	public void ajouterPanneauSud(Color couleur) {

		// Creation du panneau nord
		//
		panneauSud = new PanneauG(this, config);
		panneauSud.setBackground(couleur);
		panneauSud.ajouterImage("../_Images/Pyramide/3D/Pyramide.jpg");
		panneauSud.setPreferredSize(new Dimension(getWidth(), 300));

		// Ajout d'une bordure au panneau
		//
		panneauSud.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLoweredBevelBorder(), BorderFactory.createMatteBorder(1, 1, 0, 0,
				Color.black)));

		// Ajout du panneau au cadre support
		//
		getContentPane().add(panneauSud, BorderLayout.SOUTH);
	}

	/**
	 * Ajout de texte dans la bulle d'information
	 * 
	 * @param texte
	 * @since 1.2.0
	 */
	public void ajouterTexteAide(String texte) {
		if (bulleAide != null)
			bulleAide.setText("<HTML><BODY>" + texte + "</HTML></BODY>");
	}

	/**
	 * Obtention du panneau nord
	 * 
	 * @return panneau nord
	 * @since 1.2.0
	 */
	public PanneauG obtenirPanneauNord() {
		return panneauNord;
	}

	/**
	 * Ajouter une JList au panneau
	 * 
	 * @param contenu
	 *            : vecteur a afficher
	 * @since 1.3.0
	 */
	public void styleZoneAffichageJList(Vector contenu) {

		// Verifier l'existence d'une texteArea
		// ou d'une JList
		//
		if (zoneAffichage != null)
			getContentPane().remove(zoneAffichage);
		if (list != null)
			getContentPane().remove(list);

		// Creer la nouvelle JList
		// avec le contenu
		//
		list = new JList();
		list.setListData(contenu);

		// Ajouter la nouvelle liste
		// au frame support
		//
		getContentPane().add(list);
		getContentPane().setVisible(false);
		getContentPane().setVisible(true);
	}

	/**
	 * obtenir la liste d'items
	 * 
	 * @return list
	 * @since 1.3.0
	 */
	public JList obtenirList() {
		return list;
	}

	/**
	 * Rendre la bulle d'aide blanche
	 * 
	 * @since 1.3.0
	 */
	public void bulleBlanche() {
		Icon icon = null;
		icon = new ImageIcon("../_Images/Bouton/bulle_blanche.png");
		if (icon != null)
			bulleAide.setIcon(icon);
	}

	/**
	 * Rendre la bulle d'aide verte
	 * 
	 * @since 1.3.0
	 */
	public void bulleVerte() {
		Icon icon = null;
		icon = new ImageIcon("../_Images/Bouton/bulle_verte.png");
		if (icon != null)
			bulleAide.setIcon(icon);
	}

	/**
	 * Rendre la bulle d'aide rouge
	 * 
	 * @since 1.3.0
	 */
	public void bulleRouge() {
		Icon icon = null;
		icon = new ImageIcon("../_Images/Bouton/bulle_rouge.png");
		if (icon != null)
			bulleAide.setIcon(icon);
	}

	/**
	 * Ajouter une images au panneau du sud
	 * 
	 * @param chemin
	 *            chemin de l'image cible
	 * @since 1.3.0
	 */
	public void ajouterImageSud(String chemin) {
		if (panneauSud != null)
			panneauSud.ajouterImage(chemin);
	}

	/**
	 * Obtenir le panneau sud
	 * 
	 * @return panneauSud
	 * @since 1.3.0
	 */
	public PanneauG obtenirPanneauSud() {
		if (panneauSud != null)
			return panneauSud;
		return null;
	}

	/**
	 * Retirer le panneau sud
	 * 
	 * @since 1.3.0
	 */
	public void retirerPanneauSud() {
		if (panneauSud != null)
			getContentPane().remove(panneauSud);
	}

	/**
	 * Programme principal
	 * 
	 * @param args
	 * @since 1.3.0
	 */
	public static void main(String[] args) {
		Element configPanneauInfo = (Element) ConfigXML.load("Configuration/ConfigPanneauInformations", "1.0.0");
		if (configPanneauInfo == null)
			return;

		PanneauInformations p = new PanneauInformations(null, configPanneauInfo);
		p.modifierZoneAffichage("Zone d'affichage");
	}
}
