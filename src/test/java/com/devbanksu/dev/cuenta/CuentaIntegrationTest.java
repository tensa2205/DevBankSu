package com.devbanksu.dev.cuenta;

import com.devbanksu.dev.cliente.Cliente;
import com.devbanksu.dev.cliente.ClienteService;
import com.devbanksu.dev.dto.cliente.ClienteDTO;
import com.devbanksu.dev.dto.cuenta.CuentaDTO;
import com.devbanksu.dev.dto.cuenta.CuentaMapper;
import com.devbanksu.dev.exceptions.EntidadNoEncontradaException;
import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@AutoConfigureTestDatabase
@Transactional
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class CuentaIntegrationTest {

    @Autowired
    private ClienteService clienteService;

    @Autowired
    private CuentaRepository repository;

    @Autowired
    private CuentaService service;

    @Autowired
    private CuentaMapper mapper;

    private Cliente cliente;

    @BeforeEach
    public void setUp() {
        ClienteDTO dtoCorrecto = ClienteDTO
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
        clienteService.agregarCliente(dtoCorrecto);
        List<Cliente> clientes = clienteService.obtenerClientes();
        cliente = clientes.get(0);
    }

    @Test
    public void testCrearCuenta() {
        CuentaDTO dto = CuentaDTO
                .builder()
                .nombreCliente(cliente.getNombre())
                .saldoInicial(BigDecimal.valueOf(1500L))
                .tipo(TipoCuenta.AHORRO)
                .nroCuenta(155233L)
                .estado(true)
                .build();
        CuentaDTO cuentaCreada = service.agregarCuenta(cliente.getId(), dto);
        assertEquals(dto.getSaldoInicial(), cuentaCreada.getSaldoActual());
    }

    @Test
    public void testCrearCuenta_datosIncompletos() {
        CuentaDTO dtoIncorrecto = CuentaDTO
                .builder()
                .nombreCliente(cliente.getNombre())
                .build();
        assertThrows(ConstraintViolationException.class, () -> service.agregarCuenta(cliente.getId(), dtoIncorrecto));
    }

    @Test
    public void testMapeoADTO() {
        CuentaDTO dto = CuentaDTO
                .builder()
                .nombreCliente(cliente.getNombre())
                .saldoInicial(BigDecimal.valueOf(1500L))
                .tipo(TipoCuenta.AHORRO)
                .nroCuenta(155233L)
                .estado(true)
                .build();
        service.agregarCuenta(cliente.getId(), dto);
        Cuenta cuenta = service.obtenerCuentaPorNumeroDeCuenta(155233L);
        CuentaDTO cuentaMapeada = mapper.mapearObjetoADTO(cuenta);
        CuentaDTO cuentaMapeadaDesdeService = service.obtenerCuentaDTO(cuenta.getId());
        assertEquals(cuentaMapeada.toString(), cuentaMapeadaDesdeService.toString());
    }

    @Test
    public void testBorrarCliente_noExiste() {
        assertThrows(EntidadNoEncontradaException.class, () -> service.borrarCuenta(100L));
    }

    @Test
    public void testBorrarCliente() {
        CuentaDTO dto = CuentaDTO
                .builder()
                .nombreCliente(cliente.getNombre())
                .saldoInicial(BigDecimal.valueOf(1500L))
                .tipo(TipoCuenta.AHORRO)
                .nroCuenta(155233L)
                .estado(true)
                .build();
        service.agregarCuenta(cliente.getId(), dto);
        Cuenta cuenta = service.obtenerCuentaPorNumeroDeCuenta(155233L);
        service.borrarCuenta(cuenta.getId());
        assertThrows(EntidadNoEncontradaException.class, () -> service.obtenerCuenta(cuenta.getId()));
    }
}
