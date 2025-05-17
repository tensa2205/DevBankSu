package com.devbanksu.dev.exceptions;

public class LimiteDiarioException extends RuntimeException{
    public LimiteDiarioException() {
        super("Cupo diario excedido");
    }
}
