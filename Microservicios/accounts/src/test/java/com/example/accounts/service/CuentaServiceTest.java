package com.example.accounts.service;

import com.example.accounts.entity.Cuenta;
import com.example.accounts.repository.CuentaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CuentaServiceTest {

    @Mock
    private CuentaRepository cuentaRepository;

    @InjectMocks
    private CuentaService cuentaService;

    private Cuenta mockCuenta;

    @BeforeEach
    void setup() {
        // Configurar una cuenta simulada
        mockCuenta = new Cuenta();
        mockCuenta.setId(1L);
        mockCuenta.setClienteId("123");
        mockCuenta.setNumeroCuenta("456789");
        mockCuenta.setTipoCuenta("Ahorros");
        mockCuenta.setSaldoInicial(1000.0);
        mockCuenta.setEstado(true);
    }

    @Test
    void testCrearCuenta() {
        // Mockear el comportamiento del repositorio
        when(cuentaRepository.save(mockCuenta)).thenReturn(mockCuenta);

        // Ejecutar el método bajo prueba
        Cuenta cuentaCreada = cuentaService.crearCuenta(mockCuenta);

        // Validar el resultado
        assertNotNull(cuentaCreada);
        assertEquals(mockCuenta.getId(), cuentaCreada.getId());
        assertEquals(mockCuenta.getNumeroCuenta(), cuentaCreada.getNumeroCuenta());

        // Verificar que se haya llamado al repositorio
        verify(cuentaRepository).save(mockCuenta);
    }

    @Test
    void testListarCuentas() {
        // Mockear el comportamiento del repositorio
        when(cuentaRepository.findAll()).thenReturn(List.of(mockCuenta));

        // Ejecutar el método bajo prueba
        List<Cuenta> cuentas = cuentaService.listarCuentas();

        // Validar el resultado
        assertNotNull(cuentas);
        assertEquals(1, cuentas.size());
        assertEquals(mockCuenta.getId(), cuentas.get(0).getId());

        // Verificar que se haya llamado al repositorio
        verify(cuentaRepository).findAll();
    }

    @Test
    void testObtenerCuentaPorNumeroDeCuenta() {
        // Mockear el comportamiento del repositorio
        when(cuentaRepository.findByNumeroCuenta(mockCuenta.getNumeroCuenta())).thenReturn(Optional.of(mockCuenta));

        // Ejecutar el método bajo prueba
        Cuenta cuenta = cuentaService.obtenerCuentaPorNumeroDeCuenta(mockCuenta.getNumeroCuenta());

        // Validar el resultado
        assertNotNull(cuenta);
        assertEquals(mockCuenta.getId(), cuenta.getId());

        // Verificar que se haya llamado al repositorio
        verify(cuentaRepository).findByNumeroCuenta(mockCuenta.getNumeroCuenta());
    }

    @Test
    void testObtenerCuentaPorNumeroDeCuenta_CuentaNoEncontrada() {
        // Mockear el comportamiento del repositorio para devolver un `Optional` vacío
        when(cuentaRepository.findByNumeroCuenta("inexistente")).thenReturn(Optional.empty());

        // Ejecutar el método bajo prueba y validar que lanza una excepción
        Exception exception = assertThrows(RuntimeException.class, () ->
                cuentaService.obtenerCuentaPorNumeroDeCuenta("inexistente"));

        assertEquals("Cuenta no encontrada", exception.getMessage());

        // Verificar que se haya llamado al repositorio
        verify(cuentaRepository).findByNumeroCuenta("inexistente");
    }

    @Test
    void testEliminarCuenta() {
        // Ejecutar el método bajo prueba
        cuentaService.eliminarCuenta(mockCuenta.getId());

        // Verificar que se haya llamado al método `deleteById` del repositorio
        verify(cuentaRepository).deleteById(mockCuenta.getId());
    }

    @Test
    void testActualizarCuenta() {
        // Configurar una cuenta actualizada
        Cuenta cuentaActualizada = new Cuenta();
        cuentaActualizada.setClienteId("456");
        cuentaActualizada.setNumeroCuenta("987654");
        cuentaActualizada.setTipoCuenta("Corriente");
        cuentaActualizada.setSaldoInicial(2000.0);
        cuentaActualizada.setEstado(false);

        // Mockear el comportamiento del repositorio
        when(cuentaRepository.findById(mockCuenta.getId())).thenReturn(Optional.of(mockCuenta));
        when(cuentaRepository.save(mockCuenta)).thenReturn(mockCuenta);

        // Ejecutar el método bajo prueba
        Cuenta cuentaResultado = cuentaService.actualizarCuenta(mockCuenta.getId(), cuentaActualizada);

        // Validar el resultado
        assertNotNull(cuentaResultado);
        assertEquals(cuentaActualizada.getClienteId(), cuentaResultado.getClienteId());
        assertEquals(cuentaActualizada.getNumeroCuenta(), cuentaResultado.getNumeroCuenta());
        assertEquals(cuentaActualizada.getTipoCuenta(), cuentaResultado.getTipoCuenta());
        assertEquals(cuentaActualizada.getSaldoInicial(), cuentaResultado.getSaldoInicial());
        assertEquals(cuentaActualizada.getEstado(), cuentaResultado.getEstado());

        // Verificar que se haya llamado al repositorio
        verify(cuentaRepository).findById(mockCuenta.getId());
        verify(cuentaRepository).save(mockCuenta);
    }

    @Test
    void testActualizarCuenta_CuentaNoEncontrada() {
        // Mockear el comportamiento del repositorio para devolver un `Optional` vacío
        when(cuentaRepository.findById(999L)).thenReturn(Optional.empty());

        // Ejecutar el método bajo prueba y validar que lanza una excepción
        Exception exception = assertThrows(RuntimeException.class, () ->
                cuentaService.actualizarCuenta(999L, mockCuenta));

        assertEquals("Cuenta no encontrada", exception.getMessage());

        // Verificar que se haya llamado al repositorio
        verify(cuentaRepository).findById(999L);
    }

    @Test
    void testListarCuentasPorClienteId() {
        // Mockear el comportamiento del repositorio
        when(cuentaRepository.findByClienteId("123")).thenReturn(List.of(mockCuenta));

        // Ejecutar el método bajo prueba
        List<Cuenta> cuentas = cuentaService.listarCuentasPorClienteId("123");

        // Validar el resultado
        assertNotNull(cuentas);
        assertEquals(1, cuentas.size());
        assertEquals(mockCuenta.getClienteId(), cuentas.get(0).getClienteId());

        // Verificar que se haya llamado al repositorio
        verify(cuentaRepository).findByClienteId("123");
    }
}
