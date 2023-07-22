package com.devbanksu.dev.cliente;

import com.devbanksu.dev.exceptions.EntidadNoEncontradaException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClienteService {
    private final ClienteRepository repository;

    public ClienteService(ClienteRepository repository) {
        this.repository = repository;
    }

    public List<Cliente> obtenerClientes() {
        return this.repository.findAll();
    }

    public Cliente obtenerCliente(Long id) {
        Optional<Cliente> clienteOpt = this.repository.findById(id);
        return clienteOpt.orElseThrow(() -> new EntidadNoEncontradaException(Cliente.class, id));
    }

    public Cliente agregarCliente(Cliente cliente) {
        return this.repository.save(cliente);
    }

    public void borrarCliente(Long id) {
        this.repository.deleteById(id);
    }
}
