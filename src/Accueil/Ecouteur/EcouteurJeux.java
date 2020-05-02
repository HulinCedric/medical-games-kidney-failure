import java.awt.Cursor;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;

public class EcouteurJeux implements MouseListener {

	Jeux hamecon;
	CelluleG cellule;

	public EcouteurJeux(Jeux hamecon, CelluleG cellule) {
		this.hamecon = hamecon;
		this.cellule = cellule;
	}

	public void mouseClicked(MouseEvent arg0) {
		cellule.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

		if (cellule.presenceImage()) {
			File file = new File(cellule.obtenirCheminImage());
			String titre = file.getName().substring(0, file.getName().length() - 4);

			// Lancer un .bat
			//
			//
			try {
				
				
				String[] tab = new String[2];
				tab[0] = "..\\" + titre + "\\" + titre + ".bat";
				tab[1] = hamecon.obtenirStyleSelectionnee();

				//String[] command = { "cmd.exe", "/C", "Start", "..\\ImageMysterieuse\\ImageMysterieuse.bat" };
				/*Runtime r = Runtime.getRuntime();
				Process p = r.exec(tab);
				p.waitFor();*/
				Runtime.getRuntime().exec(tab);
				System.out.println("execution : OK");

			} catch (Exception e) {

				System.out.println("erreur d'execution");
			}
		}

		
		  /*String command = "java -jar ../../Quizz/Quizz";
		  
		  try { Runtime.getRuntime().exec("java -jar ../Quizz/Quizz.jar");
		  System.out.println("ok"); } catch (IOException ex) {
		  System.out.println(ex); }*/
		 
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
