package com.inventario.controller;

import java.io.ByteArrayOutputStream;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.inventario.model.Movimiento;
import com.inventario.repository.MovimientoRepository;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class ExportacionController {

    private final MovimientoRepository movimientoRepository;

    @GetMapping("/movimientos/exportar")
    public ResponseEntity<byte[]> exportarCSV() {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PrintWriter writer = new PrintWriter(out, true, StandardCharsets.UTF_8);

        writer.println("ID,Producto,Sucursal,Tipo,Cantidad,Fecha,Observacion");

        for (Movimiento m : movimientoRepository.findAll()) {
            writer.printf("%d,%s,%s,%s,%d,%s,%s%n",
                    m.getId(),
                    m.getInventario().getProducto().getNombre(),
                    m.getInventario().getSucursal().getNombre(),
                    m.getTipo(),
                    m.getCantidad(),
                    m.getFecha(),
                    m.getObservacion() != null ? m.getObservacion() : "");
        }

        writer.flush();

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=movimientos.csv");

        return ResponseEntity.ok()
                .headers(headers)
                .contentType(MediaType.parseMediaType("text/csv"))
                .body(out.toByteArray());
    }
}
