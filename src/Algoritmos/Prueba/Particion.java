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
 
    List<Usuario> contenido;
    public enum tipo{Test,Entrenamiento};
    public tipo tipoParticion;    
    
    public Particion() {
        contenido = new LinkedList<>();
    }
    
    public void addContenido( Usuario aContenido ){
        contenido.add(aContenido);
    }

    public tipo getTipoParticion() {
        return tipoParticion;
    }    
}
