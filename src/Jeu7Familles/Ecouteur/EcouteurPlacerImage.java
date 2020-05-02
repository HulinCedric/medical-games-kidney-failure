import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.File;

public class EcouteurPlacerImage implements MouseListener, MouseMotionListener {
    
    private Jeu7Familles hamecon;
    
    public EcouteurPlacerImage(Jeu7Familles hamecon) {
        this.hamecon = hamecon;
    }
    
    public void mouseClicked(MouseEvent e) {
        
        // Verifier l'etat du jeu
        //
        if (!hamecon.obtenirEtatJeu()) return;
        
        // Verifier la presence d'une image selectionnee
        //
        if (hamecon.obtenirCheminImageSelectionnee().equals("")) return;
        
        // Obtenir les coordonnees absolues du clic courant
        //
        int x = e.getX();
        int y = e.getY();
        
        // Obtenir la designation de la cellule cible
        //
        Dimension positionCellule = hamecon.obtenirGrilleNord().obtenirPositionCelluleCible(x, y);
        if (positionCellule == null) return;
        
        // Extraire les coordonnees (ligne, colonne) de
        // cette cellule
        //
        int ligneCellule = (int) positionCellule.getWidth();
        int colonneCellule = (int) positionCellule.getHeight();
        
        // Obtenir la position relative du clic dans la
        // cellule cible
        //
        Dimension cible = hamecon.obtenirGrilleNord().obtenirPositionRelativeCible(x, y);
        if (cible == null) return;
        
        // Visualiser les coordonnees de cette cellule
        //
        //System.out.println("[" + ligneCellule + ", " + colonneCellule + "]");
        
        // Recuperer le nombre d'images presentent dans la
        // cellule
        //
        int nombreImagesPresentent = hamecon.obtenirGrilleNord().obtenirCellule(ligneCellule, colonneCellule).obtenirNombreImagesPresentent();
        
        // Recuperer le nombre d'images possibles dans la
        // cellule
        //
        int nombreImagesPossibles = hamecon.obtenirGrilleNord().obtenirCellule(ligneCellule, colonneCellule).obtenirNombreImagesAllocation();
        
        // Creer le nouvel index pour placer la nouvelle
        // image
        //
        int nouvelleIndex = nombreImagesPresentent;      
        if (nouvelleIndex >= nombreImagesPossibles) return;
        
        // Obtenir la famille de la cellule cible
        //
        String familleCellule = (String) hamecon.obtenirFamilles().get(new Integer(colonneCellule));
        
        // Obtenir la famille de l'image selectionnee
        //
        File file = new File(hamecon.obtenirCheminImageSelectionnee());
        String familleImageSelectionnee = file.getParentFile().getName();
        
        // Verifier que la famille de l'image selectionne
        // appartienne a la bonne famille
        //
        if(!familleImageSelectionnee.equals(familleCellule)) {
        	Son.play("../_Sons/boing.wav");
        	return;
        }
  
        // Ajouter l'image selectionnee dans la cellule
        // cible
        //
        hamecon.obtenirGrilleNord().obtenirCellule(ligneCellule, colonneCellule).ajouterImage(hamecon.obtenirCheminImageSelectionnee(), nouvelleIndex, 20, 10, 19);
        hamecon.obtenirGrilleNord().repaint();
        
        // Afficher une nouvelle carte a jouer
        hamecon.afficherCarteJouer();

        //hamecon.modifierImageSelectionnee("");
    }
    
    public void mouseEntered(MouseEvent arg0) {
    	hamecon.obtenirGrilleNord().setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    
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
    
    public void mouseDragged(MouseEvent arg0) {
    // TODO Auto-generated method stub
    
    }
    
    public void mouseMoved(MouseEvent arg0) {
    // TODO Auto-generated method stub
    
    }
    
}
