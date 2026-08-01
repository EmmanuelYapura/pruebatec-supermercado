package com.PruebaTecSupermercado.Supermercado.service;

import com.PruebaTecSupermercado.Supermercado.dto.SucursalDTO;

import java.util.List;

public interface ISucursalService {
    List<SucursalDTO> traerSucursales();
    SucursalDTO crearSucursal(SucursalDTO sucursalDto);
    SucursalDTO actualizarSucursal(Long id, SucursalDTO sucursalDto);
    void eliminarSucursal(Long id);
}
