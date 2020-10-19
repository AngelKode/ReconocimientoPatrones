/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clasificadores;

import Interfaces.ClasificadorSupervisado;
import data.Patron;
import java.util.ArrayList;

/**
 *
 * @author depot
 */
public class MinimaDistancia implements ClasificadorSupervisado{
    ArrayList<Patron> representativos;
    
    public MinimaDistancia(){
        this.representativos = new ArrayList<>();
    }
    
    @Override
    public void entrenar(ArrayList<Patron> instancias) {
        //Aqui obtenemos los promedios de cada clase para obtener los patrones representataivos
        double[] vectorRepresentativo = new double[4];
        int inicio = 0;
        //Recorremos el arreglo de patrones para obtener la suma de cada caracteristica de cada clase
        //y asi obtenemos los promedios de cada una

        for(int posicionPatrones = 0; posicionPatrones < instancias.size(); posicionPatrones++){
          //Al entrar en la condicion, significa que ya estamos trabajando con otra clase
          if(posicionPatrones != 0 && (!instancias.get(posicionPatrones).getClase().equals(instancias.get(posicionPatrones - 1).getClase()))){
             //Creamos un patron con el vector obtenido
             //Obtenemos el promedio de cada posicion del arreglo respecto a la cantidad de datos
             for(int posArreglo = 0; posArreglo < vectorRepresentativo.length;posArreglo++){
                 vectorRepresentativo[posArreglo] /= (posicionPatrones - inicio);
             }
             Patron patronRepresentativo = new Patron(instancias.get(posicionPatrones - 1).getClase(),"", vectorRepresentativo);
             this.representativos.add(patronRepresentativo);
             //Limpiamos el arreglo;
             vectorRepresentativo = new double[4];
             inicio = posicionPatrones++;
          }else{
              
            double[] vectorPatron = new double[instancias.get(posicionPatrones).getVectorC().length];
            vectorPatron = instancias.get(posicionPatrones).getVectorC();

            //Ahora recorremos el vector representativo para agregar vada caracteristica
            for(int posicionVector = 0; posicionVector < vectorRepresentativo.length; posicionVector++){
                vectorRepresentativo[posicionVector] += vectorPatron[posicionVector];
            }
              
          }
          
          //Si es el ultimo elemento, agregamos esa clase
          if(posicionPatrones + 1 >= instancias.size()){
              
             for(int posArreglo = 0; posArreglo < vectorRepresentativo.length;posArreglo++){
                 vectorRepresentativo[posArreglo] /= (posicionPatrones - inicio);
             }
             
             Patron patronRepresentativo = new Patron(instancias.get(posicionPatrones).getClase(),"", vectorRepresentativo);
             this.representativos.add(patronRepresentativo);
          }
           
        }
    }

    @Override
    public ArrayList<Patron> clasificar(ArrayList<Patron> instancias) {
        //Obtenemos la minima distancia entre los patrones caracteristicas y un punto arbitrario dado
        //Y regresamos la clase a la que pertenece cada elemento ingresado
        ArrayList<Patron> patronesAsignados = new ArrayList<>();
        
        for( int posicion = 0; posicion < instancias.size(); posicion++){
            ArrayList<DistanciaIdentificador> distanciasObtenidas = new ArrayList<>();
            //Recorremos el arreglo de los vectores representativos
            for(int posVectRep = 0; posVectRep < this.representativos.size();posVectRep++){
                String identificador = this.representativos.get(posVectRep).getClase();
                double distanciaEntrePuntos = this.representativos.get(posVectRep).calcularDistancia(instancias.get(posicion));
                
                distanciasObtenidas.add(new DistanciaIdentificador(distanciaEntrePuntos,identificador));
            }
            //Ahora encontramos el de menor distancia
            distanciasObtenidas = ordenarArreglo(distanciasObtenidas);
            
            patronesAsignados.add(new Patron(instancias.get(posicion).getClase(),distanciasObtenidas.get(0).getClaseObtenida(), instancias.get(posicion).getVectorC()));            
        }
        
        return patronesAsignados;
    }

    public ArrayList<Patron> getRepresentativos() {
        return representativos;
    }

    public void setRepresentativos(ArrayList<Patron> representativos) {
        this.representativos = representativos;
    }

    private ArrayList<DistanciaIdentificador> ordenarArreglo(ArrayList<DistanciaIdentificador> distanciasObtenidas) {
        ArrayList<DistanciaIdentificador> ordenado = distanciasObtenidas;
        //Usamos el ordenamiento burbuja
        for(int i = 0; i < ordenado.size()-1;i++){
            for(int j = 0; j < ordenado.size()-1; j++){
                 
                if(ordenado.get(j).getDistanciaMinima() > ordenado.get(j+1).getDistanciaMinima()){
                    DistanciaIdentificador aux = ordenado.get(j);
                    ordenado.set(j,ordenado.get(j+1));
                    ordenado.set(j+1, aux);
                }
            }
        }
        
        
        return ordenado;
    }
    
}
