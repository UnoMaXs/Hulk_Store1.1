package com.example.hulkstore.Controller;

import com.example.hulkstore.DTO.CarritoDTO;
import com.example.hulkstore.Service.CarritoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/carrito")
public class CarritoController {

    @Autowired
    CarritoService carritoService;

    @GetMapping("/verCarritos")
    public ResponseEntity<List<CarritoDTO>> verCarritos() {
        try{
            List<CarritoDTO> carritoDTO = carritoService.getCarritos();
            return ResponseEntity.ok(carritoDTO);
        } catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Collections.emptyList());
        }

    }

    @GetMapping("/verCarrito/{carritoId}")
    public ResponseEntity<CarritoDTO> verCarrito(@PathVariable Long carritoId) {
        try {
            Optional<CarritoDTO> carritoOptional = carritoService.getCarritoById(carritoId);
            return ResponseEntity.ok(carritoOptional.get());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(null);
        }
    }

    @PostMapping("/agregarProducto/{carritoId}/{productoId}")
    public ResponseEntity<String> agregarProductoAlCarrito(@PathVariable("carritoId") Long carritoId,
                                                           @PathVariable("productoId") Long productoId) {
        try {
            carritoService.agregarProducto(carritoId, productoId);
            return ResponseEntity.ok("Producto agregado al carrito exitosamente");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al agregar el producto al carrito: " + e.getMessage());
        }
    }

    @PutMapping("/eliminarProducto/{carritoId}/{productoId}")
    public ResponseEntity<String> eliminarProductoDeCarrito(@PathVariable("carritoId") Long carritoId,
                                                            @PathVariable("productoId") Long productoId) {
        try {
            carritoService.eliminarProducto(carritoId, productoId);
            return ResponseEntity.ok("Producto eliminado del carrito exitosamente");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al eliminar el producto de carrito");
        }
    }
}