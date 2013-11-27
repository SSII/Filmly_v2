/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Algoritmos.AlgoritmosRecomendacion;

import Algoritmos.Modelo.Pelicula;
import Algoritmos.Modelo.Usuario;
import Algoritmos.Modelo.Valoracion;
import Algoritmos.persistencia.GestorPersistencia;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 *
 * @author admin
 */
public class Algoritmo {

    public List<Map> puntuacionUsuarioPelicula; /*Lista de mapas que contienen un usuario y su puntuacion sobre las distintas peliculas*/
    public List<Pelicula> items; /*Lista de peliculas*/
    public List<Usuario> usuarios; /*Lista de usuarios*/
    private List<Valoracion> valoraciones;
    
    public Algoritmo(){
        GestorPersistencia.crearConexion();
        EntityManager em = GestorPersistencia.getInstancia().getEntityManager();
        
        puntuacionUsuarioPelicula = new LinkedList<>();
        items = new LinkedList<>();
        usuarios = new LinkedList();
        valoraciones = new LinkedList();
        
        Query q = em.createNativeQuery("select *  from peliculas", Pelicula.class);
        Query q2 = em.createNativeQuery("select * from usuarios", Usuario.class);
        Query q3 = em.createNativeQuery("select * from valoraciones", Valoracion.class);
        
        
        items = q.getResultList();      
        usuarios = q2.getResultList();
        valoraciones = q3.getResultList();
        
        
            
        Query buscaValoraciones = em.createNativeQuery("select * from valoraciones v where v.idUser = 265 ", Valoracion.class);
            
        usuarios.get(1).setValoraciones(buscaValoraciones.getResultList());
        
        System.out.println("TAMAÑO: " + usuarios.get(1).getValoraciones().size());
        
        
            
         
        
        System.out.println("Tamaño peliculas: " + items.size());
        System.out.println("Tamaño usuarios: " + usuarios.size());
        System.out.println("Tamaño valoraciones: " + valoraciones.size());
    }
    
    
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        
        Algoritmo a = new Algoritmo();
        
       // a.cargarDatos();
   
    }
}
