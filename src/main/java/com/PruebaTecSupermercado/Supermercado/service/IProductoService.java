package com.PruebaTecSupermercado.Supermercado.service;

import com.PruebaTecSupermercado.Supermercado.dto.ProductoDTO;

import java.util.List;

public interface IProductoService {
    List<ProductoDTO> traerProductos();
    ProductoDTO crearProducto(ProductoDTO productoDto);
    ProductoDTO actualizarProducto(Long id, ProductoDTO productoDto);
    void eliminarProducto(Long id);
}
