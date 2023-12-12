package com.example.hulkstore.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
@Entity
@Table(name = "usuarios")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long usuarioId;
    @NotEmpty
    private String nombre;
    @NotEmpty
    private String correo;
    @NotEmpty
    private String contrasena;
    @OneToOne(mappedBy = "usuario", cascade = CascadeType.ALL)
    @JoinColumn(name= "carrito_id")
    private Carrito carrito;

}