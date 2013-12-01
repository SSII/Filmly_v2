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
    int medida;
    Particiones particiones;
    
    
    public MAE(AlgoritmoRecomendacion alg, Particiones part, int aMedida){
        algoritmo = alg;
        particiones = part;
        medida = aMedida;
    }
   
    public float precision(){
        List<Usuario> vecinos = new LinkedList<>();
        List<Pelicula> peliculasTest = new LinkedList<>();
        List<Pelicula> peliculasVecino = new LinkedList<>();
        List<Pelicula> peliculasComunes = new LinkedList<>();
        float errorAcumulado = 0;
        int prediccionesTotales = 0;


        //I veces validación cruzada
        for(int i=0; i<particiones.nParticiones; i++){
            
            Particion pTest = particiones.getParticionTest();
            KNN knn = new KNN( particiones.getUsuariosEntrenamiento(),null, 5, medida);
            
            //Recorrido de la particion test
            for(int j=0; j<pTest.getContenido().size();j++){    
                System.out.println("NUEVO USUARIO TEST");
                Usuario actualTest = pTest.getContenido().get(j);
                
                knn.setEjemplo( actualTest );
                vecinos = knn.evaluar(); 
                peliculasTest = actualTest.getPeliculasValoradas();
             
                //Recorrido de vecindario
                for(int k=0; k<vecinos.size(); k++){
                    
                    peliculasVecino = vecinos.get(k).getPeliculasValoradas();
                    System.out.println("GET PELICULAS VECINO " + k);
                    peliculasComunes = getPeliculasComunes(peliculasTest, peliculasVecino);
                    System.out.println("GET PELICULAS COMUNES " + k);
                    
                    System.out.println("TAM PELICULAS COMUNES " + peliculasComunes.size());
                    
                    
                    for(int l=0; l<peliculasComunes.size(); l++){
                        System.out.println("L VALE: " + l);
                        algoritmo.setParametros(medida, vecinos, peliculasComunes.get(l), actualTest);
                        errorAcumulado += Math.abs(actualTest.getValoracion(peliculasComunes.get(l)).getPuntuacion()-algoritmo.prediccion());           
                        prediccionesTotales++;
                    }                    
                }         
                vecinos.clear();
            }
            particiones.cambiarParticionTest();
        }
        return errorAcumulado/prediccionesTotales;    
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
