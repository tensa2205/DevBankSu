package com.devbanksu.dev.movimientos;

import com.devbanksu.dev.cuenta.Cuenta;
import com.devbanksu.dev.exceptions.EntidadNoEncontradaException;

import java.util.List;
import java.util.Optional;

public class MovimientoService {
    private final MovimientoRepository repository;

    public MovimientoService(MovimientoRepository repository) {
        this.repository = repository;
    }

    public List<Movimiento> obtenerMovimiento() {
        return this.repository.findAll();
    }

    public Movimiento obtenerMovimiento(Long id) {
        Optional<Movimiento> movimientoOpt = this.repository.findById(id);
        return movimientoOpt.orElseThrow(() -> new EntidadNoEncontradaException(Movimiento.class, id));
    }

    public Movimiento agregarMovimiento(Movimiento movimiento) {
        return this.repository.save(movimiento);
    }

    public void borrarMovimiento(Long id) {
        this.repository.deleteById(id);
    }
}
