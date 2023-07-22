package com.devbanksu.dev.dto.cuenta;

import com.devbanksu.dev.cliente.Cliente;
import com.devbanksu.dev.cuenta.TipoCuenta;
import com.devbanksu.dev.dto.movimiento.MovimientoDTO;
import com.devbanksu.dev.dto.movimiento.MovimientoDTOSerializer;
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
public class CuentaDTO {
    private Set<MovimientoDTO> movimientos;
    private Long idCliente;
    private TipoCuenta tipo;
    private BigDecimal saldoInicial;
    private BigDecimal saldoActual;
    private boolean estado;
}
