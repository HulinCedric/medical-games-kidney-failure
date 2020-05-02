import java.awt.BorderLayout;

public class Realisateurs {

	private PanneauG panneauSupport;

	public Realisateurs(Demarrer hamecon) {

		// Controler la validite des parametres
		//
		if (hamecon == null)
			return;
		

		// Renomer la fenetre
		//
		hamecon.obtenirFenetreSupport().setTitle("Realisateurs et remerciements");

		// Creer un panneau support 
		//
		panneauSupport = new PanneauG(this, null);
		
		// Ajouter l'image de fond du panneau
		//
		panneauSupport.ajouterImage("../_Images/Realisateurs/Fond/Top.jpg");

		// Ajouter le panneau support a la fenetre support
		//
		hamecon.obtenirFenetreSupport().getContentPane().add(panneauSupport, BorderLayout.CENTER);
	}
}
