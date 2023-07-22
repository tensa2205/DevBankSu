package com.devbanksu.dev.dto.movimiento;

import com.devbanksu.dev.cuenta.TipoCuenta;
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
    private TipoCuenta tipo;
    private BigDecimal valor;
    private BigDecimal saldo;
}
