package com.devbanksu.dev.dto.cuenta;

import com.devbanksu.dev.cuenta.Cuenta;
import com.devbanksu.dev.dto.Mapper;
import com.devbanksu.dev.dto.cuenta.CuentaDTO;
import org.springframework.stereotype.Component;

@Component
public class CuentaMapper implements Mapper<Cuenta, CuentaDTO> {
    @Override
    public Cuenta mapearDTOAObjeto(CuentaDTO cuentaDTO) {
        return Cuenta.builder()
                .estado(cuentaDTO.isEstado())
                .tipo(cuentaDTO.getTipo())
                .saldoInicial(cuentaDTO.getSaldoInicial())
                .build();
    }

    @Override
    public CuentaDTO mapearObjetoADTO(Cuenta objeto) {
        return CuentaDTO.builder()
                .idCliente(objeto.getCliente().getId())
                .estado(objeto.isEstado())
                .tipo(objeto.getTipo())
                .saldoInicial(objeto.getSaldoInicial())
                .build();
    }
}
