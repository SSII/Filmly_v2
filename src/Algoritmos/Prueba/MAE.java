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
import Algoritmos.persistencia.GestorPersistencia;
import java.util.LinkedList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;

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
        List<Usuario> vecinos;
        List<Pelicula> peliculasTest = new LinkedList<>();
        List<Pelicula> peliculasVecino = new LinkedList<>();
        List<Pelicula> peliculasComunes;
        float errorAcumulado = 0;
        int prediccionesTotales = 0;
        float prediccion;
        long tiempoMedio = 0;
        long tiempoMedioVecinos = 0;
        int totalVecinos = 0;
        int total = 0;

        //I veces validación cruzada
        for(int i=0; i<particiones.nParticiones; i++){
            List<Usuario> usuariosEntrenamiento;
            usuariosEntrenamiento = particiones.getUsuariosEntrenamiento();
            
            System.out.println("-------------------------------------------------------------------------------");
            
            Particion pTest = particiones.getParticionTest();
        
            //Recorrido de la particion test
            for(int j=0; j<pTest.getContenido().size();j++){     
                Usuario actualTest = pTest.getContenido().get(j);
                //System.out.println("NUEVO USUARIO TEST " + actualTest.getId());
                
                long tiempoInicialVecinos = System.currentTimeMillis();
                KNN knn = new KNN( usuariosEntrenamiento,actualTest, 20, medida );
                vecinos = knn.evaluar();
                long tiempoFinalVecinos = System.currentTimeMillis() - tiempoInicialVecinos;
                tiempoMedioVecinos += tiempoFinalVecinos;
                totalVecinos++;
                
                peliculasTest = actualTest.getPeliculasValoradas();
                //peliculasComunes = getPeliculasComunes(actualTest, vecinos);
                
                peliculasComunes = getPeliculasNoValoradas(actualTest);
                                              
                long tiempoInicial = System.currentTimeMillis();
                //Recorrido de peliculas comunes
                for(int l=0; l<peliculasComunes.size(); l++){
                    algoritmo.setParametros(medida, vecinos, peliculasComunes.get(l), actualTest, 8);
                    prediccion = algoritmo.prediccion();
                    
                    if( prediccion != 0){ 
                        
                        if(prediccion > 5){
                            prediccion = 5;
                        }                        
                        //System.out.println("NOTA USUARIO: " + actualTest.getValoracion(peliculasComunes.get(l)).getPuntuacion() + " - NOTA PREDICHA: " + prediccion);
                        //errorAcumulado += Math.abs(actualTest.getValoracion(peliculasComunes.get(l)).getPuntuacion()-prediccion);     
                        //prediccionesTotales++;
                    }
                }
                long tiempoTotal = System.currentTimeMillis() - tiempoInicial;
                tiempoMedio += tiempoTotal;
                total++;
                
                vecinos.clear();
            }
            System.out.println( " PARTICION: " + i);
            particiones.cambiarParticionTest();
        }
        
        System.out.println("TIEMPO MEDIO VECINOS: " + tiempoMedioVecinos/totalVecinos + "mseg.");
        System.out.println("TIEMPO MEDIO: " + tiempoMedio/total + "mseg.");
        
        return tiempoMedio/total;    
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
    
    
    private List<Pelicula> getPeliculasNoValoradas(Usuario test){
        List<Pelicula> resultado;
        
        GestorPersistencia.crearConexion();
        EntityManager em = GestorPersistencia.getInstancia().getEntityManager();
        
        Query q = em.createNativeQuery("select * from peliculas", Pelicula.class);
        
        resultado = q.getResultList();
        
        for (Pelicula pelicula : test.getPeliculasValoradas()) {
            resultado.remove(pelicula);
        }

        return resultado;
    }
}
