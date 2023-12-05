package com.example.hulkstore.Entity;

import jakarta.persistence.*;

@Entity
@Table(schema = "productos")
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private int cantidad;
}
