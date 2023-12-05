package com.example.hulkstore.Controller;

import com.example.hulkstore.Entity.Producto;
import com.example.hulkstore.Entity.Usuario;
import com.example.hulkstore.Service.ProductoService;
import com.example.hulkstore.Service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/usuario")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;
    @Autowired
    private ProductoService productoService;

    @PostMapping("/add")
    public void saveUsuario(@RequestBody Usuario usuario) {
        usuarioService.addUsuario(usuario);
    }

    @GetMapping("/productos")//Listar productos
    public List<Producto> getProductos() {
        return productoService.getProductos();
    }

}
