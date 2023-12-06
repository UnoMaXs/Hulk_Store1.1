package com.example.hulkstore.Service;

import com.example.hulkstore.Entity.Carrito;
import com.example.hulkstore.Entity.Usuario;
import com.example.hulkstore.Repository.ProductoRepository;
import com.example.hulkstore.Repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService{

    @Autowired
    private UsuarioRepository usuarioRepository;

    //USUARIO
    public void addUsuario(Usuario usuario){
        Carrito carrito = new Carrito();
        carrito.setUsuario(usuario);
        usuario.setCarrito(carrito);
        usuarioRepository.save(usuario);
    }

    public List<Usuario> getUsuarios() {
        return usuarioRepository.findAll();
    }

    public Optional<Usuario> getUsuariosById(Long usuarioId) {
        return usuarioRepository.findById(usuarioId);
    }


    public void updateUsuario(Usuario usuario){
        usuarioRepository.save(usuario);
    }

    public void deleteUsuarioById(Long id){
        usuarioRepository.deleteById(id);
    }



}
