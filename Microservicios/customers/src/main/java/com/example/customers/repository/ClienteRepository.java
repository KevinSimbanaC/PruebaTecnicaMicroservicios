package com.example.customers.repository;

import com.example.customers.entity.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClienteRepository  extends JpaRepository<Cliente, Long> {
    // Consulta personalizada para buscar un cliente por clienteId
    @Query("SELECT c FROM Cliente c WHERE c.clienteId = :clienteId")
    Optional<Cliente> findByClienteId(@Param("clienteId") String clienteId);
}
