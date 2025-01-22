package com.example.customers.service;

import com.example.customers.entity.Cliente;
import com.example.customers.exception.ClienteNotFoundException;
import com.example.customers.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClienteService {
    @Autowired
    private ClienteRepository clienteRepository;

    public Cliente guardarCliente(Cliente cliente) {
        return clienteRepository.save(cliente);
    }

    public List<Cliente> listarClientes() {
        return clienteRepository.findAll();
    }

    public Cliente obtenerClientePorId(Long id) {
        return clienteRepository.findById(id).orElseThrow(() -> new ClienteNotFoundException("Cliente no encontrado con ID: " + id));
    }

    public void eliminarCliente(Long id) {
        clienteRepository.deleteById(id);
    }

    public Cliente actualizarCliente(Long id, Cliente clienteActualizado) {
        // Verificar si el cliente existe
        Cliente clienteExistente = clienteRepository.findById(id)
                .orElseThrow(() -> new ClienteNotFoundException("Cliente no encontrado con ID: " + id));

        // Aplicar los cambios al cliente existente
        clienteExistente.setNombre(clienteActualizado.getNombre());
        clienteExistente.setContraseña(clienteActualizado.getContraseña());
        clienteExistente.setEstado(clienteActualizado.getEstado());

        return clienteRepository.save(clienteExistente);
    }

    public Cliente obtenerClientePorClienteId(Long id) {
        return clienteRepository.findById(id).orElse(null);
    }
}
