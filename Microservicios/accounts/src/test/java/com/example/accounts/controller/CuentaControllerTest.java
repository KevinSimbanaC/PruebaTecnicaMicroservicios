package com.example.accounts.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.example.accounts.entity.Cuenta;
import com.example.accounts.service.CuentaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

class CuentaControllerTest {

    @InjectMocks
    private CuentaController cuentaController;

    @Mock
    private CuentaService cuentaService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCrearCuenta() {
        Cuenta cuenta = new Cuenta();
        when(cuentaService.crearCuenta(any(Cuenta.class))).thenReturn(cuenta);
        ResponseEntity<Cuenta> response = cuentaController.crearCuenta(cuenta);
        assertNotNull(response.getBody());
        assertEquals(cuenta, response.getBody());
    }

    @Test
    void testListarCuentas() {
        List<Cuenta> cuentas = Arrays.asList(new Cuenta(), new Cuenta());
        when(cuentaService.listarCuentas()).thenReturn(cuentas);
        ResponseEntity<List<Cuenta>> response = cuentaController.listarCuentas();
        assertNotNull(response.getBody());
        assertEquals(2, response.getBody().size());
    }

    @Test
    void testListarCuentasPorCliente() {
        List<Cuenta> cuentas = Arrays.asList(new Cuenta());
        when(cuentaService.listarCuentasPorClienteId("123")).thenReturn(cuentas);
        ResponseEntity<List<Cuenta>> response = cuentaController.listarCuentasPorCliente("123");
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());
    }

    @Test
    void testObtenerCuentaPorNumero() {
        Cuenta cuenta = new Cuenta();
        when(cuentaService.obtenerCuentaPorNumeroDeCuenta("456"))
                .thenReturn(cuenta);
        ResponseEntity<Cuenta> response = cuentaController.obtenerCuentaPorNumero("456");
        assertNotNull(response.getBody());
        assertEquals(cuenta, response.getBody());
    }

    @Test
    void testActualizarCuenta() {
        Cuenta cuenta = new Cuenta();
        when(cuentaService.actualizarCuenta(eq(1L), any(Cuenta.class))).thenReturn(cuenta);
        ResponseEntity<Cuenta> response = cuentaController.actualizarCuenta(1L, cuenta);
        assertNotNull(response.getBody());
        assertEquals(cuenta, response.getBody());
    }

    @Test
    void testEliminarCuenta() {
        doNothing().when(cuentaService).eliminarCuenta(1L);
        ResponseEntity<Void> response = cuentaController.eliminarCuenta(1L);
        assertEquals(204, response.getStatusCodeValue());
    }
}
