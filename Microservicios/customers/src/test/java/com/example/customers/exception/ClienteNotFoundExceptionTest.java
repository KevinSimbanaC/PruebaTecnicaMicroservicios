package com.example.customers.exception;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ClienteNotFoundExceptionTest {

    @Test
    void testExceptionMessage() {
        // Arrange
        String errorMessage = "Cliente no encontrado";

        // Act
        ClienteNotFoundException exception = new ClienteNotFoundException(errorMessage);

        // Assert
        assertNotNull(exception, "La excepción no debería ser nula");
        assertEquals(errorMessage, exception.getMessage(), "El mensaje de error no coincide");
    }

    @Test
    void testExceptionInheritance() {
        // Act
        ClienteNotFoundException exception = new ClienteNotFoundException("Mensaje de prueba");

        // Assert
        assertTrue(exception instanceof RuntimeException, "La excepción debería ser una instancia de RuntimeException");
    }
}
