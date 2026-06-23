package com.inventario.controller;

import com.inventario.model.Inventario;
import com.inventario.service.InventarioService;
import com.inventario.service.ProductoService;
import com.inventario.service.SucursalService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/inventario")
@RequiredArgsConstructor
public class InventarioController {

    private final InventarioService inventarioService;
    private final ProductoService productoService;
    private final SucursalService sucursalService;

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("inventarios", inventarioService.listarTodos());
        return "inventario/listar";
    }

    @GetMapping("/nuevo")
    public String nuevo(Model model) {
        model.addAttribute("inventario", new Inventario());
        model.addAttribute("productos", productoService.listarTodos());
        model.addAttribute("sucursales", sucursalService.listarTodas());
        return "inventario/formulario";
    }

    @PostMapping("/guardar")
    public String guardar(@Valid @ModelAttribute Inventario inventario,
                          BindingResult result,
                          Model model,
                          RedirectAttributes flash) {
        if (result.hasErrors()) {
            model.addAttribute("productos", productoService.listarTodos());
            model.addAttribute("sucursales", sucursalService.listarTodas());
            return "inventario/formulario";
        }
        inventarioService.guardar(inventario);
        flash.addFlashAttribute("mensaje", "Inventario guardado correctamente");
        return "redirect:/inventario";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Long id, RedirectAttributes flash) {
        inventarioService.eliminar(id);
        flash.addFlashAttribute("mensaje", "Registro eliminado correctamente");
        return "redirect:/inventario";
    }
}