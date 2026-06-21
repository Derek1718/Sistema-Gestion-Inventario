package com.inventario.controller;

import com.inventario.service.AlertaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/alertas")
@RequiredArgsConstructor
public class AlertaController {

    private final AlertaService alertaService;

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("alertas", alertaService.listarAlertasStockBajo());
        return "alertas/listar";
    }
}
