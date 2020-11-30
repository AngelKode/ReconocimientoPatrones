/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clasificadores;

import Interfaces.ClasificadorSupervisado;
import data.Patron;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author depot
 */
public class NaiveBayes implements ClasificadorSupervisado{
    
    private double promedios_caracteristicas[][];
    private double varianza_caracteristicas[][];
    private int numeroClases;
    private int numeroCaracteristicas;
    private HashMap<String,Double> probabilidad_clases;
    private HashMap<Integer,String> id_clases;
    
    public NaiveBayes(){
        this.promedios_caracteristicas = null;
        this.varianza_caracteristicas = null;
        this.numeroClases = 0;
        this.numeroCaracteristicas = 0;
        this.probabilidad_clases = new HashMap<>();
        this.id_clases = new HashMap<>();
    }
    
    @Override
    public void entrenar(ArrayList<Patron> instancias) {
        
        //Aqui tendremos las posiciones en donde empieza cada clase
        ArrayList<Integer> posiciones_siguiente_clase = new ArrayList<>();
        
        if(instancias.size() < 1){
           System.out.println("No hay instancias");
           this.numeroClases = 0;
           return;
        }else if(instancias.size() < 2){
           this.numeroClases = 1;
           posiciones_siguiente_clase.add(0);
           this.probabilidad_clases.put(instancias.get(0).getClase(),1.0);
           this.id_clases.put(0,instancias.get(0).getClase());
        }else{
            this.numeroClases = 1;//Igualamos a 1, ya que almenos hay 1 clase
            
            //La primer clase empieza en la posicion 0
            posiciones_siguiente_clase.add(0);
            
            //Este string nos servirá para comparar el nombre de la clase mientras
            //recorremos la lista de instancias
            String clase = instancias.get(0).getClase();
            
            //Recorremos las instancias y obtenemos la cantidad de clases
            //y las posiciones donde inicia cada clase
            int cantidadInstancias = 0;
            for(int posicionInstancias=0;posicionInstancias<instancias.size();posicionInstancias++){
                //Si el nombre de la clase actual es diferente a la anterior entra en la condición
                //y se suma 1 a la cantidad de clases
                if(!instancias.get(posicionInstancias).getClase().equals(clase)){
                    this.probabilidad_clases.put(clase, (double)cantidadInstancias/instancias.size());
                    this.id_clases.put(this.numeroClases - 1,clase);
                    clase = instancias.get(posicionInstancias).getClase();
                    posiciones_siguiente_clase.add(posicionInstancias);
                    this.numeroClases++;
                    cantidadInstancias = 1;
                }else{
                    cantidadInstancias++;
                }
                
                if(posicionInstancias + 1 == instancias.size()){
                    this.probabilidad_clases.put(clase, (double)cantidadInstancias/instancias.size());
                    this.id_clases.put(this.numeroClases - 1,clase);
                }
            }
        }
        
        //Inicializamos las matrices donde estarán los promedios
        this.promedios_caracteristicas = new double[this.numeroClases][instancias.get(0).getVectorC().length];
        for(int i=0;i<this.numeroClases;i++){
            for(int j=0;j<instancias.get(0).getVectorC().length;j++){
                this.promedios_caracteristicas[i][j] = 0.0;
            }
        }
                                                   
        //Inicializamos las matrices donde estarán las varianzas
        this.varianza_caracteristicas = new double[this.numeroClases][instancias.get(0).getVectorC().length];
        for(int i=0;i<this.numeroClases;i++){
            for(int j=0;j<instancias.get(0).getVectorC().length;j++){
                this.varianza_caracteristicas[i][j] = 0.0;
            }
        }
                                                   
        //---------Ahora obtenemos los promedios de cada caracteristica---------------
        
        //Recorremos el arraylist que tiene como elementos las posiciones donde inicia
        //cada clase
        for(int posClase = 0;posClase < posiciones_siguiente_clase.size();posClase++){
          int posicion_inicial,posicion_final,numeroInstancias;
            //Si hay un elemento en la posicion 'posClase + 1', quiere decir que aun
            //faltan más clases y la posicion final es el siguiente en el arraylist, 
            //si no, es la última clase y la posicion final es el tamaño de el arraylist de las instancias
            if(posClase + 1 < posiciones_siguiente_clase.size()){
                posicion_inicial = posiciones_siguiente_clase.get(posClase);
                posicion_final = posiciones_siguiente_clase.get(posClase + 1);
                numeroInstancias = posicion_final - posicion_inicial;
                this.numeroCaracteristicas = instancias.get(0).getVectorC().length;
                
                //Recorremos las instancias desde 'posicion_inicial' hasta 'posicion_final'
                //esos limites es donde están todos los elementos de una sola clase
                for(int instancia_clase=posicion_inicial;instancia_clase<posicion_final;instancia_clase++){
                    //Recorremos el vector de cada instancia para obtener los valores y sumarlos a la matriz
                    //de los promedios
                    for(int caracteristica=0;caracteristica<this.numeroCaracteristicas;caracteristica++){
                        this.promedios_caracteristicas[posClase][caracteristica] += instancias
                                                                                    .get(instancia_clase)
                                                                                    .getVectorC()[caracteristica];
                    }
                }
                
                //Recorremos la matriz para dividir todos los valores entre el número de instancias que
                //tiene la clase y asi obtenemos los promedios
                for(int i=0;i<this.numeroCaracteristicas;i++){
                    this.promedios_caracteristicas[posClase][i] /= numeroInstancias;
                }
            }else{
               posicion_inicial = posiciones_siguiente_clase.get(posClase);
               posicion_final = instancias.size();
               numeroInstancias = posicion_final - posicion_inicial;
               this.numeroCaracteristicas = instancias.get(0).getVectorC().length;
               
                for(int instancia_clase=posicion_inicial;instancia_clase<posicion_final;instancia_clase++){
                    for(int caracteristica=0;caracteristica<this.numeroCaracteristicas;caracteristica++){
                        this.promedios_caracteristicas[posClase][caracteristica] += instancias
                                                                                    .get(instancia_clase)
                                                                                    .getVectorC()[caracteristica];
                    }
                }
               
                for(int i=0;i<this.numeroCaracteristicas;i++){
                    this.promedios_caracteristicas[posClase][i] /= numeroInstancias;
                }
              
            }
        }
        //---------Ahora obtenemos los promedios de cada caracteristica----------------
        
        
        //---------Obtenemos la varianza de cada caracteristica------------------------
        for(int posClase = 0;posClase < posiciones_siguiente_clase.size();posClase++){
            int posicion_inicial,posicion_final,numeroInstancias;
            double resultSumaCaracteristicas = 0;
            
            if(posClase + 1 < posiciones_siguiente_clase.size()){
                
                posicion_inicial = posiciones_siguiente_clase.get(posClase);
                posicion_final = posiciones_siguiente_clase.get(posClase + 1);
                
                //Recorremos caracteristica por caracteristica de cada instancia,primero todas las c1
                //luego todas las c2 y asi sucesivamente
                for(int caracteristica=0;caracteristica<this.numeroCaracteristicas;caracteristica++){
                    double valor_instancia,promedio_caracteristica,resultado;
                    
                    //Recorremos la caracteristica 'caracteristica' de todas las instancias 
                    for(int instancia_clase=posicion_inicial;instancia_clase<posicion_final;instancia_clase++){
                        //Valor de la caracteristica de la instancia
                        valor_instancia = instancias.get(instancia_clase).getVectorC()[caracteristica];
                        //Valor del promedio de esa caracteristica
                        promedio_caracteristica = this.promedios_caracteristicas[posClase][caracteristica];
                        //El resultado de la resta entre ambos
                        resultado = valor_instancia - promedio_caracteristica;
                        
                        //Sumamos a una variable lo que da de elevar al cuadrado el resultado
                        resultSumaCaracteristicas += Math.pow(resultado, 2);
                    }
                    
                    //Ahora agregamos el valor de dividir la sumatoria entre la cantidad de instancias
                    //de esa clase - 1(N - 1)
                    numeroInstancias = posicion_final - posicion_inicial;
                    //Agregamos ese valor en la matriz de varianzas
                    this.varianza_caracteristicas[posClase][caracteristica] = (double)
                                                                              resultSumaCaracteristicas / 
                                                                              (numeroInstancias - 1);
                    resultSumaCaracteristicas = 0;
                }
                
            }else{
                posicion_inicial = posiciones_siguiente_clase.get(posClase);
                posicion_final = instancias.size();
                
                //Recorremos caracteristica por caracteristica de cada instancia,primero todas las c1
                //luego todas las c2 y asi sucesivamente
                for(int caracteristica=0;caracteristica<this.numeroCaracteristicas;caracteristica++){
                    double valor_instancia,promedio_caracteristica,resultado;
                    
                    //Recorremos la caracteristica 'caracteristica' de todas las instancias 
                    for(int instancia_clase=posicion_inicial;instancia_clase<posicion_final;instancia_clase++){
                        //Valor de la caracteristica de la instancia
                        valor_instancia = instancias.get(instancia_clase).getVectorC()[caracteristica];
                        //Valor del promedio de esa caracteristica
                        promedio_caracteristica = this.promedios_caracteristicas[posClase][caracteristica];
                        //El resultado de la resta entre ambos
                        resultado = valor_instancia - promedio_caracteristica;
                        
                        //Sumamos a una variable lo que da de elevar al cuadrado el resultado
                        resultSumaCaracteristicas += Math.pow(resultado, 2);
                    }
                    
                    //Ahora agregamos el valor de dividir la sumatoria entre la cantidad de instancias
                    //de esa clase - 1(N - 1)
                    numeroInstancias = posicion_final - posicion_inicial;
                    //Agregamos ese valor en la matriz de varianzas
                    this.varianza_caracteristicas[posClase][caracteristica] = resultSumaCaracteristicas / 
                                                                              (numeroInstancias - 1);
                    resultSumaCaracteristicas = 0;
                }
            }
        }
        //---------Obtenemos la varianza de cada caracteristica------------------------
    }

    @Override
    public void entrenar(String path) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ArrayList<Patron> clasificar(ArrayList<Patron> instancias) {
        
        //Si no hay numero de clases, no se entrenó, y no podemos clasificar
        if(this.numeroClases < 1){
            System.out.println("No fue posible clasificar. Falta entrenar al clasificador");
            return instancias;
        }
        
        double valor_raiz, exponente_e,valorCaracteristica,promedio,varianza,valorDistNormal;
        HashMap<String,Double> probabilidades;
        //Recorremos el arraylist de las objetos a clasificar
        for(int numObjetoClasificar=0;numObjetoClasificar < instancias.size();numObjetoClasificar++){
            //Tenemos un ciclo para contar de 0 hasta el numero de clases
            //y asi obtener las probabilidades obteniendo la distribucion normal
            double probabilidad_clase = 0;
            probabilidades = new HashMap<>();
            for(int clase = 0;clase<this.numeroClases;clase++){
                //Obtenemos la clase y su probabilidad apriori
                //que está almacenada en unos hashmap
                String id = this.id_clases.get(clase);
                probabilidad_clase = this.probabilidad_clases.get(id);
                //Recorremos las caracteristicas del objeto
                for(int caracteristicaObjeto=0;caracteristicaObjeto < this.numeroCaracteristicas;caracteristicaObjeto++){
                    //Usamos la formula para obtener la distribucion normal
                    //de esa caracteristica
                    promedio = this.promedios_caracteristicas[clase][caracteristicaObjeto];
                    varianza = this.varianza_caracteristicas[clase][caracteristicaObjeto];
                    valor_raiz = 2 * Math.PI * varianza;
                    valorCaracteristica = instancias.get(numObjetoClasificar).getVectorC()[caracteristicaObjeto];
                    exponente_e = -1 * ((Math.pow(valorCaracteristica - promedio, 2))/(2*varianza));
                    
                    valorDistNormal = (1 / Math.sqrt(valor_raiz)) * (Math.pow(Math.E,exponente_e));
                    
                    //Multiplicamos el valor por lo que llevamos de la probabilidad
                    probabilidad_clase *= valorDistNormal;
                }      
                //Agregamos la probabilidad a un hashmap con su respectiva clase
                probabilidades.put(id,probabilidad_clase);
            }     
            //Una vez obteniendo la probabilidad de pertencer a cada clase, asignamos la clase
            //con la cual tuvo mayorm probabilidad
            double mayor_prob = 0.0;
            String clase = "";
            int numero_prob = 0;
            for(HashMap.Entry<String,Double> valores : probabilidades.entrySet()){
                if(numero_prob > 0){
                    if(valores.getValue() > mayor_prob){
                        mayor_prob = valores.getValue();
                        clase = valores.getKey();
                    }
                }else{
                    mayor_prob = valores.getValue();
                    clase = valores.getKey();
                    numero_prob++;
                }
            }
            //Asignamos al objeto la clase a la cual pertenece
            instancias.get(numObjetoClasificar).setClaseResultante(clase);
            //Limpiamos el hashmap
            probabilidades.clear();
        }
        
        return instancias;
    }

    @Override
    public ArrayList<Patron> clasificar(ArrayList<Patron> instancias, int k) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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
    
}
