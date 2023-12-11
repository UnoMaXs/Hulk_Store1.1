package com.example.hulkstore.Service;

import com.example.hulkstore.Entity.Producto;
import com.example.hulkstore.Exceptions.ProductoException;
import com.example.hulkstore.Repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductoService {

    @Autowired
    private ProductoRepository productoRepository;

    public List<Producto> obtenerProductos() {
        try {
            return productoRepository.findAll();
        } catch (Exception e) {
            System.err.println("Ocurrió un error al obtener la lista de productos: " + e.getMessage());
            throw new ProductoException("Ocurrió un error al obtener la lista de productos");
        }
    }

    public Optional<Producto> obtenerProductoPorId(Long productoId) {
        try {
            return productoRepository.findById(productoId);
        } catch (Exception e) {
            System.err.println("Ocurrió un error al obtener el producto por ID: " + e.getMessage());
            throw new ProductoException("Ocurrió un error al obtener el producto por ID");
        }
    }

    public void agregarProducto(Producto producto) {
        try {
            productoRepository.save(producto);
        } catch (Exception e) {
            System.err.println("Ocurrió un error al agregar el producto: " + e.getMessage());
            throw new ProductoException("Ocurrió un error al agregar el producto");
        }
    }

    public void borrarProducto(Long productoId) {
        try {
            Optional<Producto> productoOptional = productoRepository.findById(productoId);

            if (productoOptional.isPresent()) {
                Producto producto = productoOptional.get();
                productoRepository.delete(producto);
            } else {
                System.out.println("No se encuentra el producto");
            }
        } catch (Exception e) {
            System.err.println("Ocurrió un error al borrar el producto: " + e.getMessage());
            throw new ProductoException("Ocurrió un error al borrar el producto");
        }
    }

    public void actualizarProducto(Producto producto) {
        try {
            productoRepository.save(producto);
        } catch (Exception e) {
            System.err.println("Ocurrió un error al actualizar el producto: " + e.getMessage());
            throw new ProductoException("Ocurrió un error al actualizar el producto");
        }
    }
}


