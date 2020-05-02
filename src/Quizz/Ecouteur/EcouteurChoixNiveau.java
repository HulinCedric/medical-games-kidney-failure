import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;

public class EcouteurChoixNiveau implements MouseListener {

	/**
	 * Hamecon
	 * 
	 * @since 1.0.0
	 */
	private Quizz hamecon;

	/**
	 * Constructeur normal
	 * 
	 * @param hamecon
	 * 
	 * @since 1.0.0
	 */
	public EcouteurChoixNiveau(Object hamecon) {
		this.hamecon = (Quizz) hamecon;
	}

	public void mouseClicked(MouseEvent e) {

		// Recuperer le texte du bouton clic
		//
		String niveau = ((JButton) e.getComponent()).getText();

		// Definir le nombre de questions
		// en fonction du niveau
		//
		int nombreQuestions = 5;
		if (niveau.equals("Facile")) {
			nombreQuestions = 5;
		} else if (niveau.equals("Moyen")) {
			nombreQuestions = 10;
		} else if (niveau.equals("Difficile")) {
			nombreQuestions = 15;
		}

		// Demarer une nouvelle partie
		//
		hamecon.nouvellePartie();
		hamecon.modifierNiveau(nombreQuestions);
		hamecon.chargerNouvelleQuestion();
	}

	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub

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
