package com.devbanksu.dev.cuenta;

import com.devbanksu.dev.cliente.ClienteService;
import com.devbanksu.dev.dto.CuentaDTO;
import com.devbanksu.dev.dto.CuentaMapper;
import com.devbanksu.dev.exceptions.EntidadNoEncontradaException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CuentaService {
    private final CuentaRepository repository;
    private final ClienteService clienteService;

    public CuentaService(CuentaRepository repository, ClienteService clienteService) {
        this.repository = repository;
        this.clienteService = clienteService;
    }

    public List<CuentaDTO> obtenerCuentas() {
        List<Cuenta> cuentas = this.repository.findAll();
        return cuentas.stream().map(CuentaMapper::mapearCuentaADTO).toList();
    }

    public CuentaDTO obtenerCuenta(Long id) {
        Optional<Cuenta> cuentaOpt = this.repository.findById(id);
        if (cuentaOpt.isEmpty()) throw new EntidadNoEncontradaException(Cuenta.class, id);
        return CuentaMapper.mapearCuentaADTO(cuentaOpt.get());
    }

    public CuentaDTO agregarCuenta(Long idCliente, CuentaDTO cuentaDTO) {
        cuentaDTO.setIdCliente(idCliente);
        Cuenta cuentaAGuardar = CuentaMapper.mapearDTOACuenta(cuentaDTO);
        cuentaAGuardar.setCliente(this.clienteService.obtenerCliente(idCliente));
        return CuentaMapper.mapearCuentaADTO(this.repository.save(cuentaAGuardar));
    }

    public void borrarCuenta(Long id) {
        this.repository.deleteById(id);
    }
}
