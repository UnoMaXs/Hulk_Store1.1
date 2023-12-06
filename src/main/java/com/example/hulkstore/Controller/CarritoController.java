package com.example.hulkstore.Controller;

import com.example.hulkstore.Service.CarritoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/carrito")
public class CarritoController {

    @Autowired
    CarritoService carritoService;

    @PostMapping("/asociarCarritoAUsuario/{carritoId}/{usuarioId}")
    public void asociarCarritoAUsuario(@PathVariable("carritoId") Long carritoId,
                                       @PathVariable("usuarioId") Long usuarioId) {
        carritoService.asociarCarritoAUsuario(carritoId, usuarioId);
    }

    @PostMapping("/agregarProducto/{carritoId}/{productoId}")
    public void agregarProductoAlCarrito(@PathVariable("carritoId") Long carritoId,
                                         @PathVariable("productoId") Long productId) {
        carritoService.agregarProductoCarrito(carritoId, productId);
    }

    @DeleteMapping("/eliminarProducto/{carritoId}/{productoId}")
    public void eliminarProductoDelCarrito(@PathVariable("carritoId") Long carritoId,
                                           @PathVariable("productoId") Long productId) {
        carritoService.eliminarProductoCarrito(carritoId, productId);
    }
}
