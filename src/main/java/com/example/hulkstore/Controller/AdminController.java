package com.example.hulkstore.Controller;

import com.example.hulkstore.DTO.AdminDTO;
import com.example.hulkstore.Entity.Admin;
import com.example.hulkstore.Service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    AdminService adminService;

    @GetMapping("/verAdmins") // Listar admins
    public ResponseEntity<List<AdminDTO>> getAdmins() {
        try {
            List<AdminDTO> admins = adminService.getAdmins();
            return ResponseEntity.ok(admins);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Collections.emptyList());
        }
    }

    @GetMapping("/verAdmins/{adminId}")//Listar usuarios
    public Optional<AdminDTO> getAdminById(@PathVariable("adminId") Long adminId) {
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
