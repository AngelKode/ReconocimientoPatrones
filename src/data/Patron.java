/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package data;

/**
 *
 * @author depot
 */
public class Patron {
       
    private String clase;
    private String claseResultante;
    private double[] vectorC;

    public Patron(int n) {
        this.clase = "";
        this.claseResultante = "";
        this.vectorC = new double[n];
    }
    
    public Patron(String clase, String claseRes, double[] vectorC){
        this.clase = clase;
        this.claseResultante = claseRes;
        this.vectorC = vectorC;
    }
    
    public double calcularDistancia(Patron aux){
        double sumatoria = 0;
        
        for(int x = 0; x < aux.getVectorC().length;x++){
           sumatoria += Math.pow(this.vectorC[x] - aux.getVectorC()[x], 2);
        }
       
       sumatoria = Math.sqrt(sumatoria);
       return sumatoria;
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
        this.claseResultante = claseResultante;
    }

    public double[] getVectorC() {
        return vectorC;
    }

    public void setVectorC(double[] vectorC) {
        this.vectorC = vectorC;
    }
    
    
}
