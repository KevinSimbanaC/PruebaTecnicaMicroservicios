package com.example.customers.controller;

import com.example.customers.entity.Cliente;
import com.example.customers.repository.ClienteRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(properties = "spring.profiles.active=test") // Cargar perfil de prueba
@AutoConfigureMockMvc
public class ClienteIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setUp() {
        // Limpiar la base de datos antes de cada prueba
        clienteRepository.deleteAll();

    }

    @Test
    public void testCrearCliente() throws Exception {
        Cliente nuevoCliente = new Cliente();
        nuevoCliente.setClienteId("1");
        nuevoCliente.setNombre("Juan Pérez");
        nuevoCliente.setContrasenia("password123");
        nuevoCliente.setEstado(true);

        mockMvc.perform(post("/api/clientes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(nuevoCliente))) // Convertir objeto a JSON
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.clienteId").value("1"))
                .andExpect(jsonPath("$.nombre").value("Juan Pérez"));

        // Verificar que el cliente se guardó en la base de datos
        assert clienteRepository.findByClienteId("1").isPresent();
    }

    @Test
    public void testListarClientes() throws Exception {
        // Crear y guardar clientes en la base de datos
        Cliente cliente1 = new Cliente();
        cliente1.setClienteId("1");
        cliente1.setNombre("Juan Pérez");
        cliente1.setContrasenia("password123");
        cliente1.setEstado(true);
        clienteRepository.save(cliente1);

        Cliente cliente2 = new Cliente();
        cliente2.setClienteId("2");
        cliente2.setNombre("María López");
        cliente2.setContrasenia("password456");
        cliente2.setEstado(false);
        clienteRepository.save(cliente2);

        mockMvc.perform(get("/api/clientes")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2)) // Verificar que hay dos clientes
                .andExpect(jsonPath("$[0].clienteId").value("1"))
                .andExpect(jsonPath("$[1].clienteId").value("2"));
    }

    @Test
    public void testObtenerClientePorId() throws Exception {
        // Preparar cliente en la base de datos
        Cliente cliente = new Cliente();
        cliente.setClienteId("4"); // Este es el clienteId, pero no el ID autogenerado de la tabla
        cliente.setNombre("Juan Pérez2");
        cliente.setContrasenia("password123");
        cliente.setEstado(true);
        cliente.setDireccion("Calle 123");
        cliente.setEdad(30);
        cliente.setGenero("M");
        cliente.setIdentificacion("12345678");
        cliente.setTelefono("555-1234");

        // Guardar el cliente y obtener el ID generado automáticamente
        Cliente clienteGuardado = clienteRepository.save(cliente);
        Long idGenerado = clienteGuardado.getId(); // Capturar el ID generado automáticamente

        // Realizar la solicitud GET con el ID generado
        mockMvc.perform(get("/api/clientes/" + idGenerado) // Usar el ID generado automáticamente
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.clienteId").value("4")) // Validar clienteId
                .andExpect(jsonPath("$.nombre").value("Juan Pérez2")); // Validar nombre
    }


    @Test
    public void testActualizarCliente() throws Exception {
        // Preparar cliente en la base de datos
        Cliente cliente = new Cliente();
        cliente.setClienteId("5"); // Este es un campo único de cliente, pero no el ID autogenerado
        cliente.setNombre("Juan Pérez");
        cliente.setContrasenia("password123");
        cliente.setEstado(true);

        // Guardar el cliente y capturar el ID generado automáticamente
        Cliente clienteGuardado = clienteRepository.save(cliente);
        Long idGenerado = clienteGuardado.getId(); // Capturar el ID de la clave primaria

        // Datos para actualizar el cliente
        Cliente clienteActualizado = new Cliente();
        clienteActualizado.setNombre("Juan Actualizado");
        clienteActualizado.setContrasenia("newpassword123");
        clienteActualizado.setEstado(false);

        // Realizar la solicitud PUT para actualizar el cliente
        mockMvc.perform(put("/api/clientes/" + idGenerado) // Usar el ID generado automáticamente
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(clienteActualizado))) // Convertir objeto a JSON
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("Juan Actualizado")) // Validar nombre actualizado
                .andExpect(jsonPath("$.estado").value(false)); // Validar estado actualizado

        // Verificar que el cliente fue actualizado en la base de datos
        Cliente clienteEnBaseDeDatos = clienteRepository.findById(idGenerado).orElse(null); // Buscar por ID generado
        assert clienteEnBaseDeDatos != null;
        assert clienteEnBaseDeDatos.getNombre().equals("Juan Actualizado"); // Validar nombre
        assert clienteEnBaseDeDatos.getContrasenia().equals("newpassword123"); // Validar contraseña
        assert !clienteEnBaseDeDatos.getEstado(); // Validar estado
    }


    @Test
    public void testEliminarCliente() throws Exception {
        // Preparar cliente en la base de datos
        Cliente cliente = new Cliente();
        cliente.setClienteId("3");
        cliente.setNombre("Juan Pérez2");
        cliente.setContrasenia("password123");
        cliente.setEstado(true);
        cliente.setDireccion("Calle 123");
        cliente.setEdad(30);
        cliente.setGenero("M");
        cliente.setIdentificacion("12345678");
        cliente.setTelefono("555-1234");
        clienteRepository.save(cliente);

        // Realizar la solicitud DELETE
        mockMvc.perform(delete("/api/clientes/3")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        // Verificar que el cliente fue eliminado de la base de datos
        boolean clienteEliminado = clienteRepository.findByClienteId("2").isEmpty();
        assert clienteEliminado : "El cliente no fue eliminado correctamente de la base de datos.";
    }


    @Test
    public void testObtenerClienteNoExistente() throws Exception {
        mockMvc.perform(get("/api/clientes/999")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound()); // Verificar que devuelve el estado HTTP 404
    }
}
