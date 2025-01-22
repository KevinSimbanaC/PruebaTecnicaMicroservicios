package com.example.customers.service;

import com.example.customers.dto.CustomerDTO;
import com.example.customers.entity.Cliente;
import com.example.customers.repository.ClienteRepository;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
public class CustomerMessageService {

    private final ClienteRepository clienteRepository;
    private final RabbitTemplate rabbitTemplate;

    public CustomerMessageService(ClienteRepository clienteRepository, RabbitTemplate rabbitTemplate) {
        this.clienteRepository = clienteRepository;
        this.rabbitTemplate = rabbitTemplate;
    }

    @RabbitListener(queues = "customer.request.queue") // Escucha mensajes en la cola de solicitud
    public void handleCustomerRequest(String clienteId) {
        Cliente customer = clienteRepository.findByClienteId(clienteId)
                .orElse(null);

        if (customer != null) {
            // Convertir a DTO
            CustomerDTO customerDTO = new CustomerDTO();
            customerDTO.setClienteId(customer.getClienteId());
            customerDTO.setNombre(customer.getNombre());

            // Publicar la respuesta en la cola de respuesta
            rabbitTemplate.convertAndSend("customer.response.queue", customerDTO);
        } else {
            // Publicar un mensaje de error si no se encuentra el cliente
            rabbitTemplate.convertAndSend("customer.response.queue", "Cliente no encontrado: " + clienteId);
        }
    }
}
