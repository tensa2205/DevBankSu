package com.devbanksu.dev.dto.cuenta;

import com.devbanksu.dev.cliente.Cliente;
import com.devbanksu.dev.cuenta.TipoCuenta;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CuentaDTO {
    private Long idCliente;
    private TipoCuenta tipo;
    private BigDecimal saldoInicial;
    private BigDecimal saldoActual;
    private boolean estado;
}
