//
// IUT de Nice / Departement informatique / Module APO-Java
// Annee 2009_2010 - Package SWING
//
// Classe TitrePanneauG - Titre textuel de toute instance de
// PanneauG
//
// Edition A : Cours_10
// + Version 1.0.0 : version initiale
// + Version 1.1.0 : ajout accesseurs de consultation
// obtenirTexteTitre,
// obtenirCouleurTitre et obtenirPoliceTitre
//
// Auteur : A. Thuaire
//

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;

public class TitrePanneauG {
    private PanneauG panneau;
    private String texteTitre;
    private Color couleurTitre;
    private Font policeTitre;
    
    // ------ *** Constructeur normal
    
    public TitrePanneauG(PanneauG hamecon, String texte, Color couleur, Font police) {
        
        // Memoriser les attributs transmis par parametre
        //
        panneau = hamecon;
        texteTitre = texte;
        couleurTitre = couleur;
        policeTitre = police;
    }
    
    // ------ *** Accesseurs de consultation
    
    public String obtenirTexteTitre() {
        return texteTitre;
    }
    
    public Color obtenirCouleurTitre() {
        return couleurTitre;
    }
    
    public Font obtenirPoliceTitre() {
        return policeTitre;
    }
    
    // ------ *** Methode dessiner
    
    public void dessiner(Graphics g) {
        
        // Controler la presence d'un titre
        //
        if (texteTitre == null) return;
        
        // Calculer les dimensions du titre
        //
        FontMetrics fm = g.getFontMetrics();
        
        int largeurTitre = fm.stringWidth(texteTitre);
        int hauteurTitre = fm.getHeight();
        
        // Modifier la couleur courante de dessin dans le
        // panneau
        //
        if (couleurTitre != null) g.setColor(couleurTitre);
        
        // Modifier la police courante du panneau
        //
        if (policeTitre != null) g.setFont(policeTitre);
        
        // Determiner les dimensions du panneau cible
        //
        int largeurPanneau = panneau.getWidth();
        int hauteurPanneau = panneau.getHeight();
        
        // Traiter le cas de l'absence d'image de fond
        //
        if (!panneau.presenceImage()) {
            
            // Dessiner le texte
            //        
            g.drawString(texteTitre, (largeurPanneau - largeurTitre) / 2, (hauteurPanneau + hauteurTitre)*19 / 40);
        }
        
        else {
            
            // Traiter le cas de la presence d'une image de
            // fond
            //
            g.drawString(texteTitre, (largeurPanneau - largeurTitre) / 2, (hauteurPanneau + hauteurTitre)*19 / 40);
        }
    }
}
