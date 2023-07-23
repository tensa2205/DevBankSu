package com.devbanksu.dev.dto.cuenta;

import com.devbanksu.dev.cuenta.TipoCuenta;
import com.devbanksu.dev.dto.movimiento.MovimientoDTO;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.*;

import java.math.BigDecimal;
import java.util.Set;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonSerialize(using = CuentaDTOSerializer.class)
@ToString
public class CuentaDTO {
    private Set<MovimientoDTO> movimientos;
    private String nombreCliente;
    private Long nroCuenta;
    private TipoCuenta tipo;
    private BigDecimal saldoInicial;
    private BigDecimal saldoActual;
    private boolean estado;
}
