package com.inventario.controller;

import com.inventario.model.Categoria;
import com.inventario.service.CategoriaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/categorias")
@RequiredArgsConstructor
public class CategoriaController {

    private final CategoriaService categoriaService;

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("categorias", categoriaService.listarTodas());
        return "categorias/listar";
    }

    @GetMapping("/nueva")
    public String nuevaCategoria(Model model) {
        model.addAttribute("categoria", new Categoria());
        return "categorias/formulario";
    }

    @PostMapping("/guardar")
    public String guardar(@Valid @ModelAttribute Categoria categoria,
                          BindingResult result,
                          RedirectAttributes flash) {
        if (result.hasErrors()) {
            return "categorias/formulario";
        }
        categoriaService.guardar(categoria);
        flash.addFlashAttribute("mensaje", "Categoría guardada correctamente");
        return "redirect:/categorias";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model model) {
        model.addAttribute("categoria", categoriaService.buscarPorId(id));
        return "categorias/formulario";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Long id, RedirectAttributes flash) {
        categoriaService.eliminar(id);
        flash.addFlashAttribute("mensaje", "Categoría eliminada correctamente");
        return "redirect:/categorias";
    }
}