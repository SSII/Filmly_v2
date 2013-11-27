/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Algoritmos.Prueba;

import Algoritmos.AlgoritmosRecomendacion.AlgoritmoRecomendacion;
import Algoritmos.AlgoritmosRecomendacion.KNN;
import Algoritmos.MedidasSimilitud.MedidaSimilitud;
import Algoritmos.Modelo.Pelicula;
import Algoritmos.Modelo.Usuario;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author grupo 12
 */
public class MAE {
    AlgoritmoRecomendacion algoritmo;
    MedidaSimilitud medida;
    Particiones particiones;
    
    
    public MAE(AlgoritmoRecomendacion alg, Particiones part, MedidaSimilitud aMedida){
        algoritmo = alg;
        particiones = part;
        medida = aMedida;
    }
   
    public float precision(){
        List<Usuario> vecinos = new LinkedList<>();
        List<Pelicula> peliculas = new LinkedList<>();

        //I veces validación cruzada
        for(int i=0; i<particiones.nParticiones; i++){
            
            Particion pTest = particiones.getParticionTest();
            KNN knn = new KNN( particiones.getUsuariosEntrenamiento(), 5, medida );
            
            
            //Recorrido de la particion test
            for(int j=0; j<pTest.getContenido().size();j++){                
                
                knn.setEjemplo( pTest.getContenido().get(j) );
                vecinos = knn.evaluar();    
                
                
                vecinos.clear();
            }
            
            
            
            
            particiones.cambiarParticionTest();
        }
        return 0;
    
    }
}
