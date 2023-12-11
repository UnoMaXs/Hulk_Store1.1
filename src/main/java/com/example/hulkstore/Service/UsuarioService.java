package com.example.hulkstore.Service;

import com.example.hulkstore.Entity.Carrito;
import com.example.hulkstore.Entity.Usuario;
import com.example.hulkstore.Repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    public void addUsuario(Usuario usuario) {
        try {
            Carrito carrito = new Carrito();
            carrito.setUsuario(usuario);
            usuario.setCarrito(carrito);
            usuarioRepository.save(usuario);
        } catch (Exception e) {
            System.err.println("Ocurrió un error al agregar el usuario: " + e.getMessage());
        }
    }

    public List<Usuario> getUsuarios() {
        try {
            return usuarioRepository.findAll();
        } catch (Exception e) {
            System.err.println("Ocurrió un error al obtener la lista de usuarios: " + e.getMessage());
            return null;
        }
    }

    public Optional<Usuario> getUsuariosById(Long usuarioId) {
        try {
            return usuarioRepository.findById(usuarioId);
        } catch (Exception e) {
            System.err.println("Ocurrió un error al obtener el usuario por ID: " + e.getMessage());
            return Optional.empty();
        }
    }

    public void updateUsuario(Usuario usuario) {
        try {
            usuarioRepository.save(usuario);
        } catch (Exception e) {
            System.err.println("Ocurrió un error al actualizar el usuario: " + e.getMessage());
        }
    }

    public void deleteUsuarioById(Long id) {
        try {
            usuarioRepository.deleteById(id);
        } catch (Exception e) {
            System.err.println("Ocurrió un error al eliminar el usuario por ID: " + e.getMessage());
        }
    }
}

