package com.PruebaTecSupermercado.Supermercado.service;

import com.PruebaTecSupermercado.Supermercado.dto.DetalleVentaDTO;
import com.PruebaTecSupermercado.Supermercado.dto.VentaDTO;
import com.PruebaTecSupermercado.Supermercado.exception.NotFoundException;
import com.PruebaTecSupermercado.Supermercado.mapper.Mapper;
import com.PruebaTecSupermercado.Supermercado.model.DetalleVenta;
import com.PruebaTecSupermercado.Supermercado.model.Producto;
import com.PruebaTecSupermercado.Supermercado.model.Sucursal;
import com.PruebaTecSupermercado.Supermercado.model.Venta;
import com.PruebaTecSupermercado.Supermercado.repository.ProductoRepository;
import com.PruebaTecSupermercado.Supermercado.repository.SucursalRepository;
import com.PruebaTecSupermercado.Supermercado.repository.VentaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class VentaService implements  IVentaService{

    @Autowired
    private VentaRepository ventaRepo;
    @Autowired
    private ProductoRepository productoRepo;
    @Autowired
    private SucursalRepository sucursalRepo;

    @Override
    public List<VentaDTO> traerVentas() {
        List<Venta> ventas = ventaRepo.findAll();
        List<VentaDTO> ventasDto = new ArrayList<>();

        // Convierte cada venta a su dto
        VentaDTO dto;
        for (Venta v : ventas){
            dto = Mapper.toDTO(v);
            ventasDto.add(dto);
        }

        return ventasDto;
    }

    @Override
    public VentaDTO crearVenta(VentaDTO ventaDto) {
        if(ventaDto == null) throw new RuntimeException("VentaDto es null");
        if(ventaDto.getIdSucursal() == null) throw new RuntimeException("Debe indicar la sucursal");
        if(ventaDto.getDetalle() == null || ventaDto.getDetalle().isEmpty() ) throw new RuntimeException("Debe incluir al menos un producto");

        // buscar sucursal
        Sucursal suc = sucursalRepo.findById(ventaDto.getIdSucursal()).orElse(null);

        if (suc == null){
            throw new NotFoundException("Sucursal no encontrada");
        }

        // crear la venta
        Venta vent = new Venta();
        vent.setFecha(ventaDto.getFecha());
        vent.setEstado(ventaDto.getEstado());
        vent.setSucursal(suc);
        vent.setTotal(ventaDto.getTotal());

        // la lista de detalles
        List<DetalleVenta> detalles = new ArrayList<>();

        for (DetalleVentaDTO detDTO : ventaDto.getDetalle()){
            Producto p = productoRepo.findByNombre(detDTO.getNombreProd()).orElse(null);
            if(p == null){
                throw new RuntimeException("Producto no encontrado" + detDTO.getNombreProd());
            }

            DetalleVenta detalleVent = new DetalleVenta();
                detalleVent.setProd(p);
                detalleVent.setPrecio(detDTO.getPrecio());
                detalleVent.setCantProd(detDTO.getCantProd());
                detalleVent.setVenta(vent);

                detalles.add(detalleVent);
        }

        // setear lista detalle venta
        vent.setDetalle(detalles);

        // guardar en la db
        ventaRepo.save(vent);

        // mapeo para mostrar dto o poner void para no mostrar nada
        VentaDTO ventaSalida = Mapper.toDTO(vent);

        return ventaSalida;
    }

    @Override
    public VentaDTO actualizarVenta(Long id, VentaDTO ventaDto) {
        Venta v = ventaRepo.findById(id).orElse(null);

        if (v == null) {
            throw new NotFoundException("Venta no encontrada");
        }

        if (ventaDto.getFecha() != null) {
            v.setFecha(ventaDto.getFecha());
        }

        if (ventaDto.getEstado() != null) {
            v.setEstado(ventaDto.getEstado());
        }

        if (ventaDto.getTotal() != null) {
            v.setTotal(ventaDto.getTotal());
        }

        if (ventaDto.getIdSucursal() != null) {
            Sucursal suc = sucursalRepo.findById(ventaDto.getIdSucursal()).orElse(null);
            if (suc == null) throw new NotFoundException("Sucursal no encontrada");
            v.setSucursal(suc);
        }

        ventaRepo.save(v);

        VentaDTO ventaSalida = Mapper.toDTO(v);

        return ventaSalida;
    }

    @Override
    public void eliminarVenta(Long id) {
        Venta v = ventaRepo.findById(id).orElse(null);
        if (v == null) {
            throw new RuntimeException("Venta no encontrada");
        }
        ventaRepo.delete(v);
    }
}
