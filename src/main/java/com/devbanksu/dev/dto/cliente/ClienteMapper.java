package com.devbanksu.dev.dto.cliente;

import com.devbanksu.dev.cliente.Cliente;
import com.devbanksu.dev.dto.Mapper;
import org.springframework.stereotype.Component;

@Component
public class ClienteMapper implements Mapper<Cliente, ClienteDTO> {
    @Override
    public Cliente mapearDTOAObjeto(ClienteDTO clienteDTO) {
        return Cliente.builder()
                .nombre(clienteDTO.getNombre())
                .edad(clienteDTO.getEdad())
                .genero(clienteDTO.getGenero())
                .identificacion(clienteDTO.getIdentificacion())
                .direccion(clienteDTO.getDireccion())
                .telefono(clienteDTO.getTelefono())
                .password(clienteDTO.getPassword())
                .estado(clienteDTO.isEstado())
                .build();
    }

    @Override
    public ClienteDTO mapearObjetoADTO(Cliente objeto) {
        return ClienteDTO.builder()
                .id(objeto.getId())
                .nombre(objeto.getNombre())
                .edad(objeto.getEdad())
                .genero(objeto.getGenero())
                .identificacion(objeto.getIdentificacion())
                .direccion(objeto.getDireccion())
                .telefono(objeto.getTelefono())
                .password(objeto.getPassword())
                .estado(objeto.isEstado())
                .build();
    }
}
