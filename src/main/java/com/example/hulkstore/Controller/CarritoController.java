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

    @PostMapping("/agregarProducto")
    public ResponseEntity<String> agregarProductoAlCarrito(@RequestBody Carrito carritoDto) {
        try {
            carritoService.agregarProducto(carritoDto);
            return ResponseEntity.ok("Producto agregado al carrito exitosamente");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al agregar el producto al carrito: " + e.getMessage());
        }
    }

    @PutMapping("/actualizarProducto/{carritoId}")
    public ResponseEntity<String> actualizarProductoEnCarrito(@PathVariable Long carritoId, @RequestBody Carrito carritoDto) {
        try {
            carritoService.actualizarProducto(carritoId, carritoDto);
            return ResponseEntity.ok("Producto actualizado en el carrito exitosamente");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al actualizar el producto en el carrito: " + e.getMessage());
        }
    }

    @DeleteMapping("/eliminarProducto/{carritoId}")
    public ResponseEntity<String> eliminarProductoDeCarrito(@PathVariable Long carritoId) {
        try {
            carritoService.eliminarProducto(carritoId);
            return ResponseEntity.ok("Producto eliminado del carrito exitosamente");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al eliminar el producto de carrito");
        }
    }
}