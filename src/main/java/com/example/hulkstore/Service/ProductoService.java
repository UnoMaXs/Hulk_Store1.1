package com.example.hulkstore.Service;

import com.example.hulkstore.Entity.Producto;
import com.example.hulkstore.Repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductoService {

    @Autowired
    ProductoRepository productoRepository;

    public List<Producto> getProductos(){
        return productoRepository.findAll();
    }

    public void agregarProducto(Producto producto){
        productoRepository.save(producto);
    }

    public void borrarProducto(Producto producto){
        productoRepository.delete(producto);
    }

    public void actualizarProducto(Producto producto){
        productoRepository.save(producto);
    }

}
