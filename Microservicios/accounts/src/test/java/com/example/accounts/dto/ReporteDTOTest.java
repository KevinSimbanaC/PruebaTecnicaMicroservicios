package com.example.accounts.dto;

import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.*;

public class ReporteDTOTest {

    @Test
    public void testGettersAndSetters() {
        ReporteDTO reporte = new ReporteDTO();
        LocalDate fecha = LocalDate.of(2025, 2, 19);
        reporte.setFecha(fecha);
        reporte.setCliente("Cliente Test");
        reporte.setNumeroCuenta("1234567890");
        reporte.setTipo("Ahorro");
        reporte.setSaldoInicial(1000.0);
        reporte.setEstado(true);
        reporte.setMovimiento(500.0);
        reporte.setSaldoDisponible(1500.0);

        assertEquals(fecha, reporte.getFecha());
        assertEquals("Cliente Test", reporte.getCliente());
        assertEquals("1234567890", reporte.getNumeroCuenta());
        assertEquals("Ahorro", reporte.getTipo());
        assertEquals(1000.0, reporte.getSaldoInicial());
        assertTrue(reporte.getEstado());
        assertEquals(500.0, reporte.getMovimiento());
        assertEquals(1500.0, reporte.getSaldoDisponible());
    }

    @Test
    public void testEmptyConstructor() {
        ReporteDTO reporte = new ReporteDTO();
        assertNull(reporte.getFecha());
        assertNull(reporte.getCliente());
        assertNull(reporte.getNumeroCuenta());
        assertNull(reporte.getTipo());
        assertNull(reporte.getSaldoInicial());
        assertNull(reporte.getEstado());
        assertNull(reporte.getMovimiento());
        assertNull(reporte.getSaldoDisponible());
    }
}
