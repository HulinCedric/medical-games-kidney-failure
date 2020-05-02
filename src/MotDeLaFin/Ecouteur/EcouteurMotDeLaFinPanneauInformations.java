/**
 * IUT de Nice / Departement informatique / Stage Archet
 * Annee 2009_2010 - Ecouteur souris
 * 
 * @edition A : edition initiale.
 * 
 * @version 1.0.0
 * 
 *          version initiale.
 *          
 * @version 1.1.0
 * 
 * 			mise en place de chargement de sauvegarde.
 */
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.text.Normalizer;

/**
 * Ecouteur clavier assistant l'administrateur a creer des listes de mots pour
 * les parties du mot de la fin.
 * 
 * @version 1.0.0
 * @author Cedric Hulin
 */
public class EcouteurMotDeLaFinPanneauInformations implements KeyListener {

	/**
	 * Hamecon sur un jeu de grille.
	 * 
	 * @since 1.0.0
	 */
	private JeuxGrille hamecon;

	/**
	 * Constructeur normal.
	 * 
	 * @param hamecon
	 *            sur une jeu de grille.
	 * @since 1.0.0
	 */
	public EcouteurMotDeLaFinPanneauInformations(JeuxGrille hamecon) {
		this.hamecon = hamecon;
	}

	/**
	 * Implementation de l'evenement keyReleased.
	 * 
	 * @param evenement
	 *            capture.
	 * @since 1.0.0
	 */
	public void keyReleased(KeyEvent e) {

		// Recuperer le texte dans la zone d'affichage et le mettre en majuscule
		//
		String texte = hamecon.obtenirPanneauInformations().obtenirZoneAffichage().getText().toUpperCase();

		// Enlever tout type d'accent de la chaine
		//
		texte = Normalizer.normalize(texte, Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "");

		// Supprimer tout autre caractere y compris les espaces
		//
		String tampon = "";
		for (int i = 0; i < texte.length(); i++)
			if (!(texte.charAt(i) < 'A' || texte.charAt(i) > 'Z') || texte.charAt(i) == '\n')
				tampon += String.valueOf(texte.charAt(i));

		// Verifiez la longueur de chaque mots
		//		
		String ligne = "";
		texte = "";
		int longueurMaxMot = hamecon.obtenirGrille().obtenirNbColonnes() < hamecon.obtenirGrille().obtenirNbLignes() ? hamecon.obtenirGrille()
				.obtenirNbLignes() : hamecon.obtenirGrille().obtenirNbColonnes();

		for (int i = 0; i < tampon.length(); i++) {
			if (tampon.charAt(i) == '\n' || (i + 1) == tampon.length()) {
				if ((i + 1) == tampon.length())
					if (tampon.charAt(i) != '\n')
						ligne += String.valueOf(tampon.charAt(i));

				if (ligne.length() > longueurMaxMot) {
					ligne = ligne.substring(0, longueurMaxMot);
					if (tampon.charAt(i) != '\n')
						ligne += "\n";
				}

				if (tampon.charAt(i) == '\n')
					texte += ligne + "\n";
				else
					texte += ligne;

				ligne = "";
			} else
				ligne += String.valueOf(tampon.charAt(i));
		}

		// Precisez combien de lettre il est possible de
		// rentrer
		//
		int tailleTexte = 0;
		int nbRL = 0;
		int nbCellules = hamecon.obtenirGrille().obtenirNbCellules();
		for (int i = 0; i < texte.length(); i++) {
			if (texte.charAt(i) != '\n')
				tailleTexte++;
			if (texte.charAt(i) == '\n')
				nbRL++;
		}

		// Verifier le depassement de lettre autoriser
		//
		if (nbCellules - tailleTexte <= 0) {
			texte = texte.substring(0, nbCellules + nbRL);
			if (texte.charAt(texte.length() - 1) == '\n')
				texte = texte.substring(0, nbCellules + nbRL - 1);
		}
		hamecon.obtenirPanneauInformations().ajouterTexteAide(
				"Il vous reste " + String.valueOf(nbCellules - tailleTexte) + " lettre(s) dans la grille");

		// Mettre a jour le texte apres traitement dans la
		// zone d'affichage
		//
		hamecon.obtenirPanneauInformations().obtenirZoneAffichage().setText(texte);
	}

	public void keyPressed(KeyEvent e) {
	}

	public void keyTyped(KeyEvent e) {
	}
}