package com.example.customers.dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CustomerDTOTest {

    @Test
    void testGettersAndSetters() {
        // Arrange
        CustomerDTO customerDTO = new CustomerDTO();
        String clienteId = "12345";
        String nombre = "Juan Pérez";

        // Act
        customerDTO.setClienteId(clienteId);
        customerDTO.setNombre(nombre);

        // Assert
        assertEquals(clienteId, customerDTO.getClienteId(), "El clienteId no coincide");
        assertEquals(nombre, customerDTO.getNombre(), "El nombre no coincide");
    }

    @Test
    void testDefaultConstructor() {
        // Arrange & Act
        CustomerDTO customerDTO = new CustomerDTO();

        // Assert
        assertNotNull(customerDTO, "El objeto CustomerDTO no debería ser nulo");
        assertNull(customerDTO.getClienteId(), "El clienteId debería ser nulo por defecto");
        assertNull(customerDTO.getNombre(), "El nombre debería ser nulo por defecto");
    }

}
