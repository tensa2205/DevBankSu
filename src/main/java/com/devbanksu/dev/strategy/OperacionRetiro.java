package com.devbanksu.dev.strategy;

import com.devbanksu.dev.exceptions.LimiteDiarioException;
import com.devbanksu.dev.exceptions.SaldoNoDisponibleException;
import com.devbanksu.dev.movimientos.TipoMovimiento;

import java.math.BigDecimal;

public class OperacionRetiro implements OperacionStrategy {

    @Override
    public BigDecimal realizarOperacion(BigDecimal saldo, BigDecimal valorOperacion) {
        if (esCeroONegativo(saldo, valorOperacion)) throw new SaldoNoDisponibleException();
        return realizarRetiro(saldo, valorOperacion);
    }

    private boolean esCeroONegativo(BigDecimal saldo, BigDecimal valorOperacion) {
        int comparacion = realizarRetiro(saldo, valorOperacion).compareTo(BigDecimal.ZERO);
        return comparacion <= 0;
    }

    private BigDecimal realizarRetiro(BigDecimal saldo, BigDecimal valorOperacion) {
        return saldo.subtract(valorOperacion);
    }

    @Override
    public TipoMovimiento getTipoMovimiento() {
        return TipoMovimiento.RETIRO;
    }
}
