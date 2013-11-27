/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Algoritmos.Prueba;

import Algoritmos.Modelo.Usuario;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author Pedro
 */
public class Particiones {
    List<Particion> particiones;
    int nParticiones;

    public Particiones(int anParticiones) {
        nParticiones = anParticiones;
        particiones = new LinkedList<>();
    }
    
     /*
    *   List<Usuario>: Lista de usuarios sobre los que se realizará la partición.
    */
    public void crearParticiones(List<Usuario> usuarios){
        int tamParticion = usuarios.size()/nParticiones;
        int k = 0;
       
        for( int i=0; i<nParticiones; i++){
            particiones.add( new Particion() );
            for( int j=0; j<tamParticion; j++){
                particiones.get(i).addContenido(usuarios.get(j));
                k++;                       
            }            
        }
        
        particiones.get(0).setTipoParticion(Particion.tipo.Test);
    }
    
    public void cambiarParticionTest(){
        for(int i=0;i<particiones.size();i++){
            
            if( particiones.get(i).getTipoParticion() == Particion.tipo.Test){
                particiones.get(i).setTipoParticion(Particion.tipo.Entrenamiento);
                particiones.get( (i+1)%particiones.size() ).setTipoParticion(Particion.tipo.Test);
                
                break;
            }
            
        }
    }
    
}
