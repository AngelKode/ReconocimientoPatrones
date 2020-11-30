/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reconocimiento_patrones;

import clasificadores.KNN;
import clasificadores.MinimaDistancia;
import clasificadores.NaiveBayes;
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
        
        /*
        ArrayList<Patron> patrones = LeerDatos.tokenizarDataSet();
        
        
        //Entrenamos
        MinimaDistancia clasificador = new MinimaDistancia();
        clasificador.entrenar(patrones);
        
        //Patrones a clasificar
        ArrayList<Patron> patronesIngresar = new ArrayList<>();
        //Leemos los datos
        patronesIngresar = LeerDatos.obtenerDatos();

        //Obtenemos los clasificados
        ArrayList<Patron> clasificado = clasificador.clasificar(patronesIngresar);
        */
        
        //KNN
        /*
        KNN clasificador = new KNN();
        
        clasificador.entrenar("DataSets/wine.txt");
        
        ArrayList<Patron> patronesEntrenamiento = new ArrayList<>();
        //Leemos los datos
        patronesEntrenamiento = LeerDatos.obtenerDatos();

        ArrayList<Patron> patronesAClasificar = LeerDatos.obtenerDatos();
        //Obtenemos los clasificados
        ArrayList<Patron> clasificado = clasificador.clasificar(patronesAClasificar,3);
        
        
        double datosObtenidos[] = new double[2];
        datosObtenidos = clasificador.efectividadClasificador(clasificado);
        System.out.println("Efectividad: " +datosObtenidos[0]+"%");
        System.out.println("Elementos asignados correctamente:"+datosObtenidos[1]);
        */
        
        //NaiveBayes
        
        NaiveBayes bayes = new NaiveBayes();
        
        ArrayList<Patron> entrenadores = LeerDatos.tokenizarDataSet(false);
        //Le mandamos las instancias con las que vamos a entrenar
        bayes.entrenar(entrenadores);
        
        ArrayList<Patron> a_clasificar = LeerDatos.tokenizarDataSet(false);
        //Clasificamos los objetos que pasamos como parámetro
        a_clasificar = bayes.clasificar(a_clasificar);
        
        System.out.println("Clasificados correctamente: " + bayes.efectividadClasificador(a_clasificar)[1]);
        System.out.println("Efectividad: "+bayes.efectividadClasificador(a_clasificar)[0]+"%");
    }
    
}
