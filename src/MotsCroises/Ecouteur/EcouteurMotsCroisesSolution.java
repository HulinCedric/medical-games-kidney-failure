import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class EcouteurMotsCroisesSolution implements MouseListener {

	private MotsCroises hamecon;

	public EcouteurMotsCroisesSolution(MotsCroises hamecon) {
		this.hamecon = hamecon;
	}

	public void mouseClicked(MouseEvent e) {

		if (hamecon.obtenirSolution() != null)
			hamecon.remplirGrilleSolution();
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
