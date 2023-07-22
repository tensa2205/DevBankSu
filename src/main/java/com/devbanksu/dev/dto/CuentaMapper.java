package com.devbanksu.dev.dto;

import com.devbanksu.dev.cuenta.Cuenta;

public class CuentaMapper {
    public static Cuenta mapearDTOACuenta(CuentaDTO dto) {
        return Cuenta.builder()
                .estado(dto.isEstado())
                .tipo(dto.getTipo())
                .saldoInicial(dto.getSaldoInicial())
                .build();

    }

    public static CuentaDTO mapearCuentaADTO(Cuenta cuenta) {
        return CuentaDTO.builder()
                .idCliente(cuenta.getCliente().getId())
                .estado(cuenta.isEstado())
                .tipo(cuenta.getTipo())
                .saldoInicial(cuenta.getSaldoInicial())
                .build();
    }
}
