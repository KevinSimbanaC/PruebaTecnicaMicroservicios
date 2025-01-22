package com.example.customers.dto;

import java.io.Serializable;

public class CustomerDTO implements Serializable {
    private String clienteId;
    private String nombre;

    // Getters y setters
    public String getClienteId() {
        return clienteId;
    }

    public void setClienteId(String clienteId) {
        this.clienteId = clienteId;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
