package com.devbanksu.dev.movimientos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.util.Date;

public interface MovimientoRepository extends JpaRepository<Movimiento, Long> {

    //TODO Revisar si puedo devolver directamente un booleano
    @Query("SELECT SUM(m.valor) FROM Movimiento m WHERE m.cuenta.id = :idCuenta AND m.tipo = 'RETIRO' AND m.fecha = :fecha")
    public BigDecimal obtenerRetirosEnFecha(Long idCuenta, Date fecha);

}
