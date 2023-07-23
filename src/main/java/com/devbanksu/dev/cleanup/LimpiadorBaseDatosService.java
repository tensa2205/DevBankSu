package com.devbanksu.dev.cleanup;

import com.devbanksu.dev.cliente.ClienteRepository;
import com.devbanksu.dev.cuenta.CuentaRepository;
import com.devbanksu.dev.movimientos.MovimientoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;


@Service
public class LimpiadorBaseDatosService {

    private static final Logger logger = LoggerFactory.getLogger(LimpiadorBaseDatosService.class);

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private CuentaRepository cuentaRepository;

    @Autowired
    private MovimientoRepository movimientoRepository;

    @Value("${database.cleanup.enabled}")
    private boolean cleanupActivado;

    public void limpiarDB() {
        String estado = cleanupActivado ? "ACTIVADO" : "DESACTIVADO";
        logger.info("El servicio para hacer cleanup de la base de datos esta {}", estado);
        if (cleanupActivado) {
            logger.warn("LIMPIANDO BASE DE DATOS");
            movimientoRepository.deleteAll();
            cuentaRepository.deleteAll();
            clienteRepository.deleteAll();
        }
    }
}
