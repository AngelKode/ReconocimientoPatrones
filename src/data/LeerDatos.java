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
        
        
        public static ArrayList<Patron> tokenizarDataSet(boolean posicion_inicio_clase) throws FileNotFoundException, IOException{
            //Declaramos un objeto de tipo StringTokenizer
            ArrayList<Patron> patrones = new ArrayList<>();
           
            //Obtenemos el archivo
            FileReader fileDataSetIris = new FileReader("DataSets/wine.txt");
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
                int tamañoVector = 13;
                double[] vectorCaracteristico = new double[tamañoVector];
                String clase = "";
                
                //Si es true, la clase está al inicio de la linea
                //si no, está al final
                if(posicion_inicio_clase){
                    while(tokenizador.hasMoreTokens()){
                      if(contadorDatos == 0){
                        clase = tokenizador.nextToken();
                        contadorDatos++;
                      }else{
                        String valor = tokenizador.nextToken();
                        vectorCaracteristico[contadorDatos - 1] = Double.parseDouble(valor);
                        contadorDatos++;
                      }
                     
                      if(contadorDatos == tamañoVector + 1){
                          contadorDatos = 0;
                          patrones.add(new Patron(clase, "", vectorCaracteristico));
                          vectorCaracteristico = new double[tamañoVector];
                      }
                      
                    }
                }else{
                    while(tokenizador.hasMoreTokens()){
                       if(contadorDatos == tamañoVector){
                          clase = tokenizador.nextToken();
                          contadorDatos++;
                       }else{
                          String valor = tokenizador.nextToken();
                          vectorCaracteristico[contadorDatos] = Double.parseDouble(valor);
                          contadorDatos++; 
                       }
                       
                        if(contadorDatos == tamañoVector + 1){
                          contadorDatos = 0;
                          patrones.add(new Patron(clase, "", vectorCaracteristico));
                          vectorCaracteristico = new double[tamañoVector];
                        }
                    }
                }
            
            return patrones;
        }
        
            public static ArrayList<Patron> obtenerDatos(boolean posicion_inicio_clase) throws FileNotFoundException, IOException{
            //Declaramos un objeto de tipo StringTokenizer
            ArrayList<Patron> patrones = new ArrayList<>();
            
            //Obtenemos el archivo
            FileReader fileDataSetIris = new FileReader("DataSets/wine.txt");
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
            int tamañoVector = 13;
            double[] vectorCaracteristico = new double[tamañoVector];
            String clase = "";
            
                //Si es true, la clase está al inicio de la linea
                //si no, está al final
                if(posicion_inicio_clase){
                    while(tokenizador.hasMoreTokens()){
                      //si contadorDatos vale 0, estamos en el primer
                      //dato de la fila, por lo tanto es la clase
                      if(contadorDatos == 0){
                        clase = tokenizador.nextToken();
                        contadorDatos++;
                      }else{
                        String valor = tokenizador.nextToken();
                        vectorCaracteristico[contadorDatos - 1] = Double.parseDouble(valor);
                        contadorDatos++;
                      }
                      
                      //Si contadorDatos es igual al tamaño del vector mas 1
                      //llegamos al ultimo dato, reiniciamos los contadores
                      //y agregamos un nuevo patrón
                      if(contadorDatos == tamañoVector + 1){
                          contadorDatos = 0;
                          patrones.add(new Patron(clase, "", vectorCaracteristico));
                          vectorCaracteristico = new double[tamañoVector];
                      }
                      
                    }
                }else{
                    while(tokenizador.hasMoreTokens()){
                       //si contadorDatos vale tamañoContador, estamos en el ultimo
                       //dato de la fila, por lo tanto es la clase
                       if(contadorDatos == tamañoVector){
                          clase = tokenizador.nextToken();
                          contadorDatos++;
                       }else{
                          String valor = tokenizador.nextToken();
                          vectorCaracteristico[contadorDatos] = Double.parseDouble(valor);
                          contadorDatos++; 
                       }
                        
                        //Si contadorDatos es igual al tamaño del vector mas 1
                        //llegamos al ultimo dato, reiniciamos los contadores
                        //y agregamos un nuevo patrón
                        if(contadorDatos == tamañoVector + 1){
                          contadorDatos = 0;
                          patrones.add(new Patron(clase, "", vectorCaracteristico));
                          vectorCaracteristico = new double[tamañoVector];
                        }
                    }
                }
            
            return patrones;
        }
}
