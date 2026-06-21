package com.inventario.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.inventario.model.Producto;
import com.inventario.repository.ProductoRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductoService {

    private final ProductoRepository productoRepository;

    public List<Producto> listarTodos() {
        return productoRepository.findAll();
    }

    public Producto buscarPorId(Long id) {
        return productoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));
    }

    public void guardar(Producto producto) {
        productoRepository.save(producto);
    }

    public void eliminar(Long id) {
        productoRepository.deleteById(id);
    }
    
    public Page<Producto> listarPaginado(Pageable pageable) {
    return productoRepository.findAll(pageable);
}

}