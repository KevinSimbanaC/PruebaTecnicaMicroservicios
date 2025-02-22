package com.example.accounts.controller;

import com.example.accounts.entity.Movimiento;
import com.example.accounts.service.MovimientoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador REST para gestionar las operaciones relacionadas con movimientos.
 * Proporciona endpoints para registrar, listar, actualizar y eliminar movimientos.
 */
@RestController
@RequestMapping("/api/movimientos") // Ruta base para los endpoints relacionados con movimientos
public class MovimientoController {

    @Autowired
    private MovimientoService movimientoService;

    /**
     * Registra un nuevo movimiento en una cuenta específica.
     *
     * @param cuentaId   El ID de la cuenta a la que se asocia el movimiento.
     * @param movimiento Objeto JSON con los datos del movimiento a registrar.
     * @return El movimiento registrado envuelto en un ResponseEntity con código HTTP 200.
     */
    @PostMapping("/{cuentaId}")
    public ResponseEntity<Movimiento> registrarMovimiento(@PathVariable String cuentaId, @RequestBody Movimiento movimiento) {
        // Llama al servicio para registrar el movimiento asociado a la cuenta
        return ResponseEntity.ok(movimientoService.registrarMovimiento(cuentaId, movimiento));
    }

    /**
     * Lista todos los movimientos registrados en el sistema.
     *
     * @return Una lista de movimientos envuelta en un ResponseEntity con código HTTP 200.
     */
    @GetMapping
    public ResponseEntity<List<Movimiento>> listarMovimientos() {
        // Llama al servicio para obtener todos los movimientos
        return ResponseEntity.ok(movimientoService.listarMovimiento());
    }

    /**
     * Actualiza un movimiento existente.
     *
     * @param id         El ID del movimiento a actualizar.
     * @param movimiento Objeto JSON con los nuevos datos del movimiento.
     * @return El movimiento actualizado envuelto en un ResponseEntity con código HTTP 200.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Movimiento> actualizarMovimiento(@PathVariable Long id, @RequestBody Movimiento movimiento) {
        // Llama al servicio para actualizar el movimiento y devuelve el movimiento actualizado
        Movimiento movimientoActualizado = movimientoService.actualizarMovimiento(id, movimiento);
        return ResponseEntity.ok(movimientoActualizado);
    }

    /**
     * Elimina un movimiento específico.
     *
     * @param id El ID del movimiento a eliminar.
     * @return Una respuesta HTTP 204 (sin contenido) si la eliminación fue exitosa.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarCliente(@PathVariable Long id) {
        // Llama al servicio para eliminar el movimiento
        movimientoService.eliminarMovimiento(id);
        return ResponseEntity.noContent().build(); // Devuelve un codigo HTTP 204 (sin contenido)
    }
}
