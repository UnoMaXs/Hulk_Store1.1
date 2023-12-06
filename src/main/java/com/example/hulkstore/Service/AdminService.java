package com.example.hulkstore.Service;

import com.example.hulkstore.Entity.Admin;
import com.example.hulkstore.Entity.Usuario;
import com.example.hulkstore.Repository.AdminRepository;
import com.example.hulkstore.Repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AdminService{

    @Autowired
    private AdminRepository adminRepository;

    public List<Admin> getAdmins(){
        return adminRepository.findAll();
    }

    public Optional<Admin> getAdminById(Long adminId) {
        return adminRepository.findById(adminId);
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

}
