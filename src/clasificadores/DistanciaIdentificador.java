/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clasificadores;

/**
 *
 * @author depot
 */
public class DistanciaIdentificador{
    private String claseObtenida;
    private double distanciaMinima;

    public DistanciaIdentificador() {
        this.claseObtenida = "";
        this.distanciaMinima = 0.0;
    }
    
    public DistanciaIdentificador(double distancia, String clase){
        this.distanciaMinima = distancia;
        this.claseObtenida = clase;
    }
    
    public String getClaseObtenida() {
        return claseObtenida;
    }

    public void setClaseObtenida(String claseObtenida) {
        this.claseObtenida = claseObtenida;
    }

    public double getDistanciaMinima() {
        return distanciaMinima;
    }

    public void setDistanciaMinima(double distanciaMinima) {
        this.distanciaMinima = distanciaMinima;
    }
    
    
    
    
}
