/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Algoritmos.Prueba;

import Algoritmos.AlgoritmosRecomendacion.AlgoritmoRecomendacion;
import Algoritmos.AlgoritmosRecomendacion.WeitgthedSum;
import Algoritmos.Modelo.Pelicula;
import Algoritmos.Modelo.Usuario;
import Algoritmos.Modelo.Valoracion;
import Algoritmos.persistencia.GestorPersistencia;
import java.util.LinkedList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 *
 * @author Graciano
 */
public class MainPruebas {
    public static void main(String[] args) {
    // TODO code application logic here
        GestorPersistencia.crearConexion();
        EntityManager em = GestorPersistencia.getInstancia().getEntityManager();
        Query buscaValoraciones;
        
/*        Query q = em.createNativeQuery("select * from usuarios", Usuario.class);
        
        
        List<Usuario> l = q.getResultList();
        Usuario u = l.get(0);
        buscaValoraciones = em.createNativeQuery("select * from valoraciones v where v.idUser = :identificador1", Valoracion.class);
        buscaValoraciones.setParameter("identificador1", u.getId());
        u.setValoraciones(buscaValoraciones.getResultList());
        Usuario u2 = l.get(1);
        buscaValoraciones = em.createNativeQuery("select * from valoraciones v where v.idUser = :identificador2", Valoracion.class);
        buscaValoraciones.setParameter("identificador2", u2.getId());
        u2.setValoraciones(buscaValoraciones.getResultList());*/
        
        Usuario u0 = new Usuario ("","","","","0");
        Usuario u1 = new Usuario ("","","","","1");
        Usuario u2 = new Usuario ("","","","","2");
        Pelicula p1 = new Pelicula ("", 0, 1, new LinkedList());
        Pelicula p2 = new Pelicula ("", 0, 2, new LinkedList());
        Pelicula p3 = new Pelicula ("", 0, 3, new LinkedList());
        Valoracion v1 = new Valoracion (4);
        v1.setUsuario(u0);
        v1.setPelicula(p1);
        Valoracion v2 = new Valoracion (3);
        v2.setUsuario(u1);
        v2.setPelicula(p1);
        Valoracion v3 = new Valoracion (1);
        v3.setUsuario(u0);
        v3.setPelicula(p2);
        Valoracion v4 = new Valoracion (1);
        v4.setUsuario(u1);
        v4.setPelicula(p2);
        Valoracion v5 = new Valoracion (2);
        v5.setUsuario(u0);
        v5.setPelicula(p3);
        Valoracion v6 = new Valoracion (1);
        v6.setUsuario(u1);
        v6.setPelicula(p3);
        Valoracion v7 = new Valoracion (4);
        v6.setUsuario(u2);
        v6.setPelicula(p1);
        Valoracion v8 = new Valoracion (2);
        v6.setUsuario(u2);
        v6.setPelicula(p2);
        List<Usuario> vecinos = new LinkedList();
        vecinos.add(u0);
        vecinos.add(u1);
        
        AlgoritmoRecomendacion a = new WeitgthedSum(true, u2, p3, 0, vecinos,-1);
        System.out.println(a.prediccion());

    // a.cargarDatos();

    }
}
