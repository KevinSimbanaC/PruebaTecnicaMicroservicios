package com.example.accounts.controller;

import com.example.accounts.entity.Cuenta;
import com.example.accounts.service.CuentaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador REST para gestionar las operaciones relacionadas con las cuentas.
 * Proporciona endpoints para crear, listar, actualizar, obtener y eliminar cuentas.
 */
@RestController
@RequestMapping("/api/cuentas") // Ruta base para todos los endpoints relacionados con cuentas
public class CuentaController {

    @Autowired
    private CuentaService cuentaService;

    /**
     * Crea una nueva cuenta.
     *
     * @param cuenta Objeto JSON que representa la cuenta a crear.
     * @return La cuenta creada envuelta en un ResponseEntity con código HTTP 200.
     */
    @PostMapping
    public ResponseEntity<Cuenta> crearCuenta(@RequestBody Cuenta cuenta) {
        // Llama al servicio para crear una cuenta y devuelve la cuenta creada
        return ResponseEntity.ok(cuentaService.crearCuenta(cuenta));
    }

    /**
     * Lista todas las cuentas registradas en el sistema.
     *
     * @return Una lista de todas las cuentas registradas envuelta en un ResponseEntity con código HTTP 200.
     */
    @GetMapping
    public ResponseEntity<List<Cuenta>> listarCuentas() {
        // Llama al servicio para obtener la lista de cuentas y la devuelve
        return ResponseEntity.ok(cuentaService.listarCuentas());
    }

    /**
     * Lista todas las cuentas asociadas a un cliente específico.
     *
     * @param clienteId El ID del cliente cuyas cuentas se quieren listar.
     * @return Una lista de cuentas asociadas al cliente especificado envuelta en un ResponseEntity con código HTTP 200.
     */
    @GetMapping("/cliente/{clienteId}")
    public ResponseEntity<List<Cuenta>> listarCuentasPorCliente(@PathVariable String clienteId) {
        // Llama al servicio para obtener las cuentas del cliente y las devuelve
        return ResponseEntity.ok(cuentaService.listarCuentasPorClienteId(clienteId));
    }

    /**
     * Obtiene una cuenta específica por su número de cuenta.
     *
     * @param numCuenta El número de cuenta a buscar.
     * @return La cuenta encontrada envuelta en un ResponseEntity con código HTTP 200.
     */
    @GetMapping("/{numCuenta}")
    public ResponseEntity<Cuenta> obtenerCuentaPorNumero(@PathVariable String numCuenta) {
        // Llama al servicio para obtener la cuenta por su número y la devuelve
        return ResponseEntity.ok(cuentaService.obtenerCuentaPorNumeroDeCuenta(numCuenta));
    }

    /**
     * Actualiza una cuenta existente.
     *
     * @param id     El ID de la cuenta a actualizar.
     * @param cuenta Objeto JSON con los nuevos datos de la cuenta.
     * @return La cuenta actualizada envuelta en un ResponseEntity con código HTTP 200.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Cuenta> actualizarCuenta(@PathVariable Long id, @RequestBody Cuenta cuenta) {
        // Llama al servicio para actualizar la cuenta y devuelve la cuenta actualizada
        Cuenta cuentaActualizada = cuentaService.actualizarCuenta(id, cuenta);
        return ResponseEntity.ok(cuentaActualizada);
    }

    /**
     * Elimina una cuenta existente.
     *
     * @param id El ID de la cuenta a eliminar.
     * @return Una respuesta HTTP 204 (sin contenido) si la eliminación fue exitosa.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarCuenta(@PathVariable Long id) {
        // Llama al servicio para eliminar la cuenta
        cuentaService.eliminarCuenta(id);
        return ResponseEntity.noContent().build(); // Devuelve un código HTTP 204 (sin contenido)
    }
}
