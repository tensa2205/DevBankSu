package com.devbanksu.dev.movimientos;

import lombok.Getter;

import java.math.BigDecimal;

@Getter
public enum TipoMovimiento {
    RETIRO(true),
    DEPOSITO(false);

    private boolean tieneLimiteDiario;

    TipoMovimiento(boolean tieneLimiteDiario) {
        this.tieneLimiteDiario = tieneLimiteDiario;
    }
}
