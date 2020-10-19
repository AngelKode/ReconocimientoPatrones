/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reconocimiento_patrones;

import clasificadores.MinimaDistancia;
import data.LeerDatos;
import data.Patron;
import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author depot
 */
public class Reconocimiento_Patrones {

    /**
     * @param args the command line arguments
     * @throws java.io.IOException
     */
    public static void main(String[] args) throws IOException {
        // TODO code application logic here
        
        ArrayList<Patron> patrones = LeerDatos.tokenizarDataSet();
        
        //Entrenamos
        MinimaDistancia clasificador = new MinimaDistancia();
        clasificador.entrenar(patrones);
        
        //Patrones a clasificar
        ArrayList<Patron> patronesIngresar = new ArrayList<Patron>();
        patronesIngresar.add(new Patron("", "", new double[]{5.0,3.4,1.5,0.2}));
        patronesIngresar.add(new Patron("", "", new double[]{5.6,1.4,4.5,8.2}));
        patronesIngresar.add(new Patron("", "", new double[]{2.0,6.4,6.5,2.2}));
        patronesIngresar.add(new Patron("", "", new double[]{6.7,3.1,4.4,1.4}));
        
        
        //Obtenemos los clasificados
        ArrayList<Patron> clasificado = clasificador.clasificar(patronesIngresar);
        
        Patron setosa = new Patron("", "",clasificador.getRepresentativos().get(0).getVectorC());
        Patron versi = new Patron("", "",clasificador.getRepresentativos().get(1).getVectorC());
        Patron virgi = new Patron("", "",clasificador.getRepresentativos().get(2).getVectorC());
        
        for(int i=0;i<clasificado.size();i++){
            System.out.println("Clase asignada");
            System.out.println(clasificado.get(i).getClaseResultante());
            System.out.println("Distancias setosa,versicolor,virginica respectivamente al patron dado");
            System.out.println(setosa.calcularDistancia(patronesIngresar.get(i)));
            System.out.println(versi.calcularDistancia(patronesIngresar.get(i)));
            System.out.println(virgi.calcularDistancia(patronesIngresar.get(i)));
        }

        /*
        //Ahora calculamos la distancia con el punto {2.4,3.3,5.6,7.8}
        Patron p1 = new Patron("","", new double[]{2.4,3.3,5.6,7.8});
        ArrayList<Double> distancias = new ArrayList<>();
        
        for(int i = 0;i < patrones.size(); i++){
            distancias.add(p1.calcularDistancia(patrones.get(i)));
        }
        
        for(int i = 0;i < distancias.size(); i++){
            System.out.print("Distancia "+(i+1)+": ");
            System.out.println(distancias.get(i).doubleValue());
        }     
         /*
        Patron a = new Patron("","",new double[]{5.006,3.418,1.464,0.244});
        Patron b = new Patron("","",new double[]{1.2,0.8,5.1,1.1});
        System.out.println(a.calcularDistancia(b));
                 */
    }
    
}
