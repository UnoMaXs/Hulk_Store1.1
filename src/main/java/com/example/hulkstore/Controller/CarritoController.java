package com.example.hulkstore.Controller;

import com.example.hulkstore.Entity.Carrito;
import com.example.hulkstore.Service.CarritoService;
import org.springframework.beans.factory.annotation.Autowired;
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
    public List<Carrito> verCarrito(@PathVariable Long carritoId){
        return carritoService.verCarrito(carritoId);
    }

    @PostMapping("/agregarProducto/{carritoId}/{productoId}")
    public void agregarProductoAlCarrito(@PathVariable("carritoId") Long carritoId,
                                         @PathVariable("productoId") Long productId) {
        carritoService.agregarProductoCarrito(carritoId, productId);
    }

}
