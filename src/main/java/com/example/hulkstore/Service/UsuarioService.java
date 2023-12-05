package com.example.hulkstore.Service;

import com.example.hulkstore.Entity.Producto;
import com.example.hulkstore.Entity.Usuario;
import com.example.hulkstore.Repository.ProductoRepository;
import com.example.hulkstore.Repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioService{

    @Autowired
    UsuarioRepository usuarioRepository;

    //USUARIO
    public void addUsuario(Usuario usuario){
        usuarioRepository.save(usuario);
    }

}
