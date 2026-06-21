package com.inventario.service;

import com.inventario.model.Sucursal;
import com.inventario.repository.SucursalRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SucursalService {

    private final SucursalRepository sucursalRepository;

    public List<Sucursal> listarTodas() {
        return sucursalRepository.findAll();
    }

    public Sucursal buscarPorId(Long id) {
        return sucursalRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Sucursal no encontrada"));
    }

    public void guardar(Sucursal sucursal) {
        sucursalRepository.save(sucursal);
    }

    public void eliminar(Long id) {
        sucursalRepository.deleteById(id);
    }
}

