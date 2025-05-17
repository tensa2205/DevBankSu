package com.devbanksu.dev.dto.movimiento;

import com.devbanksu.dev.dto.Mapper;
import com.devbanksu.dev.dto.movimiento.MovimientoDTO;
import com.devbanksu.dev.movimientos.Movimiento;
import org.springframework.stereotype.Component;

@Component
public class MovimientoMapper implements Mapper<Movimiento, MovimientoDTO> {

    @Override
    public Movimiento mapearDTOAObjeto(MovimientoDTO movimientoDTO) {
        return Movimiento.builder()
                .tipo(movimientoDTO.getTipo())
                .fecha(movimientoDTO.getFecha())
                .valor(movimientoDTO.getValor())
                .saldoDisponible(movimientoDTO.getSaldoDisponible())
                .build();
    }

    @Override
    public MovimientoDTO mapearObjetoADTO(Movimiento objeto) {
        return MovimientoDTO.builder()
                .id(objeto.getId())
                .tipo(objeto.getTipo())
                .fecha(objeto.getFecha())
                .valor(objeto.getValor())
                .nroCuenta(objeto.getCuenta().getNroCuenta())
                .saldoDisponible(objeto.getSaldoDisponible())
                .build();
    }
}
