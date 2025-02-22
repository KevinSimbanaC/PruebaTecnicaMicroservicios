package com.example.accounts.service;

import com.example.accounts.dto.ClienteDTO;
import com.example.accounts.dto.ReporteDTO;
import com.example.accounts.entity.Cuenta;
import com.example.accounts.entity.Movimiento;
import com.example.accounts.repository.CuentaRepository;
import com.example.accounts.repository.MovimientoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ReporteServiceTest {

    @Mock
    private CuentaRepository cuentaRepository;

    @Mock
    private MovimientoRepository movimientoRepository;

    @InjectMocks
    @Spy
    private ReporteService reporteService;

    private ClienteDTO mockClienteDTO;
    private Cuenta mockCuenta;
    private Movimiento mockMovimiento;

    @BeforeEach
    void setup() {
        // Cliente simulado
        mockClienteDTO = new ClienteDTO();
        mockClienteDTO.setClienteId("123");
        mockClienteDTO.setNombre("Juan Pérez");

        // Cuenta simulada
        mockCuenta = new Cuenta();
        mockCuenta.setId(1L);
        mockCuenta.setNumeroCuenta("123456");
        mockCuenta.setTipoCuenta("Ahorros");
        mockCuenta.setSaldoInicial(1000.0);
        mockCuenta.setEstado(true);
        mockCuenta.setClienteId("123");

        // Movimiento simulado
        mockMovimiento = new Movimiento();
        mockMovimiento.setId(1L);
        mockMovimiento.setFecha(LocalDate.of(2023, 6, 15));
        mockMovimiento.setTipoMovimiento("Debito");
        mockMovimiento.setValor(200.0);
        mockMovimiento.setSaldo(800.0);
        mockMovimiento.setCuenta(mockCuenta);
    }

    @Test
    void testGenerarReporteConDatos() throws Exception {
        // Datos de entrada
        String clienteId = "123";
        LocalDate fechaInicio = LocalDate.of(2023, 1, 1);
        LocalDate fechaFin = LocalDate.of(2023, 12, 31);

        // Mockear el método obtenerCliente
        doReturn(mockClienteDTO).when(reporteService).obtenerCliente(clienteId);

        // Mockear el repositorio para devolver cuentas asociadas
        when(cuentaRepository.findByClienteId(clienteId)).thenReturn(List.of(mockCuenta));

        // Mockear el repositorio para devolver movimientos asociados a la cuenta
        when(movimientoRepository.findByCuentaIdAndFechaBetween(mockCuenta.getId(), fechaInicio, fechaFin))
                .thenReturn(List.of(mockMovimiento));

        // Ejecutar el método bajo prueba
        List<ReporteDTO> reporte = reporteService.generarReporte(clienteId, fechaInicio, fechaFin);

        // Validar resultados
        assertNotNull(reporte);
        assertEquals(1, reporte.size());

        ReporteDTO reporteDTO = reporte.get(0);
        assertEquals("Juan Pérez", reporteDTO.getCliente());
        assertEquals("123456", reporteDTO.getNumeroCuenta());
        assertEquals("Ahorros", reporteDTO.getTipo());
        assertEquals(1000.0, reporteDTO.getSaldoInicial());
        assertTrue(reporteDTO.getEstado());
        assertEquals(200.0, reporteDTO.getMovimiento());
        assertEquals(800.0, reporteDTO.getSaldoDisponible());

        // Verificar interacciones con los mocks
        verify(cuentaRepository).findByClienteId(clienteId);
        verify(movimientoRepository).findByCuentaIdAndFechaBetween(mockCuenta.getId(), fechaInicio, fechaFin);
    }
    @Test
    void testHandleCustomerResponse_CompletesFuture() {
        // Datos de entrada
        String clienteId = "123";
        ClienteDTO clienteDTO = new ClienteDTO();
        clienteDTO.setClienteId(clienteId);
        clienteDTO.setNombre("Juan Pérez");

        // Crear y agregar un CompletableFuture al mapa
        CompletableFuture<ClienteDTO> future = new CompletableFuture<>();
        reporteService.pendingRequests.put(clienteId, future);

        // Ejecutar el método bajo prueba
        reporteService.handleCustomerResponse(clienteDTO);

        // Validar que el CompletableFuture haya sido completado
        assertTrue(future.isDone(), "El CompletableFuture debería estar completado");
        assertEquals(clienteDTO, future.join(), "El CompletableFuture debería contener el ClienteDTO recibido");

        // Validar que el mapa ya no contenga la solicitud pendiente
        assertFalse(reporteService.pendingRequests.containsKey(clienteId), "El mapa no debería contener la solicitud pendiente");
    }

    @Test
    void testHandleCustomerResponse_NullCustomerDTO() {
        // Crear y agregar un CompletableFuture al mapa
        String clienteId = "123";
        CompletableFuture<ClienteDTO> future = new CompletableFuture<>();
        reporteService.pendingRequests.put(clienteId, future);

        // Ejecutar el método con un ClienteDTO nulo
        reporteService.handleCustomerResponse(null);

        // Validar que el CompletableFuture no haya sido completado
        assertFalse(future.isDone(), "El CompletableFuture no debería estar completado");

        // Validar que el mapa siga conteniendo la solicitud pendiente
        assertTrue(reporteService.pendingRequests.containsKey(clienteId), "El mapa debería seguir conteniendo la solicitud pendiente");
    }

    @Test
    void testHandleCustomerResponse_InvalidCustomerId() {
        // Crear y agregar un CompletableFuture al mapa
        String clienteId = "123";
        CompletableFuture<ClienteDTO> future = new CompletableFuture<>();
        reporteService.pendingRequests.put(clienteId, future);

        // Crear un ClienteDTO con un clienteId diferente
        ClienteDTO clienteDTO = new ClienteDTO();
        clienteDTO.setClienteId("456");
        clienteDTO.setNombre("Otro Cliente");

        // Ejecutar el método bajo prueba
        reporteService.handleCustomerResponse(clienteDTO);

        // Validar que el CompletableFuture no haya sido completado
        assertFalse(future.isDone(), "El CompletableFuture no debería estar completado");

        // Validar que el mapa siga conteniendo la solicitud pendiente
        assertTrue(reporteService.pendingRequests.containsKey(clienteId), "El mapa debería seguir conteniendo la solicitud pendiente");
    }



}
