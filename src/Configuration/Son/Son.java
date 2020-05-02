//
// IUT de Nice / Departement informatique / Module APO-Java
// Annee 2009_2010 - Classes graphiques generiques sous SWING
//
// Classe PlatineSonG - Diffusion sonore d'un fichier source designe par son
//                      chemin absolu 
//                              
// Edition "Draft" :  solution technique basee sur l'interface AudioClip
//                              
//    + Version 0.0.0	: TP_4 / Ex_11                             
//
// Auteur : A. Thuaire
//

import java.applet.*;
import java.net.*;

abstract public class Son implements AudioClip {
 
// ------                                                     *** Methode play
  
   public static void play(String cheminFichierSon) {
   	
   	  try {
      	 URL source= new java.net.URL("file:" + cheminFichierSon);
         java.applet.Applet.newAudioClip(source).play();
      }
      catch (Exception e){
         System.out.println("Chemin fichier son incorrect");
      }
   }
   
   public void stop() {
	   
   }
}
