package com.example.hulkstore.DTO;

import com.example.hulkstore.Entity.Carrito;
import com.example.hulkstore.Entity.Producto;
import lombok.Data;

@Data
public class ProductoDTO {
    private Long productoId;
    private String nombre;
    private int cantidad;
    private Long precio;
//    private Carrito carrito;
}
