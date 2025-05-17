package com.devbanksu.dev.strategy;

import com.devbanksu.dev.movimientos.TipoMovimiento;

import java.math.BigDecimal;

public class OperacionDeposito implements OperacionStrategy {
    @Override
    public BigDecimal realizarOperacion(BigDecimal saldo, BigDecimal valorDeposito) {
        return saldo.add(valorDeposito);
    }

    @Override
    public TipoMovimiento getTipoMovimiento() {
        return TipoMovimiento.DEPOSITO;
    }
}
