package com.example.hulkstore.Service;

import com.example.hulkstore.DTO.CarritoDTO;
import com.example.hulkstore.DTO.ProductoDTO;
import com.example.hulkstore.Entity.Carrito;
import com.example.hulkstore.Entity.Producto;
import com.example.hulkstore.Exceptions.CarritoException;
import com.example.hulkstore.Repository.CarritoRepository;
import com.example.hulkstore.Repository.ProductoRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Service
public class CarritoService {

    Logger logger = Logger.getLogger(getClass().getName());

    @Autowired
    private CarritoRepository carritoRepository;
    @Autowired
    private ProductoService productoService;
    @Autowired
    private CarritoDTO carritoDTO;
    @Autowired
    private ModelMapper modelMapper;


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
            throw new CarritoException("Ocurrió un error al obtener la lista de carritos");
        }
    }

    public Optional<CarritoDTO> getCarritoById(Long carritoId) {
        try {
            Optional<Carrito> optionalCarrito = carritoRepository.findById(carritoId);
            if (optionalCarrito.isPresent()) {
                Carrito carrito = optionalCarrito.get();
                return Optional.of(modelMapper.map(carrito, CarritoDTO.class));
            } else {
                logger.info("Carrito no encontrado en servicio");
                return Optional.empty();
            }
        } catch (Exception e) {
            logger.info("Ocurrió un error al obtener el carrito por ID: " + e.getMessage());
            throw new CarritoException("Ocurrió un error al obtener el carrito");
        }
    }

    @Transactional
    public void agregarProductoAlCarrito(Long carritoId, Long productoId) {
        try {
            Carrito carrito = obtenerCarritoPorId(carritoId);

            Optional<ProductoDTO> optionalProductoDTO = productoService.getProductoById(productoId);

            if (optionalProductoDTO.isPresent()) {
                ProductoDTO productoDTO = optionalProductoDTO.get();

                if (productoDTO.getCantidad() > 0) {
                    boolean existeEnCarrito = false;

                    // Utilizando un bucle foreach para buscar el producto en el carrito
                    for (Producto producto : carrito.getProductos()) {
                        if (producto.getProductoId().equals(productoId)) {
                            producto.setCantidad(producto.getCantidad() + 1);
                            existeEnCarrito = true;
                            break;
                        }
                    }

                    // Si no existe en el carrito, se agrega
                    if (!existeEnCarrito) {
                        Producto producto = modelMapper.map(productoDTO, Producto.class);
                        producto.setCantidad(1);
                        producto.setCarrito(carrito);
                        carrito.getProductos().add(producto);
                    }

                    productoDTO.setCantidad(productoDTO.getCantidad() - 1);

                    calcularTotal(carrito);

                    carritoRepository.save(carrito);

                    Producto producto = modelMapper.map(productoDTO, Producto.class);
                    productoService.updateProducto(producto);

                    logger.info("Producto agregado al carrito: " + productoDTO);
                } else {
                    throw new CarritoException("No hay suficiente stock");
                }
            } else {
                throw new CarritoException("Producto no encontrado");
            }
        } catch (Exception e) {
            logger.info("Ocurrió un error al agregar el producto al carrito: " + e.getMessage());
            throw new CarritoException("Ocurrió un error al agregar el producto al carrito " + e.getMessage());
        }
    }



    @Transactional
    public void eliminarProductoDelCarrito(Long carritoId, Long productoId) {
        try {
            Carrito carrito = obtenerCarritoPorId(carritoId);

            Optional<ProductoDTO> optionalProductoDTO = productoService.getProductoById(productoId);

            if (optionalProductoDTO.isPresent()) {
                ProductoDTO productoDTO = optionalProductoDTO.get();

                boolean productoEnCarrito = false;

                // Utilizando un bucle foreach para buscar el producto en el carrito
                Iterator<Producto> iterator = carrito.getProductos().iterator();
                while (iterator.hasNext()) {
                    Producto producto = iterator.next();
                    if (producto.getProductoId().equals(productoId)) {
                        productoEnCarrito = true;
                        iterator.remove();  // Eliminar el producto del carrito
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



    //UTIL
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

    public List<ProductoDTO> listaDeProductos(Long carritoId) {
        try {
            Carrito carrito = obtenerCarritoPorId(carritoId);

            // Verifica si el carrito tiene productos
            if (carrito.getProductos() != null && !carrito.getProductos().isEmpty()) {
                // Mapea la lista de productos a DTOs usando ModelMapper
                List<ProductoDTO> productosDTO = carrito.getProductos().stream()
                        .map(producto -> modelMapper.map(producto, ProductoDTO.class))
                        .toList();
                // Puedes devolver la lista de productos DTO
                return productosDTO;
            } else {
                // Manejar el caso en el que el carrito no tenga productos
                logger.info("El carrito no contiene productos.");
                // Puedes devolver una lista vacía o lanzar una excepción, según tus necesidades.
                return Collections.emptyList();
            }
        } catch (Exception e) {
            // Manejar excepciones
            logger.info("Ocurrió un error al obtener la lista de productos del carrito: " + e.getMessage());
            // Puedes lanzar una excepción específica si lo deseas
            throw new CarritoException("Ocurrió un error al obtener la lista de productos del carrito");
        }
    }
}
