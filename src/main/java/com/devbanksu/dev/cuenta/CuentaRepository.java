package com.devbanksu.dev.cuenta;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CuentaRepository extends JpaRepository<Cuenta, Long> {

    @Query("SELECT c FROM Cuenta c WHERE c.cliente.id = :idCliente")
    public List<Cuenta> obtenerCuentasDeCliente(Long idCliente);

}
