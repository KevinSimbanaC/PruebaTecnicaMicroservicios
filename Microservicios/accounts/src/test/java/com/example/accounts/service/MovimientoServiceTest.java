package com.example.accounts.service;

import com.example.accounts.entity.Cuenta;
import com.example.accounts.entity.Movimiento;
import com.example.accounts.exception.CuentaNotFoundException;
import com.example.accounts.exception.MovimientoNotFoundException;
import com.example.accounts.exception.SaldoInsuficienteException;
import com.example.accounts.repository.CuentaRepository;
import com.example.accounts.repository.MovimientoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MovimientoServiceTest {

    @Mock
    private CuentaRepository cuentaRepository;

    @Mock
    private MovimientoRepository movimientoRepository;

    @InjectMocks
    private MovimientoService movimientoService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void registrarMovimiento_SaldoSuficiente_DeberiaRegistrarMovimiento() {
        // Arrange
        String cuentaId = "12345";
        Cuenta cuenta = new Cuenta();
        cuenta.setNumeroCuenta(cuentaId);
        cuenta.setSaldoInicial(500.0);

        Movimiento nuevoMovimiento = new Movimiento();
        nuevoMovimiento.setValor(100.0);

        when(cuentaRepository.findByNumeroCuenta(cuentaId)).thenReturn(Optional.of(cuenta));
        when(movimientoRepository.findTopByCuentaOrderByFechaDesc(cuenta)).thenReturn(null); // Sin movimientos previos
        when(movimientoRepository.save(any(Movimiento.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        Movimiento resultado = movimientoService.registrarMovimiento(cuentaId, nuevoMovimiento);

        // Assert
        assertNotNull(resultado);
        assertEquals(600.0, resultado.getSaldo());
        verify(cuentaRepository, times(1)).findByNumeroCuenta(cuentaId);
        verify(movimientoRepository, times(1)).save(nuevoMovimiento);
    }

    @Test
    void registrarMovimiento_CuentaNoEncontrada_DeberiaLanzarCuentaNotFoundException() {
        // Arrange
        String cuentaId = "12345";
        Movimiento nuevoMovimiento = new Movimiento();

        when(cuentaRepository.findByNumeroCuenta(cuentaId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(CuentaNotFoundException.class, () -> movimientoService.registrarMovimiento(cuentaId, nuevoMovimiento));
        verify(cuentaRepository, times(1)).findByNumeroCuenta(cuentaId);
        verifyNoInteractions(movimientoRepository);
    }

    @Test
    void registrarMovimiento_SaldoInsuficiente_DeberiaLanzarSaldoInsuficienteException() {
        // Arrange
        String cuentaId = "12345";
        Cuenta cuenta = new Cuenta();
        cuenta.setNumeroCuenta(cuentaId);
        cuenta.setSaldoInicial(100.0);

        Movimiento nuevoMovimiento = new Movimiento();
        nuevoMovimiento.setValor(-200.0); // Movimiento que genera saldo negativo

        when(cuentaRepository.findByNumeroCuenta(cuentaId)).thenReturn(Optional.of(cuenta));
        when(movimientoRepository.findTopByCuentaOrderByFechaDesc(cuenta)).thenReturn(null); // Sin movimientos previos

        // Act & Assert
        assertThrows(SaldoInsuficienteException.class, () -> movimientoService.registrarMovimiento(cuentaId, nuevoMovimiento));
        verify(cuentaRepository, times(1)).findByNumeroCuenta(cuentaId);
    }

    @Test
    void eliminarMovimiento_DeberiaEliminarMovimiento() {
        // Arrange
        Long movimientoId = 1L;

        doNothing().when(movimientoRepository).deleteById(movimientoId);

        // Act
        movimientoService.eliminarMovimiento(movimientoId);

        // Assert
        verify(movimientoRepository, times(1)).deleteById(movimientoId);
    }

    @Test
    void listarMovimiento_DeberiaDevolverListaDeMovimientos() {
        // Arrange
        Movimiento movimiento1 = new Movimiento();
        Movimiento movimiento2 = new Movimiento();
        List<Movimiento> movimientos = List.of(movimiento1, movimiento2);

        when(movimientoRepository.findAll()).thenReturn(movimientos);

        // Act
        List<Movimiento> resultado = movimientoService.listarMovimiento();

        // Assert
        assertNotNull(resultado);
        assertEquals(2, resultado.size());
        verify(movimientoRepository, times(1)).findAll();
    }

    @Test
    void actualizarMovimiento_MovimientoExistente_DeberiaActualizarMovimiento() {
        // Arrange
        Long movimientoId = 1L;
        Movimiento movimientoExistente = new Movimiento();
        movimientoExistente.setId(movimientoId);
        movimientoExistente.setSaldo(200.0);

        Movimiento movimientoActualizado = new Movimiento();
        movimientoActualizado.setFecha(LocalDate.from(LocalDateTime.now()));
        movimientoActualizado.setTipoMovimiento("DEBITO");
        movimientoActualizado.setValor(100.0);
        movimientoActualizado.setSaldo(300.0);

        when(movimientoRepository.findById(movimientoId)).thenReturn(Optional.of(movimientoExistente));
        when(movimientoRepository.save(any(Movimiento.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        Movimiento resultado = movimientoService.actualizarMovimiento(movimientoId, movimientoActualizado);

        // Assert
        assertNotNull(resultado);
        assertEquals(300.0, resultado.getSaldo());
        assertEquals("DEBITO", resultado.getTipoMovimiento());
        verify(movimientoRepository, times(1)).findById(movimientoId);
        verify(movimientoRepository, times(1)).save(movimientoExistente);
    }

    @Test
    void actualizarMovimiento_MovimientoNoExistente_DeberiaLanzarMovimientoNotFoundException() {
        // Arrange
        Long movimientoId = 1L;
        Movimiento movimientoActualizado = new Movimiento();

        when(movimientoRepository.findById(movimientoId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(MovimientoNotFoundException.class, () -> movimientoService.actualizarMovimiento(movimientoId, movimientoActualizado));
        verify(movimientoRepository, times(1)).findById(movimientoId);
        verify(movimientoRepository, never()).save(any(Movimiento.class));
    }
}
