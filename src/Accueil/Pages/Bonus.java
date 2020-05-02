import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import org.jdom.Element;

public class Bonus {

	/**
	 * Grille support contenant la carte de france
	 * 
	 * @since 1.0.0
	 */
	private GrilleG grille;
	
	private PanneauG panneauNord;

	public Bonus(Demarrer hamecon, Element configPanneauNord, Element configGrille) {

		// Verifier les parametres
		//
		if (hamecon == null || configGrille == null || configPanneauNord == null)
			return;

		// Renommer la fenetre
		//
		hamecon.obtenirFenetreSupport().setTitle("Bonus");
		
		panneauNord = new PanneauG(this, configPanneauNord);
		panneauNord.setPreferredSize(new Dimension(hamecon.obtenirFenetreSupport().getWidth() , hamecon.obtenirFenetreSupport().getHeight()*1/7));
		
		// Creer une nouvelle grille suport contenant la carte
		//
		grille = new GrilleG(this, configGrille);
		
		// Enlever les brodure de la grille
		//
		grille.modifierCouleurCellules(new Color(0, 0, 0, 0));
		grille.setBackground(new Color(0, 0, 0, 0));
		grille.modifierBordure(null);
		
		// Ajouter une image de fond a la grille support
		//
		grille.ajouterImage("../_Images/Bonus/Fond/Squelette4.jpg");
		
		// Attacher la grille et le panneau a la fenetre support
		//
		hamecon.obtenirFenetreSupport().getContentPane().add(grille, BorderLayout.CENTER);
		hamecon.obtenirFenetreSupport().getContentPane().add(panneauNord, BorderLayout.NORTH);
	}
}
