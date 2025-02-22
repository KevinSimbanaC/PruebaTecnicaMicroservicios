package com.example.customers.entity;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ClienteTest {

    @Test
    public void testGettersAndSetters() {
        // Crear instancia de Cliente
        Cliente cliente = new Cliente();

        // Probar setters
        cliente.setId(1L);
        cliente.setClienteId("CLI123");
        cliente.setContrasenia("password123");
        cliente.setEstado(true);
        cliente.setNombre("Juan Pérez");
        cliente.setGenero("Masculino");
        cliente.setEdad(30);
        cliente.setIdentificacion("123456789");
        cliente.setDireccion("Calle Falsa 123");
        cliente.setTelefono("555-1234");

        // Probar getters
        assertEquals(1L, cliente.getId());
        assertEquals("CLI123", cliente.getClienteId());
        assertEquals("password123", cliente.getContrasenia());
        assertTrue(cliente.getEstado());
        assertEquals("Juan Pérez", cliente.getNombre());
        assertEquals("Masculino", cliente.getGenero());
        assertEquals(30, cliente.getEdad());
        assertEquals("123456789", cliente.getIdentificacion());
        assertEquals("Calle Falsa 123", cliente.getDireccion());
        assertEquals("555-1234", cliente.getTelefono());
    }

    @Test
    public void testConstructor() {
        // Probar constructor vacío
        Cliente cliente = new Cliente("1","1234566",true);
        assertNotNull(cliente);
    }


}
