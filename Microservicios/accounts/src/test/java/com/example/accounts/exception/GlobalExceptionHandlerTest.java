package com.example.accounts.exception;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GlobalExceptionHandlerTest {

    private final GlobalExceptionHandler handler = new GlobalExceptionHandler();

    @Test
    public void testHandleCuentaNotFoundException() {
        CuentaNotFoundException ex = new CuentaNotFoundException("Cuenta no encontrada");
        ResponseEntity<GlobalExceptionHandler.ErrorResponse> response = handler.handleCuentaNotFoundException(ex);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Cuenta no encontrada", response.getBody().getMessage());
        assertEquals(HttpStatus.NOT_FOUND.value(), response.getBody().getStatus());
        assertEquals(LocalDateTime.now().getDayOfMonth(), response.getBody().getTimestamp().getDayOfMonth());
    }

    @Test
    public void testHandleSaldoInsuficienteException() {
        SaldoInsuficienteException ex = new SaldoInsuficienteException("Saldo insuficiente");
        ResponseEntity<GlobalExceptionHandler.ErrorResponse> response = handler.handleSaldoInsuficienteException(ex);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Saldo insuficiente", response.getBody().getMessage());
        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getBody().getStatus());
        assertEquals(LocalDateTime.now().getDayOfMonth(), response.getBody().getTimestamp().getDayOfMonth());
    }

    @Test
    public void testHandleMovimientoNotFoundException() {
        MovimientoNotFoundException ex = new MovimientoNotFoundException("Movimiento no encontrado");
        ResponseEntity<GlobalExceptionHandler.ErrorResponse> response = handler.handleMovimientoNotFoundException(ex);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Movimiento no encontrado", response.getBody().getMessage());
        assertEquals(HttpStatus.NOT_FOUND.value(), response.getBody().getStatus());
        assertEquals(LocalDateTime.now().getDayOfMonth(), response.getBody().getTimestamp().getDayOfMonth());
    }

    @Test
    public void testHandleGeneralException() {
        Exception ex = new Exception("Error general");
        ResponseEntity<GlobalExceptionHandler.ErrorResponse> response = handler.handleGeneralException(ex);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("Ha ocurrido un error inesperado", response.getBody().getMessage());
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), response.getBody().getStatus());
        assertEquals(LocalDateTime.now().getDayOfMonth(), response.getBody().getTimestamp().getDayOfMonth());
    }
}
