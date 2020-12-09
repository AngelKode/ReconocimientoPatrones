/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clasificador_imagenes;

/**
 *
 * @author depot
 */
public class PatronImagen {
    
    int x;
    int y;
    String clase;
    String claseResultante;
    double[] vectorResultante;

    public PatronImagen(int x, int y, String clase, String claseResultante, double[] vectorResultante) {
        this.x = x;
        this.y = y;
        this.clase = clase;
        this.claseResultante = claseResultante;
        this.vectorResultante = vectorResultante;
    }

    PatronImagen(int x, int y, double[] d) {
        this.x = x;
        this.y = y;
        this.vectorResultante = d;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public String getClase() {
        return clase;
    }

    public void setClase(String clase) {
        this.clase = clase;
    }

    public String getClaseResultante() {
        return claseResultante;
    }

    public void setClaseResultante(String claseResultante) {
        //Hacer la adaptacion para el tono de la imagen
        this.claseResultante = claseResultante;
    }

    public double[] getVectorResultante() {
        return vectorResultante;
    }

    public void setVectorResultante(double[] vectorResultante) {
        this.vectorResultante = vectorResultante;
    }
    
    
    
}
