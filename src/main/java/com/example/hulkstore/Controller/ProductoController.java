package com.example.hulkstore.Controller;

import com.example.hulkstore.Entity.Producto;
import com.example.hulkstore.Service.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/producto")
public class ProductoController {

    @Autowired
    ProductoService productoService;

    @GetMapping("/productos")//Listar productos
    public List<Producto> getProductos() {
        return productoService.getProductos();
    }

    @PostMapping//Agregar producto
    public void addProduct(@RequestBody Producto producto) {
        productoService.agregarProducto(producto);
    }

    @PutMapping("/updateProducto/{productoId}")//Actualizar producto
    public void updateProducto(@PathVariable("productoId")Long productoId,@RequestBody Producto producto) {
        producto.setProductoId(productoId);
        productoService.actualizarProducto(producto);
    }

    @DeleteMapping("/deleteProducto/{productoId}")//Borrar producto
    public void deleteProducto(@PathVariable Producto productoId) {
        productoService.borrarProducto(productoId);
    }
}
