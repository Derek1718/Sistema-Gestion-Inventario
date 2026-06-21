package com.inventario.controller;

import com.inventario.model.Sucursal;
import com.inventario.service.SucursalService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/sucursales")
@RequiredArgsConstructor
public class SucursalController {

    private final SucursalService sucursalService;

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("sucursales", sucursalService.listarTodas());
        return "sucursales/listar";
    }

    @GetMapping("/nueva")
    public String nueva(Model model) {
        model.addAttribute("sucursal", new Sucursal());
        return "sucursales/formulario";
    }

    @PostMapping("/guardar")
    public String guardar(@Valid @ModelAttribute Sucursal sucursal,
                          BindingResult result,
                          RedirectAttributes flash) {
        if (result.hasErrors()) {
            return "sucursales/formulario";
        }
        sucursalService.guardar(sucursal);
        flash.addFlashAttribute("mensaje", "Sucursal guardada correctamente");
        return "redirect:/sucursales";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model model) {
        model.addAttribute("sucursal", sucursalService.buscarPorId(id));
        return "sucursales/formulario";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Long id, RedirectAttributes flash) {
        sucursalService.eliminar(id);
        flash.addFlashAttribute("mensaje", "Sucursal eliminada correctamente");
        return "redirect:/sucursales";
    }
}
