package com.devbanksu.dev.strategy;

import com.devbanksu.dev.movimientos.TipoMovimiento;
import org.springframework.stereotype.Component;

import java.util.EnumMap;
import java.util.Map;

@Component
public class OperacionFactory {
    private final Map<TipoMovimiento, OperacionStrategy> strategies = new EnumMap<>(TipoMovimiento.class);

    public OperacionFactory() {
        inicializarStrategies();
    }

    private void inicializarStrategies() {
        strategies.put(TipoMovimiento.DEPOSITO, new OperacionDeposito());
        strategies.put(TipoMovimiento.RETIRO, new OperacionRetiro());
    }

    public OperacionStrategy obtenerStrategy(TipoMovimiento tipo) {
        if (tipo == null || !strategies.containsKey(tipo)) {
            throw new IllegalArgumentException(String.format("Tipo de movimiento invalido %s", tipo));
        }
        return strategies.get(tipo);
    }
}
