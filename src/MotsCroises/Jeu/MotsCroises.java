/**
 * IUT de Nice / Departement informatique / Module APO-Java
 * Annee 2010_2011 - Package SWING
 * 
 * @edition A : edition initial
 * 
 * @version 1.0.0 :
 * 
 *          mis en place du jeu mots croises dans un main
 * 
 * @version 1.1.0 :
 * 
 *          constructeur + affichage personnalise selon le
 *          mode admin
 *          
 * @version 1.2.0 :
 * 	
 * 			gestion des boutons : enregistrement, solution, 
 * 			correction, charger
 * 
 * 
 * @author Charles Fouco
 */

import java.awt.Color;
import java.util.Iterator;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import org.jdom.Element;

/**
 * Conteneur et service pour les jeux de grille
 * 
 * @author Charles Fouco
 * @version 1.1.0
 */
public class MotsCroises {

	/**
	 * Variable de jeu motsCroises
	 * 
	 * @since 1.1.0
	 */
	private JeuxGrille motsCroises;

	/**
	 * Vecteur de donnees representant une solution de jeu
	 * 
	 * @since 1.2.0
	 */
	private Vector solution;

	/**
	 * Constructeur normal
	 * 
	 * @since 1.1.0
	 */
	public MotsCroises(Element configGrille, Element configClavier, Element configPanneauInfo) {

		// Verifier la validite des parametres
		//
		if (configGrille == null || configClavier == null || configPanneauInfo == null) {
			JOptionPane.showMessageDialog(null, Texte.load("../_Textes/Erreurs/Movais parametre"));
			System.exit(0);
		}

		// Creer un nouveau jeu
		//
		motsCroises = new JeuxGrille(configGrille);
		if (motsCroises.obtenirGrille() == null)
			System.exit(0);

		// Modifier le titre de la fenetre
		//
		motsCroises.modifierTitreFenetre("Jeu Mots Croises");

		// Ajouter un ecouteur qui permet de remplir
		// la grille avec des lettres du clavier
		//
		motsCroises.obtenirGrille().ajouterEcouteur(new EcouteurJeuxGrilleRemplir(motsCroises));

		// Ajouter un clavier au jeu
		//
		motsCroises.ajouterClavier(configClavier);
		motsCroises.obtenirClavier().ajouterEcran();
		motsCroises.obtenirClavier().styleClavierLettres();

		// Ajouter au clavier un ecouteur permettant
		// la communication entre la grille et le clavier
		//
		motsCroises.obtenirClavier().ajouterEcouteur(new EcouteurJeuxGrilleClavier(motsCroises));

		motsCroises.ajouterPanneauInformation(configPanneauInfo);
		motsCroises.obtenirPanneauInformations().ajouterTexteAide(Texte.load("../_Textes/Jeux/MotsCroises/Regle"));

		// Gerer l'affichage selon le mode admin
		//
		setModeAdmin();

		// Fermer la fenetre support lors d'un click sur la croix
		//
		motsCroises.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// Montrer le cadre support et ses panneaux internes
		//
		motsCroises.setVisible(true);
	}

	/**
	 * Programme principal
	 * 
	 * @param args
	 * @since 1.0.0
	 */
	public static void main(String[] args) {

		// Charger les fichier de configuration
		//
		Element configGrille = (Element) ConfigXML.load("Configuration/ConfigGrilleMotsCroises", "1.0.0");
		Element configPanneauInfo = (Element) ConfigXML.load("Configuration/ConfigPanneauInformations", "1.0.0");
		Element configClavier = (Element) ConfigXML.load("Configuration/ConfigClavierG", "1.0.0");

		MotsCroises jeu = new MotsCroises(configGrille, configClavier, configPanneauInfo);

		// Appliquer un style au jeu
		//
		if (args == null)
			jeu.styleArcEnCiel();
		else if (args.length <= 0)
			jeu.styleArcEnCiel();
		else if (args[0].equals("styleMasculin"))
			jeu.styleMasculin();
		else if (args[0].equals("styleFeminin"))
			jeu.styleFeminin();
		else
			jeu.styleArcEnCiel();
	}

	/**
	 * setModeAdmin
	 * 
	 * @since 1.1.0
	 */
	private void setModeAdmin() {

		if (!motsCroises.obtenirModeAdmin()) {
			motsCroises.supprimerEcouteurBouton(motsCroises.obtenirBoutonCharger(), motsCroises.obtenirBoutonCharger().getMouseListeners(),
					"EcouteurJeuxGrilleCharger");
			motsCroises.obtenirBoutonCharger().addMouseListener(new EcouteurMotsCroisesCharger(this));

			motsCroises.supprimerEcouteurBouton(motsCroises.obtenirBoutonEnregistrer(), motsCroises.obtenirBoutonEnregistrer().getMouseListeners(),
					"EcouteurJeuxGrilleEnregistrer");
			motsCroises.obtenirBoutonEnregistrer().addMouseListener(new EcouteurMotsCroisesEnregistrer(this));

			motsCroises.obtenirBoutonCorrection().addMouseListener(new EcouteurMotsCroisesCorrection(this));
			motsCroises.obtenirBoutonSolution().addMouseListener(new EcouteurMotsCroisesSolution(this));

			motsCroises.obtenirBoutonGauche().addMouseListener(new EcouteurMotsCroisesChoixNiveau(this));
			motsCroises.obtenirBoutonCentre().addMouseListener(new EcouteurMotsCroisesChoixNiveau(this));
			motsCroises.obtenirBoutonDroite().addMouseListener(new EcouteurMotsCroisesChoixNiveau(this));

			// Mettre les case en grises
			//
			motsCroises.obtenirGrille().modifierCouleurCellules(Color.GRAY);
		} else {

			motsCroises.obtenirPanneauInformations().ajouterTexteAide(Texte.load("../_Textes/Jeux/MotsCroises/Admin"));

			motsCroises.supprimerEcouteurBouton(motsCroises.obtenirBoutonEnregistrer(), motsCroises.obtenirBoutonEnregistrer().getMouseListeners(),
					"EcouteurJeuxGrilleEnregistrer");
			motsCroises.obtenirBoutonEnregistrer().addMouseListener(new EcouteurMotsCroisesEnregistrer(this));

			motsCroises.obtenirBoutonGauche().addMouseListener(new EcouteurChoixNiveau(motsCroises));
			motsCroises.obtenirBoutonCentre().addMouseListener(new EcouteurChoixNiveau(motsCroises));
			motsCroises.obtenirBoutonDroite().addMouseListener(new EcouteurChoixNiveau(motsCroises));

			// Permettre a l'utilisateur d'editer
			// la grille
			//
			motsCroises.obtenirGrille().modifierCouleurCellules(Color.WHITE);
		}
	}

	/**
	 * Applique un style feminin au design du jeu
	 * 
	 * @since 1.0.0
	 */
	public void styleFeminin() {
		motsCroises.styleFeminin();
	}

	/**
	 * Applique un style masculin au design du jeu
	 * 
	 * @since 1.0.0
	 */
	public void styleMasculin() {
		motsCroises.styleMasculin();
	}

	/**
	 * Applique un style masculin au design du jeu
	 * 
	 * @since 1.0.0
	 */
	public void styleArcEnCiel() {
		motsCroises.styleArcEnCiel();
	}

	/**
	 * Remplissage de la grille avec le vecteur solution
	 * 
	 * @since 1.2.0
	 */
	public void remplirGrilleSolution() {

		// Variables de travail
		//
		String cle = null;
		Iterator iterator = solution.iterator();

		// Definir les lignes et colonnes de depart
		// pour le remplissage
		//
		int i = 1;
		int j = 1;
		if (motsCroises.obtenirGrille().styleNum()) {
			i = 2;
			j = 2;
		}

		// Remplissage de la grille avec le vecteur solution
		//
		for (; i <= motsCroises.obtenirGrille().obtenirNbLignes(); i++) {
			j = motsCroises.obtenirGrille().styleNum() ? 2 : 1;
			for (; j <= motsCroises.obtenirGrille().obtenirNbColonnes(); j++) {
				if (iterator.hasNext()) {

					// Acquerir le contenu du vecteur
					//
					cle = (String) iterator.next();

					// Remplissage en fonction du contenu
					//
					if (cle.equals("@"))
						motsCroises.obtenirGrille().modifierCouleurCellule(i, j, Color.gray);
					else {
						if (cle.indexOf(".jpg") > 0)
							motsCroises.obtenirGrille().ajouterImage(i, j, cle);
						else {
							motsCroises.obtenirGrille().obtenirMatriceCellules()[i - 1][j - 1].ajouterTexte(cle, motsCroises.obtenirGrille()
									.obtenirCouleurPolice(), motsCroises.obtenirGrille().obtenirFont());
							motsCroises.obtenirGrille().modifierCouleurCellule(i, j, Color.white);
						}
					}
				}
			}
		}
	}

	/**
	 * Remplissage de la grille d'une partie sauvegardee
	 * 
	 * @since 1.2.0
	 */
	public void remplirGrilleSauvegarde() {

		// Remplissage de la grille avec les lettres du joueur seulement
		//
		motsCroises.obtenirGrille().remplirGrille();

		// Variables de travail
		//
		String cle = null;
		Iterator iterator = solution.iterator();

		// Definir les lignes et colonnes de depart
		// pour le remplissage
		//
		int i = 1;
		int j = 1;
		if (motsCroises.obtenirGrille().styleNum()) {
			i = 2;
			j = 2;
		}

		// Rendre blanches les cellules du vecteur solution
		//
		for (; i <= motsCroises.obtenirGrille().obtenirNbLignes(); i++) {
			j = motsCroises.obtenirGrille().styleNum() ? 2 : 1;
			for (; j <= motsCroises.obtenirGrille().obtenirNbColonnes(); j++) {
				if (iterator.hasNext()) {

					// Acquerir l'image courante
					//
					cle = (String) iterator.next();

					// Ajouter l'image ец la cellule
					// courante
					//
					if (cle.equals("@"))
						motsCroises.obtenirGrille().modifierCouleurCellule(i, j, Color.gray);
					else {
						if (cle.indexOf(".jpg") > 0)
							motsCroises.obtenirGrille().ajouterImage(i, j, cle);
						else {
							motsCroises.obtenirGrille().modifierCouleurCellule(i, j, Color.white);
						}
					}
				}
			}
		}
	}

	/**
	 * Affichage en vert des cellules dont le contenu est identique a la
	 * solution
	 * 
	 * @since 1.2.0
	 */
	public void correction() {

		// Variables de travail
		//
		String cle = null;
		String cle2 = null;

		// Iterator sur le vecteur solution
		//
		Iterator iterator = solution.iterator();

		// Iterator sur le contenu actuel de la grille
		//
		Iterator iterator2 = motsCroises.obtenirGrille().obtenirContenu().iterator();

		// Definir les lignes et colonnes de depart
		// pour le remplissage
		//
		int i = 1;
		int j = 1;
		if (motsCroises.obtenirGrille().styleNum()) {
			i = 2;
			j = 2;
		}

		// Parcourir l'ensemble des celulles
		//
		for (; i <= motsCroises.obtenirGrille().obtenirNbLignes(); i++) {
			j = motsCroises.obtenirGrille().styleNum() ? 2 : 1;
			for (; j <= motsCroises.obtenirGrille().obtenirNbColonnes(); j++) {

				if (iterator.hasNext() && iterator2.hasNext()) {

					// Acquerir le contenu du vecteur solution
					//
					cle = (String) iterator.next();

					// Acquerir le contenu du vecteur courant
					//
					cle2 = (String) iterator2.next();

					// Cas d'une lettre bien placee dans la grille
					//
					if (cle.equals(cle2))
						if (!motsCroises.obtenirGrille().obtenirCellule(i - 1, j - 1).getBackground().equals(Color.GRAY))
							motsCroises.obtenirGrille().modifierCouleurCellule(i, j, Color.GREEN);
				}

			}
		}
	}

	/**
	 * Ajouter une solution a la grille en cours
	 * 
	 * @param vecteur
	 *            solution
	 * @since 1.2.0
	 */
	public void ajouterSolution(Vector v) {
		solution = v;
	}

	/**
	 * Obtenir la solution de la grille chargee
	 * 
	 * @return vecteur solution
	 * @since 1.2.0
	 */
	public Vector obtenirSolution() {
		return solution;
	}

	/**
	 * Obtenir le jeux grille support du jeu
	 * 
	 * @return JeuxGrille
	 * 
	 * @since 1.2.0
	 */
	public JeuxGrille obtenirJeuxGrille() {
		return motsCroises;
	}
}
