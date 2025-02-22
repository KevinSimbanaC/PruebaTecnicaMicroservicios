package com.example.customers.entity;


import jakarta.persistence.*;

@Entity
@Table(name = "clientes",
        indexes = {
                @Index(name = "idx_cliente_cliente_id", columnList = "clienteId", unique = true)
        })
@PrimaryKeyJoinColumn(name = "id") // Relaciona la clave primaria con la tabla "personas"
public class Cliente extends Persona{

    @Column(unique = true, nullable = false)
    private String clienteId;
    private String contrasenia;
    private Boolean estado;

    public String getClienteId() {
        return clienteId;
    }

    public void setClienteId(String clienteId) {
        this.clienteId = clienteId;
    }

    public String getContrasenia() {
        return contrasenia;
    }

    public void setContrasenia(String contrasenia) {
        this.contrasenia = contrasenia;
    }

    public Boolean getEstado() {
        return estado;
    }

    public void setEstado(Boolean estado) {
        this.estado = estado;
    }

    public Cliente(){}
    public Cliente(String clienteId, String contrasenia, Boolean estado) {
        this.clienteId = clienteId;
        this.contrasenia = contrasenia;
        this.estado = estado;
    }
}
