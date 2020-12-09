/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clasificador_imagenes;

import java.awt.Color;
import java.awt.Image;
import java.awt.image.BufferedImage;

/**
 *
 * @author depot
 */
public class ClusterImagenesMain {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        Image imagenOriginal = AbrirImagen.openImage();
        JFrameImagen frame = new JFrameImagen(imagenOriginal);
        BufferedImage buff = AbrirImagen.toBufferedImage(imagenOriginal);
    }
    
}
