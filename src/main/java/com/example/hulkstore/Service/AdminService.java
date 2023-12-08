package com.example.hulkstore.Service;

import com.example.hulkstore.DTO.AdminDTO;
import com.example.hulkstore.Entity.Admin;
import com.example.hulkstore.Repository.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AdminService{

    @Autowired
    private AdminRepository adminRepository;

    public void addAdmin(AdminDTO admindDTO){
        Admin admin = convertirAEntidad(admindDTO);
        adminRepository.save(admin);
    }

    public List<AdminDTO> getAdmins(){
        List<Admin> admins = adminRepository.findAll();
        return admins.stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    public Optional<AdminDTO> getAdminById(Long adminId) {
        Optional<Admin> optionalAdminDTO = adminRepository.findById(adminId);
        return optionalAdminDTO.map(this::convertirADTO);
    }

    public void deleteAdminById(Long adminId){
        adminRepository.deleteById(adminId);
    }

    public void updateAdmin(AdminDTO adminDTO){
        Admin admin = convertirAEntidad(adminDTO);
        adminRepository.save(admin);
    }

    private AdminDTO convertirADTO(Admin admin){
        AdminDTO adminDTO = new AdminDTO();
        adminDTO.setAdminId(admin.getAdminId());
        adminDTO.setNombre(admin.getNombre());
        adminDTO.setCorreo(admin.getCorreo());
        adminDTO.setContrasena(admin.getContrasena());
        return adminDTO;
    }

    private Admin convertirAEntidad(AdminDTO adminDTO) {
        Admin admin = new Admin();
        admin.setAdminId(adminDTO.getAdminId());
        admin.setNombre(adminDTO.getNombre());
        admin.setCorreo(adminDTO.getCorreo());
        admin.setContrasena(adminDTO.getContrasena());
        return admin;
    }

}
