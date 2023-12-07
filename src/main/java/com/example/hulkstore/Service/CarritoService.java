package com.example.hulkstore.Service;

import com.example.hulkstore.Entity.Carrito;
import com.example.hulkstore.Entity.Producto;
import com.example.hulkstore.Repository.CarritoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CarritoService {

    @Autowired
    private CarritoRepository carritoRepository;
    @Autowired
    private ProductoService productoService;

    public List<Carrito> verCarritos(){
        return carritoRepository.findAll();
    }

    public List<Carrito> verCarrito(Long carritoId) {
        Optional<Carrito> optionalCarrito = carritoRepository.findById(carritoId);
        if (optionalCarrito.isPresent()) {
            List<Carrito> listaCarrito = new ArrayList<>();
            listaCarrito.add(optionalCarrito.get());
            return listaCarrito;
        } else {
            System.out.println("Carrito no encontrado");
            return new ArrayList<>();
        }
    }

    @Transactional
    public void agregarProductoCarrito(Long carritoId, Long productoId) {
        Optional<Carrito> optionalCarrito = carritoRepository.findById(carritoId);
        Carrito carrito = optionalCarrito.get();
        Optional<Producto> optionalProducto = productoService.obtenerProductoPorId(productoId);
        if (optionalProducto.isPresent()) {
            Producto producto = optionalProducto.get();
            this.agregarProducto(producto, carrito);
            carritoRepository.save(carrito);
            totalCarrito(carrito);
        }
    }


    private void totalCarrito(Carrito carrito) {
        double valorTotal = 0.0;
        for (Producto producto : carrito.getProductos()) {
            valorTotal += producto.getPrecio();
        }
        carrito.setCantidadProductos(carrito.getProductos().size());
        carrito.setValorTotal(valorTotal);
        carritoRepository.save(carrito);
    }


    public void agregarProducto(Producto producto, Carrito carrito) {
        List<Producto> productos = new ArrayList<>();
        productos.add(producto);
        producto.setCarrito(carrito);
    }

}
