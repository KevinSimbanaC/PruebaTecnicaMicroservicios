package com.example.customers.exception;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(GlobalExceptionHandler.class)
class GlobalExceptionHandlerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void testHandleGeneralException() throws Exception {
        // Simular un controlador que lanza una excepción general
        mockMvc.perform(get("/test/error") // Endpoint simulado
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.status").value(500)) // Verificar el estado
                .andExpect(jsonPath("$.message").value("Ha ocurrido un error inesperado")) // Verificar el mensaje
                .andExpect(jsonPath("$.timestamp").exists()); // Verificar que el campo timestamp existe
    }

    @Test
    void testHandleCustomerNotFoundException() {
        // Arrange
        GlobalExceptionHandler globalExceptionHandler = new GlobalExceptionHandler();
        String errorMessage = "Cliente no encontrado";
        ClienteNotFoundException exception = new ClienteNotFoundException(errorMessage);

        // Act
        ResponseEntity<GlobalExceptionHandler.ErrorResponse> response = globalExceptionHandler.handleCustomerNotFoundException(exception);

        // Assert
        assertNotNull(response, "La respuesta no debería ser nula");
        assertEquals(404, response.getStatusCodeValue(), "El código de estado HTTP debería ser 404");
        assertNotNull(response.getBody(), "El cuerpo de la respuesta no debería ser nulo");

    }

    @Test
    void testErrorResponseGettersAndSetters() {
        // Arrange
        GlobalExceptionHandler.ErrorResponse errorResponse = new GlobalExceptionHandler.ErrorResponse(0, null, null);

        int status = 404;
        String message = "Cliente no encontrado";
        LocalDateTime timestamp = LocalDateTime.now();

        // Act
        errorResponse.setStatus(status);
        errorResponse.setMessage(message);
        errorResponse.setTimestamp(timestamp);

        // Assert
        assertEquals(status, errorResponse.getStatus(), "El estado no coincide");
        assertEquals(message, errorResponse.getMessage(), "El mensaje no coincide");
        assertEquals(timestamp, errorResponse.getTimestamp(), "El timestamp no coincide");
    }
}
