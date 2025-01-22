package com.example.customers.entity;


import jakarta.persistence.*;

@Entity
@Table(name = "clientes")
public class Cliente extends Persona{

    @Column(unique = true, nullable = false)
    private String clienteId;
    private String contraseña;
    private Boolean estado;

    public String getClienteId() {
        return clienteId;
    }

    public void setClienteId(String clienteId) {
        this.clienteId = clienteId;
    }

    public String getContraseña() {
        return contraseña;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }

    public Boolean getEstado() {
        return estado;
    }

    public void setEstado(Boolean estado) {
        this.estado = estado;
    }

    public Cliente(){}
    public Cliente(String clienteId, String contraseña, Boolean estado) {
        this.clienteId = clienteId;
        this.contraseña = contraseña;
        this.estado = estado;
    }
}
