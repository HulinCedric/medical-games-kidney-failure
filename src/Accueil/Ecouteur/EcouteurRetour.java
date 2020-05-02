import java.awt.Cursor;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import org.jdom.Element;

public class EcouteurRetour implements MouseListener {

	private Demarrer hamecon;

	public EcouteurRetour(Demarrer hamecon) {
		this.hamecon = hamecon;
	}

	public void mouseClicked(MouseEvent arg0) {

		// Charger les fichier de configuration
		//
		Element configGrille = (Element) ConfigXML.load("Configuration/Accueil/ConfigGrille", "1.0.0");

		// Retirer le bouton retour
		//
		// hamecon.obtenirGrilleSud().retirerImage(1, 1);
		// hamecon.obtenirGrilleSud().obtenirCellule(1,
		// 1).retirerEcouteur();

		hamecon.pageAccueil(configGrille);
		hamecon.obtenirFenetreSupport().getContentPane().paintAll(hamecon.obtenirFenetreSupport().getContentPane().getGraphics());
	}

	public void mouseEntered(MouseEvent arg0) {
		hamecon.obtenirGrilleSud().obtenirCellule(1, 1).setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
	}

	public void mouseExited(MouseEvent arg0) {
	}

	public void mousePressed(MouseEvent arg0) {
	}

	public void mouseReleased(MouseEvent arg0) {
	}

}
