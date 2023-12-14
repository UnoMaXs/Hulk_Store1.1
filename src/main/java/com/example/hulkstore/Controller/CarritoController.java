package com.example.hulkstore.Controller;

import com.example.hulkstore.DTO.CarritoDTO;

import com.example.hulkstore.Service.CarritoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/carrito")
public class CarritoController {

    @Autowired
    private CarritoService carritoService;

    @GetMapping("/verCarritos")
    public ResponseEntity<List<CarritoDTO>> verCarritos() {
        try {
            List<CarritoDTO> carritoDTO = carritoService.getCarritos();
            return ResponseEntity.ok(carritoDTO);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Collections.emptyList());
        }
    }

    @GetMapping("/verCarrito/{carritoId}")
    public ResponseEntity<Object> verCarrito(@PathVariable Long carritoId) {
        try {
            Optional<CarritoDTO> optionalCarrito = carritoService.getCarritoById(carritoId);
            if (optionalCarrito.isPresent()) {
                Optional<CarritoDTO> carritoOptional = carritoService.getCarritoById(carritoId);
                return ResponseEntity.ok(carritoOptional.get());
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("No existe carrito con id + carritoId");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(null);
        }
    }

}
