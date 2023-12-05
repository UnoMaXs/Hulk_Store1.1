package com.example.hulkstore.Service;

import com.example.hulkstore.Entity.Admin;
import com.example.hulkstore.Entity.Usuario;
import com.example.hulkstore.Repository.AdminRepository;
import com.example.hulkstore.Repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminService{

    @Autowired
    AdminRepository adminRepository;
    @Autowired
    UsuarioRepository usuarioRepository;


    //ADMINISTRADOR
    public List<Admin> getAdmins(){
        return adminRepository.findAll();
    }

    public void addAdmin(Admin admin){
        adminRepository.save(admin);
    }

    public void deleteAdminById(Long id){
        adminRepository.deleteById(id);
    }

    public void updateAdmin(Admin admin){
        adminRepository.save(admin);
    }

    //USUARIOS
    public List<Usuario> getUsuarios() {
        return usuarioRepository.findAll();
    }

    public void updateUsuario(Usuario usuario){
        usuarioRepository.save(usuario);
    }

    public void deleteUsuarioById(Long id){
        usuarioRepository.deleteById(id);
    }




}
