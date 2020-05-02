import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class EcouteurAbecedaire implements MouseListener {

	private Abecedaire hamecon;

	public EcouteurAbecedaire(Abecedaire hamecon) {
		this.hamecon = hamecon;
	}

	public void mouseClicked(MouseEvent arg0) {
		int index = hamecon.obtenirListeAliments().getSelectedIndex();
		hamecon.afficherInfoAliments(index);
	}

	public void mouseEntered(MouseEvent arg0) {
	}

	public void mouseExited(MouseEvent arg0) {
	}

	public void mousePressed(MouseEvent arg0) {
	}

	public void mouseReleased(MouseEvent arg0) {
	}
}
