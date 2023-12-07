package com.example.hulkstore.DTO;

import lombok.Data;

@Data
public class CarritoDTO {
    private Long carritoId;
    private int cantidadProductos;
    private Double valorTotal;
}
