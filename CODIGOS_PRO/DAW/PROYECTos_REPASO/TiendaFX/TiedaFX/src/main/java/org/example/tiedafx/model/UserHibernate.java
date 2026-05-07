package org.example.tiedafx.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "clientes")
public class UserHibernate {

    // propiedades
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String nombre;

    @Column
    private String correo;

    @Column
    private String password;

    @Column
    private int saldo;

    public UserHibernate(String nombre, String correo, String password, int saldo) {
        this.nombre = nombre;
        this.correo = correo;
        this.password = password;
        this.saldo = saldo;
    }
}
