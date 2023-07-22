package com.devbanksu.dev.movimientos;

public enum TipoMovimiento {
    RETIRO(true),
    DEPOSITO(false);

    private boolean tieneLimiteDiario;

    TipoMovimiento(boolean tieneLimiteDiario) {
        this.tieneLimiteDiario = tieneLimiteDiario;
    }

    public boolean tieneLimiteDiario() {
        return tieneLimiteDiario;
    }
}
