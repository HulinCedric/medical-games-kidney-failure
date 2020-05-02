import java.awt.Cursor;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class EcouteurCentres implements MouseListener {

	private CelluleG cellule;
	private Centres hamecon;

	public EcouteurCentres(Centres hamecon, CelluleG cellule) {

		this.hamecon = hamecon;
		this.cellule = cellule;
	}

	public void mouseClicked(MouseEvent arg0) {
	}

	public void mouseEntered(MouseEvent arg0) {

		cellule.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

		if (cellule.presenceImage()) {

			String nomVille = (String) hamecon.obtenirListeCentres().get(cellule.obtenirPosition());

			// System.out.println("Ville: " + nomVille);
			// System.out.println("Nom image : " +
			// nomImage);

			hamecon.afficherInfoCentre(nomVille);
		}
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
