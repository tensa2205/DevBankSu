package com.devbanksu.dev.movimientos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.util.Date;

public interface MovimientoRepository extends JpaRepository<Movimiento, Long> {

    @Query("SELECT IFNULL(SUM(m.valor), 0) FROM Movimiento m WHERE m.cuenta.id = :idCuenta AND m.tipo = com.devbanksu.dev.movimientos.TipoMovimiento.RETIRO AND CAST(m.fecha AS date) = CAST(:fecha AS date)")
    public BigDecimal obtenerRetirosEnFecha(Long idCuenta, Date fecha);

}
