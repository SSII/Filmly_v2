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
        List<Pelicula> peliculasTest = new LinkedList<>();
        List<Pelicula> peliculasVecino = new LinkedList<>();
        List<Pelicula> peliculasComunes = new LinkedList<>();


        //I veces validación cruzada
        for(int i=0; i<particiones.nParticiones; i++){
            
            Particion pTest = particiones.getParticionTest();
            KNN knn = new KNN( particiones.getUsuariosEntrenamiento(), 5, medida );
            
            
            //Recorrido de la particion test
            for(int j=0; j<pTest.getContenido().size();j++){    
                Usuario actualTest = pTest.getContenido().get(j);
                
                knn.setEjemplo( actualTest );
                vecinos = knn.evaluar(); 
                peliculasTest = actualTest.getPeliculasValoradas();
             
                //Recorriddo de vecindario
                for(int k=0; k<vecinos.size(); k++){
                    
                    peliculasVecino = vecinos.get(k).getPeliculasValoradas();
                    peliculasComunes = getPeliculasComunes(peliculasTest, peliculasVecino);
                    
                    
                }
                
                
                
                
                vecinos.clear();
            }
            
            
            
            
            particiones.cambiarParticionTest();
        }
        return 0;
    
    }
    
    private List<Pelicula> getPeliculasComunes(List<Pelicula> peliculasTest,List<Pelicula> peliculasVecino){
        List<Pelicula> resultado = new LinkedList<>();
        
        for (Pelicula i : peliculasTest) {
            if( peliculasVecino.contains(i) ){
                resultado.add(i);
            }
        }
        
        return resultado;
        
        
    }
}
