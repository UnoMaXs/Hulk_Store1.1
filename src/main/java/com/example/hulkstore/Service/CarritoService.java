package com.example.hulkstore.Service;

import com.example.hulkstore.DTO.ProductoDTO;
import com.example.hulkstore.Entity.Carrito;
import com.example.hulkstore.Entity.Producto;
import com.example.hulkstore.Exceptions.CarritoException;
import com.example.hulkstore.Repository.CarritoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

@Service
public class CarritoService {

    Logger logger = Logger.getLogger(getClass().getName());

    @Autowired
    private CarritoRepository carritoRepository;
    @Autowired
    private ProductoService productoService;

    public List<Carrito> verCarritos() {
        try {
            return carritoRepository.findAll();
        } catch (Exception e) {
            logger.info("Ocurrió un error al obtener la lista de carritos: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    public List<Carrito> verCarritoId(Long carritoId) {
        try {
            Optional<Carrito> optionalCarrito = carritoRepository.findById(carritoId);
            if (optionalCarrito.isPresent()) {
                List<Carrito> listaCarrito = new ArrayList<>();
                listaCarrito.add(optionalCarrito.get());
                return listaCarrito;
            } else {
                logger.info("Carrito no encontrado");
                return new ArrayList<>();
            }
        } catch (Exception e) {
            logger.info("Ocurrió un error al obtener el carrito por ID: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    public Carrito obtenerCarritoPorId(Long carritoId) {
        try {
            Optional<Carrito> optionalCarrito = carritoRepository.findById(carritoId);
            return optionalCarrito.orElseThrow(() -> new CarritoException("Carrito no encontrado"));
        } catch (Exception e) {
            logger.info("Ocurrió un error al obtener el carrito por ID: " + e.getMessage());
            throw new CarritoException("Ocurrió un error al obtener el carrito por ID");
        }
    }

    public void calcularTotal(Carrito carrito) {
        try {
            double valorTotal = 0.0;
            for (Producto producto : carrito.getProductos()) {
                valorTotal += producto.getPrecio();
            }
            carrito.setCantidadProductos(carrito.getProductos().size());
            carrito.setValorTotal(valorTotal);
        } catch (Exception e) {
            logger.info("Ocurrió un error al calcular el total del carrito: " + e.getMessage());
        }
    }

    @Transactional
    public void agregarProducto(Long carritoId, Long productoId) {
        try {
            Carrito carrito = obtenerCarritoPorId(carritoId);

            Optional<ProductoDTO> optionalProductoDTO = productoService.getProductoById(productoId);

            if (optionalProductoDTO.isPresent()) {
                ProductoDTO productoDTO = optionalProductoDTO.get();

                if (productoDTO.getCantidad() > 0) {
                    boolean existeEnCarrito = false;

                    for (Producto p : carrito.getProductos()) {
                        if (p.getProductoId().equals(productoId)) {
                            p.setCantidad(p.getCantidad() + 1);
                            existeEnCarrito = true;
                            break;
                        }
                    }

                    if (!existeEnCarrito) {
                        productoDTO.setCantidad(1);
                        carrito.getProductos().add(ProductoDTO);
                    }

                    productoDTO.setCantidad(ProductoDTO.getCantidad() - 1);
                    calcularTotal(carrito);

                    carritoRepository.save(carrito);
                    productoService.updateProducto(productoDTO);

                    logger.info("Producto(s) agregado(s) al carrito: " + ProductoDTO);
                } else {
                    throw new CarritoException("No hay suficiente stock");
                }
            } else {
                throw new CarritoException("Producto no encontrado");
            }
        } catch (Exception e) {
            logger.info("Ocurrió un error al agregar el producto al carrito: " + e.getMessage());
            throw new CarritoException("Ocurrió un error al agregar el producto al carrito");
        }
    }


    @Transactional
    public void eliminarProducto(Long carritoId, Long productoId) {
        try {
            Carrito carrito = obtenerCarritoPorId(carritoId);

            Optional<Producto> optionalProductoDTO = productoService.obtenerProductoPorId(productoId);

            if (optionalProductoDTO.isPresent()) {
                productoDTO = optionalProductoDTO.get();

                boolean productoEnCarrito = false;

                // Verifica si el producto está en el carrito y lo elimina.
                for (Producto p : carrito.getProductos()) {
                    if (p.getProductoId().equals(productoId)) {
                        productoEnCarrito = true;
                        carrito.getProductos().remove(p);
                        break;
                    }
                }

                if (productoEnCarrito) {
                    // Incrementa la cantidad en el inventario y recalcula el valor total del carrito.
                    producto.setCantidad(producto.getCantidad() + 1);
                    calcularTotal(carrito);

                    // Guarda los cambios en el carrito y actualiza el producto.
                    carritoRepository.save(carrito);
                    productoService.updateProducto(producto);

                    logger.info("Producto eliminado del carrito: " + producto);
                } else {
                    throw new CarritoException("Producto no encontrado en el carrito");
                }
            } else {
                throw new CarritoException("Producto no encontrado");
            }
        } catch (Exception e) {
            // Maneja otras excepciones de manera adecuada.
            logger.info("Ocurrió un error al eliminar el producto del carrito: " + e.getMessage());
            throw new CarritoException("Ocurrió un error al eliminar el producto del carrito");
        }
    }
}
