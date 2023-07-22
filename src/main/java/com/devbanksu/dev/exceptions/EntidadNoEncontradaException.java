package com.devbanksu.dev.exceptions;

public class EntidadNoEncontradaException extends RuntimeException {
    public <T> EntidadNoEncontradaException(Class<T> entidad, Long id) {
        super(String.format("No se encontró %s con ID: %s", entidad.getSimpleName(), id));
    }
}
