package com.devbanksu.dev.reportes;

import com.devbanksu.dev.cuenta.Cuenta;
import com.devbanksu.dev.cuenta.CuentaService;
import com.devbanksu.dev.dto.cuenta.CuentaDTO;
import com.devbanksu.dev.dto.cuenta.CuentaMapper;
import com.devbanksu.dev.dto.movimiento.MovimientoDTO;
import com.devbanksu.dev.dto.movimiento.MovimientoMapper;
import com.devbanksu.dev.movimientos.Movimiento;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ReporteService {
    private static final String LOG_PREFIX = "[REPORTE_SERVICE]";
    private static final Logger logger = LoggerFactory.getLogger(ReporteService.class);
    private final ObjectMapper objectMapper;
    private final CuentaService cuentaService;
    private final CuentaMapper cuentaMapper;
    private final MovimientoMapper movimientoMapper;
    public ReporteService(CuentaService cuentaService, CuentaMapper cuentaMapper, MovimientoMapper movimientoMapper) {
        this.cuentaService = cuentaService;
        this.cuentaMapper = cuentaMapper;
        this.movimientoMapper = movimientoMapper;
        this.objectMapper = new ObjectMapper();
    }

    public String generarReporte(Long idCliente, Date inicio, Date fin) {
        logger.info("{} Intento de generacion de reporte para idCliente {}, fecha inicio: {}, fecha fin: {}", LOG_PREFIX, idCliente, inicio, fin);
        validarReporte(inicio, fin);
        List<Cuenta> cuentasDeCliente = this.cuentaService.obtenerCuentasDeCliente(idCliente);
        return convertirAJSONString(procesarCuentasYMovimientos(cuentasDeCliente, inicio, fin));
    }

    private void validarReporte(Date inicio, Date fin) {
        if(fin.before(inicio)) {
            logger.warn("{} Se recibio un rango erroneo, inicio: {} y fin: {}", LOG_PREFIX, inicio, fin);
            throw new IllegalArgumentException("La fecha de inicio no debe ser posterior a la fecha de fin");
        }
    }

    private List<CuentaDTO> procesarCuentasYMovimientos(List<Cuenta> cuentasDeCliente, Date inicio, Date fin){
        logger.info("{} Procesando {} cuentas", LOG_PREFIX, cuentasDeCliente.size());
        List<CuentaDTO> cuentas = new ArrayList<>();

        cuentasDeCliente.forEach(cuenta -> {
            CuentaDTO dto = cuentaMapper.mapearObjetoADTO(cuenta);
            dto.setMovimientos(filtrarMovimientosPorFechasYMapear(cuenta.getMovimientos(), inicio, fin));
            cuentas.add(dto);
        });
        return cuentas;
    }
    private Set<MovimientoDTO> filtrarMovimientosPorFechasYMapear(Set<Movimiento> movimientos, Date inicio, Date fin) {
        return filtrarMovimientosPorFechas(movimientos, inicio, fin)
                .stream()
                .map(movimientoMapper::mapearObjetoADTO)
                .sorted(Comparator.comparing(MovimientoDTO::getFecha).reversed())
                .collect(Collectors.toCollection(LinkedHashSet::new));
    }

    private Set<Movimiento> filtrarMovimientosPorFechas(Set<Movimiento> movimientos, Date inicio, Date fin) {
        return movimientos.stream().filter(m -> m.getFecha().compareTo(inicio) >= 0 && m.getFecha().compareTo(fin) <= 0).collect(Collectors.toSet());
    }

    private String convertirAJSONString(List<CuentaDTO> cuentas) {
        try {
            return objectMapper.writeValueAsString(cuentas);
        } catch (JsonProcessingException e) {
            logger.error(String.format("%s Error al convertir objeto a JSON", LOG_PREFIX), e);
        }
        return null;
    }
}
