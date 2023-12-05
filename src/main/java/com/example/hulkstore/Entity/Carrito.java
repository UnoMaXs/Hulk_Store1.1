package com.example.hulkstore.Entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Table(name="carritos")
@Data
public class Carrito {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long carritoId;

    @OneToMany
    private List<Producto> productos;

    private int cantidadProductos;

    private Double valorTotal;

    @OneToOne
    private Usuario usuario;
}
