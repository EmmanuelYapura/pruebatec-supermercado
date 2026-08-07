package com.PruebaTecSupermercado.Supermercado.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class DetalleVenta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //venta
    @ManyToOne
    private  Venta venta;

    //producto
    @ManyToOne
    private  Producto prod;
    private Integer cantProd;
    private Double precio;
}
