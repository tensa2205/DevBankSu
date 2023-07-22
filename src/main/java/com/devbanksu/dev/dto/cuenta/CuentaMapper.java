package com.devbanksu.dev.dto.cuenta;

import com.devbanksu.dev.cuenta.Cuenta;
import com.devbanksu.dev.dto.Mapper;
import org.springframework.stereotype.Component;

@Component
public class CuentaMapper implements Mapper<Cuenta, CuentaDTO> {
    @Override
    public Cuenta mapearDTOAObjeto(CuentaDTO cuentaDTO) {
        return Cuenta.builder()
                .nroCuenta(cuentaDTO.getNroCuenta())
                .estado(cuentaDTO.isEstado())
                .tipo(cuentaDTO.getTipo())
                .saldoInicial(cuentaDTO.getSaldoInicial())
                .saldoActual(cuentaDTO.getSaldoInicial())
                .build();
    }

    @Override
    public CuentaDTO mapearObjetoADTO(Cuenta objeto) {
        return CuentaDTO.builder()
                .nroCuenta(objeto.getNroCuenta())
                .nombreCliente(objeto.getCliente().getNombre())
                .estado(objeto.isEstado())
                .tipo(objeto.getTipo())
                .saldoInicial(objeto.getSaldoInicial())
                .saldoActual(objeto.getSaldoActual())
                .build();
    }
}
