package com.devbanksu.dev.cuenta;

import com.devbanksu.dev.cliente.ClienteService;
import com.devbanksu.dev.dto.cuenta.CuentaDTO;
import com.devbanksu.dev.dto.cuenta.CuentaMapper;
import com.devbanksu.dev.exceptions.EntidadNoEncontradaException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CuentaService {
    private final CuentaRepository repository;
    private final CuentaMapper mapper;
    private final ClienteService clienteService;

    public CuentaService(CuentaRepository repository, CuentaMapper mapper, ClienteService clienteService) {
        this.repository = repository;
        this.mapper = mapper;
        this.clienteService = clienteService;
    }

    public List<CuentaDTO> obtenerCuentas() {
        List<Cuenta> cuentas = this.repository.findAll();
        return cuentas.stream().map(mapper::mapearObjetoADTO).toList();
    }

    public CuentaDTO obtenerCuentaDTO(Long id) {
        return mapper.mapearObjetoADTO(obtenerCuenta(id));
    }

    public Cuenta obtenerCuenta(Long id) {
        Optional<Cuenta> cuentaOpt = this.repository.findById(id);
        return cuentaOpt.orElseThrow(() -> new EntidadNoEncontradaException(Cuenta.class, id));
    }

    public CuentaDTO agregarCuenta(Long idCliente, CuentaDTO cuentaDTO) {
        cuentaDTO.setIdCliente(idCliente);
        Cuenta cuentaAGuardar = mapper.mapearDTOAObjeto(cuentaDTO);
        cuentaAGuardar.setCliente(this.clienteService.obtenerCliente(idCliente));
        return mapper.mapearObjetoADTO(this.repository.save(cuentaAGuardar));
    }

    public void borrarCuenta(Long id) {
        this.repository.deleteById(id);
    }
}
