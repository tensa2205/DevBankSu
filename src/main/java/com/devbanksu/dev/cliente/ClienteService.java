package com.devbanksu.dev.cliente;

import com.devbanksu.dev.dto.cliente.ClienteDTO;
import com.devbanksu.dev.dto.cliente.ClienteMapper;
import com.devbanksu.dev.exceptions.EntidadNoEncontradaException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
//TODO agregar validacion de DTO apra la creación
@Service
public class ClienteService {
    private static final String LOG_PREFIX = "[CLIENTE_SERVICE]";

    private final Logger logger = LoggerFactory.getLogger(ClienteService.class);
    private final ClienteRepository repository;
    private final ClienteMapper mapper;

    public ClienteService(ClienteRepository repository, ClienteMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public Cliente obtenerClientePorIdentificacion(String identificacion) {
        return repository.obtenerClientePorIdentificacion(identificacion);
    }

    public List<ClienteDTO> obtenerClientesDTO() {
        return obtenerClientes().stream().map(mapper::mapearObjetoADTO).toList();
    }

    public List<Cliente> obtenerClientes() {
        List<Cliente> clientes = this.repository.findAll();
        logger.info("{} Hay un total de {} clientes en el sistema", LOG_PREFIX, clientes.size());
        return clientes;
    }

    public ClienteDTO obtenerClienteDTO(Long id) {
        return mapper.mapearObjetoADTO(obtenerCliente(id));
    }

    public Cliente obtenerCliente(Long id) {
        Optional<Cliente> clienteOpt = this.repository.findById(id);
        if (clienteOpt.isEmpty()) {
            logger.warn("{} Se ha intentado buscar el cliente con ID {} pero no ha sido encontrado", LOG_PREFIX, id);
            throw new EntidadNoEncontradaException(Cliente.class, id);
        }
        return clienteOpt.get();
    }

    public ClienteDTO agregarCliente(ClienteDTO clienteDTO) {
        Cliente clienteAGuardar = mapper.mapearDTOAObjeto(clienteDTO);
        return mapper.mapearObjetoADTO(this.repository.save(clienteAGuardar));
    }

    public void borrarCliente(Long id) {
        logger.info("{} Buscando cliente con ID {}", LOG_PREFIX, id);
        this.obtenerCliente(id);
        logger.warn("{} Eliminando cliente con ID {}", LOG_PREFIX, id);
        this.repository.deleteById(id);
    }
}
