import java.awt.Cursor;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class EcouteurRetourAbecedaire implements MouseListener {

	private Abecedaire hamecon;
	private Demarrer support;

	public EcouteurRetourAbecedaire(Demarrer support, Abecedaire hamecon) {
		this.hamecon = hamecon;
		this.support = support;
	}

	public void mouseClicked(MouseEvent arg0) {
		hamecon.appelAlphabet();
	}

	public void mouseEntered(MouseEvent arg0) {
		support.obtenirGrilleSud().obtenirCellule(1, 1).setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
	}

	public void mouseExited(MouseEvent arg0) {
	}

	public void mousePressed(MouseEvent arg0) {
	}

	public void mouseReleased(MouseEvent arg0) {
	}
}
