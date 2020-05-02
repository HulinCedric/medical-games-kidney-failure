//
// IUT de Nice / Departement informatique / Module APO-Java
// Annee 2009_2010 - Package SWING
//
// Classe ImagePanneauG - Image de fond de toute instance de
// PanneauG
//
// Edition A : Cours_10
// + Version 1.0.0 : version initiale
// + Version 1.1.0 : ajout de l'accesseur obtenirCheminImage
//
// Auteur : A. Thuaire
//

import java.awt.Graphics;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.Toolkit;

public class ImagePanneauG {
    private PanneauG panneau;
    private String cheminFichier;
    private Image image;
    
    private int decalageHauteur;
    private int decalageCoter;
    private int index;
    private int decalageEntreImages;
    
    // ------ *** Constructeur normal
    
    public ImagePanneauG(PanneauG hamecon, String chemin) {
        
        // Renseigner les attributs transmis par parametre
        //
        this.panneau = hamecon;
        cheminFichier = chemin;
        index = -1;
        
        // Charger l'image de fond depuis le fichier support
        //
        image = Toolkit.getDefaultToolkit().getImage(chemin);
        
        // Construire un media tracker pour controler le
        // chargement de l'image
        //
        MediaTracker mt = new MediaTracker(panneau);
        
        // Attendre la fin du chargement effectif de l'image
        //
        mt.addImage(image, 0);
        try {
            mt.waitForAll();
        }
        catch (Exception e) {}
    }
    
    // ------ *** Second constructeur normal
    
    public ImagePanneauG(PanneauG hamecon, String chemin, int index, int decalageEntreImages, int decalageHauteur, int decalageCoter) {
        
        // Renseigner les attributs transmis par parametre
        //
        this.panneau = hamecon;
        cheminFichier = chemin;
        this.decalageHauteur = decalageHauteur;
        this.decalageCoter = decalageCoter;
        this.index = index;
        this.decalageEntreImages = decalageEntreImages;
        
        // Charger l'image de fond depuis le fichier support
        //
        image = Toolkit.getDefaultToolkit().getImage(chemin);
        
        // Construire un media tracker pour controler le
        // chargement de l'image
        //
        MediaTracker mt = new MediaTracker(panneau);
        
        // Attendre la fin du chargement effectif de l'image
        //
        mt.addImage(image, 0);
        try {
            mt.waitForAll();
        }
        catch (Exception e) {}
    }
    
    // ------ *** Accesseurs de consultation
    
    public String obtenirCheminImage() {
        return cheminFichier;
    }
    
    // ------ *** Methode dessiner
    
    public void dessiner(Graphics g) {
        
        if (image != null) {
            if (index == -1)
                g.drawImage(image, 0, 0, panneau.getWidth(), panneau.getHeight(), null);
            else {
                g.drawImage(image, decalageCoter, decalageHauteur + index * decalageEntreImages, panneau.getWidth() - decalageCoter * 2, panneau.getHeight() - decalageHauteur - ((panneau.obtenirNombreImagesAllocation() - index) * decalageEntreImages) - (index * decalageEntreImages) , null);
            }
        }
    }
}
