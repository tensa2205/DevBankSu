package com.devbanksu.dev.cliente;

import com.devbanksu.dev.dto.cliente.ClienteDTO;
import com.devbanksu.dev.dto.cliente.ClienteMapper;
import com.devbanksu.dev.exceptions.EntidadNoEncontradaException;
import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureTestDatabase
@Transactional
public class ClienteIntegrationTest {

    @Autowired
    private ClienteRepository repository;

    @Autowired
    private ClienteService service;

    @Autowired
    private ClienteMapper mapper;

    ClienteDTO dtoCorrecto;
    
    @BeforeEach
    public void setUp() {
        dtoCorrecto = ClienteDTO
                .builder()
                .nombre("Pedro Samsagaz")
                .identificacion("ABC-421")
                .edad(23)
                .estado(true)
                .telefono("+5142233")
                .password("12345")
                .direccion("Calle Falsa 123")
                .genero("Masculino")
                .build();
    }

    @Test
    public void testCrearCliente() {
        service.agregarCliente(dtoCorrecto);
        List<Cliente> clientes = service.obtenerClientes();

        assertEquals(1, clientes.size());
        Cliente cliente = clientes.get(0);
        assertClienteCreado(cliente);
    }

    @Test
    public void testCrearCliente_datosIncompletos() {
        ClienteDTO dtoIncorrecto = ClienteDTO
                .builder()
                .nombre("Pedro Falso")
                .identificacion("ABC-090")
                .edad(99)
                .estado(false)
                .build();
        assertThrows(ConstraintViolationException.class, () -> service.agregarCliente(dtoIncorrecto));
    }
    
    @Test
    public void testObtenerClientePorIdentificacion() {
        service.agregarCliente(dtoCorrecto);
        Cliente cliente = service.obtenerClientePorIdentificacion(dtoCorrecto.getIdentificacion());
        assertNotNull(cliente);
        assertClienteCreado(cliente);
    }

    @Test
    public void testObtenerClientePorId_noExiste() {
        assertThrows(EntidadNoEncontradaException.class, () -> service.obtenerCliente(1L));
    }

    @Test
    public void testMapeoADTO() {
        service.agregarCliente(dtoCorrecto);
        Cliente cliente = service.obtenerClientePorIdentificacion(dtoCorrecto.getIdentificacion());
        ClienteDTO clienteMapeado = mapper.mapearObjetoADTO(cliente);
        ClienteDTO clienteMapeadoDesdeService = service.obtenerClienteDTO(cliente.getId());
        assertEquals(clienteMapeado.toString(), clienteMapeadoDesdeService.toString());
    }

    @Test
    public void testBorrarCliente_noExiste() {
        assertThrows(EntidadNoEncontradaException.class, () -> service.borrarCliente(100L));
    }

    @Test
    public void testBorrarCliente() {
        service.agregarCliente(dtoCorrecto);
        Cliente cliente = service.obtenerClientePorIdentificacion(dtoCorrecto.getIdentificacion());
        service.borrarCliente(cliente.getId());
        assertThrows(EntidadNoEncontradaException.class, () -> service.obtenerCliente(cliente.getId()));
    }

    private void assertClienteCreado(Cliente cliente) {
        assertEquals(23, cliente.getEdad());
        assertEquals("Pedro Samsagaz", cliente.getNombre());
        assertEquals("ABC-421", cliente.getIdentificacion());
        assertEquals("Masculino", cliente.getGenero());
        assertEquals("12345", cliente.getPassword());
    }
}
