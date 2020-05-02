import java.awt.Color;
import java.awt.Font;

abstract public class XML {
    
    public static String obtenirColorFont(Color couleur) {
        // Definir la couleur de fond
        //   
        // Color fond = this.getBackground();
        String CouleurResultat = "BLACK";
        
        if (couleur.getRed() == 0 && couleur.getGreen() == 0 && couleur.getBlue() == 0)
            CouleurResultat = "BLACK";
        
        else
            if (couleur.getRed() == 0 && couleur.getGreen() == 0 && couleur.getBlue() == 255)
                CouleurResultat = "BLUE";
            
            else
                if (couleur.getRed() == 0 && couleur.getGreen() == 255 && couleur.getBlue() == 0)
                    CouleurResultat = "GREEN";
                
                else
                    if (couleur.getRed() == 255 && couleur.getGreen() == 200 && couleur.getBlue() == 0)
                        CouleurResultat = "ORANGE";
                    
                    else
                        if (couleur.getRed() == 255 && couleur.getGreen() == 175 && couleur.getBlue() == 175)
                            CouleurResultat = "PINK";
                        
                        else
                            if (couleur.getRed() == 255 && couleur.getGreen() == 0 && couleur.getBlue() == 0)
                                CouleurResultat = "RED";
                            
                            else
                                if (couleur.getRed() == 64 && couleur.getGreen() == 64 && couleur.getBlue() == 64)
                                    CouleurResultat = "DARK_GRAY";
                                
                                else
                                    if (couleur.getRed() == 0 && couleur.getGreen() == 255 && couleur.getBlue() == 255)
                                        CouleurResultat = "CYAN";
                                    
                                    else
                                        if (couleur.getRed() == 128 && couleur.getGreen() == 128 && couleur.getBlue() == 128)
                                            CouleurResultat = "GRAY";
                                        
                                        else
                                            if (couleur.getRed() == 192 && couleur.getGreen() == 192 && couleur.getBlue() == 192)
                                                CouleurResultat = "LIGHT_GRAY";
                                            
                                            else
                                                if (couleur.getRed() == 255 && couleur.getGreen() == 0 && couleur.getBlue() == 255)
                                                    CouleurResultat = "MAGENTA";
                                                
                                                else
                                                    if (couleur.getRed() == 255 && couleur.getGreen() == 255 && couleur.getBlue() == 255)
                                                        CouleurResultat = "WHITE";
                                                    
                                                    else
                                                        if (couleur.getRed() == 255 && couleur.getGreen() == 255 && couleur.getBlue() == 0) CouleurResultat = "YELLOW";
        return CouleurResultat;
    }
    
    public static Color obtenirColorFont(String couleur) {
        // Definir la couleur de fond
        //   
        // Color fond = this.getBackground();
        Color CouleurResultat = Color.BLACK;
        
        if (couleur.equals("BLACK"))
            CouleurResultat = Color.BLACK;
        
        else
            if (couleur.equals("BLUE"))
                CouleurResultat = Color.BLUE;
            
            else
                if (couleur.equals("GREEN"))
                    CouleurResultat = Color.GREEN;
                
                else
                    if (couleur.equals("ORANGE"))
                        CouleurResultat = Color.ORANGE;
                    
                    else
                        if (couleur.equals("PINK"))
                            CouleurResultat = Color.PINK;
                        
                        else
                            if (couleur.equals("RED"))
                                CouleurResultat = Color.RED;
                            
                            else
                                if (couleur.equals("DARK_GRAY"))
                                    CouleurResultat = Color.DARK_GRAY;
                                
                                else
                                    if (couleur.equals("CYAN"))
                                        CouleurResultat = Color.CYAN;
                                    
                                    else
                                        if (couleur.equals("GRAY"))
                                            CouleurResultat = Color.GRAY;
                                        
                                        else
                                            if (couleur.equals("LIGHT_GRAY"))
                                                CouleurResultat = Color.LIGHT_GRAY;
                                            
                                            else
                                                if (couleur.equals("MAGENTA"))
                                                    CouleurResultat = Color.MAGENTA;
                                                
                                                else
                                                    if (couleur.equals("WHITE"))
                                                        CouleurResultat = Color.WHITE;
                                                    
                                                    else
                                                        if (couleur.equals("YELLOW")) CouleurResultat = Color.YELLOW;
        return CouleurResultat;
    }
    
    public static String obtenirStyleFont(Font font) {
        String resultat = "Classique";
        System.out.println(font.getStyle());
        if (font.getStyle() == 0)
            resultat = "Classique";
        
        else
            if (font.getStyle() == 1)
                resultat = "Gras";
            
            else
                if (font.getStyle() == 2) resultat = "Italique";
        
        return resultat;
    }
    
    public static int obtenirStyleFont(String font) {
        int resultat = 0;
        
        if (font.equals("Classique"))
            resultat = 0;
        
        else
            if (font.equals("Gras"))
                resultat = 1;
            
            else
                if (font.equals("Italique")) resultat = 2;
        
        return resultat;
    }
}
