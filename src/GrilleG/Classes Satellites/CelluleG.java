/**
 * IUT de Nice / Departement informatique / Module APO-Java
 * Annee 2009_2010 - Package SWING
 * 
 * @Edition A : Cours_10
 * 
 * @version 1.0.0 :
 * 
 *          version initiale
 * 
 * @version 1.1.0 :
 * 
 *          ajout de la possibilite d'ajout de texte ; ajout
 *          de la possibilite de retrait de texte ; ajout de
 *          gestion de bordure ;
 * 
 * @version 1.2.0
 * 
 *          ajout d'un methode controlant la presence d'un
 *          texte dans une cellule
 */
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.BorderFactory;

/**
 * Element de la partition d'une mosaique generique
 * 
 * @author Alain Thuaire, Charles Fouco, Cedric Hulin
 * @version 1.2.0
 */
public class CelluleG extends PanneauG {
    
    /**
     * Hamecon du panneau parent
     * 
     * @since 1.0.0
     */
    private Object mosaique;
    
    /**
     * Position de la cellule dans le panneau
     * 
     * @since 1.0.0
     */
    private Dimension position;
    
    /**
     * Etat de la cellule
     * 
     * @since 1.0.0
     */
    private boolean etatCellule;
    
    /**
     * Demons attacher a la cellule
     * 
     * @since 1.0.0
     */
    private Object[] demons;
    
    /**
     * Constructeur normal
     * 
     * @param hamecon
     * @param config
     * @param position
     * @since 1.1.0
     */
    public CelluleG(Object hamecon, Object config, Dimension position) {
        super(hamecon, config);
        
        // Controler la validite des parametres
        //
        if (hamecon == null) return;
        if (config == null) return;
        if (position == null) return;
        
        // Memoriser les attributs transmis par parametre
        //
        mosaique = hamecon;
        this.position = position;
        
        // Fixer l'etat par defaut de la cellule
        //
        etatCellule = false;
        
        // Bordure par defaut
        // 
        setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLoweredBevelBorder(), BorderFactory.createMatteBorder(3, 3, 0, 0, Color.black)));
        
        // Ajouter le panneau sous jacent au panneau
        // principal
        //
        ((PanneauG) mosaique).add(this);
    }
    
    /**
     * Obtention de la position de la cellule
     * 
     * @return position
     * @since 1.0.0
     */
    public Dimension obtenirPosition() {
        return position;
    }
    
    /**
     * Obtention du chemin de l'image
     * 
     * @return chemin
     * @since 1.0.0
     */
    public String obtenirCheminImage() {
        return super.obtenirCheminImage();
    }
    
    /**
     * Obtention du texte faisant office de titre
     * 
     * @return titre
     * @since 1.0.0
     */
    public String obtenirTexteTitre() {
        return super.obtenirTexteTitre();
    }
    
    /**
     * Obtention de la couleur du titre
     * 
     * @return couleur
     * @since 1.0.0
     */
    public Color obtenirCouleurTitre() {
        return super.obtenirCouleurTitre();
    }
    
    /**
     * Obtention de la police du titre
     * 
     * @return police
     * @since 1.0.0
     */
    public Font obtenirPoliceTitre() {
        return super.obtenirPoliceTitre();
    }
    
    /**
     * Obtention de l'etat de la cellule
     * 
     * @return etat
     * @since 1.0.0
     */
    public boolean obtenirEtat() {
        return etatCellule;
    }
    
    /**
     * Obtention de la totalite des demons de la cellule
     * 
     * @return demons
     * @since 1.0.0
     */
    public Object[] obtenirDemons() {
        return demons;
    }
    
    /**
     * Modification de l'etat de la cellule
     * 
     * @param etat
     * @since 1.0.0
     */
    public void fixerEtat(boolean etat) {
        etatCellule = etat;
    }
    
    /**
     * Ajout d'une image a une cellule
     * 
     * @param cheminImage
     * @since 1.0.0
     */
    public void ajouterImage(String cheminImage) {
        
        // Controler la validite du parametre
        //
        if (cheminImage == null) return;
        
        // Ajouter l'image au panneau sous jacent
        //
        super.ajouterImage(cheminImage);
    }
    
    /**
     * Retrait de l'image d'une cellule
     * 
     * @since 1.0.0
     */
    public void retirerImage() {
        super.retirerImage();
    }
    
    /**
     * Ajout d'un texte a une cellule
     * 
     * @param texte
     * @param couleur
     * @param police
     * @return flag de reussite
     * @since 1.1.0
     */
    public boolean ajouterTexte(String texte, Color couleur, Font police) {
        
        // Controler la validite des parametres
        //
        if (texte == null) return false;
        if (couleur == null) return false;
        if (police == null) return false;
        
        return super.ajouterTitre(texte, couleur, police);
    }
    
    /**
     * Retrait du texte d'une cellule
     * 
     * @since 1.1.0
     */
    public void retirerTexte() {
        super.retirerTitre();
    }
    
    /**
     * Ajout d'un demon a une cellule
     * 
     * @return flag reussite
     * @since 1.0.0
     */
    public boolean _ajouterDemon() {
        return true;
    }
    
    /**
     * Retrait d'un demon d'une cellule
     * 
     * @return flag reussite
     * @since 1.0.0
     */
    public boolean _retirerDemon() {
        return true;
    }
    
    /**
     * Controle la presence d'un demon sur une cellule
     * 
     * @return flag de presence
     * @since 1.0.0
     */
    public boolean _presenceDemon() {
        
        if (demons.length != 0) return true;
        return false;
    }
    
    /**
     * Controle la presence d'un texte dans une cellule
     * 
     * @return
     * @since 1.2.0
     */
    public boolean presenceTexte() {
        
        return super.presenceTitre();
    }
    
    /**
     * Ajout d'une image a une cellule
     * 
     * @param cheminImage
     * @since 1.3.0
     */
    public void ajouterImage(String cheminImage, int index, int decalageEntreImages, int decalageHauteur, int decalageCoter) {
        
        // Controler la validite du parametre
        //
        if (cheminImage == null) return;
        
        // Ajouter l'image au panneau sous jacent
        //
        super.ajouterImage(cheminImage, index, decalageEntreImages, decalageHauteur, decalageCoter);
    }
    
    /**
     * Modifier le nombre d'images de la cellule
     * 
     * @param nombreImages
     */
    public boolean modifierNombreImages(int nombreImages) {
       
        // Verifier la validite du parametre
        //
        if (nombreImages <= 0) return false;
        
        super.modifierNombreImages(nombreImages);
        return true;
    }
}