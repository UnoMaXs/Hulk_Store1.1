package com.example.hulkstore.Controller;

import com.example.hulkstore.DTO.ProductoDTO;
import com.example.hulkstore.Entity.Producto;
import com.example.hulkstore.Service.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/producto")
public class ProductoController {

    @Autowired
    ProductoService productoService;

    @PostMapping("/addProducto") // Agregar producto
    public void addProducto(@RequestBody ProductoDTO productoDTO) {
        productoService.addProducto(productoDTO);
    }

    @GetMapping("/verProductos") // Listar productos
    public List<ProductoDTO> getProductos() {
        return productoService.getProductos();
    }

    @GetMapping("/verProductos/{productoId}") // Llamar producto por id
    public Optional<ProductoDTO> getProductoById(@PathVariable("productoId") Long productoId) {
       return productoService.getProductoById(productoId);
    }

    @PutMapping("/updateProducto/{productoId}") // Actualizar producto
    public void updateProducto(@PathVariable("productoId") Long productoId, @RequestBody ProductoDTO productoDTO) {
        productoDTO.setProductoId(productoId);
        productoService.updateProducto(productoDTO);
    }

    @DeleteMapping("/deleteProducto/{productoId}") // Borrar producto
    public void deleteProducto(@PathVariable Long productoId) {
        productoService.deleteProducto(productoId);
    }
}

