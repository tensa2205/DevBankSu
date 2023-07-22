package com.devbanksu.dev.strategy;

import com.devbanksu.dev.movimientos.TipoMovimiento;

import java.math.BigDecimal;

public interface OperacionStrategy {
    BigDecimal realizarOperacion(BigDecimal saldo, BigDecimal valorOperacion);
    TipoMovimiento getTipoMovimiento();
}
