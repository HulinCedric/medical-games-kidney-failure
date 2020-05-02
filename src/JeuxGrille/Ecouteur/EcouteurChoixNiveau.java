import java.awt.Color;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JOptionPane;

public class EcouteurChoixNiveau extends EcouteurJeuxGrilleCharger {

	/**
	 * Difficulte choisie
	 * 
	 * @since 1.1.0
	 */
	private String difficulte;

	public EcouteurChoixNiveau(Object hamecon) {
		super(hamecon);
		difficulte = null;
	}

	public void mouseClicked(MouseEvent e) {
		String nomFichier = null;
		String versionFichier = null;

		if (!obtenirHamecon().obtenirModeAdmin()) {
			File repertoire = null;
			String cheminRepertoire = ((JButton) e.getComponent()).getText();

			// Creer le repertoire abstrait cible
			//
			if (cheminRepertoire == null)
				repertoire = new File(".");
			else
				// Pour CD
				//
				//repertoire = new File("../" + obtenirHamecon().getTitle() + "Grilles/" + cheminRepertoire);
				
				// Pour Lancement simple
				//
				repertoire = new File("Grilles/" + cheminRepertoire);

			// Controler l'existence du repertoire cible
			//
			if (repertoire == null)
				return;

			// Controler l'existence du repertoire cible

			if (!repertoire.exists() && !repertoire.isDirectory()) {
				JOptionPane.showMessageDialog(null, repertoire.getName() + " : " + Texte.load("../_Textes/Erreurs/Dossier grilles"));
				return;
			}

			// Obtenir la liste de tous les fichiers du
			// repertoire cible
			//
			File[] files = repertoire.listFiles();

			// Construire le vecteur des donnees
			//
			String grilleChoisi = null;
			int random;

			// Remplir un vecteur temporaire ordonnee
			//
			Vector listeGrilles = new Vector();
			for (int i = 0; i < files.length; i++)
				if (files[i].getName().indexOf(".xml") > 0)
					listeGrilles.add(files[i]);

			if (listeGrilles.size() <= 0) {
				JOptionPane.showMessageDialog(obtenirHamecon(), Texte.load("../_Textes/Erreurs/Nombre grilles"));
				return;
			}

			// Appliquer un ordonnancement aleatoire des
			// donnees
			//              
			random = (int) (Math.random() * (listeGrilles.size()));
			grilleChoisi = (((File) listeGrilles.get(random)).getAbsolutePath());

			// Stocker les informations concernant le
			// fichier choisi
			//
			nomFichier = grilleChoisi.substring(0, grilleChoisi.length() - 10);
			versionFichier = grilleChoisi.substring(grilleChoisi.length() - 9, grilleChoisi.length() - 4);
			difficulte = repertoire.getName();

			// Charger les donnees
			//
			charger(obtenirHamecon(), nomFichier, versionFichier);
		} else {
			String mode = ((JButton) e.getComponent()).getText();

			// Stocker les informations concernant le
			// fichier choisi
			//
			nomFichier = "Configuration/ConfigCreation" + mode;
			versionFichier = "1.0.0";
			difficulte = null;

			// Charger les donnees
			//
			charger(obtenirHamecon(), nomFichier, versionFichier);

			// Mettre les case en blanc
			//
			obtenirHamecon().obtenirGrille().modifierCouleurCellules(Color.WHITE);
		}
		this.modifierFlagReussite(true);
	}

	/**
	 * Obtention de la difficulte choisie
	 * 
	 * @return
	 * @since 1.1.0
	 */
	protected String obtenirDifficulte() {
		return difficulte;
	}

	public void mouseEntered(MouseEvent e) {
	}

	public void mouseExited(MouseEvent e) {
	}

	public void mousePressed(MouseEvent e) {
	}

	public void mouseReleased(MouseEvent e) {
	}
}
