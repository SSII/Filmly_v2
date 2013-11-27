/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Algoritmos.Prueba;
import Algoritmos.Modelo.*;
import java.util.LinkedList;
import java.util.List;
/**
 *
 * @author grupo 12
 */
public class Particion {
 
    List< List<Usuario> > particiones;
    int nParticion;

    public Particion(int anParticion) {
        nParticion = anParticion;
        particiones = new LinkedList<>();
       
    }
    
    /*
    *   List<Usuario>: Lista de usuarios sobre los que se realizará la partición.
    *   nParticion: Número de particiones deseadas.
    */
    public void crearParticiones(List<Usuario> usuarios){
        int tamParticion = usuarios.size()/nParticion;
        int k = 0;
       
        for( int i=0; i<nParticion; i++){
            particiones.add( new LinkedList<Usuario>() );
            for( int j=0; j<tamParticion; j++){
                particiones.get(i).add( usuarios.get(k) );
                k++;                       
            }            
        }
    }
    
    /*
    * n: Número de la partición que se desea obtener.
    */
    public List<Usuario> getParticion(int n){
        return particiones.get(n);
    }
    
    
    
}
