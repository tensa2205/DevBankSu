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
        jsonGenerator.writeNumberField("ID Movimiento", movimientoDTO.getId());
        jsonGenerator.writeStringField("Fecha", movimientoDTO.getFecha().toString());
        jsonGenerator.writeStringField("Tipo", movimientoDTO.getTipo().name());
        jsonGenerator.writeNumberField("Valor", movimientoDTO.getValor());
        jsonGenerator.writeNumberField("Saldo Disponible", movimientoDTO.getSaldoDisponible());
        jsonGenerator.writeEndObject();
    }


}
