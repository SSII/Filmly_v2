/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Algoritmos.MedidasSimilitud;

import Algoritmos.Modelo.Pelicula;
import Algoritmos.Modelo.Usuario;
import Algoritmos.Modelo.Valoracion;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author grupo 12
 */
public class Pearson implements MedidaSimilitud {
    
    Usuario u;
    Usuario i;
    Map<Pelicula, Pair<Valoracion, Valoracion>> valoraciones; 
    
    public Pearson(Usuario u, Usuario i) {
        
        this.u = u;
        this.i = i;
        valoraciones = new HashMap<>();
        
        for (Valoracion v1:u.getValoraciones()){
            for (Valoracion v2:i.getValoraciones()){
                if (v1.getPelicula().equals(v2.getPelicula())){
                    valoraciones.put(v1.getPelicula(), new Pair(v1, v2));
                }
            }
        }
    }
         
    private float mediaUsuario(Usuario u){
        
        float media = 0;
        
        for (Valoracion v:u.getValoraciones()){
            media += v.getPuntuacion();
        }
        
        return media/u.getValoraciones().size();
    }
    
    
    
    @Override
    public float similitud() {
        float num = 0, factor1 = 0, factor2 = 0;
        
        for (Pelicula j:valoraciones.keySet()){
            Valoracion vu = u.getValoracion(j);
            Valoracion vi = i.getValoracion(j);
            if (vu != null && vi != null){
                num += (vu.getPuntuacion() - mediaUsuario(u)) * (vi.getPuntuacion() - mediaUsuario(i));
                factor1 += Math.pow((vu.getPuntuacion() - mediaUsuario(u)), 2);
                factor2 += Math.pow((vi.getPuntuacion() - mediaUsuario(i)), 2);
            }   
        }
         
        //Medida de similitud transformada
        return (num / (float)Math.sqrt(factor1 * factor2) + 1 ) / 2;
    }
    
}
