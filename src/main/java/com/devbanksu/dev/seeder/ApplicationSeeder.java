package com.devbanksu.dev.seeder;

import com.devbanksu.dev.cliente.Cliente;
import com.devbanksu.dev.cliente.ClienteRepository;
import com.devbanksu.dev.cuenta.Cuenta;
import com.devbanksu.dev.cuenta.CuentaRepository;
import com.devbanksu.dev.cuenta.TipoCuenta;
import com.devbanksu.dev.movimientos.Movimiento;
import com.devbanksu.dev.movimientos.MovimientoRepository;
import com.devbanksu.dev.movimientos.TipoMovimiento;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;

@Component
public class ApplicationSeeder implements CommandLineRunner {
    private final ClienteRepository clienteRepository;
    private final CuentaRepository cuentaRepository;
    private final MovimientoRepository movimientoRepository;



    public ApplicationSeeder(ClienteRepository clienteRepository, CuentaRepository cuentaRepository, MovimientoRepository movimientoRepository){
        this.clienteRepository = clienteRepository;
        this.cuentaRepository = cuentaRepository;
        this.movimientoRepository = movimientoRepository;
    }
    @Override
    public void run(String... args) throws Exception {
        Cliente cliente1 = new Cliente("Jose Lema", "M", 25, "ID-1992", "Otavalo sn y principal", "098254785", "1234", true);
        Cliente cliente2 = new Cliente("Marianela Montalvo", "F", 24, "ID-1995", "Amazonas y NNUU", "097548965", "5678", true);
        Cliente cliente3 = new Cliente("Juan Osorio", "M", 27, "ID-1989", "13 junio y Equinoccial", "098874587", "1245", true);

        cliente1 = clienteRepository.save(cliente1);
        cliente2 = clienteRepository.save(cliente2);
        cliente3 = clienteRepository.save(cliente3);


        Cuenta cuenta1 = new Cuenta(cliente1, 478758L, TipoCuenta.AHORRO, BigDecimal.valueOf(2000), true);
        Cuenta cuenta2 = new Cuenta(cliente2, 225487L, TipoCuenta.CORRIENTE, BigDecimal.valueOf(100L), true);
        Cuenta cuenta3 = new Cuenta(cliente3, 495878L, TipoCuenta.AHORRO, BigDecimal.ZERO, true);
        Cuenta cuenta4 = new Cuenta(cliente2, 496825L, TipoCuenta.AHORRO, BigDecimal.valueOf(540L), true);
        Cuenta cuenta5 = new Cuenta(cliente1, 585545L, TipoCuenta.CORRIENTE, BigDecimal.valueOf(1000L), true);

        cuenta1 = cuentaRepository.save(cuenta1);
        cuenta2 = cuentaRepository.save(cuenta2);
        cuenta3 = cuentaRepository.save(cuenta3);
        cuenta4 = cuentaRepository.save(cuenta4);
        cuenta5 = cuentaRepository.save(cuenta5);

        Movimiento movimiento1 = new Movimiento(cuenta1, Date.valueOf("2023-07-28"), TipoMovimiento.RETIRO, BigDecimal.valueOf(575L), BigDecimal.valueOf(1425L));
        Movimiento movimiento2 = new Movimiento(cuenta2, Date.valueOf("2023-08-01"), TipoMovimiento.DEPOSITO, BigDecimal.valueOf(600L), BigDecimal.valueOf(700L));
        Movimiento movimiento3 = new Movimiento(cuenta3, Date.valueOf("2023-07-22"), TipoMovimiento.DEPOSITO, BigDecimal.valueOf(150L), BigDecimal.valueOf(150L));
        Movimiento movimiento4 = new Movimiento(cuenta4, Date.valueOf("2023-08-15"), TipoMovimiento.RETIRO, BigDecimal.valueOf(540L), BigDecimal.ZERO);

        movimientoRepository.saveAll(List.of(movimiento1, movimiento2, movimiento3, movimiento4));
    }

    /*
INSERT INTO cliente (id, nombre, genero, edad, identificacion, direccion, telefono, password, estado)
VALUES  (1, 'Jose Lema', 'M', 25, 'ID-1992', 'Otavalo sn y principal', '098254785', '1234', true),
        (2, 'Marianela Montalvo', 'F' 24, 'ID-1995', 'Amazonas y NNUU', '097548965', '5678',true),
        (3, 'Juan Osorio', 'M', 27, 'ID-1989', '13 junio y Equinoccial', '098874587', '1245', true);

INSERT INTO cuenta (id, cliente_id, nroCuenta, tipo, saldoInicial, saldoActual, estado)
VALUES  (1, 1, 478758, 'AHORRO', 2000, 2000, true),
        (2, 2, 225487, 'CORRIENTE', 100, 100, true),
        (3, 3, 495878, 'AHORRO', 0, 0, true),
        (4, 2, 496825, 'AHORRO', 540, 540, true),
        (5, 1, 585545, 'CORRIENTE', 1000, 1000, true);

INSERT INTO movimiento (id, cuenta_id, fecha, tipo, valor, saldoDisponible)
VALUES (1, 1, '2023-07-28', 'RETIRO', 575, 1425),
       (2, 2, '2023-08-01', 'DEPOSITO', 600, 700),
       (3, 3, '2023-07-23', 'DEPOSITO', 150, 150),
       (4, 4, '2023-08-15', 'RETIRO', 540, 0);
        */
}
