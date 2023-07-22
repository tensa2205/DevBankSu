package com.devbanksu.dev.dto.movimiento;

import com.devbanksu.dev.cuenta.TipoCuenta;
import com.devbanksu.dev.movimientos.TipoMovimiento;
import lombok.*;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MovimientoDTO {
    private Long idCuenta;
    private Date fecha;
    private TipoMovimiento tipo;
    private BigDecimal valor;
}
