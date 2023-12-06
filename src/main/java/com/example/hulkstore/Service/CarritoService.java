package com.example.hulkstore.Service;

import com.example.hulkstore.Entity.Carrito;
import com.example.hulkstore.Entity.Producto;
import com.example.hulkstore.Repository.CarritoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CarritoService {

    @Autowired
    private CarritoRepository carritoRepository;
    @Autowired
    private ProductoService productoService;

    public List<Carrito> verCarrito(Long carritoId) {
        Optional<Carrito> optionalCarrito = carritoRepository.findById(carritoId);
        if (optionalCarrito.isPresent()) {
            List<Carrito> listaCarrito = new ArrayList<>();
            listaCarrito.add(optionalCarrito.get());
            return listaCarrito;
        } else {
            System.out.println("Carrito no encontrado");
            return new ArrayList<>();
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


}
