/**
 * IUT de Nice / Departement informatique / Module APO-Java
 * Annee 2009_2010 - Ecouteur
 * 
 * @version 1.0.0
 * 
 *          version initial, chargement d'un jeu de grille :
 *          grille et panneau
 * 
 * @version 1.1.0
 * 
 *          verification des chargements affichage de
 *          messages en consequence
 * 
 * @version 1.2.0
 * 
 *          ajout d'accesseurs de consultation proteges sur
 *          le nom et version du fichier
 */
import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Iterator;
import java.util.Vector;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileFilter;

import org.jdom.Element;

/**
 * Ecouteur souris permettant de charger une grilleG selon
 * une configuration
 * 
 * @author Cedric Hulin, Charles Fouco
 * @version 1.2.0
 */
public class EcouteurJeuxGrilleCharger implements MouseListener {
    
    /**
     * Hamecon sur un jeux de grille
     * 
     * @since 1.0.0
     */
    private JeuxGrille hamecon;
    
    /**
     * Nom du fichier charger
     * 
     * @since 1.2.0
     */
    private String nomFichier;
    
    /**
     * Version du fichier charger
     * 
     * @since 1.2.0
     */
    private String versionFichier;
    
    /**
     * Flag de reussite pour le
     * chargement d'une nouvelle grille
     * 
     * @since 1.2.0
     */
    private boolean flag;
    
    
    
    /**
     * Constructeur normal
     * 
     * @param hamecon
     * @since 1.0.0
     */
    public EcouteurJeuxGrilleCharger(Object hamecon) {
        this.hamecon = (JeuxGrille) hamecon;
        nomFichier = null;
        versionFichier = null;
        flag = false;
    }
    
    /**
     * Implementation de l'evenement mouseClicked
     * 
     * @since 1.1.0
     */
    public void mouseClicked(MouseEvent e) {
        
        // Demander le type de chargement voulu :
        // - Nouvelle grille
        // - Partie sauvegardee
        //
        Object[] options = { "Nouvelle grille", "Partie sauvegardee" };
        String retour = null;
        retour = (String) JOptionPane.showInputDialog(hamecon, "Que voulez-vous charger ?", "Choisir une grille", JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
        
        // Definir le repertoire a charger
        // en fonction du l'option selectionnee
        //
        String repertoireCharger = null;
        if (retour != null) {
            if (retour.equals("Nouvelle grille"))
                repertoireCharger = "Grilles/";
            else repertoireCharger = "Sauvegardes/";
        }
        else return;
        
        // Ouvrir un explorateur et lui appliquer un filtre
        //
        JFileChooser choix = new JFileChooser(repertoireCharger);
        choix.setPreferredSize(new Dimension(500, 500));
        
        // Ajouter un filtre sur les configuration
        //
        choix.addChoosableFileFilter(new FiltreSimple("Fichiers de Configuration", ".xml"));
        
        // Ouvrir le fichier apres la selection par
        // l'utilisateur
        //
        if (choix.showOpenDialog(hamecon) == JFileChooser.APPROVE_OPTION) {
            
            // Stocker les informations concernant le
            // fichier choisi
            //
            String chemin = (choix.getSelectedFile().getAbsolutePath());
            nomFichier = chemin.substring(0, chemin.length() - 10);
            versionFichier = chemin.substring(chemin.length() - 9, chemin.length() - 4);
            
            // Charger les donnees
            //
            if(charger(hamecon, nomFichier, versionFichier))
                flag = true;
        }
    }
    
    /**
     * Charger selon des parametres
     * 
     * @param hamecon
     * @param nomFichier
     * @param versionFichier
     * @return flag de reussite
     * @sin 1.3.0
     */
    protected boolean charger(JeuxGrille hamecon, String nomFichier, String versionFichier) {
        
        this.nomFichier = nomFichier;
        this.versionFichier = versionFichier;
        
        // Recuperer la description du panneau
        // d'informations
        //
        String texte = (String) Data.load(nomFichier + "$PanneauInfo", versionFichier);
        
        // Verification du load du panneau
        // d'informations
        //
        if (texte != null) {
            // Affichage du texte recupere
            // dans le panneau d'informations
            //
            if (hamecon.obtenirPanneauInformations() != null) hamecon.obtenirPanneauInformations().modifierZoneAffichage(texte);
        }
        
        // Recuperation de la configuration de la grille
        //
        Element configGrille = (Element) ConfigXML.load(nomFichier, versionFichier);
        
        // Verification du load de la
        // configuration de la grille
        //
        if (configGrille == null) {
            JOptionPane.showMessageDialog(hamecon,  Texte.load("../_Textes/Erreurs/Chargement grille configuration"));
            return false;
        }
        
        // Recuperation des ecouteur deja present
        //
        Vector event = new Vector();
        MouseListener[] mL = hamecon.obtenirGrille().getMouseListeners();
        for (int i = 0; i < mL.length; i++)
            event.add(mL[i]);
        MouseMotionListener[] mML = hamecon.obtenirGrille().getMouseMotionListeners();
        for (int i = 0; i < mML.length; i++) {
            if (!event.contains(mML[i])) event.add(mML[i]);
        }
        
        // Suppression de l'ancienne grille
        //
        if (hamecon.obtenirGrille() != null) hamecon.getContentPane().remove(hamecon.obtenirGrille());
        
        // Accrocher la nouvelle grille a l'hamecon
        //
        GrilleG g = new GrilleG(hamecon, configGrille);
        hamecon.modifierGrille(g);
        hamecon.getContentPane().add(hamecon.obtenirGrille());
        
        // Ajouter les ecouteurs
        //
        Iterator i = event.iterator();
        while (i.hasNext()) {
            // Recuperer la class de l'ecouteur
            //
            Class cl = i.next().getClass();
            
            // Creer les parametres du constructeur
            //
            Class[] types = new Class[] { Object.class };
            
            // Recuperer le constructeur avec les deux
            // parametres
            //
            Constructor construc = null;
            try {
                construc = cl.getConstructor(types);
            }
            catch (SecurityException e2) {
                e2.printStackTrace();
            }
            catch (NoSuchMethodException e2) {
                e2.printStackTrace();
            }
            
            // Creer une nouvelle instance de l'ecouteur
            //
            Object o2 = null;
            try {
                o2 = construc.newInstance(new Object[] { hamecon });
            }
            catch (IllegalArgumentException e2) {
                e2.printStackTrace();
            }
            catch (InstantiationException e2) {
                e2.printStackTrace();
            }
            catch (IllegalAccessException e2) {
                e2.printStackTrace();
            }
            catch (InvocationTargetException e2) {
                e2.printStackTrace();
            }
            
            // Ajouter l'ecouteur a la grille
            //
            hamecon.obtenirGrille().ajouterEcouteur(o2);
        }
        
        // Effet Magique ++
        //
        hamecon.paintAll(hamecon.obtenirGrille().getGraphics());
        //hamecon.setVisible(false);
        //hamecon.setVisible(true);
        return true;
    }
    
    public void mouseEntered(MouseEvent arg0) {}
    
    public void mouseExited(MouseEvent arg0) {}
    
    public void mousePressed(MouseEvent arg0) {}
    
    public void mouseReleased(MouseEvent arg0) {}
    
    /**
     * Filtre permettant une utilisation plus simple d'un
     * JFileChooser
     * 
     * @author Cedric Hulin, Charles Fouco
     * @version 1.0.0
     */
    public class FiltreSimple extends FileFilter {
        /**
         * Description acceptee par le filtre
         * 
         * @since 1.0.0
         */
        private String description;
        
        /**
         * extension acceptee par le filtre
         * 
         * @since 1.0.0
         */
        private String extension;
        
        /**
         * Constructeur � partir de la description et de
         * l'extension acceptee
         * 
         * @param description
         * @param extension
         */
        public FiltreSimple(String description, String extension) {
            if (description == null || extension == null) throw new NullPointerException("La description (ou extension) ne peut ?tre null.");
            this.description = description;
            this.extension = extension;
        }
        
        /**
         * Implementation de accept
         * 
         * @since 1.0.0
         */
        public boolean accept(File file) {
            if (file.isDirectory()) { return true; }
            String nomFichier = file.getName().toLowerCase();
            
            return nomFichier.endsWith(extension);
        }
        
        /**
         * Implementation de getDescription
         * 
         * @since 1.0.0
         */
        public String getDescription() {
            return description;
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
     * Obtention du nom du fichier
     * 
     * @return nom du fichier
     * @since 1.2.0
     */
    protected String obtenirNomFichier() {
        return nomFichier;
    }
    
    /**
     * Modification du nom du fichier
     * 
     * @return nom du fichier
     * @since 1.2.0
     */
    protected void modifierNomFichier() {
        nomFichier = null;
    }
    
    /**
     * Obtention de la version du fichier
     * 
     * @return version du fichier
     * @since 1.2.0
     */
    protected String obtenirVersionFichier() {
        return versionFichier;
    }
    
    /**
     * Obtention du flag de reussite
     * pour le chargement d'une grille
     * 
     * @return flag
     * @since 1.3.0
     */
    protected boolean obtenirFlagReussite() {
        return flag;
    }
    
    protected void modifierFlagReussite(boolean valeur) {
        flag = valeur;
    }
}
