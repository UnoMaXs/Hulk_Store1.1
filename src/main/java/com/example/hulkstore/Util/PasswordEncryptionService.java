package com.example.hulkstore.Util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class PasswordEncryptionService {

    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public String cifrarContrasena(String contrasena) {
        return passwordEncoder.encode(contrasena);
    }

    public boolean validarContrasena(String contrasenaPlana, String contrasenaCifrada) {
        return passwordEncoder.matches(contrasenaPlana, contrasenaCifrada);
    }
}
