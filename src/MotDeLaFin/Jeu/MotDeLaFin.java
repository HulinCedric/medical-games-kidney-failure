/**
 * IUT de Nice / Departement informatique / Stage Archet
 * Annee 2009_2010 - Applications graphiques
 * 
 * @edition A : edition initiale.
 * 
 * @version 1.0.0
 * 
 *          version initiale.
 * 
 * @version 1.1.0
 * 
 *          gestion du mode administrateur.
 * 
 * @version 1.2.0
 * 
 *          externalisation des placements dans des methodes
 *          privees.
 */
import java.awt.Color;
import java.util.Iterator;
import java.util.LinkedList;

import javax.swing.JOptionPane;

import org.jdom.Element;

/**
 * Regroupement d'objet necessaire dans une classe pour
 * construire le jeu mot de la fin.
 * 
 * @version 1.2.0
 * @author Cedric Hulin
 */
public class MotDeLaFin {

	/**
	 * Enumeration des points cardinaux utile pour
	 * l'algorithme de placement.
	 * 
	 * @since 1.0.0
	 */
	private final int EST = 0;
	private final int SUD = 1;
	private final int NORDEST = 2;
	private final int SUDEST = 3;
	private final int NORDOUEST = 4;
	private final int NORD = 5;
	private final int SUDOUEST = 6;
	private final int OUEST = 7;

	/**
	 * Jeux de grilles support.
	 * 
	 * @since 1.0.0
	 */
	private JeuxGrille jeu;

	/**
	 * Constructeur normal.
	 * 
	 * @param configurationG
	 *            de la grille.
	 * @param configurationP
	 *            du panneau d'information.
	 * @since 1.0.0
	 */
	public MotDeLaFin(Element configurationG, Element configurationP) {

		// Creer un nouveau jeu.
		//
		jeu = new JeuxGrille(configurationG);

		// Controler l'instance de la grille.
		//
		if (jeu.obtenirGrille() == null)
			System.exit(0);

		// Modifier le titre du composant central.
		//
		jeu.modifierTitreFenetre("MotDeLaFin");

		// Enlever les boutons inutiles.
		//
		jeu.retirerBoutonPanneauSud(jeu.obtenirBoutonCorrection());
		jeu.retirerBoutonPanneauSud(jeu.obtenirBoutonSolution());

		// Ajouter un panneau d'information.
		//
		jeu.ajouterPanneauInformation(configurationP);

		// Mise en place du mode selectionner dans le jeu de
		// grille.
		//
		setModeAdmin();

		// Rendre visible l'interface.
		//
		jeu.setVisible(true);
	}

	/**
	 * Applique aux composants des ecouteurs et un aspect
	 * different selon le mode passee par fichier de
	 * configuration.
	 * 
	 * @since 1.1.0
	 */
	private void setModeAdmin() {

		// Ajouter des ecouteurs aux boutons de difficulte.
		//
		jeu.obtenirBoutonGauche().addMouseListener(new EcouteurMotDeLaFinChoixNiveau(jeu, this));
		jeu.obtenirBoutonCentre().addMouseListener(new EcouteurMotDeLaFinChoixNiveau(jeu, this));
		jeu.obtenirBoutonDroite().addMouseListener(new EcouteurMotDeLaFinChoixNiveau(jeu, this));

		// Verifier le mode du jeu de grille.
		//
		if (!jeu.obtenirModeAdmin()) {

			// Ajouter l'ecouteur des regles du jeu a la
			// grille.
			//
			jeu.obtenirGrille().ajouterEcouteur(new EcouteurMotDeLaFin(jeu));

			// Retirer les ecouteurs par defaut du jeu de
			// grille.
			//
			jeu.supprimerEcouteurBouton(jeu.obtenirBoutonEnregistrer(), jeu.obtenirBoutonEnregistrer().getMouseListeners(),
					"EcouteurJeuxGrilleEnregistrer");
			jeu.supprimerEcouteurBouton(jeu.obtenirBoutonCharger(), jeu.obtenirBoutonCharger().getMouseListeners(), "EcouteurJeuxGrilleCharger");

			// Ajouter des ecouteurs au boutons de service.
			//
			jeu.obtenirBoutonEnregistrer().addMouseListener(new EcouteurMotDeLaFinEnregistrer(jeu));
			jeu.obtenirBoutonCharger().addMouseListener(new EcouteurMotDeLaFinCharger(jeu, this));

		} else {

			// Afficher un message dans la bulle d'aide du
			// panneau
			// d'information.
			//
			jeu.obtenirPanneauInformations().ajouterTexteAide(Texte.load("../_Textes/Jeux/MotDeLaFin/Admin"));

			// Ajouter un ecouteur textuel sur le panneau
			// d'information.
			//
			jeu.obtenirPanneauInformations().obtenirZoneAffichage().addKeyListener(new EcouteurMotDeLaFinPanneauInformations(jeu));
		}
	}

	/**
	 * Programme principal
	 * 
	 * @param arguments
	 * @since 1.2.0
	 */
	public static void main(String[] arguments) {

		// Charger les fichier de configuration.
		//
		Element configGrille = (Element) ConfigXML.load("Configuration/ConfigGrilleG", "4.0.0");
		Element configPanneauInfo = (Element) ConfigXML.load("Configuration/ConfigPanneauInformations", "2.0.0");

		// Instancier un jeu.
		//
		MotDeLaFin jeu = new MotDeLaFin(configGrille, configPanneauInfo);

		// Appliquer un style au jeu
		//
		if (arguments == null)
			jeu.styleArcEnCiel();
		else if (arguments.length <= 0)
			jeu.styleArcEnCiel();
		else if (arguments[0].equals("styleMasculin"))
			jeu.styleMasculin();
		else if (arguments[0].equals("styleFeminin"))
			jeu.styleFeminin();
		else
			jeu.styleArcEnCiel();
	}

	/**
	 * Verifie la possibilite de placement d'une liste de
	 * mot dans une grille.
	 * 
	 * @param liste
	 *            des mots a placer.
	 * @return flag de reussite.
	 * @since 1.0.0
	 */
	public boolean verificationListeMot(LinkedList liste) {

		// Recuperer le nombre de lettres a placer.
		//
		Iterator k = liste.iterator();
		int nblettre = 0;
		while (k.hasNext())
			nblettre += ((String) k.next()).length();

		// Verifier que le nombre cellules ne soit pas
		// inferieur au nombre de
		// lettres a placer.
		//
		System.out.print("Verification Cellule (" + jeu.obtenirGrille().obtenirNbCellules() + ") > Nombre de lettre (" + nblettre + ") : ");
		if (jeu.obtenirGrille().obtenirNbCellules() < nblettre) {
			System.out.println("NOK");
			return false;
		} else
			System.out.println("OK");

		// Verifier que la longueur des mots ne soit pas
		// superieur a la hauteur
		// ou largeur de la grille.
		//
		k = liste.iterator();
		while (k.hasNext()) {

			// Recuperer le mot courant.
			//
			String cle = (String) k.next();

			// Controler la longueur avec le nombre de ligne
			// et de colonne.
			//
			if (cle.length() > jeu.obtenirGrille().obtenirNbLignes() || cle.length() > jeu.obtenirGrille().obtenirNbColonnes()) {

				// Afficher un message d'erreur.
				//
				JOptionPane.showMessageDialog(null, Texte.load("../_Textes/Jeux/MotDeLaFin/Mot trop grand") + cle);
				return false;
			}
		}

		// Retourner le resultat.
		//
		return true;
	}

	/**
	 * Algorithme de placement de mot dans une grille.
	 * 
	 * @param liste
	 *            des mots a placer.
	 * @since 1.0.0
	 */
	public void placement(LinkedList liste) {
		boolean retry = true;
		int motplacer = 0;

		// Trier le tableau de maniere decroissante.
		//
		trieDecroissant(liste);

		// Pour chaque mot de la liste l'algorithme va
		// determiner s'il peut
		// etre placer dans tous les sens, s'il n'arrive pas
		// l'algorithme
		// recommence a la partie initialisation.
		//
		while (retry) {

			//
			// --- Partie initialisation ---
			//

			// Vider la grille courante.
			//
			jeu.obtenirGrille().viderGrille();

			// Initialiser les flag de placement.
			//
			boolean flagOuest = false;
			boolean flagEst = false;
			boolean flagNord = false;
			boolean flagSud = false;
			boolean flagNordOuest = false;
			boolean flagNordEst = false;
			boolean flagSudOuest = false;
			boolean flagSudEst = false;

			// Initialiser la variable de boucle sur les
			// mots.
			//
			boolean placer = true;

			// Initialiser le nombre de mot placer.
			//
			motplacer = 0;

			// Parcourir la liste de mots
			//
			Iterator k = liste.iterator();
			while (k.hasNext()) {

				//
				// ----- Partie verification et recuperation
				// des mots -----
				//

				// Verifier si un mot n'a pas pue etre
				// placer.
				//
				if (!placer)
					break;

				// Incrementer le nombre de mot placer.
				//
				motplacer++;
				placer = false;

				// Recuperer le prochain mot a placer.
				//
				String mot = (String) k.next();

				// Boucler tant que le mot n'a pas essayer
				// toute les tentatives
				// de placement
				//
				while (!placer) {

					//
					// ----- Partie placement des mots -----
					//

					// Verifier si toute les tentatives de
					// placement ont ete
					// effectue.
					//
					if (flagOuest && flagEst && flagNord && flagSud && flagNordOuest && flagNordEst && flagSudOuest && flagSudEst)
						break;

					// Effectuer une tentative de placement
					// dans un sens
					// aleatoire.
					//
					switch ((int) (Math.random() * (8))) {

					// Cas de Placement OUEST.
					//
					case OUEST:

						// Effectuer une tentative de
						// placement.
						//
						placer = placementOuest(mot);

						// Renseigner que la tentative de
						// placement OUEST a ete
						// effectue.
						//
						flagOuest = true;
						break;

					// Cas de Placement EST.
					//
					case EST:

						// Effectuer une tentative de
						// placement.
						//
						placer = placementEst(mot);

						// Renseigner que la tentative de
						// placement EST a ete
						// effectue.
						//
						flagEst = true;
						break;

					// Cas de Placement NORD.
					//
					case NORD:

						// Effectuer une tentative de
						// placement.
						//
						placer = placementNord(mot);

						// Renseigner que la tentative de
						// placement NORD a ete
						// effectue.
						//
						flagNord = true;
						break;

					// Cas de Placement SUD.
					//
					case SUD:

						// Effectuer une tentative de
						// placement.
						//
						placer = placementSud(mot);

						// Renseigner que la tentative de
						// placement SUD a ete
						// effectue.
						//
						flagSud = true;
						break;

					case NORDOUEST:

						// Effectuer une tentative de
						// placement.
						//
						placer = placementNordOuest(mot);

						// Renseigner que la tentative de
						// placement NORDOUEST a
						// ete effectue.
						//
						flagNordOuest = true;
						break;

					case NORDEST:

						// Effectuer une tentative de
						// placement.
						//
						placer = placementNordEst(mot);

						// Renseigner que la tentative de
						// placement NORDEST a
						// ete effectue.
						//
						flagNordEst = true;
						break;

					case SUDOUEST:

						// Effectuer une tentative de
						// placement.
						//
						placer = placementSudOuest(mot);

						// Renseigner que la tentative de
						// placement SUDOUEST a
						// ete effectue.
						//						
						flagSudOuest = true;
						break;

					case SUDEST:

						// Effectuer une tentative de
						// placement.
						//
						placer = placementSudEst(mot);

						// Renseigner que la tentative de
						// placement SUDEST a
						// ete effectue.
						//		
						flagSudEst = true;
						break;
					}
				}
			}

			// Verifier que tous les mots ont ete places.
			//
			if (!k.hasNext() && placer)
				retry = false;
		}

		// Renseigner que les mot on ete placer.
		//
		System.out.println("Les " + motplacer + " mots on ete placer.");

		// Parcourir l'ensemble des cellules
		//
		for (int i = 1; i <= jeu.obtenirGrille().obtenirNbLignes(); i++) {
			for (int j = 1; j <= jeu.obtenirGrille().obtenirNbColonnes(); j++) {

				// Modifier la couleur de la cellule
				// courante.
				//
				jeu.obtenirGrille().modifierCouleurCellule(i, j, Color.white);

				// Remplir par une lettre aleatoire les
				// cellules vide.
				//
				if (!jeu.obtenirGrille().obtenirCellule(i, j).presenceTexte())
					jeu.obtenirGrille().ajouterTexte(i, j, String.valueOf((char) ('A' + (int) (Math.random() * ('Z' + 1 - 'A')))));
			}
		}

		// Repeindre le jeu.
		//
		jeu.paintAll(jeu.getGraphics());
	}

	/**
	 * Trie un tableau de mots de maniere decroissant.
	 * 
	 * @param liste
	 *            de mots a trier.
	 * @since 1.0.0
	 */
	private void trieDecroissant(LinkedList liste) {
		Object[] tableauMots = liste.toArray();
		int longueur = tableauMots.length;
		String tampon = null;
		boolean permuter;

		// Effectuer un tri sur la liste de mots en les
		// rangeant du plus grand
		// au plus petit.
		//
		do {
			permuter = false;
			for (int i = 0; i < longueur - 1; i++) {

				// Verifier si 2 elements successifs sont
				// dans le bon ordre
				//
				if (((String) tableauMots[i]).length() < ((String) tableauMots[i + 1]).length()) {

					// Echanger leurs positions
					//
					tampon = (String) tableauMots[i];
					tableauMots[i] = tableauMots[i + 1];
					tableauMots[i + 1] = tampon;
					permuter = true;
				}
			}
		} while (permuter);

		// Vider la liste.
		//
		liste.clear();

		// Remplir la liste avec les elements trie.
		//
		for (int i = 0; i < longueur; i++)
			liste.add(tableauMots[i]);
	}

	/**
	 * Place un mot dans la direction Ouest.
	 * 
	 * @param mot
	 *            a placer.
	 * @return flag de placement du mot.
	 */
	private boolean placementOuest(String mot) {
		boolean placer = false;
		CelluleG cellule = null;
		CelluleG celluleTemp = null;

		// Parcourir l'ensemble des cellules.
		//
		for (int i = jeu.obtenirGrille().obtenirNbLignes(); i > 0; i--) {
			for (int j = jeu.obtenirGrille().obtenirNbColonnes(); j > 0; j--) {

				// Verifier que le mot est placer.
				//
				if (placer)
					break;

				// Recuperer la cellule courante.
				//
				cellule = jeu.obtenirGrille().obtenirCellule(i, j);

				// Verifier qu'elle ne comporte pas de texte
				// ou
				// verifier que le texte correspond avec la
				// premiere lettre du mot a placer.
				//
				if (!cellule.presenceTexte() || cellule.obtenirTexteTitre().equals(String.valueOf(mot.charAt(0)))) {

					// Verifier que la taille du mot ne
					// depasse.
					// pas la grille.
					//
					if ((j - mot.length()) >= 0) {
						boolean pb = false;
						int m = 0;

						// Parcourir les lettre du mot.
						//
						for (int l = j; m < mot.length(); l--) {

							// Recuperer la cellule de
							// placement
							// de la lettre.
							//
							celluleTemp = jeu.obtenirGrille().obtenirCellule(i, l);

							// Verifier qu'elle comporte du
							// texte.
							//
							if (celluleTemp.presenceTexte())

								// Verifier que la lettre
								// correspond a celle qui
								// doit
								// etre placer.
								//
								if (!celluleTemp.obtenirTexteTitre().equals(String.valueOf(mot.charAt(m)))) {
									pb = true;
									break;
								}
							m++;
						}

						// Verifier que le placement ne pose
						// pas
						// de probleme.
						//
						if (!pb) {
							m = 0;

							// Ajouter le texte dans la
							// grille.
							//
							for (int l = j; m < mot.length(); l--)
								jeu.obtenirGrille().ajouterTexte(i, l, String.valueOf(mot.charAt(m++)));

							// Renseigner que le mot a ete
							// placer.
							//
							placer = true;
							break;
						}
					}
				}
			}
		}

		// Restituer le resultat de placement.
		//
		return placer;
	}

	/**
	 * Place un mot dans la direction Est.
	 * 
	 * @param mot
	 *            a placer.
	 * @return flag de placement du mot.
	 * @since 1.2.0
	 */
	private boolean placementEst(String mot) {
		boolean placer = false;
		CelluleG cellule = null;
		CelluleG celluleTemp = null;

		// Parcourir l'ensemble des cellules.
		//
		for (int i = 1; i <= jeu.obtenirGrille().obtenirNbLignes(); i++) {
			for (int j = 1; j <= jeu.obtenirGrille().obtenirNbColonnes(); j++) {

				// Verifier que le mot est placer.
				//
				if (placer)
					break;

				// Recuperer la cellule courante.
				//
				cellule = jeu.obtenirGrille().obtenirCellule(i, j);

				// Verifier qu'elle ne comporte pas de texte
				// ou
				// verifier que le texte correspond avec la
				// premiere lettre du mot a placer.
				//
				if (!cellule.presenceTexte() || cellule.obtenirTexteTitre().equals(String.valueOf(mot.charAt(0)))) {

					// Verifier que la taille du mot ne
					// depasse
					// pas la grille.
					//
					if ((j - 1 + mot.length()) <= jeu.obtenirGrille().obtenirNbColonnes()) {
						boolean pb = false;
						int m = 0;

						// Parcourir les lettre du mot.
						//
						for (int l = j; m < mot.length(); l++) {

							// Recuperer la cellule de
							// placement
							// de la lettre.
							//
							celluleTemp = jeu.obtenirGrille().obtenirCellule(i, l);

							// Verifier qu'elle comporte du
							// texte.
							//
							if (celluleTemp.presenceTexte())

								// Verifier que la lettre
								// correspond a celle qui
								// doit
								// etre placer.
								//
								if (!celluleTemp.obtenirTexteTitre().equals(String.valueOf(mot.charAt(m)))) {
									pb = true;
									break;
								}
							m++;
						}

						// Verifier que le placement ne pose
						// pas
						// de probleme.
						//
						if (!pb) {
							m = 0;

							// Ajouter le texte dans la
							// grille.
							//
							for (int l = j; m < mot.length(); l++)
								jeu.obtenirGrille().ajouterTexte(i, l, String.valueOf(mot.charAt(m++)));

							// Renseigner que le mot a ete
							// placer.
							//
							placer = true;
							break;
						}
					}
				}
			}
		}

		// Restituer le resultat de placement.
		//
		return placer;
	}

	/**
	 * Place un mot dans la direction Nord.
	 * 
	 * @param mot
	 *            a placer.
	 * @return flag de placement du mot.
	 * @since 1.2.0
	 */
	private boolean placementNord(String mot) {
		boolean placer = false;
		CelluleG cellule = null;
		CelluleG celluleTemp = null;

		// Parcourir l'ensemble des cellules.
		//
		for (int i = jeu.obtenirGrille().obtenirNbLignes(); i > 0; i--) {
			for (int j = 1; j <= jeu.obtenirGrille().obtenirNbColonnes(); j++) {

				// Verifier que le mot est placer.
				//
				if (placer)
					break;

				// Recuperer la cellule courante.
				//
				cellule = jeu.obtenirGrille().obtenirCellule(i, j);

				// Verifier qu'elle ne comporte pas de texte
				// ou
				// verifier que le texte correspond avec la
				// premiere lettre du mot a placer.
				//
				if (!cellule.presenceTexte() || cellule.obtenirTexteTitre().equals(String.valueOf(mot.charAt(0)))) {

					// Verifier que la taille du mot ne
					// depasse
					// pas la grille.
					//
					if ((i - mot.length()) >= 0) {
						boolean pb = false;
						int m = 0;

						// Parcourir les lettre du mot.
						//
						for (int l = i; m < mot.length(); l--) {

							// Recuperer la cellule de
							// placement
							// de la lettre.
							//
							celluleTemp = jeu.obtenirGrille().obtenirCellule(l, j);

							// Verifier qu'elle comporte du
							// texte.
							//
							if (celluleTemp.presenceTexte())

								// Verifier que la lettre
								// correspond a celle qui
								// doit
								// etre placer.
								//
								if (!celluleTemp.obtenirTexteTitre().equals(String.valueOf(mot.charAt(m))))
									pb = true;
							m++;
						}

						// Verifier que le placement ne pose
						// pas
						// de probleme.
						//
						if (!pb) {
							m = 0;

							// Ajouter le texte dans la
							// grille.
							//
							for (int l = i; m < mot.length(); l--)
								jeu.obtenirGrille().ajouterTexte(l, j, String.valueOf(mot.charAt(m++)));

							// Renseigner que le mot a ete
							// placer.
							//
							placer = true;
							break;
						}
					}
				}
			}
		}

		// Restituer le resultat de placement.
		//
		return placer;
	}

	/**
	 * Place un mot dans la direction Sud.
	 * 
	 * @param mot
	 *            a placer.
	 * @return flag de placement du mot.
	 * @since 1.2.0
	 */
	private boolean placementSud(String mot) {
		boolean placer = false;
		CelluleG cellule = null;
		CelluleG celluleTemp = null;

		// Parcourir l'ensemble des cellules.
		//
		for (int i = 1; i <= jeu.obtenirGrille().obtenirNbLignes(); i++) {
			for (int j = 1; j <= jeu.obtenirGrille().obtenirNbColonnes(); j++) {

				// Verifier que le mot est placer.
				//
				if (placer)
					break;

				// Recuperer la cellule courante.
				//
				cellule = jeu.obtenirGrille().obtenirCellule(i, j);

				// Verifier qu'elle ne comporte pas de texte
				// ou
				// verifier que le texte correspond avec la
				// premiere lettre du mot a placer.
				//
				if (!cellule.presenceTexte() || cellule.obtenirTexteTitre().equals(String.valueOf(mot.charAt(0)))) {

					// Verifier que la taille du mot ne
					// depasse.
					// pas la grille.
					//
					if ((i - 1 + mot.length()) <= jeu.obtenirGrille().obtenirNbLignes()) {
						boolean pb = false;
						int m = 0;

						// Parcourir les lettre du mot.
						//
						for (int l = i; m < mot.length(); l++) {

							// Recuperer la cellule de
							// placement
							// de la lettre.
							//
							celluleTemp = jeu.obtenirGrille().obtenirCellule(l, j);

							// Verifier qu'elle comporte du
							// texte.
							//
							if (celluleTemp.presenceTexte())

								// Verifier que la lettre
								// correspond a celle qui
								// doit
								// etre placer.
								//
								if (!celluleTemp.obtenirTexteTitre().equals(String.valueOf(mot.charAt(m))))
									pb = true;
							m++;
						}

						// Verifier que le placement ne pose
						// pas
						// de probleme.
						//
						if (!pb) {
							m = 0;

							// Ajouter le texte dans la
							// grille.
							//
							for (int l = i; m < mot.length(); l++)
								jeu.obtenirGrille().ajouterTexte(l, j, String.valueOf(mot.charAt(m++)));

							// Renseigner que le mot a ete
							// placer.
							//
							placer = true;
							break;
						}
					}
				}
			}
		}

		// Restituer le resultat de placement.
		//
		return placer;
	}

	/**
	 * Place un mot dans la direction Nord-Ouest.
	 * 
	 * @param mot
	 *            a placer.
	 * @return flag de placement du mot.
	 * @since 1.2.0
	 */
	private boolean placementNordOuest(String mot) {
		boolean placer = false;
		CelluleG cellule = null;
		CelluleG celluleTemp = null;

		for (int i = jeu.obtenirGrille().obtenirNbLignes(); i > 0; i--) {
			for (int j = jeu.obtenirGrille().obtenirNbColonnes(); j > 0; j--) {
				if (placer)
					break;

				cellule = jeu.obtenirGrille().obtenirCellule(i, j);
				if (!cellule.presenceTexte() || cellule.obtenirTexteTitre().equals(String.valueOf(mot.charAt(0)))) {
					if ((i - mot.length()) >= 0 && (j - mot.length()) >= 0) {
						boolean pb = false;
						int m = 0;
						int n = j;
						for (int l = i; m < mot.length(); l--) {

							celluleTemp = jeu.obtenirGrille().obtenirCellule(l, n);
							if (celluleTemp.presenceTexte())
								if (!celluleTemp.obtenirTexteTitre().equals(String.valueOf(mot.charAt(m))))
									pb = true;
							m++;
							n--;
						}

						if (!pb) {
							m = 0;
							n = j;
							for (int l = i; m < mot.length(); l--) {
								jeu.obtenirGrille().ajouterTexte(l, n, String.valueOf(mot.charAt(m++)));
								n--;
							}
							placer = true;
							break;
						}
					}
				}
			}
		}

		// Restituer le resultat de placement.
		//
		return placer;
	}

	/**
	 * Place un mot dans la direction Nord-Est.
	 * 
	 * @param mot
	 *            a placer.
	 * @return flag de placement du mot.
	 * @since 1.2.0
	 */
	private boolean placementNordEst(String mot) {
		boolean placer = false;
		CelluleG cellule = null;
		CelluleG celluleTemp = null;

		for (int i = jeu.obtenirGrille().obtenirNbLignes(); i > 0; i--) {
			for (int j = 1; j <= jeu.obtenirGrille().obtenirNbColonnes(); j++) {
				if (placer)
					break;

				cellule = jeu.obtenirGrille().obtenirCellule(i, j);
				if (!cellule.presenceTexte() || cellule.obtenirTexteTitre().equals(String.valueOf(mot.charAt(0)))) {
					if ((i - mot.length()) >= 0 && (j - 1 + mot.length()) <= jeu.obtenirGrille().obtenirNbColonnes()) {
						boolean pb = false;
						int m = 0;
						int n = j;
						for (int l = i; m < mot.length(); l--) {

							celluleTemp = jeu.obtenirGrille().obtenirCellule(l, n);
							if (celluleTemp.presenceTexte())
								if (!celluleTemp.obtenirTexteTitre().equals(String.valueOf(mot.charAt(m))))
									pb = true;
							m++;
							n++;
						}

						if (!pb) {
							m = 0;
							n = j;
							for (int l = i; m < mot.length(); l--) {
								jeu.obtenirGrille().ajouterTexte(l, n, String.valueOf(mot.charAt(m++)));
								n++;
							}
							placer = true;
							break;
						}
					}
				}
			}
		}

		// Restituer le resultat de placement.
		//
		return placer;
	}

	/**
	 * Place un mot dans la direction Sud-Ouest.
	 * 
	 * @param mot
	 *            a placer.
	 * @return flag de placement du mot.
	 * @since 1.2.0
	 */
	private boolean placementSudOuest(String mot) {
		boolean placer = false;
		CelluleG cellule = null;
		CelluleG celluleTemp = null;

		for (int i = 1; i <= jeu.obtenirGrille().obtenirNbLignes(); i++) {
			for (int j = jeu.obtenirGrille().obtenirNbColonnes(); j > 0; j--) {
				if (placer)
					break;

				cellule = jeu.obtenirGrille().obtenirCellule(i, j);
				if (!cellule.presenceTexte() || cellule.obtenirTexteTitre().equals(String.valueOf(mot.charAt(0)))) {
					if ((i - 1 + mot.length()) <= jeu.obtenirGrille().obtenirNbLignes() && (j - mot.length()) >= 0) {
						boolean pb = false;
						int m = 0;
						int n = j;
						for (int l = i; m < mot.length(); l++) {

							celluleTemp = jeu.obtenirGrille().obtenirCellule(l, n);
							if (celluleTemp.presenceTexte())
								if (!celluleTemp.obtenirTexteTitre().equals(String.valueOf(mot.charAt(m))))
									pb = true;
							m++;
							n--;
						}

						if (!pb) {
							m = 0;
							n = j;
							for (int l = i; m < mot.length(); l++) {
								jeu.obtenirGrille().ajouterTexte(l, n, String.valueOf(mot.charAt(m++)));
								n--;
							}
							placer = true;
							break;
						}
					}
				}
			}
		}

		// Restituer le resultat de placement.
		//
		return placer;
	}

	/**
	 * Place un mot dans la direction Sud-Est.
	 * 
	 * @param mot
	 *            a placer.
	 * @return flag de placement du mot.
	 * @since 1.2.0
	 */
	private boolean placementSudEst(String mot) {
		boolean placer = false;
		CelluleG cellule = null;
		CelluleG celluleTemp = null;

		for (int i = 1; i <= jeu.obtenirGrille().obtenirNbLignes(); i++) {
			for (int j = 1; j <= jeu.obtenirGrille().obtenirNbColonnes(); j++) {
				if (placer)
					break;

				cellule = jeu.obtenirGrille().obtenirCellule(i, j);
				if (!cellule.presenceTexte() || cellule.obtenirTexteTitre().equals(String.valueOf(mot.charAt(0)))) {
					if ((i - 1 + mot.length()) <= jeu.obtenirGrille().obtenirNbLignes()
							&& (j - 1 + mot.length()) <= jeu.obtenirGrille().obtenirNbColonnes()) {
						boolean pb = false;
						int m = 0;
						int n = j;
						for (int l = i; m < mot.length(); l++) {

							celluleTemp = jeu.obtenirGrille().obtenirCellule(l, n);
							if (celluleTemp.presenceTexte())
								if (!celluleTemp.obtenirTexteTitre().equals(String.valueOf(mot.charAt(m))))
									pb = true;
							m++;
							n++;
						}

						if (!pb) {
							m = 0;
							n = j;
							for (int l = i; m < mot.length(); l++) {
								jeu.obtenirGrille().ajouterTexte(l, n, String.valueOf(mot.charAt(m++)));
								n++;
							}
							placer = true;
							break;
						}
					}
				}
			}
		}

		// Restituer le resultat de placement.
		//
		return placer;
	}

	/**
	 * Applique un style feminin au design du jeu.
	 * 
	 * @since 1.0.0
	 */
	public void styleFeminin() {
		jeu.styleFeminin();
	}

	/**
	 * Applique un style masculin au design du jeu.
	 * 
	 * @since 1.0.0
	 */
	public void styleMasculin() {
		jeu.styleMasculin();
	}

	/**
	 * Applique un style arc en ciel au design du jeu.
	 * 
	 * @since 1.0.0
	 */
	public void styleArcEnCiel() {
		jeu.styleArcEnCiel();
	}
}