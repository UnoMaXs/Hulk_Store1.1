package com.example.hulkstore.Controller;

import com.example.hulkstore.Entity.Usuario;
import com.example.hulkstore.Service.IUsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class UsuarioController {

    @Autowired
    private IUsuarioService usuarioService;


    public String crear(Model model) {
        Usuario usuario = new Usuario();
    }
}
