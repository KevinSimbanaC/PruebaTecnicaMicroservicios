package com.example.accounts.service;

import com.example.accounts.entity.Cuenta;
import com.example.accounts.repository.CuentaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CuentaService {
    @Autowired
    private CuentaRepository cuentaRepository;

    public Cuenta crearCuenta(Cuenta cuenta){
        return cuentaRepository.save(cuenta);
    }

    public List<Cuenta> listarCuentas(){
        return cuentaRepository.findAll();
    }
    public Cuenta obtenerCuentaPorNumeroDeCuenta(String cuentaId){
        return cuentaRepository.findByNumeroCuenta(cuentaId)
                .orElseThrow(() -> new RuntimeException("Cuenta no encontrada"));
    }
    public void eliminarCuenta(Long id){
        cuentaRepository.deleteById(id);
    }
    public Cuenta actualizarCuenta(Long id, Cuenta cuentaActualizada) {

        Cuenta cuentaExistente = cuentaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cuenta no encontrada"));

        cuentaExistente.setClienteId(cuentaActualizada.getClienteId());
        cuentaExistente.setNumeroCuenta(cuentaActualizada.getNumeroCuenta());
        cuentaExistente.setTipoCuenta(cuentaActualizada.getTipoCuenta());
        cuentaExistente.setSaldoInicial(cuentaActualizada.getSaldoInicial());
        cuentaExistente.setEstado(cuentaActualizada.getEstado());

        return cuentaRepository.save(cuentaExistente);
    }

    public List<Cuenta> listarCuentasPorClienteId(String clienteId) {
        return cuentaRepository.findByClienteId(clienteId);
    }

}
