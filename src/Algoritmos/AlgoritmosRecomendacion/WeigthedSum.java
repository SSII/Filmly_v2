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
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

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
    Map<Usuario,Valoracion> valoracionesVecinos; // Mapa de valoraciones comunes;
    int n; // Tipo de algoritmo; -1 algoritmo general o n mayor que 0, n-dados
    

    public WeitgthedSum(boolean ws, Usuario usuario, Pelicula pelicula, int algoritmo, List<Usuario> vecinos, int n) {
        this.ws = ws;
        this.usuario = usuario;
        this.pelicula = pelicula;
        this.algoritmo = algoritmo;
        this.valoracionesVecinos = new HashMap();
        
       for (Usuario u:vecinos){
            Valoracion vUsuario = u.getValoracion(pelicula);
            if (vUsuario != null){
                valoracionesVecinos.put(u, vUsuario);
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
        List<Usuario> valoracionesVecinosAux = new LinkedList<>(valoracionesVecinos.keySet());
            
        if (ws){
            for (int i=0; i<n; ++i){
                if (algoritmo == 0){
                    medida = new Coseno(usuario, valoracionesVecinosAux.get(i));
                }else{
                    medida = new Pearson(usuario, valoracionesVecinosAux.get(i));
                }
                den += medida.similitud();
                num += (float)valoracionesVecinos.get(valoracionesVecinosAux.get(i)).getPuntuacion() * medida.similitud();
            }
            
            return num/den;
        }else{
            float media = mediaUsuario();
            for (int i=0; i<n; ++i){
                if (algoritmo == 0){
                    medida = new Coseno(usuario, valoracionesVecinosAux.get(i));
                }else{
                    medida = new Pearson(usuario, valoracionesVecinosAux.get(i));
                }
                System.out.println(valoracionesVecinosAux.get(i).getId() + " " + medida.similitud());
                den += medida.similitud();
                num += ((float)valoracionesVecinos.get(valoracionesVecinosAux.get(i)).getPuntuacion() - mediaUsuario()) * medida.similitud();
            } 
            return media + num/den;
        }
    }
        
    @Override
     public void setParametros(int medida, List<Usuario> vecinos, Pelicula pelicula, Usuario usuario) {
        /*this.medida = medida;
        for (Valoracion v:pelicula.getValoraciones()){
            for (Usuario u:vecinos){
                Valoracion vUsuario = u.getValoracion(pelicula);
                if (vUsuario != null){
                    //valoracionesVecinos.add(v);
                }   
            }
        }
        this.pelicula = pelicula;
        this.usuario = usuario;*/
   }
    
    
    
}