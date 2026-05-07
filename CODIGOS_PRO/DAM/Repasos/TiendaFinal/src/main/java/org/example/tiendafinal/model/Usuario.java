package org.example.tiendafinal.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Usuario {

    private int id;
    private String nombre;
    private String correo, pass;
    private int salario;
    private int perfil;

    public Usuario(String nombre, String correo, String pass, int salario, int perfil) {
        this.nombre = nombre;
        this.correo = correo;
        this.pass = pass;
        this.salario = salario;
        this.perfil = perfil;
    }

    public Usuario(String nombre, String correo, int salario, int perfil) {
        this.nombre = nombre;
        this.correo = correo;
        this.salario = salario;
        this.perfil = perfil;
    }
}
