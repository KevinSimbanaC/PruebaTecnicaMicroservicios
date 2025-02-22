package com.example.customers.service;

import com.example.customers.dto.CustomerDTO;
import com.example.customers.entity.Cliente;
import com.example.customers.repository.ClienteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class CustomerMessageServiceTest {

    @Mock
    private ClienteRepository clienteRepository;

    @Mock
    private RabbitTemplate rabbitTemplate;

    @InjectMocks
    private CustomerMessageService customerMessageService;

    private Cliente cliente;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Cliente de ejemplo
        cliente = new Cliente();
        cliente.setClienteId("12345");
        cliente.setNombre("Juan Pérez");
    }

    @Test
    void testHandleCustomerRequest_ClienteEncontrado() {
        // Mockear el comportamiento del repositorio
        when(clienteRepository.findByClienteId("12345")).thenReturn(Optional.of(cliente));

        // Simular el manejo de la solicitud
        customerMessageService.handleCustomerRequest("12345");

        // Capturar el argumento enviado al RabbitTemplate
        ArgumentCaptor<CustomerDTO> captor = ArgumentCaptor.forClass(CustomerDTO.class);
        verify(rabbitTemplate).convertAndSend(eq("customer.response.queue"), captor.capture());

        // Verificar el contenido del DTO enviado
        CustomerDTO sentDTO = captor.getValue();
        assertEquals("12345", sentDTO.getClienteId());
        assertEquals("Juan Pérez", sentDTO.getNombre());

        // Verificar que el repositorio y RabbitTemplate fueron invocados correctamente
        verify(clienteRepository, times(1)).findByClienteId("12345");
        verify(rabbitTemplate, times(1)).convertAndSend(eq("customer.response.queue"), any(CustomerDTO.class));
    }

    @Test
    void testHandleCustomerRequest_ClienteNoEncontrado() {
        // Mockear el comportamiento del repositorio (cliente no encontrado)
        when(clienteRepository.findByClienteId("12345")).thenReturn(Optional.empty());

        // Simular el manejo de la solicitud
        customerMessageService.handleCustomerRequest("12345");

        // Capturar el mensaje enviado al RabbitTemplate
        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
        verify(rabbitTemplate).convertAndSend(eq("customer.response.queue"), captor.capture());

        // Verificar el contenido del mensaje enviado
        String sentMessage = captor.getValue();
        assertEquals("Cliente no encontrado: 12345", sentMessage);

        // Verificar que el repositorio y RabbitTemplate fueron invocados correctamente
        verify(clienteRepository, times(1)).findByClienteId("12345");
        verify(rabbitTemplate, times(1)).convertAndSend(eq("customer.response.queue"), any(String.class));
    }
}
