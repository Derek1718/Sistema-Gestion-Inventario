package com.inventario.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.inventario.model.Producto;
import com.inventario.service.CategoriaService;
import com.inventario.service.ProductoService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/productos")
@RequiredArgsConstructor
public class ProductoController {

    private final ProductoService productoService;
    private final CategoriaService categoriaService;

    @GetMapping
public String listar(Model model,
                     @RequestParam(defaultValue = "0") int pagina,
                     @RequestParam(defaultValue = "5") int tamano) {
    Pageable pageable = PageRequest.of(pagina, tamano);
        Page<Producto> productosPaginados = productoService.listarPaginado(pageable);

    model.addAttribute("productos", productosPaginados.getContent());
    model.addAttribute("paginaActual", pagina);
    model.addAttribute("totalPaginas", productosPaginados.getTotalPages());
    return "productos/listar";
}


    @GetMapping("/nuevo")
    public String nuevo(Model model) {
        model.addAttribute("producto", new Producto());
        model.addAttribute("categorias", categoriaService.listarTodas());
        return "productos/formulario";
    }

    @PostMapping("/guardar")
    public String guardar(@Valid @ModelAttribute Producto producto,
                          BindingResult result,
                          Model model,
                          RedirectAttributes flash) {
        if (result.hasErrors()) {
            model.addAttribute("categorias", categoriaService.listarTodas());
            return "productos/formulario";
        }
        productoService.guardar(producto);
        flash.addFlashAttribute("mensaje", "Producto guardado correctamente");
        return "redirect:/productos";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model model) {
        model.addAttribute("producto", productoService.buscarPorId(id));
        model.addAttribute("categorias", categoriaService.listarTodas());
        return "productos/formulario";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Long id, RedirectAttributes flash) {
        productoService.eliminar(id);
        flash.addFlashAttribute("mensaje", "Producto eliminado correctamente");
        return "redirect:/productos";
    }
}