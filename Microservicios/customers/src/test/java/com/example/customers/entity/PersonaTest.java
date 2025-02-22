package com.example.customers.entity;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PersonaTest {

    @Test
    void testGettersAndSetters() {
        // Arrange
        Persona persona = new Persona();
        Long id = 1L;
        String nombre = "Juan Pérez";
        String genero = "Masculino";
        Integer edad = 30;
        String identificacion = "1234567890";
        String direccion = "Calle Falsa 123";
        String telefono = "987654321";

        // Act
        persona.setId(id);
        persona.setNombre(nombre);
        persona.setGenero(genero);
        persona.setEdad(edad);
        persona.setIdentificacion(identificacion);
        persona.setDireccion(direccion);
        persona.setTelefono(telefono);

        // Assert
        assertEquals(id, persona.getId(), "El ID no coincide");
        assertEquals(nombre, persona.getNombre(), "El nombre no coincide");
        assertEquals(genero, persona.getGenero(), "El género no coincide");
        assertEquals(edad, persona.getEdad(), "La edad no coincide");
        assertEquals(identificacion, persona.getIdentificacion(), "La identificación no coincide");
        assertEquals(direccion, persona.getDireccion(), "La dirección no coincide");
        assertEquals(telefono, persona.getTelefono(), "El teléfono no coincide");
    }

    @Test
    void testDefaultConstructor() {
        // Arrange & Act
        Persona persona = new Persona();

        // Assert
        assertNotNull(persona, "El objeto Persona no debería ser nulo");
        assertNull(persona.getId(), "El ID debería ser nulo por defecto");
        assertNull(persona.getNombre(), "El nombre debería ser nulo por defecto");
        assertNull(persona.getGenero(), "El género debería ser nulo por defecto");
        assertNull(persona.getEdad(), "La edad debería ser nula por defecto");
        assertNull(persona.getIdentificacion(), "La identificación debería ser nula por defecto");
        assertNull(persona.getDireccion(), "La dirección debería ser nula por defecto");
        assertNull(persona.getTelefono(), "El teléfono debería ser nulo por defecto");
    }


}
