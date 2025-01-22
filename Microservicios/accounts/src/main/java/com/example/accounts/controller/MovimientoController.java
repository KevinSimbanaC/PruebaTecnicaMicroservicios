package com.example.accounts.controller;

import com.example.accounts.entity.Movimiento;
import com.example.accounts.service.MovimientoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/movimientos")
public class MovimientoController {

    @Autowired
    private MovimientoService movimientoService;
    @PostMapping("/{cuentaId}")
    public ResponseEntity<Movimiento> registrarMovimiento(@PathVariable String cuentaId, @RequestBody Movimiento movimiento){
        return ResponseEntity.ok(movimientoService.registrarMovimiento(cuentaId,movimiento));

    }
    @GetMapping
    public ResponseEntity<List<Movimiento>> listarMovimientos(){
        return ResponseEntity.ok(movimientoService.listarMovimiento());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Movimiento> actualizarMovimiento(@PathVariable Long id, @RequestBody Movimiento movimiento) {
        Movimiento movimientoActualizado = movimientoService.actualizarMovimiento(id, movimiento);
        return ResponseEntity.ok(movimientoActualizado);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarCliente(@PathVariable Long id){
        movimientoService.eliminarMovimiento(id);
        return ResponseEntity.noContent().build();
    }

}
