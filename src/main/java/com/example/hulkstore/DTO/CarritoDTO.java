package com.example.hulkstore.DTO;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Data
public class CarritoDTO {
    private Long carritoId;
    private int cantidadProductos;
    private Double valorTotal;
    private List<ProductoDTO> Productos;
}

