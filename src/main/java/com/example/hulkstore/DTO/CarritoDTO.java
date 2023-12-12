package com.example.hulkstore.DTO;

import com.example.hulkstore.Entity.Producto;
import com.example.hulkstore.Entity.Usuario;
import lombok.Data;

import java.util.List;

@Data
public class CarritoDTO {
    private Long carritoId;
    private int cantidadProductos;
    private Double valorTotal;
    private List<Producto> Productos;
    private Usuario usuario;
}
