package com.devbanksu.dev.dto.movimiento;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;

public class MovimientoDTOSerializer extends StdSerializer<MovimientoDTO> {
    public MovimientoDTOSerializer() {
        this(null);
    }

    public MovimientoDTOSerializer(Class<MovimientoDTO> t) {
        super(t);
    }

    @Override
    public void serialize(MovimientoDTO movimientoDTO, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeStartObject();
        jsonGenerator.writeStringField("fecha", movimientoDTO.getFecha().toString());
        jsonGenerator.writeNumberField("nroCuenta", movimientoDTO.getNroCuenta());
        jsonGenerator.writeStringField("tipo", movimientoDTO.getTipo().name());
        jsonGenerator.writeNumberField("valor", movimientoDTO.getValor());
        jsonGenerator.writeNumberField("saldoDisponible", movimientoDTO.getSaldoDisponible());
        jsonGenerator.writeNumberField("id", movimientoDTO.getId());
        jsonGenerator.writeEndObject();
    }


}
