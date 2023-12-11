package com.example.hulkstore.Controller;


import com.example.hulkstore.Entity.Admin;
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

    @GetMapping("/admins")//Listar admins
    public List<Admin> getAdmins() {
        return adminService.getAdmins();
    }
    @GetMapping("/verAdmins/{adminId}")//Listar usuarios
    public Optional<Admin> getAdminById(@PathVariable("adminId") Long adminId) {
        return adminService.getAdminById(adminId);
    }

    @PostMapping("/addAdmin")//Agregar admin
    public void addAdmin(@RequestBody Admin admin) {
        adminService.addAdmin(admin);
    }

    @DeleteMapping("/deleteAdmin/{adminId}")//Borrar admin
    public void deleteAdminById(@PathVariable("adminId") Long adminId) {
        adminService.deleteAdminById(adminId);
    }

    @PutMapping("/updateAdmin/{adminId}")//Actualizar admin
    public void updateAdmin(@PathVariable("adminId") Long adminId, @RequestBody Admin admin) {
        admin.setAdminId(adminId);
        adminService.updateAdmin(admin);
    }

}
