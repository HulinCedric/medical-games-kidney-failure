import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.TextArea;
import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Vector;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import org.jdom.Element;

public class Centres {

	/**
	 * Grille support contenant la carte de france
	 * 
	 * @since 1.0.0
	 */
	private GrilleG grilleCarte;

	/**
	 * Panneau support contenant les differentes informations relatives a un
	 * centre
	 * 
	 * @since 1.0.0
	 */
	private PanneauG panneauInfo;

	/**
	 * Information concernant la ville du centre
	 * 
	 * @since 1.0.0
	 */
	private TextArea ville;

	/**
	 * Information concernant l'adresse du centre
	 * 
	 * @since 1.0.0
	 */
	private TextArea adresse;

	/**
	 * Information concernant le medecin responsable du centre
	 * 
	 * @since 1.0.0
	 */
	private TextArea medecin;

	/**
	 * Information concernant l'email du centre
	 * 
	 * @since 1.0.0
	 */
	private TextArea mail;

	/**
	 * Information concernant le numero du cadre de sante du centre
	 * 
	 * @since 1.0.0
	 */
	private TextArea numCadreSante;

	/**
	 * Information concernant le numero de la salle de dialyse du centre
	 * 
	 * @since 1.0.0
	 */
	private TextArea numSalleDialyse;

	/**
	 * Information concernant le numero du secreatariat du centre
	 * 
	 * @since 1.0.0
	 */
	private TextArea numSecretariat;

	/**
	 * Information concernant le numero du telecopie du centre
	 * 
	 * @since 1.0.0
	 */
	private TextArea numTelecopie;

	/**
	 * Dictionnaire contenant en cle la position sur la carte du centre, et en
	 * associe le nom du centre (ville)
	 * 
	 * @since 1.0.0
	 */
	private HashMap listeCentres;

	/**
	 * Vecteur contenant les chemins des informations relatives a chaque centre
	 * 
	 * @since 1.0.0
	 */
	private Vector repertoireCentres;

	/**
	 * Constructeur normal
	 * 
	 * @param hamecon
	 * @param configGrille
	 * 
	 * @since 1.0.0
	 */
	public Centres(Demarrer hamecon, Element configGrille) {

		// Verifier les parametres
		//
		if (hamecon == null || configGrille == null)
			return;

		// Renommer la fenetre
		//
		hamecon.obtenirFenetreSupport().setTitle("Centres");

		// Creer une nouvelle grille suport contenant la carte
		//
		grilleCarte = new GrilleG(this, configGrille);
		grilleCarte.setPreferredSize(new Dimension(hamecon.obtenirFenetreSupport().getHeight() * (1973 / 1945)
				- (hamecon.obtenirFenetreSupport().getHeight() * 311 / 1600) / 2, hamecon.obtenirFenetreSupport().getHeight()));

		// Enlever les brodure de la grille
		//
		grilleCarte.modifierCouleurCellules(new Color(0, 0, 0, 0));
		grilleCarte.setBackground(new Color(0, 0, 0, 0));
		grilleCarte.modifierBordure(null);

		// Charger les liens contenants les informations
		// relatives a chaque centre
		//
		setRepertoireCentres();

		// Remplir le dictionnaire contenant les coordonnees
		// de chaque centre sur la carte (grille support)
		//
		setPositionCentres();

		// Placer les differents centres sur la carte a l'aide du
		// dictionnaire rempli
		//
		placerBoutons();

		// Ajouter une image de fond a la grille support
		//
		grilleCarte.ajouterImage("../_Images/Centres/Fond/Carte.png");

		// Creer le panneau support contenant les informations
		// relatives a chaque centre
		//
		panneauInfo = new PanneauG(this, configGrille);

		// Creer les differents champs du panneau d'informations
		//
		ville = creerTexteArea("");
		adresse = creerTexteArea("");
		medecin = creerTexteArea("");
		mail = creerTexteArea("");
		numCadreSante = creerTexteArea("");
		numSalleDialyse = creerTexteArea("");
		numSecretariat = creerTexteArea("");
		numTelecopie = creerTexteArea("");

		// Definir une nouvelle disposition pour les champs
		//
		panneauInfo.setLayout(new GridLayout(16, 1));

		// Ajouter les differents champs au panneau support
		//
		panneauInfo.add(new JLabel(Texte.load("../_Textes/Accueil/Centres/Ville")));
		panneauInfo.add(ville);

		panneauInfo.add(new JLabel(Texte.load("../_Textes/Accueil/Centres/Adresse")));
		panneauInfo.add(adresse);

		panneauInfo.add(new JLabel(Texte.load("../_Textes/Accueil/Centres/Medecin responsable")));
		panneauInfo.add(medecin);

		panneauInfo.add(new JLabel(Texte.load("../_Textes/Accueil/Centres/Mail")));
		panneauInfo.add(mail);

		panneauInfo.add(new JLabel(Texte.load("../_Textes/Accueil/Centres/Cadre de sante")));
		panneauInfo.add(numCadreSante);

		panneauInfo.add(new JLabel(Texte.load("../_Textes/Accueil/Centres/Salle de dialyse")));
		panneauInfo.add(numSalleDialyse);

		panneauInfo.add(new JLabel(Texte.load("../_Textes/Accueil/Centres/Secretariat")));
		panneauInfo.add(numSecretariat);

		panneauInfo.add(new JLabel(Texte.load("../_Textes/Accueil/Centres/Telecopie")));
		panneauInfo.add(numTelecopie);

		// Attacher la grille et le panneau a la fenetre support
		//
		hamecon.obtenirFenetreSupport().getContentPane().add(grilleCarte, BorderLayout.WEST);
		hamecon.obtenirFenetreSupport().getContentPane().add(panneauInfo, BorderLayout.CENTER);
	}

	private TextArea creerTexteArea(String titre) {

		TextArea resultat = new TextArea(titre);

		resultat.setEditable(false);
		/*
		 * resultat.setLineWrap(true);
		 * resultat.setBorder(BorderFactory.createCompoundBorder
		 * (BorderFactory.createLoweredBevelBorder(),
		 * BorderFactory.createMatteBorder(1, 1, 0, 0, Color.black)));
		 */

		return resultat;
	}

	private void setPositionCentres() {

		listeCentres = new HashMap();

		listeCentres.put(new Dimension(6, 21), "Amiens");
		listeCentres.put(new Dimension(14, 13), "Angers");
		listeCentres.put(new Dimension(15, 31), "Besancon");
		listeCentres.put(new Dimension(23, 12), "Bordeaux");
		listeCentres.put(new Dimension(2, 26), "Bruxelles");
		listeCentres.put(new Dimension(8, 14), "Caen");
		listeCentres.put(new Dimension(20, 23), "Clermont-Ferrand");
		listeCentres.put(new Dimension(22, 30), "Grenoble");
		listeCentres.put(new Dimension(17, 32), "Lausanne");
		listeCentres.put(new Dimension(2, 29), "Liege");
		listeCentres.put(new Dimension(4, 22), "Lille");
		listeCentres.put(new Dimension(20, 27), "Lyon");
		listeCentres.put(new Dimension(28, 28), "Marseille");
		listeCentres.put(new Dimension(28, 24), "Montpellier");
		listeCentres.put(new Dimension(10, 30), "Nancy");
		listeCentres.put(new Dimension(14, 10), "Nantes");
		listeCentres.put(new Dimension(26, 34), "Nice");
		listeCentres.put(new Dimension(10, 21), "Paris-Necker");
		listeCentres.put(new Dimension(9, 21), "Paris Robert Debre");
		listeCentres.put(new Dimension(9, 20), "Paris Trousseau");
		listeCentres.put(new Dimension(8, 25), "Reims");
		listeCentres.put(new Dimension(12, 10), "Rennes");
		listeCentres.put(new Dimension(10, 4), "Roscoff");
		listeCentres.put(new Dimension(6, 17), "Rouen");
		listeCentres.put(new Dimension(29, 3), "Saint Denis de la Reunion");
		listeCentres.put(new Dimension(21, 26), "Saint Etienne");
		listeCentres.put(new Dimension(31, 3), "Saint-Pierre");
		listeCentres.put(new Dimension(10, 34), "Strasbourg");
		listeCentres.put(new Dimension(28, 18), "Toulouse");
		listeCentres.put(new Dimension(15, 15), "Tours");
	}

	private void placerBoutons() {

		Iterator i = listeCentres.keySet().iterator();
		Dimension cle;

		while (i.hasNext()) {
			cle = (Dimension) i.next();
			grilleCarte.obtenirCellule(cle.width, cle.height).ajouterImage("../_Images/Centres/Boutons/icon.png");
			grilleCarte.obtenirCellule(cle.width, cle.height).addMouseListener(
					new EcouteurCentres(this, grilleCarte.obtenirCellule(cle.width, cle.height)));
		}
	}

	public HashMap obtenirListeCentres() {
		return listeCentres;
	}

	private void setRepertoireCentres() {

		repertoireCentres = Dossier.chargerDirectory("../_Textes/Accueil/Centres/Centres/");

	}

	public void afficherInfoCentre(String nomCentre) {

		Iterator i = repertoireCentres.iterator();
		String cle;
		File file = null;
		String nomDossier;
		String cheminDossier = null;

		while (i.hasNext()) {
			cle = (String) i.next();
			file = new File(cle);

			nomDossier = file.getName();

			if (nomDossier.equals(nomCentre)) {
				cheminDossier = file.getAbsolutePath();
				break;
			}
		}

		// System.out.println(cheminDossier);

		Vector dossierCourant;

		// Ouvrir le dossier contenant les informations du centre
		//
		dossierCourant = Dossier.chargerRepertoire(cheminDossier, ".txt");
		if (dossierCourant != null) {

			// Verifier que le dossier contient 8
			// fichiers
			//
			if (dossierCourant.size() != 8) {
				JOptionPane.showMessageDialog(null, Texte.load("../_Textes/Erreurs/Nombre fichiers"));
				return;
			}

			// Lister les differents fichiers textes du dossier
			//
			String nomFichier = null;
			String cheminFichier = null;
			i = dossierCourant.iterator();
			while (i.hasNext()) {

				cle = (String) i.next();
				file = new File(cle);

				nomFichier = file.getName().substring(0, file.getName().length() - 4);
				cheminFichier = file.getAbsolutePath().substring(0, file.getAbsolutePath().length() - 4);

				// System.out.println("Cle : " + cheminFichier);
				if (nomFichier.equals("Adresse")) {
					adresse.setText(Texte.load(cheminFichier));
				} else if (nomFichier.equals("Cadre de sante")) {
					numCadreSante.setText(Texte.load(cheminFichier));
				} else if (nomFichier.equals("Mail")) {
					mail.setText(Texte.load(cheminFichier));
				} else if (nomFichier.equals("Medecin responsable")) {
					medecin.setText(Texte.load(cheminFichier));
				} else if (nomFichier.equals("Salle de dialyse")) {
					numSalleDialyse.setText(Texte.load(cheminFichier));
				} else if (nomFichier.equals("Secretariat")) {
					numSecretariat.setText(Texte.load(cheminFichier));
				} else if (nomFichier.equals("Telecopie")) {
					numTelecopie.setText(Texte.load(cheminFichier));
				} else if (nomFichier.equals("Ville")) {
					ville.setText(Texte.load(cheminFichier));
				}

			}
		}

	}

}
