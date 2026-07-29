package com.PruebaTecSupermercado.Supermercado.mapper;

import com.PruebaTecSupermercado.Supermercado.dto.DetalleVentaDTO;
import com.PruebaTecSupermercado.Supermercado.dto.ProductoDTO;
import com.PruebaTecSupermercado.Supermercado.dto.SucursalDTO;
import com.PruebaTecSupermercado.Supermercado.dto.VentaDTO;
import com.PruebaTecSupermercado.Supermercado.model.DetalleVenta;
import com.PruebaTecSupermercado.Supermercado.model.Producto;
import com.PruebaTecSupermercado.Supermercado.model.Sucursal;
import com.PruebaTecSupermercado.Supermercado.model.Venta;

import java.util.stream.Collectors;
import java.util.List;


public class Mapper {
    //  Mapeo de producto a productoDTO
    public static ProductoDTO toDTO(Producto p){
        if (p == null) return null;

        // Patron de disenio Builder
        return ProductoDTO.builder()
                .id(p.getId())
                .nombre(p.getNombre())
                .categoria(p.getCategoria())
                .precio(p.getPrecio())
                .build();
    }

    //  Mapeo de venta a ventaDTO
    public static VentaDTO toDTO(Venta venta){
        if (venta == null) return null;

        var detalle = venta.getDetalle().stream().map( det ->
                DetalleVentaDTO.builder()
                        .id(det.getProd().getId())
                        .nombreProd(det.getProd().getNombre())
                        .cantProd(det.getCantProd())
                        .precio(det.getPrecio())
                        .subtotal(det.getPrecio() * det.getCantProd())
                        .build()
        ).collect(Collectors.toList());

        var total = detalle.stream()
                .map(DetalleVentaDTO::getSubtotal)
                .reduce(0.0, Double::sum);

        return VentaDTO.builder()
                .id(venta.getId())
                .fecha(venta.getFecha())
                .idSucursal(venta.getSucursal().getId())
                .estado(venta.getEstado())
                .detalle(detalle)
                .total(total)
                .build();
    }


    //  Mapeo de sucursal a sucursalDTO
    public static SucursalDTO toDTO(Sucursal s){
        if (s == null) return null;

        // Patron de disenio Builder
        return SucursalDTO.builder()
                .id(s.getId())
                .nombre(s.getNombre())
                .direccion(s.getDireccion())
                .build();
    }
}
