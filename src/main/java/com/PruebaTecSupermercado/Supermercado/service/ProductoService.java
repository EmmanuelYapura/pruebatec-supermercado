package com.PruebaTecSupermercado.Supermercado.service;

import com.PruebaTecSupermercado.Supermercado.dto.ProductoDTO;
import com.PruebaTecSupermercado.Supermercado.exception.NotFoundException;
import com.PruebaTecSupermercado.Supermercado.mapper.Mapper;
import com.PruebaTecSupermercado.Supermercado.model.Producto;
import com.PruebaTecSupermercado.Supermercado.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductoService implements IProductoService {

    @Autowired
    private ProductoRepository repo;

    @Override
    public List<ProductoDTO> traerProductos() {
        return repo.findAll().stream().map(Mapper::toDTO).toList();
    }

    @Override
    public ProductoDTO crearProducto(ProductoDTO productoDto) {
        var prod = Producto.builder()
                .nombre(productoDto.getNombre())
                .categoria(productoDto.getCategoria())
                .precio(productoDto.getPrecio())
                .cantidad(productoDto.getCantidad())
                .build();

        return Mapper.toDTO(repo.save(prod));
    }

    @Override
    public ProductoDTO actualizarProducto(Long id, ProductoDTO productoDto) {
        // verifico si existe el prod
        Producto prod = repo.findById(id)
                .orElseThrow(() -> new NotFoundException("Producto no encontrado"));

        prod.setNombre(productoDto.getNombre());
        prod.setCategoria(productoDto.getCategoria());
        prod.setCantidad(productoDto.getCantidad());
        prod.setPrecio(productoDto.getPrecio());

        return Mapper.toDTO(repo.save(prod));
    }

    @Override
    public void eliminarProducto(Long id) {
        if (!repo.existsById(id)){
            throw new NotFoundException("Producto no encontrado para eliminar");
        }

        repo.deleteById(id);
    }
}
