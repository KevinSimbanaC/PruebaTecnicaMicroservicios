package com.example.customers.service;

import com.example.customers.entity.Cliente;
import com.example.customers.exception.ClienteNotFoundException;
import com.example.customers.repository.ClienteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ClienteServiceTest {

    @Mock
    private ClienteRepository clienteRepository;

    @InjectMocks
    private ClienteService clienteService;

    private Cliente cliente;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        cliente = new Cliente();
        cliente.setId(1L);
        cliente.setNombre("Juan");
        cliente.setContrasenia("1234");
        cliente.setEstado(true);
    }

    @Test
    void testGuardarCliente() {
        when(clienteRepository.save(cliente)).thenReturn(cliente);

        Cliente result = clienteService.guardarCliente(cliente);

        assertNotNull(result);
        assertEquals("Juan", result.getNombre());
        verify(clienteRepository, times(1)).save(cliente);
    }

    @Test
    void testListarClientes() {
        Cliente cliente2 = new Cliente();
        cliente2.setId(2L);
        cliente2.setNombre("Maria");
        cliente2.setContrasenia("5678");
        cliente2.setEstado(false);

        when(clienteRepository.findAll()).thenReturn(Arrays.asList(cliente, cliente2));

        List<Cliente> result = clienteService.listarClientes();

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(clienteRepository, times(1)).findAll();
    }

    @Test
    void testObtenerClientePorId_Encontrado() {
        when(clienteRepository.findById(1L)).thenReturn(Optional.of(cliente));

        Cliente result = clienteService.obtenerClientePorId(1L);

        assertNotNull(result);
        assertEquals("Juan", result.getNombre());
        verify(clienteRepository, times(1)).findById(1L);
    }

    @Test
    void testObtenerClientePorId_NoEncontrado() {
        when(clienteRepository.findById(1L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(ClienteNotFoundException.class, () -> {
            clienteService.obtenerClientePorId(1L);
        });

        assertEquals("Cliente no encontrado con ID: 1", exception.getMessage());
        verify(clienteRepository, times(1)).findById(1L);
    }

    @Test
    void testEliminarCliente() {
        doNothing().when(clienteRepository).deleteById(1L);

        clienteService.eliminarCliente(1L);

        verify(clienteRepository, times(1)).deleteById(1L);
    }

    @Test
    void testActualizarCliente() {
        Cliente clienteActualizado = new Cliente();
        clienteActualizado.setNombre("Juan Updated");
        clienteActualizado.setContrasenia("4321");
        clienteActualizado.setEstado(false);

        when(clienteRepository.findById(1L)).thenReturn(Optional.of(cliente));
        when(clienteRepository.save(any(Cliente.class))).thenReturn(clienteActualizado);

        Cliente result = clienteService.actualizarCliente(1L, clienteActualizado);

        assertNotNull(result);
        assertEquals("Juan Updated", result.getNombre());
        assertEquals("4321", result.getContrasenia());
        assertEquals(false, result.getEstado());
        verify(clienteRepository, times(1)).findById(1L);
        verify(clienteRepository, times(1)).save(cliente);
    }

    @Test
    void testActualizarCliente_NoEncontrado() {
        Cliente clienteActualizado = new Cliente();
        when(clienteRepository.findById(1L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(ClienteNotFoundException.class, () -> {
            clienteService.actualizarCliente(1L, clienteActualizado);
        });

        assertEquals("Cliente no encontrado con ID: 1", exception.getMessage());
        verify(clienteRepository, times(1)).findById(1L);
    }

    @Test
    void testObtenerClientePorClienteId() {
        when(clienteRepository.findById(1L)).thenReturn(Optional.of(cliente));

        Cliente result = clienteService.obtenerClientePorClienteId(1L);

        assertNotNull(result);
        assertEquals("Juan", result.getNombre());
        verify(clienteRepository, times(1)).findById(1L);
    }
}
