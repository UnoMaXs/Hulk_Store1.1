package com.example.hulkstore.DTO;

import lombok.Data;
import java.util.List;

@Data
public class CarritoDTO {
    private Long carritoId;
    private int cantidadProductos;
    private Double valorTotal;
}
