/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clasificador_imagenes;

import java.awt.Color;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 *
 * @author depot
 */
public class ClusterizacionImagenes {
    
    private Image imagenOriginal;

    public ClusterizacionImagenes() {
        this.imagenOriginal = null;
    }
    
    
    public void abrir(){
        this.imagenOriginal = AbrirImagen.openImage();
        JFrameImagen imagen = new JFrameImagen(imagenOriginal);
    }
    
    
    public void clusterizar(){
    
        BufferedImage buff = AbrirImagen.toBufferedImage(imagenOriginal);
        ArrayList<PatronImagen> instancias = new ArrayList<>();
        
        //recorremos la imagen
        Color color;
        for(int x=0;x<buff.getWidth();x++){
            for(int y=0;y<buff.getHeight();y++){
                int rgb = buff.getRGB(x, y);
                color = new Color(rgb);
                
                instancias.add(new PatronImagen(x, y, new double[]{color.getRed(),color.getGreen(),color.getBlue()}));
            }
        }
    }
    
}
