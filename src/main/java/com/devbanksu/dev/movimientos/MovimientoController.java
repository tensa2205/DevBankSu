package com.devbanksu.dev.movimientos;

import com.devbanksu.dev.dto.movimiento.MovimientoDTO;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/movimientos")
public class MovimientoController {
    private final MovimientoService service;

    public MovimientoController(MovimientoService service) {
        this.service = service;
    }

    @GetMapping()
    public List<MovimientoDTO> obtenerMovimientos() {
        return this.service.obtenerMovimientos();
    }
    @GetMapping("/{id}")
    public MovimientoDTO obtenerMovimiento(@PathVariable Long id) {
        return this.service.obtenerMovimiento(id);
    }

    @PostMapping("/{idCuenta}")
    public MovimientoDTO agregarMovimiento(@PathVariable Long idCuenta, @RequestBody MovimientoDTO dto) {
        MovimientoDTO movimientoAgregado = this.service.agregarMovimiento(idCuenta, dto);
        return movimientoAgregado;
    }

    @DeleteMapping("/{id}")
    public String borrarMovimiento(@PathVariable Long id) {
        this.service.borrarMovimiento(id);
        return "Borrado";
    }
}
