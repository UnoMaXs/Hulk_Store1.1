package com.example.hulkstore.Service;

import com.example.hulkstore.Entity.Carrito;
import com.example.hulkstore.Entity.Producto;
import com.example.hulkstore.Entity.Usuario;
import com.example.hulkstore.Repository.CarritoRepository;
import com.example.hulkstore.Repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CarritoService {

    @Autowired
    private CarritoRepository carritoRepository;
    @Autowired
    private ProductoService productoService;
    @Autowired
    private UsuarioRepository usuarioRepository;

    public void agregarProductoCarrito(Long carritoId, Long productoId){
        Optional<Carrito> optionalCarrito = carritoRepository.findById(carritoId);
        if (optionalCarrito.isPresent()) {
            Carrito carrito = optionalCarrito.get();
            Optional<Producto> optionalProducto = productoService.obtenerProductoPorId(productoId);

            if (optionalProducto.isPresent()) {
                Producto producto = optionalProducto.get();
                carrito.getProductos().add(producto);
                totalCarrito(carrito);
            } else {
                System.out.println("El producto con ID " + productoId + " no se encuentra");
            }
        }
    }

   public void eliminarProductoCarrito(Long carritoId, Long productoId){
       Optional<Carrito> optionalCarrito = carritoRepository.findById(carritoId);
       if(optionalCarrito.isPresent()){
           Carrito carrito = optionalCarrito.get();
           carrito.getProductos().removeIf(producto -> producto.getProductoId().equals(productoId));
           totalCarrito(carrito);
       }

   }

    private void totalCarrito(Carrito carrito) {
       double valorTotal = 0.0;
       for(Producto producto : carrito.getProductos()){
           valorTotal += producto.getPrecio();
       }
       carrito.setCantidadProductos(carrito.getProductos().size());
       carrito.setValorTotal(valorTotal);
       carritoRepository.save(carrito);
    }

    public void asociarCarritoAUsuario(Long carritoId, Long usuarioId) {
        Optional<Carrito> optionalCarrito = carritoRepository.findById(carritoId);
        Optional<Usuario> optionalUsuario = usuarioRepository.findById(usuarioId);

        if (optionalCarrito.isPresent() && optionalUsuario.isPresent()) {
            Carrito carrito = optionalCarrito.get();
            Usuario usuario = optionalUsuario.get();

            carrito.setUsuario(usuario);
            carritoRepository.save(carrito);
        } else {
            System.out.println("No se encontró el carrito o el usuario");
        }
    }
}
