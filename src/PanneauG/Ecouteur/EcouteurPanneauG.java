//
// IUT de Nice / Departement informatique / Module APO-Java
// Annee 2009_2010 - Package SWING
//
// Classe EcouteurPanneauG - Ecouteur souris de toute
// instance de PanneauG
//
// Edition A : Cours_10
// + Version 1.0.0 : limite a des "call back" independantes
// de la cible
// + Version 1.1.0 : introduction d'une "call back" pour
// visualiser les
// coordonnees (ligne, colonne) de la maille designee
// par un clic souris
// 
// Auteur : A. Thuaire
//

import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class EcouteurPanneauG implements MouseListener, MouseMotionListener {
    protected PanneauG panneauCible;
    
    public EcouteurPanneauG() {}
    
    public EcouteurPanneauG(PanneauG hamecon) {
        panneauCible = hamecon;
    }
    
    public void mouseClicked(MouseEvent e) {
        
        // Obtenir les coordonnees absolues du clic courant
        //
        int x = e.getX();
        int y = e.getY();
        
        // Obtenir la designation de la maille cible
        //
        Dimension cible = panneauCible.obtenirPositionCible(x, y);
        if (cible == null) return;
        
        // Extraire les coordonnees (ligne, colonne) de
        // cette maille
        //
        int ligne = (int) cible.getWidth();
        int colonne = (int) cible.getHeight();
        
        // Visualiser la designation de cette maille
        //
        System.out.println("(" + ligne + ", " + colonne + ")");
    }
    
    public void mouseMoved(MouseEvent e) {}
    
    public void mouseDragged(MouseEvent e) {}
    
    public void mousePressed(MouseEvent e) {}
    
    public void mouseReleased(MouseEvent e) {}
    
    public void mouseEntered(MouseEvent e) {}
    
    public void mouseExited(MouseEvent e) {}
}
