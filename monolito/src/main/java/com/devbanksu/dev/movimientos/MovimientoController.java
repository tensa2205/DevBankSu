package com.devbanksu.dev.movimientos;

import com.devbanksu.dev.dto.movimiento.MovimientoDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    @ResponseStatus(HttpStatus.CREATED)
    public MovimientoDTO agregarMovimiento(@PathVariable Long idCuenta, @RequestBody MovimientoDTO dto) {
        return this.service.agregarMovimiento(idCuenta, dto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void borrarMovimiento(@PathVariable Long id) {
        this.service.borrarMovimiento(id);
    }
}
