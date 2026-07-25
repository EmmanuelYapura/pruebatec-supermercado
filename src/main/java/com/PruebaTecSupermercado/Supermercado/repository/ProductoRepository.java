package com.PruebaTecSupermercado.Supermercado.repository;

import com.PruebaTecSupermercado.Supermercado.model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductoRepository extends JpaRepository<Producto, Long> {
}
