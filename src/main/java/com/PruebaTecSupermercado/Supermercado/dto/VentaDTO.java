package com.PruebaTecSupermercado.Supermercado.dto;

import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class VentaDTO {
    // datos de la venta
    private Long id;
    private LocalDate fecha;
    private String estado;
    private Double total;

    // datos de la sucursal
    private Long idSucursal;

    // lista de detalles
    private List<DetalleVentaDTO> detalle;
}
