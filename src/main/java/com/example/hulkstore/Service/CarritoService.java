package com.example.hulkstore.Service;

import com.example.hulkstore.DTO.CarritoDTO;
import com.example.hulkstore.DTO.ProductoDTO;
import com.example.hulkstore.Entity.Carrito;
import com.example.hulkstore.Entity.Producto;
import com.example.hulkstore.Exceptions.CarritoException;
import com.example.hulkstore.Repository.CarritoRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

@Service
public class CarritoService {

    Logger logger = Logger.getLogger(getClass().getName());

    @Autowired
    private CarritoRepository carritoRepository;
    @Autowired
    private ProductoService productoService;
    @Autowired
    private ModelMapper modelMapper;


    public List<CarritoDTO> getCarritos() {
        try {
            List<Carrito> carritos = carritoRepository.findAll();
            List<CarritoDTO> carritoDTO = new ArrayList<>();
            for (Carrito carrito : carritos) {
                carritoDTO.add(modelMapper.map(carrito, CarritoDTO.class));
            }
            return carritoDTO;
        } catch (Exception e) {
            logger.info("Ocurrió un error al obtener la lista de carritos: " + e.getMessage());
            throw new CarritoException("Ocurrió un error al obtener la lista de carritos");
        }
    }

    public Optional<CarritoDTO> getCarritoById(Long carritoId) {
        try {
            Optional<Carrito> optionalCarrito = carritoRepository.findById(carritoId);
            if (optionalCarrito.isPresent()) {
                Carrito carrito = optionalCarrito.get();
                return Optional.of(modelMapper.map(carrito, CarritoDTO.class));
            } else {
                logger.info("Carrito no encontrado en servicio");
                return Optional.empty();
            }
        } catch (Exception e) {
            logger.info("Ocurrió un error al obtener el carrito por ID: " + e.getMessage());
            throw new CarritoException("Ocurrió un error al obtener el carrito");
        }
    }

}
