import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Iterator;

import javax.swing.Timer;

import org.jdom.Element;

public class Accueil {

	private Demarrer hamecon;
	private GrilleG grille;
	private ActionListener tache_timer;
	private Timer chrono;
	private HashMap listePositionsImagesAccueil;
	int compteur = 0;

	public Accueil(Demarrer hamecon, Element configGrille) {

		// Memoriser l'hamecon vers le conteneur amont
		//
		this.hamecon = hamecon;

		// Renomer la fenetre
		//
		hamecon.obtenirFenetreSupport().setTitle("Accueil");

		// Creation de la grille de disposition
		//
		grille = new GrilleG(this, configGrille);

		// Ajout de l'image de fond
		//
		grille.ajouterImage("../_Images/Accueil/Fond/Top_2.jpg");

		// Enlever les bordure de la grilleCentrale
		//
		grille.modifierCouleurCellules(new Color(0, 0, 0, 0));
		grille.setBackground(new Color(0, 0, 0, 0));
		grille.modifierBordure(null);

		// Attacher la grille a la fenetre support
		//
		hamecon.obtenirFenetreSupport().getContentPane().add(grille, BorderLayout.CENTER);

		// Placer les differents boutons sur l'ecran
		//
		setPositionImagesAccueil();
		placerBoutons();

		// Placer les etoiles
		//
		placerEtoiles();
	}

	/**
	 * Mise en place des differents bouton
	 * 
	 * @since 1.0.0
	 */
	private void placerEtoiles() {

		// Ajout d'etoiles
		//
		grille.obtenirCellule(2, 3).ajouterImage("../_Images/Accueil/Boutons/Etoile.gif");
		grille.obtenirCellule(2, 15).ajouterImage("../_Images/Accueil/Boutons/Etoile.gif");
		grille.obtenirCellule(4, 7).ajouterImage("../_Images/Accueil/Boutons/Etoile.gif");
		grille.obtenirCellule(5, 9).ajouterImage("../_Images/Accueil/Boutons/Etoile.gif");
		grille.obtenirCellule(7, 7).ajouterImage("../_Images/Accueil/Boutons/Etoile.gif");
		grille.obtenirCellule(8, 1).ajouterImage("../_Images/Accueil/Boutons/Etoile.gif");
		grille.obtenirCellule(9, 11).ajouterImage("../_Images/Accueil/Boutons/Etoile.gif");
		grille.obtenirCellule(3, 2).ajouterImage("../_Images/Accueil/Boutons/Etoile.gif");
		grille.obtenirCellule(6, 4).ajouterImage("../_Images/Accueil/Boutons/Etoile.gif");
		grille.obtenirCellule(10, 5).ajouterImage("../_Images/Accueil/Boutons/Etoile.gif");
		grille.obtenirCellule(7, 13).ajouterImage("../_Images/Accueil/Boutons/Etoile.gif");
		grille.obtenirCellule(1, 7).ajouterImage("../_Images/Accueil/Boutons/Etoile.gif");

		// Definir l'action realise par le timer
		//	
		tache_timer = new ActionListener() {

			public void actionPerformed(ActionEvent e1) {

				grille.repaint();
				// System.out.println(compteur++);
			}
		};

		// Instanciation du chronometre
		//
		chrono = new Timer(100, tache_timer);
		chrono.start();
	}

	private void setPositionImagesAccueil() {

		listePositionsImagesAccueil = new HashMap();

		listePositionsImagesAccueil.put(new Dimension(6, 9), "Jeux");
		listePositionsImagesAccueil.put(new Dimension(5, 10), "Centres");
		listePositionsImagesAccueil.put(new Dimension(8, 13), "Bonus");
		listePositionsImagesAccueil.put(new Dimension(10, 3), "Realisateurs");
		listePositionsImagesAccueil.put(new Dimension(7, 6), "Abecedaire");
		listePositionsImagesAccueil.put(new Dimension(4, 5), "Recettes");

	}

	private void placerBoutons() {

		Iterator i = listePositionsImagesAccueil.keySet().iterator();
		Dimension cle;
		String associe;

		while (i.hasNext()) {
			cle = (Dimension) i.next();
			associe = (String) listePositionsImagesAccueil.get(cle);

			grille.obtenirCellule(cle.width, cle.height).ajouterImage("../_Images/Accueil/Boutons/" + associe + ".gif");
			grille.obtenirCellule(cle.width, cle.height).addMouseListener(
					new EcouteurAfficherTitre(hamecon, grille.obtenirCellule(cle.width, cle.height)));
			grille.obtenirCellule(cle.width, cle.height).addMouseListener(new EcouteurAccueil(hamecon, grille.obtenirCellule(cle.width, cle.height)));

		}
	}

	public void arreterChrono() {
		chrono.stop();
	}
}