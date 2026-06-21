package com.inventario.service;

import com.inventario.model.Inventario;
import com.inventario.repository.InventarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class InventarioService {
    private final InventarioRepository inventarioRepository;

    public List<Inventario> listarTodos() {
        return inventarioRepository.findAll();
    }

    public Inventario buscarPorId(Long id) {
        return inventarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Registro de inventario no encontrado"));
    }

    public void guardar(Inventario inventario) {
        inventarioRepository.save(inventario);
    }
}