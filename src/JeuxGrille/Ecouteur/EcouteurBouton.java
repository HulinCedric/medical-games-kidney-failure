/**
 * IUT de Nice / Departement informatique / Module APO-Java
 * Annee 2009_2010 - Ecouteur
 * 
 * @version 1.0.0
 * 
 *          version initial modification de la couleur de
 *          fond d'un bouton modification du curseur via un
 *          dossier d'images modification de l'aspect du
 *          bouton lors d'un clic
 * 
 * @version 1.1.0
 * 
 *          verification du chargement du dossier d'images
 */
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.util.Vector;

import javax.swing.JButton;

/**
 * Ecouteur souris permettant de modifier la couleur d'un
 * bouton, l'aspect lors d'un clic et la forme du curseur
 * 
 * @author Cedric Hulin, Charles Fouco
 * @version 1.1.0
 */
public class EcouteurBouton implements MouseListener {

	/**
	 * Hamecon sur un jeux de grille
	 * 
	 * @since 1.0.0
	 */
	private JButton hamecon;

	/**
	 * Constructeur normal
	 * 
	 * @param hamecon
	 * @since 1.0.0
	 */
	public EcouteurBouton(Object hamecon) {
		this.hamecon = (JButton) hamecon;
	}

	/**
	 * Implementation de l'evenement mouseEntered
	 * 
	 * @since 1.1.0
	 */
	public void mouseEntered(MouseEvent arg0) {

		// Modifier la couleur de fond aleatoirement
		//
		hamecon.setBackground(obtenirCouleurAleatoire());

		// Recuperer une image aleatoire
		//
		Image img = null;
		// img =
		// obtenirImageAleatoire("../_Images/Curseur/",".png");

		// Si une image a ete recuperee
		//
		if (img != null) {
			Toolkit tk = Toolkit.getDefaultToolkit();
			Cursor monCurseur = tk.createCustomCursor(img, new Point(24, 24), "Curseur");
			hamecon.setCursor(monCurseur);
		}
		// Si aucune image n'a ete trouvee
		// le curseur est une main
		//
		else
			hamecon.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
	}

	/**
	 * Implementation de l'evenement mouseReleased
	 * 
	 * @since 1.0.0
	 */
	public void mouseReleased(MouseEvent arg0) {
		// hamecon.setBackground(null);
		hamecon.setEnabled(true);
		hamecon.setDisabledIcon(hamecon.getIcon());
		// hamecon.setCursor(Cursor.getDefaultCursor());
	}

	/**
	 * Obtention d'une image aleatoire
	 * 
	 * @return couleur aleatoire
	 * @since 1.0.0
	 */
	private Image obtenirImageAleatoire(String chemin, String extension) {

		File repertoireImages = null;

		// Verification des parametres
		//
		if (extension == null) {
			System.out.println("obtenirImageAleatoire : aucune extension n'a �t� donn�e");
			return null;
		}
		if (chemin == null) {
			System.out.println("obtenirImageAleatoire : aucun chemin n'a �t� donn�");
			return null;
		}

		// Creer le repertoire abstrait cible
		//
		else
			repertoireImages = new File(chemin);

		// Controler l'existence du repertoire cible
		//
		if (repertoireImages == null) {
			System.out.println("Le dossier d'images n'existe pas");
			return null;
		}

		// Controler l'existence du repertoire cible

		if (!repertoireImages.exists() && !repertoireImages.isDirectory()) {
			System.out.println("Le dossier d'images n'existe pas");
			return null;
		}

		// File repertoireImages = new File(chemin);
		File[] files = repertoireImages.listFiles();

		Vector fichiersImage = new Vector();
		for (int i = 0; i < files.length; i++)
			if (files[i].getName().indexOf(extension) > 0)
				fichiersImage.add(files[i]);

		int random = (int) (Math.random() * (fichiersImage.size()));
		String path = (((File) fichiersImage.get(random)).getAbsolutePath());

		Toolkit tk = Toolkit.getDefaultToolkit();

		return tk.getImage(path);
	}

	/**
	 * Obtention d'une couleur aleatoire
	 * 
	 * @return couleur aleatoire
	 * @since 1.0.0
	 */
	private Color obtenirCouleurAleatoire() {
		Color[] colors = { Color.cyan, Color.lightGray, Color.green, Color.magenta, Color.orange, Color.pink, Color.red, Color.yellow };

		int random = (int) (Math.random() * colors.length);

		return colors[random];
	}

	public void mouseClicked(MouseEvent e) {

	}

	public void mouseExited(MouseEvent arg0) {
	}

	public void mousePressed(MouseEvent arg0) {
		hamecon.setEnabled(false);
		hamecon.setDisabledIcon(null);
	}
}