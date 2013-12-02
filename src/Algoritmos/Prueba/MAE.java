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
        float prediccion = 0;


        //I veces validación cruzada
        for(int i=0; i<particiones.nParticiones; i++){
            
            Particion pTest = particiones.getParticionTest();
            KNN knn = new KNN( particiones.getUsuariosEntrenamiento(),null, 5, medida);
            
            //Recorrido de la particion test
            for(int j=0; j<pTest.getContenido().size();j++){    
                System.out.println("NUEVO USUARIO TEST " + j);
                Usuario actualTest = pTest.getContenido().get(j);
                
                knn.setEjemplo( actualTest );
                vecinos = knn.evaluar(); 
                
                peliculasTest = actualTest.getPeliculasValoradas();
                peliculasComunes = getPeliculasComunes(actualTest, vecinos);
                                              
                //Recorrido de peliculas comunes
                for(int l=0; l<peliculasComunes.size(); l++){
                    algoritmo.setParametros(medida, vecinos, peliculasComunes.get(l), actualTest);
                    prediccion = algoritmo.prediccion();
                    System.out.println("NOTA USUARIO: " + actualTest.getValoracion(peliculasComunes.get(l)).getPuntuacion() + " - NOTA PREDICHA: " + prediccion);
                    errorAcumulado += Math.abs(actualTest.getValoracion(peliculasComunes.get(l)).getPuntuacion()-prediccion);     
                    prediccionesTotales++;
                }                    
                   
                vecinos.clear();
                //System.out.println("ERROR MEDIO: " + errorAcumulado/prediccionesTotales);
            }
            particiones.cambiarParticionTest();
        }
        return errorAcumulado/prediccionesTotales;    
    }
    
    private List<Pelicula> getPeliculasComunes(Usuario test,List<Usuario> vecinos){
        List<Pelicula> resultado = new LinkedList<>();
      
        
        for (Pelicula i : test.getPeliculasValoradas()) {
            for (Usuario u : vecinos) {
                for(Pelicula p : u.getPeliculasValoradas()){
                    if( i.equals(p) ){
                        resultado.add(p);
                    }
                }                
            }
        }
        
        return resultado;    
    }
}
