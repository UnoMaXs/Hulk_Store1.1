package com.example.hulkstore.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
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

}
