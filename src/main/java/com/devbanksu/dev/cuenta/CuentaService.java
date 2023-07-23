package com.devbanksu.dev.cuenta;

import com.devbanksu.dev.cliente.ClienteService;
import com.devbanksu.dev.dto.cuenta.CuentaDTO;
import com.devbanksu.dev.dto.cuenta.CuentaMapper;
import com.devbanksu.dev.dto.movimiento.MovimientoDTO;
import com.devbanksu.dev.exceptions.EntidadNoEncontradaException;
import com.devbanksu.dev.strategy.OperacionFactory;
import com.devbanksu.dev.strategy.OperacionStrategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CuentaService {
    private static final String LOG_PREFIX = "[CUENTA_SERVICE]";
    private static final Logger logger = LoggerFactory.getLogger(CuentaService.class);
    private final CuentaRepository repository;
    private final CuentaMapper mapper;
    private final OperacionFactory operacionFactory;
    private final ClienteService clienteService;

    public CuentaService(CuentaRepository repository, CuentaMapper mapper, OperacionFactory factory,ClienteService clienteService) {
        this.repository = repository;
        this.mapper = mapper;
        this.operacionFactory = factory;
        this.clienteService = clienteService;
    }

    public List<Cuenta> obtenerCuentasDeCliente(Long idCliente) {
        return this.repository.obtenerCuentasDeCliente(idCliente);
    }
    public List<CuentaDTO> obtenerCuentasDTO() {
        return obtenerCuentas().stream().map(mapper::mapearObjetoADTO).toList();
    }

    public List<Cuenta> obtenerCuentas() {
        List<Cuenta> cuentas = this.repository.findAll();
        logger.info("{} Hay un total de {} cuentas en el sistema", LOG_PREFIX, cuentas.size());
        return cuentas;
    }

    public CuentaDTO obtenerCuentaDTO(Long id) {
        return mapper.mapearObjetoADTO(obtenerCuenta(id));
    }

    public Cuenta realizarMovimientoSobreCuenta(Long idCuenta, MovimientoDTO movimientoDTO) {
        OperacionStrategy strategy = operacionFactory.obtenerStrategy(movimientoDTO.getTipo());

        Cuenta cuenta = this.obtenerCuenta(idCuenta);
        logger.info("{} Se realizara la operacion {} por un valor de {} sobre la cuenta {}", LOG_PREFIX, movimientoDTO.getTipo(), movimientoDTO.getValor(), cuenta.getNroCuenta());
        cuenta.setSaldoActual(strategy.realizarOperacion(cuenta.getSaldoActual(), movimientoDTO.getValor()));

        this.repository.save(cuenta);
        return cuenta;
    }

    public Cuenta obtenerCuenta(Long id) {
        Optional<Cuenta> cuentaOpt = this.repository.findById(id);
        if (cuentaOpt.isEmpty()) {
            logger.warn("{} Se ha intentado buscar la cuenta con ID {} pero no ha sido encontrada", LOG_PREFIX, id);
            throw new EntidadNoEncontradaException(Cuenta.class, id);
        }
        return cuentaOpt.get();
    }

    //Restringir el setear saldoDisponible desde la request.
    public CuentaDTO agregarCuenta(Long idCliente, CuentaDTO cuentaDTO) {
        Cuenta cuentaAGuardar = mapper.mapearDTOAObjeto(cuentaDTO);
        cuentaAGuardar.setCliente(this.clienteService.obtenerCliente(idCliente));
        return mapper.mapearObjetoADTO(this.repository.save(cuentaAGuardar));
    }

    public void borrarCuenta(Long id) {
        logger.info("{} Buscando cuenta con ID {}", LOG_PREFIX, id);
        this.obtenerCuenta(id);
        logger.warn("{} Eliminando cuenta con ID {}", LOG_PREFIX, id);
        this.repository.deleteById(id);
    }
}
