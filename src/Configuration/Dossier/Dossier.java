/**
 * IUT de Nice / Departement informatique / Module APO-Java
 * Annee 2008_2009 - Traitement de dossier
 */
import java.io.File;
import java.util.Iterator;
import java.util.Vector;

import javax.swing.JOptionPane;

/**
 * La classe Dossier fournit 7 services destines a
 * simplifier la gestion de donnees des dossier.
 * 
 * @author Hulin Cedric, Fouco Charles
 * @version 1.0.0
 */
abstract public class Dossier {
    
    /**
     * Creation d'un vecteur contenant un nombre de donnees
     * identique sur plusieurs repertoire d'une extension
     * parametrable
     * 
     * @param cheminsRepertoire
     * @param nombreDonnees
     * @param extension
     * @return vecteur de donnees
     * @since 1.0.0
     */
    public static Vector obtenirVecteur(Vector cheminsRepertoire, int nombreDonnees, String extension) {
        
        // Verifier les parametres
        //
        if (cheminsRepertoire == null || nombreDonnees <= 0 || extension == null || extension.length() <= 0) {
            JOptionPane.showMessageDialog(null, "Dossier : " + Texte.load("../_Textes/Erreurs/Parametre"));
            return null;
        }
        
        Iterator i = cheminsRepertoire.iterator();
        Vector donnees = new Vector();
        while (i.hasNext()) {
            
            // Remplir un vecteur temporaire ordonnee
            //
            Vector fichiers = chargerRepertoire((String) i.next(), extension);
            if (fichiers == null)
                return null;
            
            // Appliquer un ordonnancement aleatoire
            //
            if (!ajouter(donnees, fichiers, nombreDonnees))
                return null;
        }
        
        // Retourner un vecteur melanger
        //
        if (!melanger(donnees))
            return null;
        return donnees;
    }
    
    /**
     * Creation d'un vecteur contenant un nombre de donnees
     * identique sur plusieurs repertoire d'une extension
     * parametrable
     * 
     * @param cheminsRepertoire
     * @param nombreDonnees
     * @param extension
     * @return vecteur de donnees
     * @since 1.0.0
     */
    public static Vector obtenirVecteur(String cheminsRepertoire, int nombreDonnees, String extension) {
        
        // Verifier les parametres
        //
        if (cheminsRepertoire.length() <= 0 || nombreDonnees <= 0 || extension == null || extension.length() <= 0) {
            JOptionPane.showMessageDialog(null, "Dossier : " + Texte.load("../_Textes/Erreurs/Parametre"));
            return null;
        }
        
        // Remplir un vecteur temporaire ordonnee
        //
        Vector fichiers = chargerRepertoire(cheminsRepertoire, extension);
        if (fichiers == null)
            return null;
        
        // Appliquer un ordonnancement aleatoire
        //
        Vector donnees = new Vector();
        if (!ajouter(donnees, fichiers, nombreDonnees))
            return null;
        
        // Retourner un vecteur melanger
        //
        if (!melanger(donnees))
            return null;
        return donnees;
    }
    
    /**
     * Creation d'un vecteur contenant la totalite des
     * donnees de plusieurs repertoire d'une extension
     * parametrable
     * 
     * @param cheminsRepertoire
     * @param nombreDonneesVariable
     * @param extension
     * @return vecteur de donnees
     * @since 1.0.0
     */
    public static Vector obtenirVecteur(Vector cheminsRepertoire, Vector nombreDonneesVariable, String extension) {
        
        // Verifier les parametres
        //
        if (cheminsRepertoire == null || nombreDonneesVariable == null || extension == null || extension.length() <= 0) {
            JOptionPane.showMessageDialog(null, "Dossier : " + Texte.load("../_Textes/Erreurs/Parametre"));
            return null;
        }
        
        // Verifier que les vecteur ont la meme taille
        //
        if (cheminsRepertoire.size() != nombreDonneesVariable.size()) {
            JOptionPane.showMessageDialog(null, "Dossier : " + Texte.load("../_Textes/Erreurs/Taille Vecteur"));
            return null;
        }
        
        // Verifier que les nombre variable sont correcte
        //
        Iterator i = nombreDonneesVariable.iterator();
        while (i.hasNext()) {
            if (((Integer) i.next()).intValue() <= 0) {
                JOptionPane.showMessageDialog(null, "Dossier : " + Texte.load("../_Textes/Erreurs/Parametre"));
                return null;
            }
        }
        
        i = cheminsRepertoire.iterator();
        Iterator j = nombreDonneesVariable.iterator();
        Vector donnees = new Vector();
        while (i.hasNext()) {
            
            // Remplir un vecteur temporaire ordonnee
            //
            Vector fichiers = chargerRepertoire((String) i.next(), extension);
            if (fichiers == null)
                return null;
            
            // Appliquer un ordonnancement aleatoire
            //
            if (!ajouter(donnees, fichiers, ((Integer) j.next()).intValue()))
                return null;
        }
        
        // Retourner un vecteur melanger
        //
        if (!melanger(donnees))
            return null;
        return donnees;
    }
    
    /**
     * Creation d'un vecteur contenant un nombre de donnees
     * variable sur plusieurs repertoire d'une extension
     * parametrable
     * 
     * @param cheminsRepertoire
     * @param extension
     * @return vecteur de donnees
     * @since 1.0.0
     */
    public static Vector obtenirVecteur(Vector cheminsRepertoire, String extension) {
        
        // Verifier les parametres
        //
        if (cheminsRepertoire == null || extension == null || extension.length() <= 0) {
            JOptionPane.showMessageDialog(null, "Dossier : " + Texte.load("../_Textes/Erreurs/Parametre"));
            return null;
        }
        
        Iterator i = cheminsRepertoire.iterator();
        Vector donnees = new Vector();
        while (i.hasNext()) {
            
            // Remplir un vecteur temporaire ordonnee
            //
            Vector fichiers = chargerRepertoire((String) i.next(), extension);
            if (fichiers == null)
                return null;
            
            // Appliquer un ordonnancement aleatoire
            //
            if (!ajouter(donnees, fichiers, fichiers.size()))
                return null;
        }
        
        // Retourner un vecteur melanger
        //
        if (!melanger(donnees))
            return null;
        return donnees;
    }
    
    /**
     * Recuperer le contenu d'un repertoire dans un vecteur
     * 
     * @param cheminRepertoire
     * @param extension
     * @return vecteur de donnees
     * @since 1.0.0
     */
    public static Vector chargerRepertoire(String cheminRepertoire, String extension) {
        
        // Verifier les parametres
        //
        if (cheminRepertoire == null || extension == null || extension.length() <= 0) {
            JOptionPane.showMessageDialog(null, "Dossier : " + Texte.load("../_Textes/Erreurs/Parametre"));
            return null;
        }
        
        // Creer le repertoire abstrait cible
        //
        File repertoire = new File(cheminRepertoire);
        
        // Controler l'existence des repertoires cibles
        //
        if (!repertoire.exists()) {
            JOptionPane.showMessageDialog(null, "Dossier : " + Texte.load("../_Textes/Erreurs/Dossier inexistant"));
            return null;
        }
        
        // Controler l'existence des repertoires cibles
        //
        if (!repertoire.isDirectory()) {
            JOptionPane.showMessageDialog(null, "Dossier : " + Texte.load("../_Textes/Erreurs/Dossier fichier"));
            return null;
        }
        
        // Obtenir la liste de tous les fichiers du
        // repertoire cible
        //
        File[] files = repertoire.listFiles();
        
        // Remplir un vecteur temporaire ordonnee
        //
        Vector fichiers = new Vector();
        for (int j = 0; j < files.length; j++)
            if (files[j].getName().indexOf(extension) > 0)
                fichiers.add(files[j].getAbsolutePath());
        
        // Restitution du resultat
        //
        return fichiers;
    }
    
    /**
     * Melanger un vecteur de donnees
     * 
     * @param donnees
     * @return flag de reussite
     * @since 1.0.0
     */
    public static boolean melanger(Vector donnees) {
        
        // Verifier les parametres
        //
        if (donnees == null) {
            JOptionPane.showMessageDialog(null, "Dossier : " + Texte.load("../_Textes/Erreurs/Parametre"));
            return false;
        }
        
        // Organiser de maniere aleatoire les donnees
        //
        Vector donneesMelanger = new Vector();
        while (donneesMelanger.size() != donnees.size()) {
            int random = (int) (Math.random() * (donnees.size()));
            if (!donneesMelanger.contains(donnees.get(random)))
                donneesMelanger.add(donnees.get(random));
        }
        donnees.clear();
        donnees.addAll(donneesMelanger);
        return true;
    }
    
    /**
     * Ajout d'un nombre de donnees aleatoirement dans un
     * vecteur de donnees
     * 
     * @param donnees
     * @param donneesVoulues
     * @param nombreDonnees
     * @return flag de reussite
     */
    public static boolean ajouter(Vector donnees, Vector donneesVoulues, int nombreDonnees) {
        
        // Verifier les parametres
        //
        if (donnees == null || donneesVoulues == null || nombreDonnees <= 0) {
            JOptionPane.showMessageDialog(null, "Dossier : " + Texte.load("../_Textes/Erreurs/Parametre"));
            return false;
        }
        
        // Controler le nombre d'images demande
        //
        if (donneesVoulues.size() < nombreDonnees) {
            JOptionPane.showMessageDialog(null, "Dossier : " + Texte.load("../_Textes/Erreurs/Nombre donnees"));
            return false;
        }
        
        // Appliquer un ordonnancement aleatoire
        //
        int j = 0;
        while (j < nombreDonnees) {
            int random = (int) (Math.random() * (donneesVoulues.size()));
            if (!donnees.contains(donneesVoulues.get(random))) {
                donnees.add(donneesVoulues.get(random));
                j++;
            }
        }
        return true;
    }
    
    /**
     * Recuperer le contenu d'un repertoire dans un vecteur
     * 
     * @param cheminRepertoire
     * @param extension
     * @return vecteur de donnees
     * @since 1.0.0
     */
    public static Vector chargerDirectory(String cheminRepertoire) {
        
        // Verifier les parametres
        //
        if (cheminRepertoire == null) {
            JOptionPane.showMessageDialog(null, "Dossier : " + Texte.load("../_Textes/Erreurs/Parametre"));
            return null;
        }
        
        // Creer le repertoire abstrait cible
        //
        File repertoire = new File(cheminRepertoire);
        
        // Controler l'existence des repertoires cibles
        //
        if (!repertoire.exists()) {
            JOptionPane.showMessageDialog(null, "Dossier : " + Texte.load("../_Textes/Erreurs/Dossier inexistant"));
            return null;
        }
        
        // Controler l'existence des repertoires cibles
        //
        if (!repertoire.isDirectory()) {
            JOptionPane.showMessageDialog(null, "Dossier : " + Texte.load("../_Textes/Erreurs/Dossier fichier"));
            return null;
        }
        
        // Obtenir la liste de tous les fichiers du
        // repertoire cible
        //
        File[] files = repertoire.listFiles();
        
        // Remplir un vecteur temporaire ordonnee
        //
        Vector fichiers = new Vector();
        for (int j = 0; j < files.length; j++)
                fichiers.add(files[j].getAbsolutePath());
        
        // Restitution du resultat
        //
        return fichiers;
    }
}