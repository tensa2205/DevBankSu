package com.devbanksu.dev.advice;

import com.devbanksu.dev.exceptions.EntidadNoEncontradaException;
import com.devbanksu.dev.exceptions.LimiteDiarioException;
import com.devbanksu.dev.exceptions.SaldoNoDisponibleException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ManejadorGlobalDeException {

    @ExceptionHandler(EntidadNoEncontradaException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String manejarEntidadNoEncontrada(EntidadNoEncontradaException ex) {
        return ex.getMessage();
    }

    @ExceptionHandler(SaldoNoDisponibleException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    String manejarSaldoNoDisponible(SaldoNoDisponibleException ex) {
        return ex.getMessage();
    }

    @ExceptionHandler(LimiteDiarioException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    String manejarLimiteDiarioExcedido(LimiteDiarioException ex) {
        return ex.getMessage();
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    String manejarIllegalArgument(IllegalArgumentException ex) {
        return ex.getMessage();
    }
}
