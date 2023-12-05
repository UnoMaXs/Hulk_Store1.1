package com.example.hulkstore.Controller;

import com.example.hulkstore.Entity.Admin;
import com.example.hulkstore.Entity.Usuario;
import com.example.hulkstore.Service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    AdminService adminService;

    //ADMIN
    @GetMapping("/admins")//Listar admins
    public List<Admin> getAdmins() {
        return adminService.getAdmins();
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

    //USUARIOS
    @GetMapping("/usuarios")//Listar usuarios
    public List<Usuario> getUsuarios() {
        return adminService.getUsuarios();
    }

    @PutMapping("/updateUsuario/{usuarioId}")//Actualizar usuario
    public void updateUsuario(@PathVariable("usuarioId") Long usuarioId, @RequestBody Usuario usuario) {
        usuario.setUsuarioId(usuarioId);
        adminService.updateUsuario(usuario);
    }

    @DeleteMapping("/deleteUsuario/{usuarioId}")//Borrar usuario
    public void deleteUsuarioById(@PathVariable("usuarioId") Long usuarioId) {
        adminService.deleteUsuarioById(usuarioId);
    }

}
