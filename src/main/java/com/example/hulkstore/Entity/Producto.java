package com.example.hulkstore.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Entity
@Table(schema = "productos")
@Data
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productoId;

    @NotEmpty
    private String nombre;
    @NotNull
    private int cantidad;
    @NotNull
    private Long precio;

    @ManyToOne
    @JoinColumn(name="carrito_id")
    @JsonIgnore
    private Carrito carrito;
}
