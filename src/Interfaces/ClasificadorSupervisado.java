/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interfaces;

import data.Patron;
import java.util.ArrayList;

/**
 *
 * @author depot
 */
public interface ClasificadorSupervisado {
     public abstract void entrenar(ArrayList<Patron> instancias);
     public abstract void entrenar(String path);
     public abstract ArrayList<Patron> clasificar(ArrayList<Patron> instancias);
     public abstract ArrayList<Patron> clasificar(ArrayList<Patron> instancias,int k);
     public abstract double[] efectividadClasificador(ArrayList<Patron> asignados);
}
