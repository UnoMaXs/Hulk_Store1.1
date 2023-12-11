package com.example.hulkstore.Service;

import com.example.hulkstore.Entity.Admin;
import com.example.hulkstore.Repository.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AdminService {

    @Autowired
    private AdminRepository adminRepository;

    public List<Admin> getAdmins() {
        try {
            return adminRepository.findAll();
        } catch (Exception e) {
            System.err.println("Ocurrió un error al obtener la lista de admins: " + e.getMessage());
            return null;
        }
    }

    public Optional<Admin> getAdminById(Long adminId) {
        try {
            return adminRepository.findById(adminId);
        } catch (Exception e) {
            System.err.println("Ocurrió un error al obtener el admin por ID: " + e.getMessage());
            return Optional.empty();
        }
    }

    public void addAdmin(Admin admin) {
        try {
            adminRepository.save(admin);
        } catch (Exception e) {
            System.err.println("Ocurrió un error al agregar el admin: " + e.getMessage());
        }
    }

    public void deleteAdminById(Long id) {
        try {
            adminRepository.deleteById(id);
        } catch (Exception e) {
            System.err.println("Ocurrió un error al eliminar el admin por ID: " + e.getMessage());
        }
    }

    public void updateAdmin(Admin admin) {
        try {
            adminRepository.save(admin);
        } catch (Exception e) {
            System.err.println("Ocurrió un error al actualizar el admin: " + e.getMessage());
        }
    }
}
