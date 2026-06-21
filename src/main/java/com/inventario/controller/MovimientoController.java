package com.inventario.controller;

import com.inventario.model.Movimiento;
import com.inventario.service.InventarioService;
import com.inventario.service.MovimientoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/movimientos")
@RequiredArgsConstructor
public class MovimientoController {
    private final MovimientoService movimientoService;
    private final InventarioService inventarioService;

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("movimientos", movimientoService.listarTodos());
        return "movimientos/listar";
    }

    @GetMapping("/nuevo")
    public String nuevo(Model model) {
        model.addAttribute("movimiento", new Movimiento());
        model.addAttribute("inventarios", inventarioService.listarTodos());
        model.addAttribute("tipos", Movimiento.TipoMovimiento.values());
        return "movimientos/formulario";
    }

    @PostMapping("/guardar")
    public String guardar(@Valid @ModelAttribute Movimiento movimiento,
            BindingResult result,
            Model model,
            RedirectAttributes flash) {
        if (result.hasErrors()) {
            model.addAttribute("inventarios", inventarioService.listarTodos());
            model.addAttribute("tipos", Movimiento.TipoMovimiento.values());
            return "movimientos/formulario";
        }

        // VALIDACIÓN EXTRA: Si el inventario o su ID vienen nulos desde la vista
        if (movimiento.getInventario() == null || movimiento.getInventario().getId() == null) {
            flash.addFlashAttribute("error", "Debe seleccionar un producto del inventario válido.");
            return "redirect:/movimientos/nuevo";
        }

        try {
            movimientoService.registrarMovimiento(movimiento);
            flash.addFlashAttribute("mensaje", "Movimiento registrado correctamente");
        } catch (RuntimeException e) {
            flash.addFlashAttribute("error", e.getMessage());
            // Si el stock es insuficiente, te regresa al formulario para corregirlo
            return "redirect:/movimientos/nuevo";
        }
        return "redirect:/movimientos";
    }
}