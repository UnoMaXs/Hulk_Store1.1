package com.example.hulkstore.DTO;

import com.example.hulkstore.Entity.Carrito;
import lombok.Data;

@Data
public class UsuarioDTO {
    private Long usuarioId;
    private String nombre;
    private String correo;
    private String contrasena;
    private Carrito carrito;
}
