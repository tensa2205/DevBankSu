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
        jsonGenerator.writeNumberField("ID Cliente", clienteDTO.getId());
        jsonGenerator.writeStringField("Nombre", clienteDTO.getNombre());
        jsonGenerator.writeStringField("Genero", clienteDTO.getGenero());
        jsonGenerator.writeNumberField("Edad", clienteDTO.getEdad());
        jsonGenerator.writeStringField("Identificacion", clienteDTO.getIdentificacion());
        jsonGenerator.writeStringField("Direccion", clienteDTO.getDireccion());
        jsonGenerator.writeStringField("Telefono", clienteDTO.getTelefono());
        jsonGenerator.writeBooleanField("Estado", clienteDTO.isEstado());
        jsonGenerator.writeEndObject();
    }
}
