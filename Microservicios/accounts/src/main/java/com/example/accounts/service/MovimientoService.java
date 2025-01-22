package com.example.accounts.service;

import com.example.accounts.entity.Cuenta;
import com.example.accounts.entity.Movimiento;
import com.example.accounts.exception.CuentaNotFoundException;
import com.example.accounts.exception.MovimientoNotFoundException;
import com.example.accounts.exception.SaldoInsuficienteException;
import com.example.accounts.repository.CuentaRepository;
import com.example.accounts.repository.MovimientoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MovimientoService {
    @Autowired
    private CuentaRepository cuentaRepository;
    @Autowired
    private MovimientoRepository movimientoRepository;

    public Movimiento registrarMovimiento (String cuentaId,Movimiento movimiento){
        Cuenta cuenta = cuentaRepository.findByNumeroCuenta(cuentaId)
                .orElseThrow(() -> new CuentaNotFoundException("Cuenta con número " + cuentaId + " no encontrada"));

        // Verificar si ya existe algún movimiento asociado a la cuenta
        Movimiento ultimoMovimiento = movimientoRepository.findTopByCuentaOrderByFechaDesc(cuenta);

        Double saldoAnterior;
        if (ultimoMovimiento == null) {
            // Si no hay movimientos previos, usar el saldo inicial de la cuenta
            saldoAnterior = cuenta.getSaldoInicial();
        }else{
            // Si ya hay movimientos, usar el saldo del último movimiento
            saldoAnterior = ultimoMovimiento.getSaldo();
        }
       Double nuevoSaldo = saldoAnterior + movimiento.getValor();

        if(nuevoSaldo<0){
            throw new SaldoInsuficienteException("Saldo no disponible");
        }

        movimiento.setSaldo(nuevoSaldo);
        movimiento.setCuenta(cuenta);


        return movimientoRepository.save(movimiento);
    }

    public void eliminarMovimiento(Long id){
        movimientoRepository.deleteById(id);
    }
    public List<Movimiento> listarMovimiento(){
        return movimientoRepository.findAll();
    }

    public Movimiento actualizarMovimiento(Long id, Movimiento movimientoActualizado) {

        Movimiento movimientoExistente = movimientoRepository.findById(id)
                .orElseThrow(() -> new MovimientoNotFoundException("Movimiento con ID " + id + " no encontrado"));

        movimientoExistente.setFecha(movimientoActualizado.getFecha());
        movimientoExistente.setTipoMovimiento(movimientoActualizado.getTipoMovimiento());
        movimientoExistente.setValor(movimientoActualizado.getValor());
        movimientoExistente.setSaldo(movimientoActualizado.getSaldo());

        return movimientoRepository.save(movimientoExistente);
    }

}
