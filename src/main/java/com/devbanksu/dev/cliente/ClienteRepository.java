package com.devbanksu.dev.cliente;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {

    @Query("SELECT c FROM Cliente c WHERE c.identificacion = :identificacion")
    public Cliente obtenerClientePorIdentificacion(String identificacion);
}
