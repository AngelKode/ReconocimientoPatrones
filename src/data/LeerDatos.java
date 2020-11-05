/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package data;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.StringTokenizer;

/**
 *
 * @author depot
 */
public class LeerDatos{

        public LeerDatos() {
        }
        
        
        public static ArrayList<Patron> tokenizarDataSet() throws FileNotFoundException, IOException{
            //Declaramos un objeto de tipo StringTokenizer
            ArrayList<Patron> patrones = new ArrayList<>();
            
            //Obtenemos el archivo
            FileReader fileDataSetIris = new FileReader("DataSets/diferentesEntrenadores.txt");
            BufferedReader buffer = new BufferedReader(fileDataSetIris);
            
            //Ahora leemos el archivo
            String textoDataSetIris = "", textoComparar = "";
            while((textoComparar = buffer.readLine()) != null){
                textoDataSetIris += textoComparar+",";
            }
            //Separamos los datos que esten entre comas
            StringTokenizer tokenizador = new StringTokenizer(textoDataSetIris,",");
            
            //Ahora guardamos los datos en el ArrayList de Patrones
            int contadorDatos = 0;
            int tamañoVector = 3;
            double[] vectorCaracteristico = new double[tamañoVector];
            
            while(tokenizador.hasMoreTokens()){
                  if(contadorDatos != tamañoVector){
                    //Obtenemos primero los valores del vector caracteristico
                    String valor = tokenizador.nextToken();
                    vectorCaracteristico[contadorDatos] = Double.parseDouble(valor);
                    contadorDatos++;
                  }else{
                    patrones.add(new Patron(tokenizador.nextToken(), "", vectorCaracteristico));
                    contadorDatos = 0; 
                    vectorCaracteristico = new double[tamañoVector];
                  }
            }
            
            return patrones;
        }
        
            public static ArrayList<Patron> obtenerDatos() throws FileNotFoundException, IOException{
            //Declaramos un objeto de tipo StringTokenizer
            ArrayList<Patron> patrones = new ArrayList<>();
            
            //Obtenemos el archivo
            FileReader fileDataSetIris = new FileReader("DataSets/datosIngresados.txt");
            BufferedReader buffer = new BufferedReader(fileDataSetIris);
            
            //Ahora leemos el archivo
            String textoDataSetIris = "", textoComparar = "";
            while((textoComparar = buffer.readLine()) != null){
                textoDataSetIris += textoComparar+",";
            }
            //Separamos los datos que esten entre comas
            StringTokenizer tokenizador = new StringTokenizer(textoDataSetIris,",");
            
            //Ahora guardamos los datos en el ArrayList de Patrones
            int contadorDatos = 0;
            int tamañoVector = 3;
            double[] vectorCaracteristico = new double[tamañoVector];
            
            while(tokenizador.hasMoreTokens()){
                  if(contadorDatos != tamañoVector){
                    //Obtenemos primero los valores del vector caracteristico
                    String valor = tokenizador.nextToken();
                    vectorCaracteristico[contadorDatos] = Double.parseDouble(valor);
                    contadorDatos++;
                  }else{
                    patrones.add(new Patron(tokenizador.nextToken(), "", vectorCaracteristico));
                    contadorDatos = 0; 
                    vectorCaracteristico = new double[tamañoVector];
                  }
            }
            
            return patrones;
        }
}
