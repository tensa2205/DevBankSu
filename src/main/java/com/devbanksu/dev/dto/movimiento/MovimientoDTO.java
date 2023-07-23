package com.devbanksu.dev.dto.movimiento;

import com.devbanksu.dev.cuenta.TipoCuenta;
import com.devbanksu.dev.movimientos.TipoMovimiento;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.*;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonSerialize(using = MovimientoDTOSerializer.class)
public class MovimientoDTO {
    private Long id;
    private Long idCuenta;
    private Date fecha;
    private TipoMovimiento tipo;
    private BigDecimal valor;
    private BigDecimal saldoDisponible;
}
