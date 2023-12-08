package com.example.hulkstore.Service;

import com.example.hulkstore.DTO.CarritoDTO;
import com.example.hulkstore.DTO.ProductoDTO;
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

    public List<CarritoDTO> getCarritos() {
        List<Carrito> carritos = carritoRepository.findAll();
        return carritos.stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    public List<CarritoDTO> getCarritoId(Long carritoId) {
        Optional<Carrito> optionalCarrito = carritoRepository.findById(carritoId);
        if (optionalCarrito.isPresent()) {
            List<CarritoDTO> listaCarrito = new ArrayList<>();
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

        Optional<ProductoDTO> optionalProducto = productoService.getProductoById(productoId);
        if (optionalProducto.isPresent()) {
            ProductoDTO productoDTO = optionalProducto.get();

            if (productoDTO.getCantidad() > 0) {
                carrito.getProductos().add(productoDTO);
                productoDTO.setCantidad(productoDTO.getCantidad() - 1);
                calcularTotal(carrito);
                carritoRepository.save(carrito);
                productoService.updateProducto(productoDTO);
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
            Optional<ProductoDTO> optionalProducto = productoService.getProductoById(productoId);

            if (optionalProducto.isPresent()) {
                ProductoDTO producto = optionalProducto.get();

                producto.setCantidad(producto.getCantidad() + 1);
                calcularTotal(carrito);
                carritoRepository.save(carrito);
                productoService.updateProducto(producto);
            } else {
                throw new Excepcion("Producto no encontrado");
            }
        }

    }

    private CarritoDTO convertirADTO(Carrito carrito){
        CarritoDTO carritoDTO = new CarritoDTO();
        carritoDTO.setCarritoId(carrito.getCarritoId());
        carritoDTO.setCantidadProductos(carrito.getCantidadProductos());
        carritoDTO.setValorTotal(carrito.getValorTotal());
        return carritoDTO;
    }

    private Carrito convertirAEntidad(CarritoDTO carritoDTO) {
        Carrito carrito = new Carrito();
        carrito.setCarritoId(carritoDTO.getCarritoId());
        carrito.setCantidadProductos(carritoDTO.getCantidadProductos());
        carrito.setValorTotal(carritoDTO.getValorTotal());
        return carrito;
    }

}

