import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class EcouteurAlphabet implements MouseListener {

	private Abecedaire hamecon;
	private char caractere;

	public EcouteurAlphabet(Abecedaire hamecon, char car) {
		this.hamecon = hamecon;
		this.caractere = car;
	}

	public void mouseClicked(MouseEvent arg0) {
		hamecon.appelFicheAlimentaire(caractere);
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
