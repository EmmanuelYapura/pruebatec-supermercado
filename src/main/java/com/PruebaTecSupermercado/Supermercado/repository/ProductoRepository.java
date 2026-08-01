package com.PruebaTecSupermercado.Supermercado.repository;

import com.PruebaTecSupermercado.Supermercado.model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductoRepository extends JpaRepository<Producto, Long> {

    // metodo para buscar producto
    Optional<Producto> findByNombre(String nombre);
}
