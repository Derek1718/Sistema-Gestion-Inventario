package com.inventario.service;

import com.inventario.model.Inventario;
import com.inventario.repository.InventarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AlertaService {

    private final InventarioRepository inventarioRepository;

    public List<Inventario> listarAlertasStockBajo() {
        return inventarioRepository.findAll().stream()
                .filter(inv -> inv != null && inv.getProducto() != null && inv.getStockActual() != null)
                .filter(inv -> inv.getStockActual() < inv.getProducto().getStockMinimo())
                .collect(Collectors.toList());
    }
}