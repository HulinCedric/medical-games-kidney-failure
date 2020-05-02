import java.awt.Cursor;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;

public class EcouteurAfficherTitre implements MouseListener {

	Demarrer hamecon;
	CelluleG cellule;

	public EcouteurAfficherTitre(Demarrer hamecon, CelluleG cellule) {
		this.hamecon = hamecon;
		this.cellule = cellule;
	}

	public void mouseClicked(MouseEvent e) {

	}

	public void mouseEntered(MouseEvent e) {

		cellule.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

		if (cellule.presenceImage()) {
			File file = new File(cellule.obtenirCheminImage());
			String titre = file.getName().substring(0, file.getName().length() - 4);
			hamecon.obtenirGrilleSud().ajouterImage("../_Images/" + hamecon.obtenirNomPageEnCours() + "/Fond/Bottom_" + titre + ".jpg");
		}
	}

	public void mouseExited(MouseEvent e) {

		hamecon.obtenirGrilleSud().ajouterImage("../_Images/" + hamecon.obtenirNomPageEnCours() + "/Fond/Bottom.jpg");

	}

	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

}
