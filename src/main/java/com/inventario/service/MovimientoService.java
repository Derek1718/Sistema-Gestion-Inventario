package com.inventario.service;

import com.inventario.model.Inventario;
import com.inventario.model.Movimiento;
import com.inventario.repository.InventarioRepository;
import com.inventario.repository.MovimientoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MovimientoService {
    private final MovimientoRepository movimientoRepository;
    private final InventarioRepository inventarioRepository;

    public List<Movimiento> listarTodos() {
        return movimientoRepository.findAll();
    }

    @Transactional
    public void registrarMovimiento(Movimiento movimiento) {
        Inventario inventario = inventarioRepository.findById(movimiento.getInventario().getId())
                .orElseThrow(() -> new RuntimeException("Inventario no encontrado"));

        int stockActual = inventario.getStockActual();

        switch (movimiento.getTipo()) {
            case ENTRADA:
                inventario.setStockActual(stockActual + movimiento.getCantidad());
                break;
            case SALIDA:
                if (stockActual < movimiento.getCantidad()) {
                    throw new RuntimeException("Stock insuficiente para realizar la salida");
                }
                inventario.setStockActual(stockActual - movimiento.getCantidad());
                break;
            case AJUSTE:
                inventario.setStockActual(movimiento.getCantidad());
                break;
        }

        inventarioRepository.save(inventario);
        movimientoRepository.save(movimiento);
    }
}