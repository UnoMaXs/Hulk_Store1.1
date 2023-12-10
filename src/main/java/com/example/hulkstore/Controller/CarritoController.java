package com.example.hulkstore.Controller;

import com.example.hulkstore.Entity.Carrito;
import com.example.hulkstore.Service.CarritoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/carrito")
public class CarritoController {

    @Autowired
    CarritoService carritoService;

    @GetMapping("/verCarritos")
    public List<Carrito> verCarritos() {
        return carritoService.verCarritos();
    }

    @GetMapping("/verCarrito/{carritoId}")
    public List<Carrito> verCarrito(@PathVariable Long carritoId) {
        return carritoService.verCarritoId(carritoId);
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