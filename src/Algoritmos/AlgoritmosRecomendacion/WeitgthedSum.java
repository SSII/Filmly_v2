/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Algoritmos.AlgoritmosRecomendacion;

import Algoritmos.MedidasSimilitud.MedidaSimilitud;
import Algoritmos.Modelo.Pelicula;
import Algoritmos.Modelo.Usuario;
import Algoritmos.Modelo.Valoracion;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author grupo 12
 */
public class WeitgthedSum implements AlgoritmoRecomendacion {
    
    MedidaSimilitud medida; // Objeto medida de similitud (Coseno o Pearson)
    Usuario usuario; // Usuario actual
    Pelicula pelicula; // Película actual
    boolean ws; // Tipo de algoritmo; true WS, false WA
    List<Valoracion> valoracionesVecinos; // Lista de usuario comunes;

    public WeitgthedSum(boolean ws, Usuario usuario, Pelicula pelicula, MedidaSimilitud medida, List<Usuario> vecinos) {
        this.ws = ws;
        this.usuario = usuario;
        this.pelicula = pelicula;
        this.medida = medida;
        this.valoracionesVecinos = new LinkedList();
        
        for (Valoracion v:pelicula.getValoraciones()){
            for (Usuario u:vecinos){
                Valoracion vUsuario = u.getValoracion(pelicula);
                if (vUsuario != null){
                    valoracionesVecinos.add(v);
                }   
            }
        }
               
    }
    
    @Override
    public float mediaUsuario(){
        
        float media = 0;
        
        for(Valoracion v:usuario.getValoraciones()){
            media += v.getPuntuacion();
        }  
        
        return media/usuario.getValoraciones().size();
        
    }
    
    @Override
    public float mediaPelicula(){
        
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        
    }

    @Override
    public float prediccion() {
        float num = 0, den = 0;
        
        /* Sumatoria de la similitud de todos los usuarios que han votado la Pelicula
           con el Usuario. Denominador común de ambos algoritmos*/
        if (ws){
            for (Valoracion v:valoracionesVecinos){
                den += medida.similitud(usuario, v.getUsuario());
                num += v.getPuntuacion() * medida.similitud(usuario, v.getUsuario());
            }
            
            return num/den;
        }else{
            float media = mediaUsuario();
            for (Valoracion v:valoracionesVecinos){
                den += medida.similitud(usuario, v.getUsuario());
                num += (v.getPuntuacion() - mediaUsuario()) * medida.similitud(usuario, v.getUsuario());
            } 
            return media + num/den;
        }
    }
        
    @Override
     public void setParametros(MedidaSimilitud medida, List<Usuario> vecinos, Pelicula pelicula, Usuario usuario) {
        this.medida = medida;
        for (Valoracion v:pelicula.getValoraciones()){
            for (Usuario u:vecinos){
                Valoracion vUsuario = u.getValoracion(pelicula);
                if (vUsuario != null){
                    valoracionesVecinos.add(v);
                }   
            }
        }
        this.pelicula = pelicula;
        this.usuario = usuario;
   }
    
    
    
}
