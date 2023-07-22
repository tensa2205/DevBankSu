package com.devbanksu.dev.dto.cuenta;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;

public class CuentaDTOSerializer extends StdSerializer<CuentaDTO> {

    public CuentaDTOSerializer() {
        this(null);
    }

    public CuentaDTOSerializer(Class<CuentaDTO> t) {
        super(t);
    }

    @Override
    public void serialize(CuentaDTO cuentaDTO, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeStartObject();
        jsonGenerator.writeStringField("Cliente", cuentaDTO.getNombreCliente());
        jsonGenerator.writeNumberField("Numero de Cuenta", cuentaDTO.getNroCuenta());
        jsonGenerator.writeStringField("Tipo de cuenta", cuentaDTO.getTipo().name());
        jsonGenerator.writeNumberField("Saldo Inicial", cuentaDTO.getSaldoInicial());
        jsonGenerator.writeNumberField("Saldo Disponible", cuentaDTO.getSaldoActual());
        jsonGenerator.writeBooleanField("Estado", cuentaDTO.isEstado());

        if (cuentaDTO.getMovimientos() != null) {
            jsonGenerator.writeFieldName("Movimientos");
            jsonGenerator.writeObject(cuentaDTO.getMovimientos());
        }
        jsonGenerator.writeEndObject();
    }
}
