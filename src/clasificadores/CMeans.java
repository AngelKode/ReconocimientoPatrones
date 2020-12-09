/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clasificadores;

import data.Patron;
import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author depot
 */
public class CMeans{
    
    private ArrayList<Patron> entrenadores;
    private ArrayList<Patron[]> centroides;
    private int numeroCentroides;
    private int c;
    private boolean isDone;
    
    public CMeans(ArrayList<Patron> clusters, int c){
        this.centroides = new ArrayList<>();
        this.entrenadores = clusters;
        this.c = c;
        this.numeroCentroides = 0;
        this.isDone = false;
    }
    
    public void clasificar(){
        //Generamos un arraylist de patrones con los clusters para evitar errores de referencia
        ArrayList<Patron> clustersAux = (ArrayList<Patron>) this.entrenadores.clone();
        
        //Generamos los nuevos centroides de tamaño c
        Patron[] centroidesNuevos = new Patron[this.c];
        
        //De manera aleatoria, obtenemos patrones de los clusters iniciales
        Random numeroAleatorio = new Random();
        for(int i=0;i<this.c;i++){
            centroidesNuevos[i] = clustersAux.get(numeroAleatorio.nextInt(clustersAux.size()));
        }
        //Agregamos los centroides al arraylist
        this.centroides.add(centroidesNuevos);
        this.numeroCentroides++;
        actualizarEntrenadores();
        //Una vez actualizados los grupos con los centroides aleatorios, hacemos las comparaciones
        //hasta que los dos ultimos centroides sean iguales
        do{
            //Creamos otros centroides
            Patron[] centroides = new Patron[this.c];
            //De manera aleatoria, obtenemos patrones de los clusters iniciales
            for(int i=0;i<this.c;i++){
                centroides[i] = clustersAux.get(numeroAleatorio.nextInt(clustersAux.size()));
            }
            //Agregamos los centroides al arraylist
            this.centroides.add(centroides);
            //aumentamos la cantidad de centroides
            this.numeroCentroides++;
            //creamos un arreglo que tendrá la cantidad de instancias que hay entre cada centroide
            //y cada una de las instancias
            int[] numeroInstancias = new int[this.c];
            
            //Hacemos las comparaciones entre los ultimos centroides y las instancias
            //y obtenemos el promedio entre ambos
            for(Patron patron : this.entrenadores){
                for(int i=0;i<this.c;i++){
                    //Si entra a la condicion, ambas clases son iguales y obtenemos el promedio entre ambos
                    //y se asigna al vector de los centroides en la posicion i
                    if(patron.getClase().equals(centroidesNuevos[i].getClase())){
                        double[] vectorPatron = patron.getVectorC();
                        double[] vectorCentroide = centroidesNuevos[i].getVectorC();
                        
                        //Asignamos la suma
                        centroidesNuevos[i].setVectorC(sumaVectores(vectorPatron, vectorCentroide));
                        numeroInstancias[i]++;
                    }
                }
            }
            //obtenemos el promedio en cada una de las caracteristicas
            //del vector de cada centroide
            obtenerPromediosVector(numeroInstancias);
            //actualizamos los entrenadores
            actualizarEntrenadores();
            //verficamos los ultimos dos centroides
            verificarCentroides();
        }while(this.isDone == false);
        //Si termina, acabamos de obtener los centroides
    }
    
    private void obtenerPromediosVector(int[] numeroInstancias){
        for(int i=0;i<this.c;i++){
            double[] vectorPromedios = this.centroides.get(this.centroides.size() - 1)[i].getVectorC();
            //en cada posicion lo dividimos entre la cantidad de instancias que tiene
            for(int posCaracteristica=0;posCaracteristica<vectorPromedios.length;posCaracteristica++){
                vectorPromedios[posCaracteristica] = (double) vectorPromedios[posCaracteristica] / numeroInstancias[i];
            }
        }
    }
    
    private void actualizarEntrenadores(){
        //Checamos con los entrenadores y los centroides, y con el que tenga la menor distancia, le asignamos esa clase
        for(Patron patron : this.entrenadores){
            for(int posCentroides = 0;posCentroides<this.c;posCentroides++){
                if(posCentroides != 0){
                    double distancia_uno = patron.calcularDistancia(this.centroides.get(this.numeroCentroides-1)[posCentroides - 1]);
                    double distancia_dos = patron.calcularDistancia(this.centroides.get(this.numeroCentroides-1)[posCentroides]);
                    //Verificamos si la distancia con el centroide actual es menor que el anterior
                    //si es menor, le cambiamos la clase
                    if(distancia_dos < distancia_uno){
                       patron.setClase(this.centroides.get(this.numeroCentroides-1)[posCentroides].getClase()); 
                    }
                }else{
                    patron.setClase(this.centroides.get(this.numeroCentroides-1)[posCentroides].getClase());
                }
            }
        }
    }
    
    private double[] sumaVectores(double[] v1, double[] v2){
        double[] sumas = new double[v1.length];
         
        for(int i=0;i<v1.length;i++){
            sumas[i] = v1[i] + v2[i];
        }
        return sumas;
    }
    private void verificarCentroides(){
        //Verificamos si los ultimos 2 centroides son iguales
        for(int posPatron = 0; posPatron < this.c; posPatron++){
            if(!(this.centroides.get(this.centroides.size() - 1)[posPatron] != 
               this.centroides.get(this.centroides.size() - 2)[posPatron])){
               //Si entra a la condición, son iguales
               this.isDone = true;
            }
        }
    }
    
    public ArrayList<Patron> getEntrenadores() {
        return entrenadores;
    }

    
    public double[] efectividadClasificador() {
        
        double cantidadAsignados = this.entrenadores.size();
        double cantidadCorrectos = 0;
        double datos[] = new double[2];
        
        for(int i = 0; i < cantidadAsignados; i++){
        
            if(this.entrenadores.get(i).getClase().equals(this.entrenadores.get(i).getClaseResultante())){
                cantidadCorrectos++;
            }
            
        }
        datos[0] = (cantidadCorrectos * 100) / cantidadAsignados;
        datos[1] = cantidadCorrectos;
        return datos;
        
    }
        
    public int getC() {
        return c;
    }

    public void setC(int c) {
        this.c = c;
    }
    
    
}
