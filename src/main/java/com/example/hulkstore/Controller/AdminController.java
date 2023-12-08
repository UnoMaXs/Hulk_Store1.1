package com.example.hulkstore.Controller;

import com.example.hulkstore.DTO.AdminDTO;
import com.example.hulkstore.Entity.Admin;
import com.example.hulkstore.Entity.Usuario;
import com.example.hulkstore.Service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    AdminService adminService;

    @PostMapping("/addAdmin")//Agregar admin
    public void addAdmin(@RequestBody AdminDTO adminDTO) {
        adminService.addAdmin(adminDTO);
    }

    @GetMapping("/verAdmins")//Listar admins
    public List<AdminDTO> getAdmins() {
        return adminService.getAdmins();
    }

    @GetMapping("/verAdmins/{adminId}")//Llamar admin por id
    public Optional<AdminDTO> getAdminById(@PathVariable("adminId") Long adminId) {
        return adminService.getAdminById(adminId);
    }

    @PutMapping("/updateAdmin/{adminId}")//Actualizar admin
    public void updateAdmin(@PathVariable("adminId") Long adminId, @RequestBody AdminDTO adminDTO) {
        adminDTO.setAdminId(adminId);
        adminService.updateAdmin(adminDTO);
    }

    @DeleteMapping("/deleteAdmin/{adminId}")//Borrar admin
    public void deleteAdminById(@PathVariable("adminId") Long adminId) {
        adminService.deleteAdminById(adminId);
    }
}
