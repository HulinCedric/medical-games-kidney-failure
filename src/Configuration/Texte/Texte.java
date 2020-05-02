/**
 * IUT de Nice / Departement informatique / Module APO-Java
 * Annee 2008_2009 - Traitement des fichiers textes
 */
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * La classe Texte fournit une methode load permettant de
 * simplifier la gestion de donnees textuelles.
 * 
 * @author Fouco Charles, Hulin Cedric
 * @version 1.0.0
 */
abstract public class Texte {

	// ------------------------------------------ ***
	// Methode store

	public static String load(String name) {

		if (name == null)
			return null;

		String chaineResultat = "";

		// Lecture du fichier texte
		//
		try {
			InputStream ips = new FileInputStream(name + ".txt");
			InputStreamReader ipsr = new InputStreamReader(ips);
			BufferedReader br = new BufferedReader(ipsr);
			String ligne;
			while ((ligne = br.readLine()) != null) {
				chaineResultat += ligne + "\n";
			}
			br.close();
		} catch (Exception e) {
			System.out.println(e.toString());
		}

		return chaineResultat;
	}
}
