import java.io.File;
import java.io.FileOutputStream;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;

abstract public class ConfigXML {
    
    public static Object load(String name, String version) {
        
        String nomFichier;
        Document document = null;
        Element resultat;
        
        // Construire le nom du fichier source de la
        // configuration
        //
        nomFichier = name + "-" + version + ".xml";
        
        // On crée une instance de SAXBuilder
        //
        SAXBuilder sxb = new SAXBuilder();
        
        // Parsing : création d'un nouveau document JDOM
        //
        try {
            document = sxb.build(new File(nomFichier));
        }
        catch (Exception e) {
            System.out.println("Chargement du fichier " + nomFichier + " : NOK");
            return null;
        }
        
        // On initialise un nouvel élément racine avec
        // l'élément racine du document.
        //
        try {
            resultat = document.getRootElement();
        }
        catch (Exception e) {
            System.out.println(e.getStackTrace());
            return null;
        }
        
        System.out.println("Chargement du fichier " + nomFichier + " : OK");
        return resultat;
    }
    
    // On enregsitre notre nouvelle arborescence dans le
    // fichier
    // d'origine dans un format classique.
    public static boolean store(Object config, String name, String version) {
        
        String origine;
        String nomFichier;
        XMLOutputter sortie = null;
        
        // Controler l'existence de la configuration
        //
        if (config == null) return false;
        
        // Controler la classe d'origine de la configuration
        //
        origine = config.getClass().getName();
        if (origine != "org.jdom.Document") return false;
        
        // Construire le nom du fichier de configuration
        //
        nomFichier = name + "-" + version + ".xml";
        
        // Construire un flux de sortie avec un affichage
        // simple
        //
        try {
            sortie = new XMLOutputter(Format.getPrettyFormat());
        }
        catch (Exception e) {
            return false;
        }
        
        // Serialiser la configuration dans le flux de
        // sortie
        //  
        try {
            sortie.output((org.jdom.Document) config, new FileOutputStream(nomFichier));
        }
        catch (Exception e) {
            return false;
        }
        
        System.out.println("Enregistrement du fichier " + nomFichier + " : OK");
        return true;
    }
    
}
