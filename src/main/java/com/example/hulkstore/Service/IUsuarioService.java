package com.example.hulkstore.Service;

import com.example.hulkstore.Entity.Usuario;

import java.util.Optional;

public interface IUsuarioService {

    public void guardarUsuario(Usuario usuario);
    public Optional<Usuario> buscarPorId(Long Id);
    public void eliminarUsuario(Long Id);
}
