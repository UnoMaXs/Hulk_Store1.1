package com.example.hulkstore.Service;

import com.example.hulkstore.Entity.Admin;
import com.example.hulkstore.Entity.Usuario;

import java.util.List;

public interface IAdminService {

    public List<Usuario> listarTodos();
    public String guardarAdmin(Admin admin);
    public void eliminarAdmin(Admin admin);

}
