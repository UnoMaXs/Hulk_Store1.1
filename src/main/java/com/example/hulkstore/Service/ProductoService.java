package com.example.hulkstore.Service;

import com.example.hulkstore.DTO.AdminDTO;
import com.example.hulkstore.DTO.ProductoDTO;
import com.example.hulkstore.Entity.Admin;
import com.example.hulkstore.Entity.Producto;
import com.example.hulkstore.Repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductoService {

    @Autowired
    private ProductoRepository productoRepository;

    public void addProducto(ProductoDTO productoDTO){
        Producto producto = convertirAEntidad(productoDTO);
        productoRepository.save(producto);
    }

    public List<ProductoDTO> getProductos(){
        List<Producto> productos = productoRepository.findAll();
        return productos.stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    public Optional<ProductoDTO> getProductoById(Long productoId){
        Optional<Producto> optionalProductoDTO = productoRepository.findById(productoId);
        return optionalProductoDTO.map(this::convertirADTO);
    }

    public void deleteProducto(Long productoId){
        Optional<Producto> productoOptional = productoRepository.findById(productoId);
        if(productoOptional.isPresent()){
            Producto producto = productoOptional.get();
            productoRepository.delete(producto);
        } else {
            System.out.println("No se encuentra el producto");
        }
    }

    public void updateProducto(ProductoDTO productoDTO){
        Producto producto = convertirAEntidad(productoDTO);
        productoRepository.save(producto);
    }

    public ProductoDTO convertirADTO(Producto producto){
        ProductoDTO productoDTO = new ProductoDTO();
        productoDTO.setProductoId(producto.getProductoId());
        productoDTO.setNombre(producto.getNombre());
        productoDTO.setCantidad(producto.getCantidad());
        productoDTO.setPrecio(producto.getPrecio());
        return productoDTO;
    }

    public Producto convertirAEntidad(ProductoDTO productoDTO) {
        Producto producto = new Producto();
        producto.setProductoId(productoDTO.getProductoId());
        producto.setNombre(productoDTO.getNombre());
        producto.setCantidad(productoDTO.getCantidad());
        producto.setPrecio(productoDTO.getPrecio());
        return producto;
    }

}
