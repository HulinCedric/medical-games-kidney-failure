/**
 * IUT de Nice / Departement informatique / Module APO-Java
 * Annee 2009_2010 - Ecouteur
 * 
 * @version 1.0.0
 * 
 *          version initial, enregistrement d'un jeu de
 *          grille : grille et panneau
 * 
 * @version 1.1.0
 * 
 *          verification des enregistrements affichage de
 *          messages en consequence
 * 
 * @version 1.2.0
 * 
 *          ajout d'un accesseur de consultation protege sur
 *          l'hamecon ; ajout d'un attribut nomGrille ;
 *          ajout d'un accesseur de consultation protege sur
 *          le nom de la grille
 */
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JOptionPane;

/**
 * Ecouteur souris permettant d'enregistrer une grilleG
 * 
 * @author Cedric Hulin, Charles Fouco
 * @version 1.2.0
 */
public class EcouteurJeuxGrilleEnregistrer implements MouseListener {
    
    /**
     * Hamecon sur un jeux de grille
     * 
     * @since 1.0.0
     */
    private JeuxGrille hamecon;
    
    /**
     * Nom de la grille a enregistrer
     * 
     * @since 1.2.0
     */
    private String nomGrille;
    
    /**
     * Constructeur normal
     * 
     * @param hamecon
     * @since 1.0.0
     */
    public EcouteurJeuxGrilleEnregistrer(Object hamecon) {
        this.hamecon = (JeuxGrille) hamecon;
    }
    
    /**
     * Implementation de l'evenement mouseClicked
     * 
     * @since 1.1.0
     */
    public void mouseClicked(MouseEvent e) {
        
        nomGrille = JOptionPane.showInputDialog(hamecon, "Veuillez saisir un nom de grille", "Enregistrer la grille", JOptionPane.QUESTION_MESSAGE);
        
        
        // Cas d'une fermeture de la fenetre
        // ou du bouton CANCEL
        //
        if (nomGrille == null) { return; }
        
        // Si l'utilisateur a entre du texte
        // dans le champs de saisie
        //
        if (nomGrille != null) {
            
            // Si l'utilisateur a supprime le
            // texte qu'il avait entre
            //
            if (nomGrille.length() == 0) {
                JOptionPane.showMessageDialog(hamecon,  Texte.load("../_Textes/Erreurs/Enregistrement grille nom manquant"));
                return;
            }
            
            // Si aucune grille n'est presente dans le jeu
            //
            if (hamecon.obtenirGrille() == null) {
                JOptionPane.showMessageDialog(hamecon, Texte.load("../_Textes/Erreurs/Enregistrement grille presence"));
                return;
            }
            
            // Definir le chemin de l'enregistrement
            //
            if (hamecon.obtenirModeAdmin()) {
                
                // Demander le type de chargement voulu :
                // - Nouvelle grille
                // - Partie sauvegardee
                //
                Object[] options = { "Facile", "Moyen", "Difficile" };
                String retour = null;
                retour = (String) JOptionPane.showInputDialog(hamecon, "Choisissez un niveau de difficult� : ", "Niveau de difficult�", JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
                
                // Definir le repertoire a charger
                // en fonction du l'option selectionnee
                //
                String repertoireNiveau = null;
                if (retour != null) {
                    if (retour.equals(options[0]))
                        repertoireNiveau = "Facile/";
                    else if (retour.equals(options[1]))
                        repertoireNiveau = "Moyen/";
                    else if (retour.equals(options[2]))
                        repertoireNiveau = "Difficile/";
                }              
                else return;

                // Creer le chemin de l'enregistrement
                //
                nomGrille = "Grilles/" + repertoireNiveau + nomGrille;
            }
            else nomGrille = "Sauvegardes/" + nomGrille;
            
            // Si une grille est presente dans le jeu
            //
            if (hamecon.obtenirGrille() != null) {
                
                // Enregistrement de la configuration de la grille
                //
                if (ConfigXML.store(hamecon.obtenirGrille().obtenirProprietesXML(), nomGrille, "1.0.0"))
                    JOptionPane.showMessageDialog(hamecon, Texte.load("../_Textes/Confirmations/Enregistrement grille configuration"));
                
                // Si l'enregistrement ne s'est pas bien
                // passe
                //
                else JOptionPane.showMessageDialog(hamecon, Texte.load("../_Textes/Erreurs/Enregistrement grille configuration"));
            }
            
            // Si un panneau d'informations est present
            // dans le jeu
            //
            if (hamecon.obtenirPanneauInformations() != null) {
                
                // Enregistrement du panneau information
                // passe
                //
                if (Data.store(hamecon.obtenirPanneauInformations().obtenirZoneAffichage().getText(), nomGrille + "$PanneauInfo", "1.0.0"))
                    JOptionPane.showMessageDialog(hamecon, Texte.load("../_Textes/Confirmations/Enregistrement panneau informations"));
                
                // SI l'enregistrement ne s'est pas bien
                // passe
                //
                else JOptionPane.showMessageDialog(hamecon, Texte.load("../_Textes/Erreurs/Enregistrement panneau informations"));
            }
        }
    }
    
    /**
     * Obtention de l'hamecon
     * 
     * @return hamecon
     * @since 1.2.0
     */
    protected JeuxGrille obtenirHamecon() {
        return hamecon;
    }
    
    /**
     * Obtention de l'hamecon
     * 
     * @return nom de la grille
     * @since 1.2.0
     */
    protected String obtenirCheminEnregistrement() {
        return nomGrille;
    }
    
    public void mouseEntered(MouseEvent arg0) {}
    
    public void mouseExited(MouseEvent arg0) {}
    
    public void mousePressed(MouseEvent arg0) {}
    
    public void mouseReleased(MouseEvent arg0) {}
}
