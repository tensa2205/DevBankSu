package com.devbanksu.dev.cliente;

import com.devbanksu.dev.dto.cliente.ClienteDTO;
import com.devbanksu.dev.dto.cliente.ClienteMapper;
import com.devbanksu.dev.exceptions.EntidadNoEncontradaException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClienteService {
    private final ClienteRepository repository;
    private final ClienteMapper mapper;

    public ClienteService(ClienteRepository repository, ClienteMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public List<ClienteDTO> obtenerClientes() {
        List<Cliente> clientes = this.repository.findAll();
        return clientes.stream().map(mapper::mapearObjetoADTO).toList();
    }

    public ClienteDTO obtenerClienteDTO(Long id) {
        return mapper.mapearObjetoADTO(obtenerCliente(id));
    }

    public Cliente obtenerCliente(Long id) {
        Optional<Cliente> clienteOpt = this.repository.findById(id);
        return clienteOpt.orElseThrow(() -> new EntidadNoEncontradaException(Cliente.class, id));
    }

    public ClienteDTO agregarCliente(ClienteDTO clienteDTO) {
        Cliente clienteAGuardar = mapper.mapearDTOAObjeto(clienteDTO);
        return mapper.mapearObjetoADTO(this.repository.save(clienteAGuardar));
    }

    public void borrarCliente(Long id) {
        this.repository.deleteById(id);
    }
}
