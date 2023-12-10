package com.example.hulkstore.Service;

import com.example.hulkstore.DTO.UsuarioDTO;
import com.example.hulkstore.Entity.Carrito;
import com.example.hulkstore.Entity.Usuario;
import com.example.hulkstore.Repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UsuarioService{

    @Autowired
    private UsuarioRepository usuarioRepository;

    public void addUsuario(UsuarioDTO usuarioDTO){
        Usuario usuario = convertirAEntidad(usuarioDTO);
        Carrito carrito = new Carrito();
        carrito.setUsuario(usuario);
        usuario.setCarrito(carrito);
        usuarioRepository.save(usuario);
    }

    public List<UsuarioDTO> getUsuarios() {
        List<Usuario> usuarios = usuarioRepository.findAll();
        return usuarios.stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    public Optional<UsuarioDTO> getUsuariosById(Long usuarioId) {
        Optional<Usuario> optionalUsuario = usuarioRepository.findById(usuarioId);
        return optionalUsuario.map(this::convertirADTO);
    }

    public void deleteUsuarioById(Long id){
        usuarioRepository.deleteById(id);
    }

    public void updateUsuario(UsuarioDTO usuarioDTO){
        Usuario usuario = convertirAEntidad(usuarioDTO);
        usuarioRepository.save(usuario);
    }

    private UsuarioDTO convertirADTO(Usuario usuario) {
        UsuarioDTO usuarioDTO = new UsuarioDTO();
        usuarioDTO.setUsuarioId(usuario.getUsuarioId());
        usuarioDTO.setNombre(usuario.getNombre());
        usuarioDTO.setCorreo(usuario.getCorreo());
        usuarioDTO.setContrasena(usuario.getContrasena());
        usuarioDTO.setCarrito(usuario.getCarrito());
        return usuarioDTO;
    }

    private Usuario convertirAEntidad(UsuarioDTO usuarioDTO) {
        Usuario usuario = new Usuario();
        usuario.setUsuarioId(usuarioDTO.getUsuarioId());
        usuario.setNombre(usuarioDTO.getNombre());
        usuario.setCorreo(usuarioDTO.getCorreo());
        usuario.setContrasena(usuarioDTO.getContrasena());
        usuario.setCarrito(usuarioDTO.getCarrito());
        return usuario;
    }

}
