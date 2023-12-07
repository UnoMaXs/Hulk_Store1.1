package com.example.hulkstore.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name="carritos")
@Data
public class Carrito {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long carritoId;

    private int cantidadProductos;

    private Double valorTotal=0.0;

    @OneToMany(mappedBy = "carrito")
    @JsonIgnore
    private List<Producto> productos = new ArrayList<>();

    @OneToOne
    @JoinColumn(name = "usuario_id")
    @JsonIgnore
    private Usuario usuario;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Carrito carrito = (Carrito) o;
        return Objects.equals(carritoId, carrito.carritoId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(carritoId);
    }

}
