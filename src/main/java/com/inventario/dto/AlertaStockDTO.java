package com.inventario.dto;

import lombok.Data;

@Data
public class AlertaStockDTO {

    private String producto;
    private Integer stockActual;
    private Integer stockMinimo;
    private String mensaje;

    public AlertaStockDTO(String producto, Integer stockActual, Integer stockMinimo) {
        this.producto = producto;
        this.stockActual = stockActual;
        this.stockMinimo = stockMinimo;
        this.mensaje = "Stock bajo: se necesita reponer producto";
    }
}