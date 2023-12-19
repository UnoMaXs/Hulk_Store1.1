package com.example.hulkstore.Service;

import com.example.hulkstore.DTO.CarritoDTO;
import com.example.hulkstore.DTO.ProductoDTO;
import com.example.hulkstore.DTO.UsuarioDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.example.hulkstore.Entity.Carrito;
import com.example.hulkstore.Entity.Usuario;
import com.example.hulkstore.Repository.UsuarioRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private JdbcTemplate jdbcTemplate;
    public void addUsuario(Usuario usuario) {
        try {
            Carrito carrito = new Carrito();
            carrito.setUsuario(usuario);
            usuarioRepository.save(usuario);
        } catch (Exception e) {
            System.err.println("Ocurrió un error al agregar el usuario: " + e.getMessage());
        }
    }

    public List<UsuarioDTO> getUsuarios() {
        try {
            return jdbcTemplate.query(usuarioRepository.sqlAllUser,
                (rs, rowNum) -> {
                    Long usuarioId = rs.getLong("usuario_id");

                    UsuarioDTO usuarioDto = new UsuarioDTO();
                    usuarioDto.setUsuarioId(usuarioId);
                    usuarioDto.setNombre(rs.getString("nombre"));
                    usuarioDto.setCorreo(rs.getString("correo"));
                    usuarioDto.setContrasena(rs.getString("contrasena"));

                    CarritoDTO carrito = new CarritoDTO();
                    carrito.setCarritoId(rs.getLong("carrito_id"));
                    carrito.setCantidadProductos(rs.getInt("total_productos"));
                    carrito.setValorTotal(rs.getDouble("total_valor"));
                    carrito.setUsuario(usuarioDto);

                    // Asegúrate de que la lista de carritos en el usuario esté inicializada
                    usuarioDto.setCarrito(new ArrayList<>());
                    usuarioDto.getCarrito().add(carrito);

                    // Consulta para obtener los productos asociados a este carrito
                    List<ProductoDTO> productos = jdbcTemplate.query(usuarioRepository.sqlProductUser,
                            (rs2, rowNum2) -> {
                                ProductoDTO producto = new ProductoDTO();
                                producto.setProductoId(rs2.getLong("producto_id"));
                                producto.setNombre(rs2.getString("nombre"));
                                producto.setPrecio(rs2.getLong("precio"));
                                producto.setCantidad(rs2.getInt("cantidad"));
                                return producto;
                            }, carrito.getCarritoId());

                    // Asegúrate de que la lista de productos en el carrito esté inicializada
                    carrito.setProductos(new ArrayList<>());
                    carrito.getProductos().addAll(productos);

                    return usuarioDto;
                });
        } catch (Exception e) {
            System.err.println("Ocurrió un error al obtener la lista de usuarios: " + e.getMessage());
            return null;
        }
    }

    public Optional<Usuario> getUsuariosById(Long usuarioId) {
        try {
            return usuarioRepository.findById(usuarioId);
        } catch (Exception e) {
            System.err.println("Ocurrió un error al obtener el usuario por ID: " + e.getMessage());
            return Optional.empty();
        }
    }

    public void updateUsuario(Usuario usuario) {
        try {
            usuarioRepository.save(usuario);
        } catch (Exception e) {
            System.err.println("Ocurrió un error al actualizar el usuario: " + e.getMessage());
        }
    }

    public void deleteUsuarioById(Long id) {
        try {
            usuarioRepository.deleteById(id);
        } catch (Exception e) {
            System.err.println("Ocurrió un error al eliminar el usuario por ID: " + e.getMessage());
        }
    }

}

