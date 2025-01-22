package com.example.accounts.repository;

import com.example.accounts.entity.Cuenta;
import com.example.accounts.entity.Movimiento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.*;

@Repository
public interface MovimientoRepository extends JpaRepository<Movimiento, Long> {

    Movimiento findTopByCuentaOrderByFechaDesc(Cuenta cuenta);

    List<Movimiento> findByCuentaIdAndFechaBetween(Long cuentaId, LocalDate fechaInicio, LocalDate fechaFin);
}