package com.example.hulkstore.Service;

import com.example.hulkstore.Entity.Carrito;
import com.example.hulkstore.Entity.Producto;
import com.example.hulkstore.Repository.CarritoRepository;
import com.example.hulkstore.Repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CarritoService {

    @Autowired
    private CarritoRepository carritoRepository;
    @Autowired
    private ProductoService productoService;

    public List<Carrito> verCarritos() {
        return carritoRepository.findAll();
    }

    public List<Carrito> verCarritoId(Long carritoId) {
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


    public Carrito obtenerCarritoPorId(Long carritoId) {
        Optional<Carrito> optionalCarrito = carritoRepository.findById(carritoId);
        return optionalCarrito.orElseThrow(() -> new Excepcion("Carrito no encontrado"));
    }

    public void calcularTotal(Carrito carrito) {
        double valorTotal = 0.0;
        for (Producto producto : carrito.getProductos()) {
            valorTotal += producto.getPrecio();
        }
        carrito.setCantidadProductos(carrito.getProductos().size());
        carrito.setValorTotal(valorTotal);
    }

    @Transactional
    public void agregarProducto(Long carritoId, Long productoId) {
        Carrito carrito = obtenerCarritoPorId(carritoId);

        Optional<Producto> optionalProducto = productoService.obtenerProductoPorId(productoId);
        if (optionalProducto.isPresent()) {
            Producto producto = optionalProducto.get();

            if (producto.getCantidad() > 0) {
                carrito.getProductos().add(producto);
                producto.setCantidad(producto.getCantidad() - 1);
                calcularTotal(carrito);
                carritoRepository.save(carrito);
                productoService.actualizarProducto(producto);
            } else {
                throw new Excepcion("No hay suficiente stock");
            }
        } else {
            throw new Excepcion("Producto no encontrado");
        }
    }


    @Transactional
    public void eliminarProducto(Long carritoId, Long productoId) {
        Carrito carrito = obtenerCarritoPorId(carritoId);

        boolean productoEnCarrito = carrito.getProductos().removeIf(producto -> producto.getProductoId().equals(productoId));

        if (productoEnCarrito) {
            Optional<Producto> optionalProducto = productoService.obtenerProductoPorId(productoId);

            if (optionalProducto.isPresent()) {
                Producto producto = optionalProducto.get();

                producto.setCantidad(producto.getCantidad() + 1);
                calcularTotal(carrito);
                carritoRepository.save(carrito);
                productoService.actualizarProducto(producto);
            } else {
                throw new Excepcion("Producto no encontrado");
            }
        }

    }

}

