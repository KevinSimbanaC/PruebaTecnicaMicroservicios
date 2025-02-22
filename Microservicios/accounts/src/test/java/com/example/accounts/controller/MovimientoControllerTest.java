package com.example.accounts.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.example.accounts.entity.Movimiento;
import com.example.accounts.service.MovimientoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

class MovimientoControllerTest {

    @InjectMocks
    private MovimientoController movimientoController;

    @Mock
    private MovimientoService movimientoService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testRegistrarMovimiento() {
        Movimiento movimiento = new Movimiento();
        when(movimientoService.registrarMovimiento(eq("123"), any(Movimiento.class))).thenReturn(movimiento);
        ResponseEntity<Movimiento> response = movimientoController.registrarMovimiento("123", movimiento);
        assertNotNull(response.getBody());
        assertEquals(movimiento, response.getBody());
    }

    @Test
    void testListarMovimientos() {
        List<Movimiento> movimientos = Arrays.asList(new Movimiento(), new Movimiento());
        when(movimientoService.listarMovimiento()).thenReturn(movimientos);
        ResponseEntity<List<Movimiento>> response = movimientoController.listarMovimientos();
        assertNotNull(response.getBody());
        assertEquals(2, response.getBody().size());
    }

    @Test
    void testActualizarMovimiento() {
        Movimiento movimiento = new Movimiento();
        when(movimientoService.actualizarMovimiento(eq(1L), any(Movimiento.class))).thenReturn(movimiento);
        ResponseEntity<Movimiento> response = movimientoController.actualizarMovimiento(1L, movimiento);
        assertNotNull(response.getBody());
        assertEquals(movimiento, response.getBody());
    }

    @Test
    void testEliminarMovimiento() {
        doNothing().when(movimientoService).eliminarMovimiento(1L);
        ResponseEntity<Void> response = movimientoController.eliminarCliente(1L);
        assertEquals(204, response.getStatusCodeValue());
    }
}
