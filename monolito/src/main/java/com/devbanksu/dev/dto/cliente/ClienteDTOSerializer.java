package com.devbanksu.dev.dto.cliente;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;

public class ClienteDTOSerializer extends StdSerializer<ClienteDTO> {
    public ClienteDTOSerializer() {
        this(null);
    }

    public ClienteDTOSerializer(Class<ClienteDTO> t) {
        super(t);
    }

    @Override
    public void serialize(ClienteDTO clienteDTO, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeStartObject();
        jsonGenerator.writeNumberField("id", clienteDTO.getId());
        jsonGenerator.writeStringField("nombre", clienteDTO.getNombre());
        jsonGenerator.writeStringField("genero", clienteDTO.getGenero());
        jsonGenerator.writeNumberField("edad", clienteDTO.getEdad());
        jsonGenerator.writeStringField("identificacion", clienteDTO.getIdentificacion());
        jsonGenerator.writeStringField("direccion", clienteDTO.getDireccion());
        jsonGenerator.writeStringField("telefono", clienteDTO.getTelefono());
        jsonGenerator.writeBooleanField("estado", clienteDTO.isEstado());
        jsonGenerator.writeEndObject();
    }
}
