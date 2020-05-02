import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.util.HashMap;
import java.util.Iterator;

import javax.swing.ButtonGroup;
import javax.swing.JLabel;
import javax.swing.JRadioButton;

import org.jdom.Element;

public class Jeux {

	private Demarrer hamecon;
	private PanneauG panneauChoixStyle;
	private GrilleG grille;

	/**
	 * Bouton radio styleFeminin
	 * 
	 * @since 1.2.0
	 */
	private JRadioButton styleFeminin;

	/**
	 * Bouton radio styleGarcon
	 * 
	 * @since 1.2.0
	 */
	private JRadioButton styleGarcon;

	/**
	 * Bouton radio styleArcEnCiel
	 * 
	 * @since 1.2.0
	 */
	private JRadioButton styleArcEnCiel;

	private HashMap listePositionImagesJeu;
	private JLabel label;

	/**
	 * Afficher la page de jeux
	 * 
	 * @param configGrille
	 * @since 1.2.0
	 */
	public Jeux(Demarrer hamecon, Element configGrille, Element configPanneauNord) {

		// Memoriser l'hamecon vers le conteneur amont
		//
		this.hamecon = hamecon;

		// Renommer la fenetre
		//
		hamecon.obtenirFenetreSupport().setTitle("Jeux");

		// Creer le panneau choix style
		//
		setPanneauChoixStyle(configPanneauNord);

		// Ajouter le panneau choix style a la fenetre support
		//
		if (panneauChoixStyle != null)
			hamecon.obtenirFenetreSupport().getContentPane().add(panneauChoixStyle, BorderLayout.NORTH);

		// Creation d'une nouvelle grille
		//
		grille = new GrilleG(this, configGrille);

		// Attacher la grille a la fenetre support
		//
		hamecon.obtenirFenetreSupport().getContentPane().add(grille, BorderLayout.CENTER);

		// Enlever les bordure de la grille
		//
		grille.modifierCouleurCellules(new Color(0, 0, 0, 0));
		grille.setBackground(new Color(0, 0, 0, 0));
		grille.modifierBordure(null);

		// Ajouter une image de fond par default
		//
		grille.ajouterImage("../_Images/Jeux/Fond/Top.jpg");

		// Placer les boutons de jeux
		//
		setPositionImagesJeu();
		placerBoutons();
	}

	private void setPanneauChoixStyle(Element configPanneauNord) {

		// Ajouter un panneau support pour le choix du style
		//
		panneauChoixStyle = new PanneauG(this, configPanneauNord);

		// Creer un nouveau label
		//
		label = new JLabel("Choisi ton style : ");
		label.setForeground(Color.WHITE);

		// Creer les differents boutons
		//
		styleFeminin = new JRadioButton("Fille");
		styleGarcon = new JRadioButton("Garçon");
		styleArcEnCiel = new JRadioButton("Arc en ciel");

		styleFeminin.setForeground(Color.WHITE);

		styleGarcon.setForeground(Color.WHITE);

		styleArcEnCiel.setForeground(Color.WHITE);

		// Definir le bouton selectionne par defaut
		//
		styleArcEnCiel.setSelected(true);

		// Regrouper les boutons dans un ButtonGroup
		// (permet de selectionner un seul bouton)
		//
		ButtonGroup groupe = new ButtonGroup();
		groupe.add(styleFeminin);
		groupe.add(styleGarcon);
		groupe.add(styleArcEnCiel);

		// Ajouter les differents elements au panneau support
		//
		panneauChoixStyle.add(label);
		panneauChoixStyle.add(styleFeminin);
		panneauChoixStyle.add(styleGarcon);
		panneauChoixStyle.add(styleArcEnCiel);
		
		setBackgroundPanneauNord(configPanneauNord);
		setForegroundPanneauNord(configPanneauNord);
	}

	public PanneauG obtenirPanneauChoixStyle() {
		return panneauChoixStyle;
	}

	/**
	 * Obtenir le style selectionnee
	 * 
	 * @since 1.2.0
	 */
	public String obtenirStyleSelectionnee() {

		if (styleArcEnCiel.isSelected())
			return "styleArcEnCiel";
		if (styleGarcon.isSelected())
			return "styleGarcon";
		if (styleFeminin.isSelected())
			return "styleFeminin";

		else
			return null;
	}

	/**
	 * Definir la position des images du jeu
	 * 
	 * @since 1.3.0
	 */
	private void setPositionImagesJeu() {

		listePositionImagesJeu = new HashMap();

		listePositionImagesJeu.put(new Dimension(2, 2), "DroitAuBut");
		listePositionImagesJeu.put(new Dimension(2, 6), "FaisTonMarche");
		listePositionImagesJeu.put(new Dimension(4, 2), "ImageMysterieuse");
		listePositionImagesJeu.put(new Dimension(5, 2), "Intrus");
		listePositionImagesJeu.put(new Dimension(5, 4), "Jeu7Familles");
		listePositionImagesJeu.put(new Dimension(5, 5), "JeuDuPlacard");
		listePositionImagesJeu.put(new Dimension(5, 6), "Memo");
		listePositionImagesJeu.put(new Dimension(3, 5), "MotsCroises");
		listePositionImagesJeu.put(new Dimension(2, 8), "Pyramide");
		listePositionImagesJeu.put(new Dimension(3, 8), "Quizz");
		listePositionImagesJeu.put(new Dimension(5, 8), "MotDeLaFin");
	}

	private void placerBoutons() {

		Iterator i = listePositionImagesJeu.keySet().iterator();
		Dimension cle;
		String associe;

		while (i.hasNext()) {
			cle = (Dimension) i.next();
			associe = (String) listePositionImagesJeu.get(cle);

			grille.obtenirCellule(cle.width, cle.height).ajouterImage("../_Images/Jeux/Boutons/" + associe + ".png");
			grille.obtenirCellule(cle.width, cle.height).addMouseListener(new EcouteurJeux(this, grille.obtenirCellule(cle.width, cle.height)));
			grille.obtenirCellule(cle.width, cle.height).addMouseListener(
					new EcouteurAfficherTitre(hamecon, grille.obtenirCellule(cle.width, cle.height)));

		}
	}

	private void setBackgroundPanneauNord(Element config) {
		String StringCouleur;

		if (config == null) {
			panneauChoixStyle.setBackground(Color.black);
			
			label.setBackground(Color.black);

			styleFeminin.setBackground(Color.BLACK);
			styleGarcon.setBackground(Color.BLACK);
			styleArcEnCiel.setBackground(Color.BLACK);

			return;
		}

		if (config.getChild("ArrierePlan") == null) {
			panneauChoixStyle.setBackground(Color.black);
			
			label.setBackground(Color.black);

			styleFeminin.setBackground(Color.BLACK);
			styleGarcon.setBackground(Color.BLACK);
			styleArcEnCiel.setBackground(Color.BLACK);

			return;
		}
		StringCouleur = config.getChild("ArrierePlan").getTextTrim();

		panneauChoixStyle.setBackground(XML.obtenirColorFont(StringCouleur));
		
		label.setBackground(XML.obtenirColorFont(StringCouleur));

		styleFeminin.setBackground(XML.obtenirColorFont(StringCouleur));
		styleGarcon.setBackground(XML.obtenirColorFont(StringCouleur));
		styleArcEnCiel.setBackground(XML.obtenirColorFont(StringCouleur));
	}

	private void setForegroundPanneauNord(Element config) {
		String StringCouleur;

		// Verifier le parametre
		//
		if (config == null) {
			
			label.setForeground(Color.YELLOW);

			styleFeminin.setForeground(Color.YELLOW);
			styleGarcon.setForeground(Color.YELLOW);
			styleArcEnCiel.setForeground(Color.YELLOW);

			return;
		}

		// Verifier la presence de la cle
		//
		if (config.getChild("AvantPlan") == null) {
			
			label.setForeground(Color.YELLOW);

			styleFeminin.setForeground(Color.YELLOW);
			styleGarcon.setForeground(Color.YELLOW);
			styleArcEnCiel.setForeground(Color.YELLOW);
			
			return;
		}

		// Recuperer la valeur associee
		//
		StringCouleur = config.getChild("AvantPlan").getTextTrim();

		// Affecter la nouvelle couleur
		//
		label.setForeground(XML.obtenirColorFont(StringCouleur));

		styleFeminin.setForeground(XML.obtenirColorFont(StringCouleur));
		styleGarcon.setForeground(XML.obtenirColorFont(StringCouleur));
		styleArcEnCiel.setForeground(XML.obtenirColorFont(StringCouleur));
	}

}
