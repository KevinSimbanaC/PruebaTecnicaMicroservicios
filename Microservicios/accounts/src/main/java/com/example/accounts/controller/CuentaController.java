package com.example.accounts.controller;

import com.example.accounts.entity.Cuenta;
import com.example.accounts.service.CuentaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cuentas")
public class CuentaController {

    @Autowired
    private CuentaService cuentaService;
    @PostMapping
    public ResponseEntity<Cuenta> crearCuenta(@RequestBody Cuenta cuenta){
        return ResponseEntity.ok(cuentaService.crearCuenta(cuenta));
    }

    @GetMapping
    public ResponseEntity<List<Cuenta>> listarCuentas(){
        return ResponseEntity.ok(cuentaService.listarCuentas());
    }
    @GetMapping("/cliente/{clienteId}")
    public ResponseEntity<List<Cuenta>> listarCuentasPorCliente(@PathVariable String clienteId) {
        return ResponseEntity.ok(cuentaService.listarCuentasPorClienteId(clienteId));
    }
    @GetMapping("/{numCuenta}")
    public ResponseEntity<Cuenta> obtenerCuentaPorNumero(@PathVariable String numCuenta){
        return ResponseEntity.ok(cuentaService.obtenerCuentaPorNumeroDeCuenta(numCuenta));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Cuenta> actualizarCuenta(@PathVariable Long id, @RequestBody Cuenta cuenta) {
        Cuenta cuentaActualizada = cuentaService.actualizarCuenta(id, cuenta);
        return ResponseEntity.ok(cuentaActualizada);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarCuenta(@PathVariable Long id) {
        cuentaService.eliminarCuenta(id);
        return ResponseEntity.noContent().build();
    }

}
