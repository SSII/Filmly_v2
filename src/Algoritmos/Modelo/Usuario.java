/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Algoritmos.Modelo;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author Alvaro
 */
@Entity
@Table(name="usuarios")
public class Usuario implements Serializable {
    
    @Column(name="nombre")
    String _nombre;
    @Column(name="nick")
    String _nick;
    @Column(name="pass")
    String _pass;
    @Column(name="email")
    String _email;
    @Id
    @Column(name="id")
    String _id;
    @OneToMany(targetEntity=Valoracion.class, cascade=CascadeType.ALL, mappedBy="_puntuacion")
    private List<Valoracion> _valoraciones;

    public Usuario() {
    }

    public Usuario(String _nombre, String _nick, String _pass, String _email, String _id) {
        this._nombre = _nombre;
        this._nick = _nick;
        this._pass = _pass;
        this._email = _email;
        this._id = _id;
        this._valoraciones = new LinkedList<>();
    }

    public String getNombre() {
        return _nombre;
    }

    public void setNombre(String _nombre) {
        this._nombre = _nombre;
    }

    public String getNick() {
        return _nick;
    }

    public void setNick(String _nick) {
        this._nick = _nick;
    }

    public String getPass() {
        return _pass;
    }

    public void setPass(String _pass) {
        this._pass = _pass;
    }

    public String getEmail() {
        return _email;
    }

    public void setEmail(String _email) {
        this._email = _email;
    }

    public String getId() {
        return _id;
    }

    public void setId(String _id) {
        this._id = _id;
    }
    
    public void setValoraciones(List<Valoracion> valoraciones) {
        
        _valoraciones = valoraciones;
        
    }
    
    public List<Valoracion> getValoraciones() {
        
        return _valoraciones;
        
    }
    
    public Valoracion getValoracion(Pelicula p){
        
        for(Valoracion v:_valoraciones){
            if (v.getPelicula().equals(p)){
                return v;
            }
        }
        
        return null;
    }
    
    
}