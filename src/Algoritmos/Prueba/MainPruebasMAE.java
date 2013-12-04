/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Algoritmos.Prueba;

import Algoritmos.AlgoritmosRecomendacion.WeigthedSum;
import Algoritmos.Modelo.Usuario;
import Algoritmos.persistencia.GestorPersistencia;
import java.util.LinkedList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 *
 * @author Pedro
 */
public class MainPruebasMAE {
     public static void main(String[] args) {
        // TODO code application logic here
        GestorPersistencia.crearConexion();
        EntityManager em = GestorPersistencia.getInstancia().getEntityManager();
     
        Query q = em.createNativeQuery("select * from usuarios", Usuario.class);

        List<Usuario> l = q.getResultList();
        List<Usuario> l2 = new LinkedList<>();
        
//        for(int i=50; i<100; i++){
//            l2.add( l.get(i));
//        }
//        
        
       
         WeigthedSum ws = new WeigthedSum(false, null, null, 0, null, -1);
         Particiones particiones = new Particiones(7);
         particiones.crearParticiones(l);
         
         MAE mae = new MAE(ws, particiones, 0);
         
         System.out.println("MAE: " + mae.precision());
     }

}
