import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Vector;

import javax.swing.JButton;

import org.jdom.Element;

public class EcouteurJeuxGrilleMemoChoixNiveau implements MouseListener {

	private Memo hamecon;

	public EcouteurJeuxGrilleMemoChoixNiveau(Object hamecon) {
		this.hamecon = (Memo) hamecon;
	}

	public void mouseClicked(MouseEvent e) {

		// Recuperer le texte du bouton clic
		//
		String niveau = ((JButton) e.getComponent()).getText();

		// Definir le nombre de mailles
		// en fonction du niveau
		//
		Element configGrille = (Element) ConfigXML.load("Configuration/ConfigMemoGrille" + niveau, "1.0.0");
		if (configGrille == null)
			return;

		// Definir le nouveau temps de recouvrement des
		// images
		//
		hamecon.setTempsRecouvrement(configGrille);

		// Retirer l'ancien maillage
		//
		if (hamecon.obtenirJeuxGrille().obtenirGrille().presenceMaillage())
			hamecon.obtenirJeuxGrille().obtenirGrille().retirerMaillage();

		// Suppression de l'ancienne grille
		//
		if (hamecon.obtenirJeuxGrille().obtenirGrille() != null)
			hamecon.obtenirJeuxGrille().getContentPane().remove(hamecon.obtenirJeuxGrille().obtenirGrille());

		// Accrocher la nouvelle grille a l'hamecon
		//
		GrilleG g = new GrilleG(hamecon, configGrille);
		hamecon.obtenirJeuxGrille().modifierGrille(g);
		hamecon.obtenirJeuxGrille().getContentPane().add(hamecon.obtenirJeuxGrille().obtenirGrille());
		hamecon.obtenirJeuxGrille().obtenirGrille().ajouterEcouteur(new EcouteurJeuxGrilleMemo(hamecon));

		// Creer le vecteur de travail
		//
		Vector listeDossier = new Vector();

		// Remplir le vecteur avec les elements
		// de chaque repertoire
		//
		listeDossier.add("../_Images/Legumes");
		listeDossier.add("../_Images/Fruits");
		listeDossier.add("../_Images/Feculents");
		listeDossier.add("../_Images/Matieres Grasses");
		listeDossier.add("../_Images/Produits Laitiers");
		listeDossier.add("../_Images/Produits Sucres");
		listeDossier.add("../_Images/Viande Poisson Oeuf");

		// Obtenir toutes les images des repertoires
		//
		Vector tousLesDossier = Dossier.obtenirVecteur(listeDossier, ".jpg");

		// Charger un lot d'images paires
		//
		hamecon.obtenirJeuxGrille().obtenirGrille().chargerLotImagesPaires(tousLesDossier);

		// Reinitialiser le chronometre
		//
		hamecon.obtenirChronometre().stoper();
		hamecon.obtenirChronometre().initialiser();
		hamecon.obtenirChronometre().demarer();

		// Effet magique ++
		//
		hamecon.obtenirJeuxGrille().paintAll(hamecon.obtenirJeuxGrille().obtenirGrille().getGraphics());

	}

	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

}
