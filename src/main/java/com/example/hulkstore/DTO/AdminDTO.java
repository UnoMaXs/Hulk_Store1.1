package com.example.hulkstore.DTO;

import lombok.Data;

@Data
public class AdminDTO {
    private Long adminId;
    private String nombre;
    private String correo;
    private String contrasena;
}
