package com.example.hulkstore.Service;

import com.example.hulkstore.Entity.Producto;
import com.example.hulkstore.Repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductoService {

    @Autowired
    private ProductoRepository productoRepository;

    public List<Producto> obtenerProductos(){
        return productoRepository.findAll();
    }

    public Optional<Producto> obtenerProductoPorId(Long productoId){
        return productoRepository.findById(productoId);
    }

    public void agregarProducto(Producto producto){
        productoRepository.save(producto);
    }

    public void borrarProducto(Long productoId){
        Optional<Producto> productoOptional = productoRepository.findById(productoId);

        if(productoOptional.isPresent()){
            Producto producto = productoOptional.get();
            productoRepository.delete(producto);
        } else {
            System.out.println("No se encuentra el producto");
        }
    }

    public void actualizarProducto(Producto producto){
        productoRepository.save(producto);
    }

}


