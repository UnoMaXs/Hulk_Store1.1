package com.example.hulkstore.Controller;

import com.example.hulkstore.DTO.UsuarioDTO;
import com.example.hulkstore.Entity.Usuario;
import com.example.hulkstore.Service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/usuario")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/verUsuarios")
    public ResponseEntity<List<UsuarioDTO>> getUsuarios() {
        try {
            List<UsuarioDTO> usuarioDTO = usuarioService.getUsuarios();
            return ResponseEntity.ok(usuarioDTO);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Collections.emptyList());
        }
    }
    @GetMapping("/usuario/{usuarioId}")//Listar usuarios
    public ResponseEntity<Object> getUsuariosById(@PathVariable("usuarioId") Long usuarioId) {
        try {
            Optional<Object> usuarioOptional = usuarioService.getUsuariosById(usuarioId);
            return ResponseEntity.ok(usuarioOptional.get());
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(null);
        }
    }

    @PostMapping("/addUsuario")
    public ResponseEntity<String> saveUsuario(@RequestBody Usuario usuario) {
        try {
            usuarioService.addUsuario(usuario);
            return ResponseEntity.ok("Admin añadido correctamente");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al agregar Administrador");
        }

    }

    @PutMapping("/updateUsuario/{usuarioId}")//Actualizar usuario
    public ResponseEntity<String> updateUsuario(@PathVariable("usuarioId") Long usuarioId, @RequestBody Usuario usuario) {
        try {
            usuario.setUsuarioId(usuarioId);
            usuarioService.updateUsuario(usuario);
            return ResponseEntity.ok("Usuario actualizado correctamente");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al actualizar el usuario");
        }
    }

    @DeleteMapping("/deleteUsuario/{usuarioId}")//Borrar usuario
    public ResponseEntity<String> deleteUsuarioById(@PathVariable("usuarioId") Long usuarioId) {
        try {
            usuarioService.deleteUsuarioById(usuarioId);
            return ResponseEntity.ok("Usuario eliminado correctamente");

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al eliminar usuario");
        }
    }

}
