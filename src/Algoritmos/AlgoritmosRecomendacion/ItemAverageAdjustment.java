/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Algoritmos.AlgoritmosRecomendacion;

import Algoritmos.MedidasSimilitud.MedidaSimilitud;
import Algoritmos.Modelo.*;
import java.util.List;

/**
 *
 * @author grupo12
 */
public class ItemAverageAdjustment implements AlgoritmoRecomendacion {
    
    MedidaSimilitud medida; // Objeto medida de similitud (Coseno o Pearson)
    Usuario usuario; // Usuario actual
    Pelicula pelicula; // Película actual
    List<Usuario> vecinos;
    int n; // Tipo de algoritmo; -1 algoritmo general o n mayor que 0, n-dados

    public ItemAverageAdjustment(int n, Usuario usuario, Pelicula pelicula, MedidaSimilitud medida, List<Usuario> vecinos) {

        this.usuario = usuario;
        this.pelicula = pelicula;
        this.medida = medida;
        this.vecinos = vecinos;
        
        if (n == -1){
            this.n = n;
        }else{
            this.n = this.usuario.getValoraciones().size();
        }        
        
    }
    
    @Override
    public float mediaUsuario(){
        
        float media = 0;
        
        for(Valoracion v:usuario.getValoraciones()){
            media += v.getPuntuacion();
        }  
        
        return media/n;
        
    }
    
    @Override
    public float mediaPelicula(){
        int cont = 0;
        float media = 0;
        
        for(Valoracion v:pelicula.getValoraciones()){
            for (Usuario u:vecinos){
                Valoracion vUsuario = u.getValoracion(pelicula);
                if (vUsuario != null){
                    media += vUsuario.getPuntuacion();
                    cont++;
                }
                
            }
        }  
        
        return media/cont;
        
    }
    
    

    @Override
    public float prediccion() {
       float den = 0, num = 0, media = mediaUsuario();
       
       /*for(int i=0; i<n; ++i){
           num += (medida.similitud(pelicula, usuario.getValoraciones().get(i).getPelicula()))*
                    (usuario.getValoraciones().get(i).getPuntuacion() - media);
           den += Math.abs(medida.similitud(pelicula, usuario.getValoraciones().get(i).getPelicula()));
       }*/
           
       return num/den + mediaPelicula();
        
    }

    @Override
    public void setParametros(MedidaSimilitud medida, List<Usuario> vecinos, Pelicula pelicula, Usuario usuario) {
        this.medida = medida;
        this.vecinos = vecinos;
        this.pelicula = pelicula;
        this.usuario = usuario;
    }
    
}
