package com.example.hulkstore.Controller;

import com.example.hulkstore.DTO.UsuarioDTO;
import com.example.hulkstore.Entity.Usuario;
import com.example.hulkstore.Service.ProductoService;
import com.example.hulkstore.Service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/usuario")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping("/addUsuario")
    public void saveUsuario(@RequestBody UsuarioDTO usuarioDTO) {
        usuarioService.addUsuario(usuarioDTO);
    }

    @GetMapping("/verUsuarios")
    public List<UsuarioDTO> getUsuarios() {
        return usuarioService.getUsuarios();
    }

    @GetMapping("/usuario/{usuarioId}")//Listar usuarios
    public Optional<UsuarioDTO> getUsuariosById(@PathVariable("usuarioId") Long usuarioId) {
        return usuarioService.getUsuariosById(usuarioId);
    }

    @PutMapping("/updateUsuario/{usuarioId}")//Actualizar usuario
    public void updateUsuario(@PathVariable("usuarioId") Long usuarioId, @RequestBody UsuarioDTO usuarioDTO) {
        usuarioDTO.setUsuarioId(usuarioId);
        usuarioService.updateUsuario(usuarioDTO);
    }

    @DeleteMapping("/deleteUsuario/{usuarioId}")//Borrar usuario
    public void deleteUsuarioById(@PathVariable("usuarioId") Long usuarioId) {
        usuarioService.deleteUsuarioById(usuarioId);
    }

}
