package com.example.customers.controller;

import com.example.customers.entity.Cliente;
import com.example.customers.repository.ClienteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(properties = "spring.profiles.active=test")
@AutoConfigureMockMvc
public class ClienteIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ClienteRepository clienteRepository;

    @BeforeEach
    public void setUp() {
        // Limpiar la base de datos antes de cada prueba
        clienteRepository.deleteAll();

        // Insertar datos de prueba
        Cliente cliente = new Cliente();
        cliente.setClienteId("1");
        cliente.setContraseña("password123");
        cliente.setEstado(true);
        cliente.setNombre("Juan Pérez");
        clienteRepository.save(cliente);
    }

    @Test
    public void testObtenerClientePorId() throws Exception {
        mockMvc.perform(get("/api/clientes/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.clienteId").value("1"))
                .andExpect(jsonPath("$.nombre").value("Juan Pérez"));
    }
}
