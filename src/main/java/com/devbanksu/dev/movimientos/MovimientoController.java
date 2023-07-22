package com.devbanksu.dev.movimientos;

import com.devbanksu.dev.cliente.Cliente;
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
    public List<Movimiento> obtenerMovimientos() {
        return this.service.obtenerMovimientos();
    }
    @GetMapping("/{id}")
    public Movimiento obtenerMovimiento(@PathVariable Long id) {
        return this.service.obtenerMovimiento(id);
    }

    @PostMapping()
    public Movimiento agregarMovimiento(@RequestBody Movimiento movimiento) {
        Movimiento movimientoAgregado = this.service.agregarMovimiento(movimiento);
        return movimientoAgregado;
    }

    @DeleteMapping("/{id}")
    public String borrarMovimiento(@PathVariable Long id) {
        this.service.borrarMovimiento(id);
        return "Borrado";
    }
}
