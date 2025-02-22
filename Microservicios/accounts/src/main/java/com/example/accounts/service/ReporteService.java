package com.example.accounts.service;

import com.example.accounts.dto.ClienteDTO;
import com.example.accounts.dto.ReporteDTO;
import com.example.accounts.entity.Cuenta;
import com.example.accounts.entity.Movimiento;
import com.example.accounts.repository.CuentaRepository;
import com.example.accounts.repository.MovimientoRepository;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Service
public class ReporteService {

    private final RabbitTemplate rabbitTemplate;
    private final CuentaRepository cuentaRepository;
    private final MovimientoRepository movimientoRepository;

    protected final ConcurrentHashMap<String, CompletableFuture<ClienteDTO>> pendingRequests = new ConcurrentHashMap<>();

    public ReporteService(RabbitTemplate rabbitTemplate, CuentaRepository cuentaRepository, MovimientoRepository movimientoRepository) {
        this.rabbitTemplate = rabbitTemplate;
        this.cuentaRepository = cuentaRepository;
        this.movimientoRepository = movimientoRepository;
    }

    // Generar el reporte
    public List<ReporteDTO> generarReporte(String clienteId, LocalDate fechaInicio, LocalDate fechaFin) {

        ClienteDTO cliente = obtenerCliente(clienteId);

        // Obtener cuentas asociadas al cliente
        List<Cuenta> cuentas = cuentaRepository.findByClienteId(clienteId);

        // Construir el reporte
        List<ReporteDTO> reporte = new ArrayList<>();

        for (Cuenta cuenta : cuentas) {
            // Filtrar movimientos de la cuenta en el rango de fechas
            List<Movimiento> movimientos = movimientoRepository.findByCuentaIdAndFechaBetween(
                    cuenta.getId(), fechaInicio, fechaFin);

            // Convertir cada movimiento en un objeto ReporteDTO
            List<ReporteDTO> reporteMovimientos = movimientos.stream().map(mov -> {
                ReporteDTO reporteDTO = new ReporteDTO();
                reporteDTO.setFecha(mov.getFecha());
                reporteDTO.setCliente(cliente.getNombre());
                reporteDTO.setNumeroCuenta(cuenta.getNumeroCuenta());
                reporteDTO.setTipo(cuenta.getTipoCuenta());
                reporteDTO.setSaldoInicial(cuenta.getSaldoInicial());
                reporteDTO.setEstado(cuenta.getEstado());
                reporteDTO.setMovimiento(mov.getValor());
                reporteDTO.setSaldoDisponible(mov.getSaldo());
                return reporteDTO;
            }).collect(Collectors.toList());

            // Agregar los movimientos al reporte
            reporte.addAll(reporteMovimientos);
        }

        pendingRequests.remove(clienteId); // Limpia la solicitud pendiente
        return reporte;
    }

    // Escuchar la cola de respuesta
    @RabbitListener(queues = "customer.response.queue")
    public void handleCustomerResponse(ClienteDTO customerDTO) {
        if (customerDTO != null && customerDTO.getClienteId() != null) {
            // Recuperar el CompletableFuture asociado al clienteId
            CompletableFuture<ClienteDTO> future = pendingRequests.remove(customerDTO.getClienteId());
            if (future != null) {
                // Completa la solicitud pendiente
                future.complete(customerDTO);
            }
        }
    }
    // Obtener el cliente usando CompletableFuture
    protected ClienteDTO obtenerCliente(String clienteId) {
        // Crear un CompletableFuture específico para esta solicitud
        CompletableFuture<ClienteDTO> future = new CompletableFuture<>();

        pendingRequests.put(clienteId, future);

        // Publicar solicitud en RabbitMQ
        rabbitTemplate.convertAndSend("customer.request.queue", clienteId);

        // Bloquear hasta que se reciba la respuesta
        return future.join();
    }
}
