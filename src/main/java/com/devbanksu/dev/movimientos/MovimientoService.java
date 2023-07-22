package com.devbanksu.dev.movimientos;

import com.devbanksu.dev.cuenta.CuentaService;
import com.devbanksu.dev.dto.movimiento.MovimientoDTO;
import com.devbanksu.dev.dto.movimiento.MovimientoMapper;
import com.devbanksu.dev.exceptions.EntidadNoEncontradaException;
import com.devbanksu.dev.exceptions.LimiteDiarioException;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class MovimientoService {
    private static final BigDecimal LIMITE_DIARIO_RETIRO = BigDecimal.valueOf(1000L);
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
        Movimiento movimientoAGuardar = mapper.mapearDTOAObjeto(movimientoDTO);
        if (noPuedoSeguirRetirando(idCuenta, movimientoDTO)) throw new LimiteDiarioException();
        movimientoAGuardar.setCuenta(this.cuentaService.realizarMovimientoSobreCuenta(idCuenta, movimientoDTO));
        movimientoAGuardar.setSaldoDisponible(movimientoAGuardar.getCuenta().getSaldoActual());
        return mapper.mapearObjetoADTO(this.repository.save(movimientoAGuardar));
    }

    private boolean noPuedoSeguirRetirando(Long idCuenta, MovimientoDTO dto) {
        if (!dto.getTipo().tieneLimiteDiario()) return false;
        BigDecimal sumaDeRetirosDeLaFecha = this.repository.obtenerRetirosEnFecha(idCuenta, dto.getFecha());
        return excedeLimiteDiario(sumaDeRetirosDeLaFecha) || excedeLimiteDiario(sumaDeRetirosDeLaFecha.add(dto.getValor()));
    }

    private boolean excedeLimiteDiario(BigDecimal valor) {
        return valor.compareTo(LIMITE_DIARIO_RETIRO) > 0;
    }

    public void borrarMovimiento(Long id) {
        this.repository.deleteById(id);
    }
}
