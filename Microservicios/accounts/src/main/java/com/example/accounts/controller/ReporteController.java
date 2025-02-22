package com.example.accounts.controller;

import com.example.accounts.dto.ReporteDTO;
import com.example.accounts.service.ReporteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

/**
 * Controlador REST para generar reportes de movimientos de cuentas.
 * Proporciona un endpoint para generar reportes basados en un cliente y un rango de fechas.
 */
@RestController
@RequestMapping("/api/reportes") // Ruta base para los endpoints relacionados con reportes
public class ReporteController {

    @Autowired
    private ReporteService reporteService;

    /**
     * Genera un reporte de movimientos de cuentas basado en un cliente y un rango de fechas.
     *
     * @param clienteId   El ID del cliente para el cual se genera el reporte.
     * @param fechaInicio La fecha de inicio del rango (formato ISO: yyyy-MM-dd).
     * @param fechaFin    La fecha de fin del rango (formato ISO: yyyy-MM-dd).
     * @return Una lista de objetos ReporteDTO que contienen la información del reporte,
     *         envuelta en un ResponseEntity con código HTTP 200.
     */
    @GetMapping
    public ResponseEntity<List<ReporteDTO>> generarReporte(
            @RequestParam String clienteId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaInicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaFin) {
        // Llama al servicio para generar el reporte basado en el cliente y el rango de fechas
        List<ReporteDTO> reporte = reporteService.generarReporte(clienteId, fechaInicio, fechaFin);
        return ResponseEntity.ok(reporte);
    }
}
