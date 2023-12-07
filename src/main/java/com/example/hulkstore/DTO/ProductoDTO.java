package com.example.hulkstore.DTO;

import lombok.Data;

@Data
public class ProductoDTO {
    private Long productoId;
    private String nombre;
    private int cantidad;
    private Long precio;
}
