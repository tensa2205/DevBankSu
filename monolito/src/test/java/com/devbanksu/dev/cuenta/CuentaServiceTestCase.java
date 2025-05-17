package com.devbanksu.dev.cuenta;

import com.devbanksu.dev.cliente.Cliente;
import com.devbanksu.dev.cliente.ClienteRepository;
import com.devbanksu.dev.cliente.ClienteService;
import com.devbanksu.dev.dto.cuenta.CuentaDTO;
import com.devbanksu.dev.dto.cuenta.CuentaMapper;
import com.devbanksu.dev.exceptions.EntidadNoEncontradaException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class CuentaServiceTestCase {

    @Mock
    private CuentaMapper mapper;

    @Mock
    private CuentaRepository repository;

    @Mock
    private ClienteRepository clienteRepository;

    @Mock
    private ClienteService clienteService;

    @InjectMocks
    private CuentaService cuentaService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testObtenerCuenta() {
        Cuenta cuenta = Cuenta.builder()
                .nroCuenta(41235L)
                .tipo(TipoCuenta.CORRIENTE)
                .cliente(new Cliente())
                .build();
        when(mapper.mapearDTOAObjeto(any()))
                .thenReturn(cuenta);

        when(cuentaService.agregarCuenta(1L, any())).thenReturn(new CuentaDTO());
        when(repository.findById(1L)).thenReturn(Optional.of(Cuenta.builder()
                    .id(1L)
                    .nroCuenta(cuenta.getNroCuenta())
                    .tipo(cuenta.getTipo())
                    .cliente(cuenta.getCliente())
                    .build()));

        CuentaDTO dto = CuentaDTO.builder()
                .nroCuenta(41235L)
                .tipo(TipoCuenta.CORRIENTE)
                .saldoActual(BigDecimal.valueOf(1000))
                .nombreCliente("Pedro")
                .build();

        CuentaDTO cuentaDTO = cuentaService.agregarCuenta(1L, dto);
        assertNotNull(cuentaDTO);

        Cuenta agregado = cuentaService.obtenerCuenta(1L);
        assertEquals(1L, agregado.getId());
        assertEquals(TipoCuenta.CORRIENTE, agregado.getTipo());
    }

    @Test
    public void testObtenerCuenta_queNoExiste() {
        when(repository.findById(1L)).thenReturn(Optional.of(Cuenta.builder().id(1L).nroCuenta(43123L).tipo(TipoCuenta.CORRIENTE).build()));
        Cuenta cuentaExistente = cuentaService.obtenerCuenta(1L);
        assertNotNull(cuentaExistente);
        assertThrows(EntidadNoEncontradaException.class, () -> cuentaService.obtenerCuenta(2L));
    }

    @Test
    public void testBorrarCuenta_queNoExiste() {
        assertThrows(EntidadNoEncontradaException.class, () -> cuentaService.borrarCuenta(1L));
    }


}
