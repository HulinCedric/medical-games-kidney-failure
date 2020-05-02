import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class EcouteurRecettes implements MouseListener, MouseMotionListener {

	private Recettes hamecon;

	public EcouteurRecettes(Recettes hamecon) {
		this.hamecon = hamecon;
	}

	public void mouseClicked(MouseEvent arg0) {
		int index = hamecon.obtenirListeCentres().getSelectedIndex();
		hamecon.afficherInfoCentre(index);
	}

	public void mouseEntered(MouseEvent arg0) {
	}

	public void mouseExited(MouseEvent arg0) {
		int index = hamecon.obtenirListeCentres().getSelectedIndex();
		String cheminImage = hamecon.obtenirCheminImage(index);
		if (cheminImage != null) {
			hamecon.ajouterImage(cheminImage);
		}
	}

	public void mousePressed(MouseEvent arg0) {
	}

	public void mouseReleased(MouseEvent arg0) {
	}

	public void mouseDragged(MouseEvent e) {
	}

	public void mouseMoved(MouseEvent e) {
		Point p = new Point(e.getX(), e.getY());
		int index = hamecon.obtenirListeCentres().locationToIndex(p);
		String cheminImage = hamecon.obtenirCheminImage(index);

		if (cheminImage != null) {
			hamecon.ajouterImage(cheminImage);
		}
	}
}
