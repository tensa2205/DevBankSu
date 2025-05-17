package com.devbanksu.dev.movimiento;

import com.devbanksu.dev.cliente.Cliente;
import com.devbanksu.dev.cliente.ClienteService;
import com.devbanksu.dev.cuenta.Cuenta;
import com.devbanksu.dev.cuenta.CuentaService;
import com.devbanksu.dev.cuenta.TipoCuenta;
import com.devbanksu.dev.dto.cliente.ClienteDTO;
import com.devbanksu.dev.dto.cuenta.CuentaDTO;
import com.devbanksu.dev.dto.movimiento.MovimientoDTO;
import com.devbanksu.dev.dto.movimiento.MovimientoMapper;
import com.devbanksu.dev.exceptions.LimiteDiarioException;
import com.devbanksu.dev.movimientos.MovimientoRepository;
import com.devbanksu.dev.movimientos.MovimientoService;
import com.devbanksu.dev.movimientos.TipoMovimiento;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@AutoConfigureTestDatabase
@Transactional
public class MovimientoIntegrationTest {

    @Autowired
    private MovimientoRepository repository;

    @Autowired
    private MovimientoService service;

    @Autowired
    private MovimientoMapper mapper;

    @Autowired
    private ClienteService clienteService;
    @Autowired
    private CuentaService cuentaService;

    private Cuenta cuenta;

    @BeforeEach
    public void setUp() {
        ClienteDTO dtoCliente = ClienteDTO
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
        clienteService.agregarCliente(dtoCliente);
        Cliente cliente = clienteService.obtenerClientePorIdentificacion("ABC-421");

        CuentaDTO dtoCuenta = CuentaDTO
                .builder()
                .nombreCliente(cliente.getNombre())
                .saldoInicial(BigDecimal.valueOf(1500L))
                .tipo(TipoCuenta.AHORRO)
                .nroCuenta(155233L)
                .estado(true)
                .build();

        cuentaService.agregarCuenta(cliente.getId(), dtoCuenta);
        cuenta = cuentaService.obtenerCuentaPorNumeroDeCuenta(155233L);
    }

    @Test
    public void testAgregarDepositoCorrecto() {
        MovimientoDTO dto = MovimientoDTO
                .builder()
                .fecha(new Date())
                .valor(BigDecimal.valueOf(850))
                .tipo(TipoMovimiento.DEPOSITO)
                .build();

        MovimientoDTO dtoResul = service.agregarMovimiento(cuenta.getId(), dto);
        assertEquals(BigDecimal.valueOf(2350L), dtoResul.getSaldoDisponible());
    }

    @Test
    public void testAgregarOperacionErronea_valorInvalido() {
        MovimientoDTO dto = MovimientoDTO
                .builder()
                .fecha(new Date())
                .valor(BigDecimal.valueOf(-150))
                .tipo(TipoMovimiento.RETIRO)
                .build();

        assertThrows(IllegalArgumentException.class, () -> service.agregarMovimiento(cuenta.getId(), dto));
    }

    @Test
    public void testRetiroErroneo_excedeLimiteDiario() {
        MovimientoDTO dto = MovimientoDTO
                .builder()
                .fecha(new Date())
                .valor(BigDecimal.valueOf(1500))
                .tipo(TipoMovimiento.RETIRO)
                .build();

        assertThrows(LimiteDiarioException.class, () -> service.agregarMovimiento(cuenta.getId(), dto));
    }

    @Test
    public void testRetiroErroneo_excedeLimiteDiarioDespuesDeVariasOperaciones() {
        MovimientoDTO op1 = MovimientoDTO
                .builder()
                .fecha(new Date())
                .valor(BigDecimal.valueOf(750))
                .tipo(TipoMovimiento.RETIRO)
                .build();

        MovimientoDTO dtoResul = service.agregarMovimiento(cuenta.getId(), op1);
        assertEquals(BigDecimal.valueOf(750), dtoResul.getSaldoDisponible());

        MovimientoDTO op2 = MovimientoDTO
                .builder()
                .fecha(new Date())
                .valor(BigDecimal.valueOf(200))
                .tipo(TipoMovimiento.RETIRO)
                .build();

        dtoResul = service.agregarMovimiento(cuenta.getId(), op2);
        assertEquals(BigDecimal.valueOf(550), dtoResul.getSaldoDisponible());

        MovimientoDTO op3Erronea = MovimientoDTO
                .builder()
                .fecha(new Date())
                .valor(BigDecimal.valueOf(55))
                .tipo(TipoMovimiento.RETIRO)
                .build();

        assertThrows(LimiteDiarioException.class, () -> service.agregarMovimiento(cuenta.getId(), op3Erronea));
    }
}
