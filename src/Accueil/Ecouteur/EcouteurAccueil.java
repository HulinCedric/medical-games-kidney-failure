import java.awt.Cursor;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;

import org.jdom.Element;

public class EcouteurAccueil implements MouseListener {

	Demarrer hamecon;
	CelluleG cellule;

	public EcouteurAccueil(Demarrer hamecon, CelluleG cellule) {
		this.hamecon = hamecon;
		this.cellule = cellule;
	}

	public void mouseClicked(MouseEvent e) {

		cellule.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

		if (cellule.presenceImage()) {
			File file = new File(cellule.obtenirCheminImage());
			String titre = file.getName().substring(0, file.getName().length() - 4);
			
			// Traiter le cas du bouton jeux
			//
			if(titre.equals("Jeux"))
			{
				Element configPanneauNord = (Element) ConfigXML.load("Configuration/Jeux/ConfigPanneauNord", "1.0.0");
				Element configGrille = (Element) ConfigXML.load("Configuration/Jeux/ConfigGrille", "1.0.0");
			
				hamecon.pageJeux(configGrille, configPanneauNord); 
				hamecon.obtenirFenetreSupport().getContentPane().paintAll(hamecon.obtenirFenetreSupport().getContentPane().getGraphics());
			}
			if(titre.equals("Realisateurs"))
			{
				
				hamecon.pageRealisateurs();
				hamecon.obtenirFenetreSupport().getContentPane().paintAll(hamecon.obtenirFenetreSupport().getContentPane().getGraphics());
			}
			if(titre.equals("Centres"))
			{
				Element configGrille = (Element) ConfigXML.load("Configuration/Centres/ConfigGrille", "1.0.0");
				
				hamecon.pageCentres(configGrille);
				hamecon.obtenirFenetreSupport().getContentPane().paintAll(hamecon.obtenirFenetreSupport().getContentPane().getGraphics());
			}
			if(titre.equals("Accueil"))
			{
				Element configGrille = (Element) ConfigXML.load("Configuration/Accueil/ConfigGrille", "1.0.0");
				
				hamecon.pageAccueil(configGrille);
				hamecon.obtenirFenetreSupport().getContentPane().paintAll(hamecon.obtenirFenetreSupport().getContentPane().getGraphics());
			}
			if(titre.equals("Bonus"))
			{
				Element configPanneauNord = (Element) ConfigXML.load("Configuration/Bonus/ConfigPanneauNord", "1.0.0");
				Element configGrille = (Element) ConfigXML.load("Configuration/Bonus/ConfigGrille", "1.0.0");
				
				hamecon.pageBonus(configPanneauNord, configGrille);
				hamecon.obtenirFenetreSupport().getContentPane().paintAll(hamecon.obtenirFenetreSupport().getContentPane().getGraphics());
			}
			
			if (titre.equals("Recettes")) {
				Element configGrille = (Element) ConfigXML.load("Configuration/Recettes/ConfigurationSommaire", "1.0.0");
				
				hamecon.pageRecettes(configGrille);
				hamecon.obtenirFenetreSupport().getContentPane().paintAll(hamecon.obtenirFenetreSupport().getContentPane().getGraphics());
			}
			if (titre.equals("Abecedaire")) {
				Element configGrille = (Element) ConfigXML.load("Configuration/Abecedaire/ConfigurationSommaire", "1.0.0");
				
				hamecon.pageAbecedaire(configGrille);
				hamecon.obtenirFenetreSupport().getContentPane().paintAll(hamecon.obtenirFenetreSupport().getContentPane().getGraphics());
			}
			
			//hamecon.obtenirGrilleSud().ajouterImage("../_Images/Accueil/Fond/Bottom_" + titre + ".jpg");
		}

		/*String command = "java -jar ../../Quizz/Quizz";

		try {
			Runtime.getRuntime().exec("java -jar ../Quizz/Quizz.jar");
			System.out.println("ok");
		} catch (IOException ex) {
			System.out.println(ex);
		}
		*/
		
		// Lancer un .bat
		//
		/*try {
			String[] command = { "cmd.exe", "/C", "Start", "C:\\Fichier.bat" };
               	Runtime r = Runtime.getRuntime();
                Process p = r.exec(command);
                p.waitFor();

                } catch (Exception e) 
                {

                System.out.println("erreur d'execution");} */
		
		/*
		 * avec parametre
		 
		String[] tab = new String[2];
		tab[0] = "lancement.bat";
		tab[1] = "param";
		try{ Runtine.getRunTime().exec(tab); }*/

	}

	public void mouseEntered(MouseEvent e) {


	}

	public void mouseExited(MouseEvent e) {

	}

	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

}
