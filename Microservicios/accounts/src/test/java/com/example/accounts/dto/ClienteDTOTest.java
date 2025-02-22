package com.example.accounts.dto;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ClienteDTOTest {

    @Test
    public void testGettersAndSetters() {
        ClienteDTO cliente = new ClienteDTO();
        cliente.setClienteId("123");
        cliente.setNombre("John Doe");

        assertEquals("123", cliente.getClienteId());
        assertEquals("John Doe", cliente.getNombre());
    }

    @Test
    public void testEmptyConstructor() {
        ClienteDTO cliente = new ClienteDTO();
        assertNull(cliente.getClienteId());
        assertNull(cliente.getNombre());
    }
}
