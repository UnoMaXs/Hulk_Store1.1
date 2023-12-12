package com.example.hulkstore.Service;

import com.example.hulkstore.DTO.CarritoDTO;
import com.example.hulkstore.DTO.ProductoDTO;
import com.example.hulkstore.Entity.Carrito;
import com.example.hulkstore.Entity.Producto;
import com.example.hulkstore.Exceptions.CarritoException;
import com.example.hulkstore.Repository.CarritoRepository;
import org.modelmapper.ModelMapper;
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
    @Autowired
    private ModelMapper modelMapper;
    private CarritoDTO carritoDTO;
    private ProductoDTO productoDTO;

    public List<CarritoDTO> getCarritos() {
        try {
            List<Carrito> carritos = carritoRepository.findAll();
            List<CarritoDTO> carritoDTO = new ArrayList<>();
            for (Carrito carrito : carritos) {
                carritoDTO.add(modelMapper.map(carrito, CarritoDTO.class));
            }
            return carritoDTO;
        } catch (Exception e) {
            logger.info("Ocurrió un error al obtener la lista de carritos: " + e.getMessage());
            throw new CarritoException("Ocurrió un error al obtener la lista de productos");
        }
    }

    public Optional<CarritoDTO> getCarritoById(Long carritoId) {
        try {
            Optional<Carrito> optionalCarrito = carritoRepository.findById(carritoId);
            if (optionalCarrito.isPresent()) {
//                List<Carrito> listaCarrito = new ArrayList<>();
                Carrito carrito = optionalCarrito.get();
                return Optional.of(modelMapper.map(carrito, CarritoDTO.class));
            } else {
                logger.info("Carrito no encontrado");
                return Optional.empty();
            }
        } catch (Exception e) {
            logger.info("Ocurrió un error al obtener el carrito por ID: " + e.getMessage());
            throw new CarritoException("Ocurrió un error al obtener el carrito");
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
            List<ProductoDTO> productosDTO = carritoDTO.getProductos();
            for (ProductoDTO productoDTO : productosDTO) {
                valorTotal += productoDTO.getPrecio();
            }
            carritoDTO.setCantidadProductos(carritoDTO.getProductos().size());
            carritoDTO.setValorTotal(valorTotal);
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
                productoDTO = optionalProductoDTO.get();

                if (productoDTO.getCantidad() > 0) {
                    boolean existeEnCarrito = false;

                    for (Producto producto : carrito.getProductos()) {
                        if (producto.getProductoId().equals(productoId)) {
                            producto.setCantidad(producto.getCantidad() + 1);
                            existeEnCarrito = true;
                            break;
                        }
                    }

                    if (!existeEnCarrito) {
                        Producto producto = modelMapper.map(productoDTO, Producto.class);
                        productoDTO.setCantidad(1);
                        carrito.getProductos().add(producto);
                    }

                    productoDTO.setCantidad(productoDTO.getCantidad() + 1);
                    calcularTotal(carrito);

                    carritoRepository.save(carrito);
                    Producto producto = modelMapper.map(productoDTO, Producto.class);
                    productoService.updateProducto(producto);

                    logger.info("Producto(s) agregado(s) al carrito: " + productoDTO);
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

            Optional<ProductoDTO> optionalProductoDTO = productoService.getProductoById(productoId);

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
                    productoDTO.setCantidad(productoDTO.getCantidad() + 1);
                    calcularTotal(carrito);

                    carritoRepository.save(carrito);
                    Producto producto = modelMapper.map(productoDTO, Producto.class);
                    productoService.updateProducto(producto);

                    logger.info("Producto eliminado del carrito: " + producto);
                } else {
                    throw new CarritoException("Producto no encontrado en el carrito");
                }
            } else {
                throw new CarritoException("Producto no encontrado");
            }
        } catch (Exception e) {
            logger.info("Ocurrió un error al eliminar el producto del carrito: " + e.getMessage());
            throw new CarritoException("Ocurrió un error al eliminar el producto del carrito");
        }
    }
}
