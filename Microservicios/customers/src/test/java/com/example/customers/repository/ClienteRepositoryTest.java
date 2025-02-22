package com.example.customers.repository;

import com.example.customers.entity.Cliente;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class ClienteRepositoryTest {

    @Autowired
    private ClienteRepository clienteRepository;

    @Test
    void testSaveAndFindById() {
        // Arrange
        Cliente cliente = new Cliente();
        cliente.setClienteId("12345");
        cliente.setNombre("Juan Pérez");
        cliente.setGenero("Masculino");
        cliente.setEdad(30);
        cliente.setIdentificacion("1234567890");
        cliente.setDireccion("Calle Falsa 123");
        cliente.setTelefono("987654321");

        // Act
        Cliente savedCliente = clienteRepository.save(cliente);
        Optional<Cliente> retrievedCliente = clienteRepository.findById(savedCliente.getId());

        // Assert
        assertTrue(retrievedCliente.isPresent(), "El cliente debería estar presente en la base de datos");
        assertEquals(savedCliente.getId(), retrievedCliente.get().getId(), "El ID del cliente no coincide");
        assertEquals("Juan Pérez", retrievedCliente.get().getNombre(), "El nombre del cliente no coincide");
    }

    @Test
    void testFindByClienteId() {
        // Arrange
        Cliente cliente = new Cliente();
        cliente.setClienteId("12345");
        cliente.setNombre("María López");
        cliente.setGenero("Femenino");
        cliente.setEdad(25);
        cliente.setIdentificacion("987654321");
        cliente.setDireccion("Calle Verdadera 456");
        cliente.setTelefono("123456789");

        clienteRepository.save(cliente);

        // Act
        Optional<Cliente> retrievedCliente = clienteRepository.findByClienteId("12345");

        // Assert
        assertTrue(retrievedCliente.isPresent(), "El cliente debería estar presente en la base de datos");
        assertEquals("12345", retrievedCliente.get().getClienteId(), "El clienteId no coincide");
        assertEquals("María López", retrievedCliente.get().getNombre(), "El nombre no coincide");
    }

    @Test
    void testFindByClienteIdNotFound() {
        // Act
        Optional<Cliente> retrievedCliente = clienteRepository.findByClienteId("99999");

        // Assert
        assertFalse(retrievedCliente.isPresent(), "No debería encontrarse ningún cliente con el clienteId proporcionado");
    }
}
