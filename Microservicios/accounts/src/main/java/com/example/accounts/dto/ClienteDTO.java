package com.example.accounts.dto;

import java.io.Serializable;

public class ClienteDTO implements Serializable {

    private String clienteId;
    private String nombre;

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
