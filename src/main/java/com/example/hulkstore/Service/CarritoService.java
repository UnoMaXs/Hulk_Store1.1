package com.example.hulkstore.Service;

import com.example.hulkstore.Entity.Carrito;
import com.example.hulkstore.Exceptions.CarritoException;
import com.example.hulkstore.Repository.CarritoRepository;
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

    public List<Carrito> verCarritos() {
        try {
            return carritoRepository.findAll();
        } catch (Exception e) {
            System.err.println("Ocurrió un error al obtener la lista de carritos: " + e.getMessage());
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
                System.out.println("Carrito no encontrado");
                return new ArrayList<>();
            }
        } catch (Exception e) {
            System.err.println("Ocurrió un error al obtener el carrito por ID: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    public Carrito obtenerCarritoPorId(Long carritoId) {
        try {
            Optional<Carrito> optionalCarrito = carritoRepository.findById(carritoId);
            return optionalCarrito.orElse(null);
        } catch (Exception e) {
            System.err.println("Ocurrió un error al obtener el carrito por ID: " + e.getMessage());
            throw new CarritoException("Ocurrió un error al obtener el carrito por ID");
        }
    }
    @Transactional
    public void agregarProducto(Carrito carritoDto) {
        try {
            System.out.println("carritoDto: " + carritoDto);
            // Valida que el carrito no esté vacío.
            if (carritoDto == null) {
                throw new CarritoException("El carrito no puede estar vacío");
            }
            //Crear carrito
            carritoRepository.save(carritoDto);
            System.out.println("Carrito creado: " + carritoDto);

            // Mostramos el mensaje de éxito.
            System.out.println("Producto agregado al carrito exitosamente");

        } catch (Exception e) {
            // Maneja otras excepciones de manera adecuada.
            System.err.println("Ocurrió un error al agregar el producto al carrito: " + e.getMessage());
            throw new CarritoException("Ocurrió un error al agregar el producto al carrito");
        }
    }

    // Actualizar producto en el carrito
    @Transactional
    public void actualizarProducto(Long carritoId, Carrito carritoDto) {
        try {
            // Validamos que el id exista.
            if (carritoId == null) {
                throw new CarritoException("El id del carrito no puede ser nulo");
            }
            // Obtenemos el carrito por id.
            Carrito carrito = obtenerCarritoPorId(carritoId);
            carrito.setUsuario(carritoDto.getUsuario());
            carrito.setProductos(carritoDto.getProductos());
            carritoRepository.save(carrito);
            // Mostramos el mensaje de éxito.
            System.out.println("Producto actualizado en el carrito exitosamente");
        } catch (Exception e) {
            // Maneja otras excepciones de manera adecuada.
            System.err.println("Ocurrió un error al actualizar el producto en el carrito: " + e.getMessage());
            throw new CarritoException("Ocurrió un error al actualizar el producto en el carrito");
        }
    }

    @Transactional
    public void eliminarProducto(Long carritoId) {
        try {
            // Validamos que el id exista.
            if (carritoId == null) {
                throw new CarritoException("El id del carrito no puede ser nulo");
            }
            // Obtenemos el carrito por id.
            Carrito carrito = obtenerCarritoPorId(carritoId);
            // Eliminamos el carrito.
            carritoRepository.delete(carrito);
            // Mostramos el mensaje de éxito.
            System.out.println("Producto eliminado del carrito exitosamente");
        } catch (Exception e) {
            // Maneja otras excepciones de manera adecuada.
            System.err.println("Ocurrió un error al eliminar el producto del carrito: " + e.getMessage());
            throw new CarritoException("Ocurrió un error al eliminar el producto del carrito");
        }
    }
}
