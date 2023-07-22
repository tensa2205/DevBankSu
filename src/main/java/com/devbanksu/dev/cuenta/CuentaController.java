package com.devbanksu.dev.cuenta;

import com.devbanksu.dev.dto.CuentaDTO;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cuentas")
public class CuentaController {
    private final CuentaService service;

    public CuentaController(CuentaService service) {
        this.service = service;
    }

    @GetMapping()
    public List<CuentaDTO> obtenerCuentas() {
        return this.service.obtenerCuentas();
    }
    @GetMapping("/{id}")
    public CuentaDTO obtenerCuenta(@PathVariable Long id) {
        return this.service.obtenerCuenta(id);
    }

    @PostMapping("/{idCliente}")
    public CuentaDTO agregarCuenta(@PathVariable Long idCliente, @RequestBody CuentaDTO dto) {
        return this.service.agregarCuenta(idCliente, dto);
    }

    @DeleteMapping("/{id}")
    public String borrarCuenta(@PathVariable Long id) {
        this.service.borrarCuenta(id);
        return "Borrado";
    }
}
