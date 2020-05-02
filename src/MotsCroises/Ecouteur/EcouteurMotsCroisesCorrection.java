import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JOptionPane;



public class EcouteurMotsCroisesCorrection implements MouseListener {

    private MotsCroises hamecon;
    
    public EcouteurMotsCroisesCorrection(MotsCroises hamecon)
    {
        this.hamecon = hamecon;
    }
    
    public void mouseClicked(MouseEvent e) {
        if(hamecon.obtenirSolution().equals(hamecon.obtenirJeuxGrille().obtenirGrille().obtenirContenu()))
                JOptionPane.showMessageDialog(null, Texte.load("../_Textes/Jeux/MotsCroises/Bravo"));
        else
        {
            JOptionPane.showMessageDialog(null, Texte.load("../_Textes/Jeux/MotsCroises/Dommage"));
            
            hamecon.correction();
        }
    }

    public void mouseEntered(MouseEvent e) {
        // TODO Auto-generated method stub
        
    }

    public void mouseExited(MouseEvent e) {
        // TODO Auto-generated method stub
        
    }

    public void mousePressed(MouseEvent e) {
        // TODO Auto-generated method stub
        
    }

    public void mouseReleased(MouseEvent e) {
        // TODO Auto-generated method stub
        
    }

}
