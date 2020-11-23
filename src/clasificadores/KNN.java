/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clasificadores;

import Interfaces.ClasificadorSupervisado;
import data.Patron;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author depot
 */
public class KNN implements ClasificadorSupervisado{
    
    private ArrayList<Patron> representativos;
    
    @Override
    public void entrenar( ArrayList<Patron> instancias) {

    }
    
    @Override
    public void entrenar(String path) {         
        //El entrenamiento KNN unicamente es obtener los datos
            this.representativos = new ArrayList<>();
            
            //Obtenemos el archivo
             BufferedReader buffer;
            FileReader fileDataSetIris;
            try {
                fileDataSetIris = new FileReader(path);
                buffer = new BufferedReader(fileDataSetIris);
                
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
                    this.representativos.add(new Patron(tokenizador.nextToken(), "", vectorCaracteristico));
                    contadorDatos = 0; 
                    vectorCaracteristico = new double[tamañoVector];
                  }
            }
            } catch (FileNotFoundException ex) {
                Logger.getLogger(KNN.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
            Logger.getLogger(KNN.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    @Override
    public ArrayList<Patron> clasificar(ArrayList<Patron> instancias,int k) {
        //Una vez teniendo el arraylist de los patrones de entrenamiento,
        //dependiendo el arraylist de las instancias, vamos a calcular las distancias, ordenamos
        //las distancias, y dependiendo el valor de k, será esa la clase a la que pertenece
        
       ArrayList<Patron> patronesAsignados = instancias;//Aqui devolveremos los patrones ya clasificados
       ArrayList<DistanciaIdentificador> distancias = new ArrayList<>();//Aqui obtendremos las distancias, y despues se ordenará
       int contadorPosicion = 0;
        for (Patron patronesAsignado : patronesAsignados) {
            for (Patron representativo : this.representativos) {
                //Aqui vamos a obtener las distancias entre el punto a clasificar y los demás
                double distancia = patronesAsignado.calcularDistancia(representativo);
                distancias.add(new DistanciaIdentificador(distancia, representativo.getClase()));
            }
            //Acabado el ciclo, ordenamos el arreglo, y le damos su clase
            
            //---------------Ordenamos las distancias por inserción-----------------
            int sizeDistancias = distancias.size(); 

                for(int step = 1; step < sizeDistancias; step++) { 
                  double key = distancias.get(step).getDistanciaMinima();
                  String clase = distancias.get(step).getClaseObtenida();
                  
                  int j = step - 1;                       

                  while (j >= 0 && key < distancias.get(j).getDistanciaMinima()) {
                    DistanciaIdentificador aux = distancias.get(j);
                    distancias.set(j+1, aux);             
                    --j;                                  
                  }
                  
                  distancias.get(j+1).setDistanciaMinima(key);
                  distancias.get(j+1).setClaseObtenida(clase);
                }
            //---------------Ordenamos las distancias por inserción-----------------
                
            //Una vez ordenadas, contamos, y la primer clase que llegue al valor de k, le asignamos esa clase
            //Llenamos un arreglo con el nombre de las clases para ir contando
            //cuantas van teniendo
            HashMap<String,Integer> clasesVecinos = new HashMap<>();
            for(DistanciaIdentificador distanciasChecar : distancias){
                if(!clasesVecinos.containsKey(distanciasChecar.getClaseObtenida())){
                    clasesVecinos.put(distanciasChecar.getClaseObtenida(), 0);
                }
            }
            
            for(DistanciaIdentificador distanciasChecar : distancias){
                int cantidadVecinos = clasesVecinos.get(distanciasChecar.getClaseObtenida());
                    if(cantidadVecinos + 1 == k){
                        Patron nuevoPatron = patronesAsignados.get(contadorPosicion);
                        nuevoPatron.setClaseResultante(distanciasChecar.getClaseObtenida());
                        patronesAsignados.set(contadorPosicion,nuevoPatron);
                        contadorPosicion++;
                        break;
                    }
                cantidadVecinos++;
                clasesVecinos.replace(distanciasChecar.getClaseObtenida(),cantidadVecinos);
            }
            //Limpiamos el arraylist de las distancias obtenidas
            distancias.clear();
        }
       return patronesAsignados;
    }

    @Override
    public double[] efectividadClasificador(ArrayList<Patron> asignados) {
        
        double cantidadAsignados = asignados.size();
        double cantidadCorrectos = 0;
        double datos[] = new double[2];
        
        for(int i = 0; i < cantidadAsignados; i++){
        
            if(asignados.get(i).getClase().equals(asignados.get(i).getClaseResultante())){
                cantidadCorrectos++;
            }
            
        }
        datos[0] = (cantidadCorrectos * 100) / cantidadAsignados;
        datos[1] = cantidadCorrectos;
        return datos;
        
    }
     public ArrayList<Patron> getRepresentativos() {
        return representativos;
    }

    public void setRepresentativos(ArrayList<Patron> representativos) {
        this.representativos = representativos;
    }

    @Override
    public ArrayList<Patron> clasificar(ArrayList<Patron> instancias) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }



}
