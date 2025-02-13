package com.example.demo.models;

import jakarta.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "User")
public class User {
    @Id
    @GeneratedValue(strategy  = GenerationType.IDENTITY)
    private Long id;

    @Column( name = "nombre")
    private String nombre;

    @Column(name = "contrasena")
    private String contrasena;

    @Column(name = "edad")
    private int edad;

    @Column(name = "administrador")
    private boolean administrador;


    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        User other = (User) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }

    public User(Long id, String nombre, String contrasena, int edad, boolean administrador) {
        this.id = id;
        this.nombre = nombre;
        this.contrasena = contrasena;
        this.edad = edad;
        this.administrador = administrador;
    }

    public User() {
        //TODO Auto-generated constructor stub
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public boolean isAdministrador() {
        return administrador;
    }

    public void setAdministrador(boolean administrador) {
        this.administrador = administrador;
    }

   

}
