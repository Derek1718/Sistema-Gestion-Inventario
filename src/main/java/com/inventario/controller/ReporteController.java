package com.inventario.controller;

import com.inventario.repository.MovimientoRepository;
import com.inventario.repository.ProductoRepository;
import com.inventario.repository.SucursalRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/reportes")
@RequiredArgsConstructor
public class ReporteController {

    private final MovimientoRepository movimientoRepository;
    private final ProductoRepository productoRepository;
    private final SucursalRepository sucursalRepository;

    @GetMapping
    public String mostrarFiltros(Model model) {
        model.addAttribute("productos", productoRepository.findAll());
        model.addAttribute("sucursales", sucursalRepository.findAll());
        return "reportes/filtros";
    }

    @GetMapping("/resultado")
    public String generarReporte(
            @RequestParam(required = false) Long productoId,
            @RequestParam(required = false) Long sucursalId,
            Model model) {

        var movimientos = movimientoRepository.findAll().stream()
                .filter(m -> productoId == null || m.getInventario().getProducto().getId().equals(productoId))
                .filter(m -> sucursalId == null || m.getInventario().getSucursal().getId().equals(sucursalId))
                .toList();

        model.addAttribute("movimientos", movimientos);
        return "reportes/resultado";
    }
}