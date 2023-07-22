package com.devbanksu.dev.cuenta;

import com.devbanksu.dev.cliente.Cliente;
import com.devbanksu.dev.exceptions.EntidadNoEncontradaException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CuentaService {
    private final CuentaRepository repository;

    public CuentaService(CuentaRepository repository) {
        this.repository = repository;
    }

    public List<Cuenta> obtenerCuentas() {
        return this.repository.findAll();
    }

    public Cuenta obtenerCuenta(Long id) {
        Optional<Cuenta> cuentaOpt = this.repository.findById(id);
        return cuentaOpt.orElseThrow(() -> new EntidadNoEncontradaException(Cuenta.class, id));
    }

    public Cuenta agregarCuenta(Cuenta cuenta) {
        return this.repository.save(cuenta);
    }

    public void borrarCuenta(Long id) {
        this.repository.deleteById(id);
    }
}
