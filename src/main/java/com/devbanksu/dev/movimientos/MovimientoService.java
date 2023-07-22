package com.devbanksu.dev.movimientos;

import com.devbanksu.dev.cuenta.CuentaService;
import com.devbanksu.dev.dto.movimiento.MovimientoDTO;
import com.devbanksu.dev.dto.movimiento.MovimientoMapper;
import com.devbanksu.dev.exceptions.EntidadNoEncontradaException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MovimientoService {
    private final MovimientoRepository repository;
    private final MovimientoMapper mapper;
    private final CuentaService cuentaService;

    public MovimientoService(MovimientoRepository repository, MovimientoMapper mapper, CuentaService cuentaService) {
        this.repository = repository;
        this.mapper = mapper;
        this.cuentaService = cuentaService;
    }

    public List<MovimientoDTO> obtenerMovimientos() {
        List<Movimiento> movimientos = this.repository.findAll();
        return movimientos.stream().map(mapper::mapearObjetoADTO).toList();
    }

    public MovimientoDTO obtenerMovimiento(Long id) {
        Optional<Movimiento> movimientoOpt = this.repository.findById(id);
        if (movimientoOpt.isEmpty()) throw new EntidadNoEncontradaException(Movimiento.class, id);
        return mapper.mapearObjetoADTO(movimientoOpt.get());
    }

    public MovimientoDTO agregarMovimiento(Long idCuenta, MovimientoDTO movimientoDTO) {
        movimientoDTO.setIdCuenta(idCuenta);
        Movimiento movimientoAGuardar = mapper.mapearDTOAObjeto(movimientoDTO);
        movimientoAGuardar.setCuenta(this.cuentaService.obtenerCuenta(idCuenta));
        return mapper.mapearObjetoADTO(this.repository.save(movimientoAGuardar));
    }

    public void borrarMovimiento(Long id) {
        this.repository.deleteById(id);
    }
}
