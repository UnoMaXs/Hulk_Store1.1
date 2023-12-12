package com.example.hulkstore.Service;

import com.example.hulkstore.DTO.UsuarioDTO;
import com.example.hulkstore.Entity.Carrito;
import com.example.hulkstore.Entity.Usuario;
import com.example.hulkstore.Repository.UsuarioRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

@Service
public class UsuarioService {

    Logger logger = Logger.getLogger(getClass().getName());

    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private ModelMapper modelMapper;
    UsuarioDTO usuarioDTO;

    public void addUsuario(Usuario usuario) {
        try {
            Carrito carrito = new Carrito();
            carrito.setUsuario(usuario);
            usuario.setCarrito(carrito);
            usuarioRepository.save(usuario);
            usuarioDTO = modelMapper.map(usuario, UsuarioDTO.class);
            logger.info("Usuario agregado correctamente");
        } catch (Exception e) {
            logger.info("Ocurrió un error al agregar el usuario: " + e.getMessage());
        }
    }

    public List<UsuarioDTO> getUsuarios() {
        try {
            List<Usuario> usuarios = usuarioRepository.findAll();
            List<UsuarioDTO>usuarioDTO = new ArrayList<>();
            for (Usuario usuario : usuarios){
                usuarioDTO.add(modelMapper.map(usuario, UsuarioDTO.class));
            }
            return usuarioDTO;
        } catch (Exception e) {
            logger.info("Ocurrió un error al obtener la lista de usuarios: " + e.getMessage());
            return null;
        }
    }

    public Optional<Object> getUsuariosById(Long usuarioId) {
        try {
            Optional<Usuario> optionalUsuario = usuarioRepository.findById(usuarioId);
            if (optionalUsuario.isPresent()) {
                Usuario usuario = optionalUsuario.get();
                return Optional.of(modelMapper.map(usuario, UsuarioDTO.class));
            } else {
                return Optional.empty();
            }
        } catch (Exception e) {
            logger.info("Ocurrió un error al obtener el usuario por ID: " + e.getMessage());
            return Optional.empty();
        }
    }

    public void updateUsuario(Usuario usuario) {
        try {
            usuarioRepository.save(usuario);
            usuarioDTO = modelMapper.map(usuario, UsuarioDTO.class);
            logger.info("Usuario actualizado correctamente");
        } catch (Exception e) {
            logger.info("Ocurrió un error al actualizar el usuario: " + e.getMessage());
        }
    }

    public void deleteUsuarioById(Long UsuarioId) {
        try {
            Optional<Usuario> optionalUsuario = usuarioRepository.findById(UsuarioId);
            if (optionalUsuario.isPresent()){
                usuarioRepository.deleteById(UsuarioId);
                usuarioDTO = modelMapper.map(optionalUsuario, UsuarioDTO.class);
                logger.info("Usuario eliminado correctamente");
            }
        } catch (Exception e) {
            logger.info("Ocurrió un error al eliminar el usuario por ID: " + e.getMessage());
        }

    }
}