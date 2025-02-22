package com.example.accounts.controller;

import com.example.accounts.dto.ReporteDTO;
import com.example.accounts.service.ReporteService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ReporteControllerTest {

    @InjectMocks
    private ReporteController reporteController;

    @Mock
    private ReporteService reporteService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGenerarReporte() {
        String clienteId = "123";
        LocalDate fechaInicio = LocalDate.of(2024, 1, 1);
        LocalDate fechaFin = LocalDate.of(2024, 1, 31);

        ReporteDTO reporteDTO = new ReporteDTO();
        reporteDTO.setCliente("Juan Perez");
        reporteDTO.setNumeroCuenta("456");
        reporteDTO.setMovimiento(200.0);
        reporteDTO.setSaldoDisponible(800.0);

        List<ReporteDTO> reporteList = Arrays.asList(reporteDTO);
        when(reporteService.generarReporte(clienteId, fechaInicio, fechaFin)).thenReturn(reporteList);

        ResponseEntity<List<ReporteDTO>> response = reporteController.generarReporte(clienteId, fechaInicio, fechaFin);

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(1, response.getBody().size());
        assertEquals("Juan Perez", response.getBody().get(0).getCliente());
    }
}
