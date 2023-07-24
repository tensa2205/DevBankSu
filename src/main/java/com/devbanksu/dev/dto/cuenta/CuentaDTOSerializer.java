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
        jsonGenerator.writeNumberField("id", cuentaDTO.getId());
        jsonGenerator.writeStringField("nombreCliente", cuentaDTO.getNombreCliente());
        jsonGenerator.writeNumberField("nroCuenta", cuentaDTO.getNroCuenta());
        jsonGenerator.writeStringField("tipo", cuentaDTO.getTipo().name());
        jsonGenerator.writeNumberField("saldoInicial", cuentaDTO.getSaldoInicial());
        jsonGenerator.writeNumberField("saldoActual", cuentaDTO.getSaldoActual());
        jsonGenerator.writeBooleanField("estado", cuentaDTO.isEstado());

        if (cuentaDTO.getMovimientos() != null) {
            jsonGenerator.writeFieldName("Movimientos");
            jsonGenerator.writeObject(cuentaDTO.getMovimientos());
        }
        jsonGenerator.writeEndObject();
    }
}
