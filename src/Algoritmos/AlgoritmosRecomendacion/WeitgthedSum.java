/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Algoritmos.AlgoritmosRecomendacion;

import Algoritmos.MedidasSimilitud.*;
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
    
    int algoritmo;
    MedidaSimilitud medida; // medida de similitud (Coseno = 0 o Pearson = 1)
    Usuario usuario; // Usuario actual
    Pelicula pelicula; // Película actual
    boolean ws; // Tipo de algoritmo; true WS, false WA
    List<Valoracion> valoracionesVecinos; // Lista de valoraciones comunes;
    int n; // Tipo de algoritmo; -1 algoritmo general o n mayor que 0, n-dados
    

    public WeitgthedSum(boolean ws, Usuario usuario, Pelicula pelicula, int algoritmo, List<Usuario> vecinos, int n) {
        this.ws = ws;
        this.usuario = usuario;
        this.pelicula = pelicula;
        this.algoritmo = algoritmo;
        this.valoracionesVecinos = new LinkedList();
        
        for (Valoracion v:pelicula.getValoraciones()){
            for (Usuario u:vecinos){
                Valoracion vUsuario = u.getValoracion(pelicula);
                if (vUsuario != null){
                    valoracionesVecinos.add(v);
                }   
            }
        }
        
        if (n != -1){
            this.n = n;
        }else{
            this.n = this.usuario.getValoraciones().size();
        }
               
    }
    
    @Override
    public float mediaUsuario(){
        
        float media = 0;
        
        for(Valoracion v:usuario.getValoraciones()){
            media += (float)v.getPuntuacion();
        }  
        
        return media/n;
        
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
                if (algoritmo == 0){
                    medida = new Coseno(usuario, v.getUsuario());
                }else{
                    medida = new Pearson(usuario, v.getUsuario());
                }
                den += medida.similitud();
                num += (float)v.getPuntuacion() * medida.similitud();
            }
            
            return num/den;
        }else{
            float media = mediaUsuario();
            for (int i=0; i<n; ++i){
                if (algoritmo == 0){
                    medida = new Coseno(usuario, valoracionesVecinos.get(i).getUsuario());
                }else{
                    medida = new Pearson(usuario, valoracionesVecinos.get(i).getUsuario());
                }
                den += medida.similitud();
                num += ((float)valoracionesVecinos.get(i).getPuntuacion() - mediaUsuario()) * medida.similitud();
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
