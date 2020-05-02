//
// IUT de Nice / Departement informatique / Module APO-Java
// Annee 2009_2010 - Package SWING
//
// Classe MaillagePanneauG - Maillage generique d'un
// panneau, supportant toute
// homothetie dynamique de la taille de ce dernier
//
// Edition A : Cours_10
// + Version 1.0.0 : la maille elementaire ne peut etre
// qu'un rectangle
// + Version 1.1.0 : ajout des methodes obtenirPositionClic
// dans les
// classes MaillagePanneauG et classe interne Maille

//
// Auteur : A. Thuaire
//

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Polygon;

import javax.swing.JPanel;

public class MaillagePanneauG {
    
    private JPanel hamecon;
    private Color couleurTrait;
    private Color couleurFond;
    private int nbLignes;
    private int nbColonnes;
    private int aritePolygone = 4;
    private Maille[][] matriceMailles;
    
    // ------ *** Premier constructeur normal
    
    public MaillagePanneauG(JPanel hamecon, Color couleurTrait, Color couleurFond, int nbLignes, int nbColonnes) {
        
        // Controler la validite des parametres
        //
        if (hamecon == null) return;
        if (couleurTrait == null) return;
        if (nbLignes <= 0) return;
        if (nbColonnes <= 0) return;
        
        // Memoriser les attributs transmis par parametre
        //
        this.hamecon = hamecon;
        this.couleurTrait = couleurTrait;
        this.couleurFond = couleurFond;
        this.nbLignes = nbLignes;
        this.nbColonnes = nbColonnes;
        
        // Creer la matrice des mailles
        //
        matriceMailles = new Maille[nbLignes][nbColonnes];
        
        // Remplir cette matrice en y ajoutant chacune des
        // mailles
        //
        for (int i = 1; i <= nbLignes; i++)
            for (int j = 1; j <= nbColonnes; j++)
                ajouterMaille(i, j);
    }
    
    // ------ *** Methode ajouterMaille
    
    public boolean ajouterMaille(int ligne, int colonne) {
        
        // Controler la validite des parametres
        //
        if (ligne < 1 || ligne > nbLignes) return false;
        
        // Controler la validite des parametres
        //
        if (colonne < 1 || colonne > nbColonnes) return false;
        
        // Controler la validite (absence prealable) de
        // l'ajout
        //
        if (matriceMailles[ligne - 1][colonne - 1] != null) return false;
        
        // Creer et affecter la nouvelle maille
        //
        matriceMailles[ligne - 1][colonne - 1] = new Maille(ligne, colonne);
        
        // Repeindre le panneau support
        //
        hamecon.repaint();
        
        return true;
    }
    
    // ------ *** Methode retirerMaille
    
    public boolean retirerMaille(int ligne, int colonne) {
        
        // Controler la validite des parametres
        //
        if (ligne < 1 || ligne > nbLignes) return false;
        
        // Controler la validite des parametres
        //
        if (colonne < 1 || colonne > nbColonnes) return false;
        
        // Controler la validite (absence prealable) de
        // l'ajout
        //
        if (matriceMailles[ligne - 1][colonne - 1] == null) return false;
        
        // Retirer la maille cible
        //
        matriceMailles[ligne - 1][colonne - 1] = null;
        
        // Repeindre le panneau support
        //
        hamecon.repaint();
        
        return true;
    }
    
    // ------ *** Methode dessiner
    
    public void dessiner(Graphics g) {
        
        // Dessiner chacune des mailles presentes
        //
        for (int i = 0; i < nbLignes; i++)
            for (int j = 0; j < nbColonnes; j++)
                if (matriceMailles[i][j] != null) matriceMailles[i][j].dessiner(g);
    }
    
    // ------ *** Methode obtenirPositionCible
    
    public Dimension obtenirPositionCible(int x, int y) {
        
        // Parcourir la matrice des mailles
        //
        Maille mailleCourante = null;
        Dimension designation;
        
        for (int i = 0; i < nbLignes; i++) {
            
            for (int j = 0; j < nbColonnes; j++) {
                
                // Obtenir la maille courante
                //
                mailleCourante = matriceMailles[i][j];
                
                // Determiner si la maille courante a ete
                // neutralisee
                //
                if (mailleCourante == null) continue;
                
                // Determiner si le point cible (x, y)
                // appartient
                // a la maille courante
                //
                designation = mailleCourante.obtenirPositionCible(x, y);
                if (designation != null) return designation;
            }
        }
        
        return null;
    }
    
    // ------------------------------------------- ***
    // Classe interne Maille
    
    private class Maille {
        private int positionLigne;
        private int positionColonne;
        
        public Maille(int ligne, int colonne) {
            
            // Memoriser les attributs transmis par
            // parametre
            //
            positionLigne = ligne;
            positionColonne = colonne;
        }
        
        // ------ *** Methode obtenirPolygone
        
        private Polygon obtenirPolygone() {
            
            // Obtenir la taille courante du panneau support
            //
            int largeurPanneau = hamecon.getWidth();
            int hauteurPanneau = hamecon.getHeight();
            
            // Determiner les dimensions de l'enveloppe de
            // la maille elementaire
            //
            int largeurMaille = (int) largeurPanneau / nbColonnes;
            int hauteurMaille = (int) hauteurPanneau / nbLignes;
            
            // Determiner les abscisses des sommets de la
            // maille cible
            //
            int[] abscisses = new int[aritePolygone];
            
            abscisses[0] = (positionColonne - 1) * largeurMaille;
            abscisses[1] = abscisses[0];
            abscisses[2] = abscisses[0] + largeurMaille;
            abscisses[3] = abscisses[2];
            
            // Determiner les ordonnees des sommets de la
            // maille cible
            //
            int[] ordonnees = new int[aritePolygone];
            
            ordonnees[0] = (positionLigne - 1) * hauteurMaille;
            ordonnees[1] = ordonnees[0] + hauteurMaille;
            ordonnees[2] = ordonnees[1];
            ordonnees[3] = ordonnees[0];
            
            // Construire le polygone associe a la maille
            // cible
            //
            Polygon polygone = new Polygon(abscisses, ordonnees, aritePolygone);
            
            // Restituer le resultat
            //
            return polygone;
        }
        
        // ------ *** Methode dessiner
        
        public void dessiner(Graphics g) {
            
            // Construire le polygone attache a la maille
            // support
            //
            Polygon polygone = obtenirPolygone();
            
            // Traiter le cas d'un pavage colore
            //
            if (couleurFond != null) {
                g.setColor(couleurFond);
                g.fillPolygon(polygone.xpoints, polygone.ypoints, aritePolygone);
            }
            
            // Modifier la couleur courante du trait de
            // dessin
            //
            g.setColor(couleurTrait);
            
            // Dessiner les bords de la maille courante
            //
            g.drawPolygon(polygone);
        }
        
        // ------ *** Methode obtenirPositionCible
        
        public Dimension obtenirPositionCible(int x, int y) {
            
            // Construire le polygone attache a la maille
            // cible
            //
            Polygon polygone = obtenirPolygone();
            
            // Determiner si le point cible (x, y)
            // appartient a cette maille
            //	
            if (polygone.contains(x, y)) return new Dimension(positionLigne, positionColonne);
            
            return null;
        }
    }
}
