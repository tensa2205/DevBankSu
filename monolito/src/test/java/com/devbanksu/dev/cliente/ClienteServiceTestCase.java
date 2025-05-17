package com.devbanksu.dev.cliente;

import com.devbanksu.dev.dto.cliente.ClienteDTO;
import com.devbanksu.dev.dto.cliente.ClienteMapper;
import com.devbanksu.dev.exceptions.EntidadNoEncontradaException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class ClienteServiceTestCase {

    @Mock
    private ClienteMapper mapper;

    @Mock
    private ClienteRepository repository;

    @InjectMocks
    private ClienteService clienteService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testObtenerCliente() {
        when(mapper.mapearDTOAObjeto(any())).thenReturn(Cliente.builder().nombre("Pedro").edad(23).identificacion("AB-123").build());
        when(clienteService.agregarCliente(any())).thenReturn(new ClienteDTO());
        when(repository.findById(1L)).thenReturn(Optional.of(Cliente.builder().id(1L).nombre("Pedro").identificacion("AB-123").build()));

        ClienteDTO dto = ClienteDTO.builder()
                .nombre("Pedro")
                .edad(23)
                .identificacion("AB-123")
                .build();
        ClienteDTO dtoAgregado = clienteService.agregarCliente(dto);
        assertNotNull(dtoAgregado);

        Cliente clienteAgregado = clienteService.obtenerCliente(1L);

        assertEquals(1L, clienteAgregado.getId());
        assertEquals("Pedro", clienteAgregado.getNombre());
        assertEquals("AB-123", clienteAgregado.getIdentificacion());
    }

    @Test
    public void testObtenerCliente_queNoExiste() {
        when(repository.findById(1L)).thenReturn(Optional.of(Cliente.builder().id(1L).nombre("Pedro").identificacion("AB-123").build()));
        Cliente clienteExistente = clienteService.obtenerCliente(1L);
        assertNotNull(clienteExistente);
        assertThrows(EntidadNoEncontradaException.class, () -> clienteService.obtenerCliente(2L));
    }

    @Test
    public void testBorrarCliente_queNoExiste() {
        assertThrows(EntidadNoEncontradaException.class, () -> clienteService.borrarCliente(1L));
    }
}
