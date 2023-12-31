package com.devbanksu.dev.movimientos;

import com.devbanksu.dev.cuenta.CuentaService;
import com.devbanksu.dev.dto.movimiento.MovimientoDTO;
import com.devbanksu.dev.dto.movimiento.MovimientoMapper;
import com.devbanksu.dev.exceptions.EntidadNoEncontradaException;
import com.devbanksu.dev.exceptions.LimiteDiarioException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class MovimientoService {
    private static final String LOG_PREFIX = "[MOVIMIENTO_SERVICE]";
    private static final Logger logger = LoggerFactory.getLogger(MovimientoService.class);
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
        logger.info("{} Hay un total de {} movimientos en el sistema", LOG_PREFIX, movimientos.size());
        return movimientos.stream().map(mapper::mapearObjetoADTO).toList();
    }

    public MovimientoDTO obtenerMovimiento(Long id) {
        Optional<Movimiento> movimientoOpt = this.repository.findById(id);
        if (movimientoOpt.isEmpty()) {
            logger.warn("{} Se ha intentado buscar el movimiento con ID {} pero no ha sido encontrado", LOG_PREFIX, id);
            throw new EntidadNoEncontradaException(Movimiento.class, id);
        }
        return mapper.mapearObjetoADTO(movimientoOpt.get());
    }

    public void borrarMovimiento(Long id) {
        logger.warn("{} Eliminando movimiento con ID {}", LOG_PREFIX, id);
        this.repository.deleteById(id);
    }

    public MovimientoDTO agregarMovimiento(Long idCuenta, MovimientoDTO movimientoDTO) {
        Movimiento movimientoAGuardar = mapper.mapearDTOAObjeto(movimientoDTO);
        validarDTO(idCuenta, movimientoDTO);
        movimientoAGuardar.setCuenta(this.cuentaService.realizarMovimientoSobreCuenta(idCuenta, movimientoDTO));
        movimientoAGuardar.setSaldoDisponible(movimientoAGuardar.getCuenta().getSaldoActual());
        return mapper.mapearObjetoADTO(this.repository.save(movimientoAGuardar));
    }

    private void validarDTO(Long idCuenta, MovimientoDTO dto) {
        if (dto.getValor().compareTo(BigDecimal.ZERO) <= 0) {
            logger.warn("{} Se ha recibido un movimiento para la cuenta {} con un valor erroneo: {}", LOG_PREFIX, idCuenta, dto.getValor());
            throw new IllegalArgumentException("Valor debe ser más grande que cero");
        }
        if (noPuedeSeguirRetirando(idCuenta, dto)) {
            logger.warn("{} La cuenta con id {} no puede hacer más retiros por el dia de hoy: {}", LOG_PREFIX, idCuenta, dto.getFecha());
            throw new LimiteDiarioException();
        }
    }

    private boolean noPuedeSeguirRetirando(Long idCuenta, MovimientoDTO dto) {
        if (!dto.getTipo().tieneLimiteDiario()) return false;
        BigDecimal sumaDeRetirosDeLaFecha = this.repository.obtenerRetirosEnFecha(idCuenta, dto.getFecha());
        return excedeLimiteDiario(sumaDeRetirosDeLaFecha) || excedeLimiteDiario(sumaDeRetirosDeLaFecha.add(dto.getValor()));
    }

    private boolean excedeLimiteDiario(BigDecimal valor) {
        return valor.compareTo(LIMITE_DIARIO_RETIRO) > 0;
    }
}
