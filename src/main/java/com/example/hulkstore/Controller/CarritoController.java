package com.example.hulkstore.Controller;

import com.example.hulkstore.DTO.CarritoDTO;
import com.example.hulkstore.DTO.ProductoDTO;
import com.example.hulkstore.Exceptions.CarritoException;
import com.example.hulkstore.Service.CarritoService;
import com.example.hulkstore.Service.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/carrito")
public class CarritoController {

    @Autowired
    private CarritoService carritoService;
    @Autowired
    private ProductoService productoService;

    @GetMapping("/verCarritos")
    public ResponseEntity<List<CarritoDTO>> verCarritos() {
        try {
            List<CarritoDTO> carritoDTO = carritoService.getCarritos();
            return ResponseEntity.ok(carritoDTO);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Collections.emptyList());
        }
    }

    @GetMapping("/verCarrito/{carritoId}")
    public ResponseEntity<Object> verCarrito(@PathVariable Long carritoId) {
        try {
            Optional<CarritoDTO> optionalCarrito = carritoService.getCarritoById(carritoId);
            if (optionalCarrito.isPresent()) {
                Optional<CarritoDTO> carritoOptional = carritoService.getCarritoById(carritoId);
                return ResponseEntity.ok(carritoOptional.get());
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("No existe carrito con id + carritoId");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(null);
        }
    }

    @PostMapping("/agregarProducto/{carritoId}/{productoId}")
    public ResponseEntity<String> agregarProductoAlCarrito(@PathVariable("carritoId") Long carritoId,
                                                           @PathVariable("productoId") Long productoId) {
        try {
            Optional<CarritoDTO> optionalCarrito = carritoService.getCarritoById(carritoId);
            if (optionalCarrito.isPresent()) {
                Optional<ProductoDTO> optinalProducto = productoService.getProductoById(productoId);
                if (optinalProducto.isPresent()) {
                    carritoService.agregarProductoAlCarrito(carritoId, productoId);
                    return ResponseEntity.ok("Producto agregado al carrito exitosamente");
                } else {
                    return ResponseEntity.status(HttpStatus.NOT_FOUND)
                            .body("Producto con la id " + productoId + " no encontrado");
                }
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Carrito con la id " + carritoId + " no encontrado");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al agregar el producto al carrito: " + e.getMessage());
        }
    }

    @PutMapping("/eliminarProducto/{carritoId}/{productoId}")
    public ResponseEntity<String> eliminarProductoDeCarrito(@PathVariable("carritoId") Long carritoId,
                                                            @PathVariable("productoId") Long productoId) {
        try {
            Optional<CarritoDTO> optionalCarrito = carritoService.getCarritoById(carritoId);
            if (optionalCarrito.isPresent()) {
                Optional<ProductoDTO> optionalProducto = productoService.getProductoById(productoId);
                if (optionalProducto.isPresent()) {
                    carritoService.eliminarProductoDelCarrito(carritoId, productoId);
                    return ResponseEntity.ok("Producto eliminado del carrito exitosamente");
                } else {
                    return ResponseEntity.status(HttpStatus.NOT_FOUND)
                            .body("Producto con la id " + productoId + " no encontrado");
                }
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Producto con la id " + productoId + " no encontrado");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al eliminar el producto de carrito " + e.getMessage());
        }
    }

    @GetMapping("/verProductos/{carritoId}")
    public String verProductosDelCarrito(@PathVariable Long carritoId, Model model) {
        try {
            // Llama al método en CarritoService para obtener la lista de productos del carrito
            List<ProductoDTO> productosDTO = carritoService.listaDeProductos(carritoId);

            // Agrega la lista de productos al modelo para pasarla a la vista
            model.addAttribute("productos", productosDTO);

            // Puedes devolver el nombre de la vista que mostrará la lista de productos
            return productosDTO.toString();
        } catch (CarritoException e) {
            // Manejar excepciones
            model.addAttribute("error", "Error al obtener la lista de productos del carrito.");
            return "error"; // Puedes tener una vista específica para errores
        }
    }}