package com.devbanksu.dev.cuenta;

import com.devbanksu.dev.dto.cuenta.CuentaDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
        return this.service.obtenerCuentasDTO();
    }
    @GetMapping("/{id}")
    public CuentaDTO obtenerCuenta(@PathVariable Long id) {
        return this.service.obtenerCuentaDTO(id);
    }

    @PostMapping("/{idCliente}")
    @ResponseStatus(HttpStatus.CREATED)
    public CuentaDTO agregarCuenta(@PathVariable Long idCliente, @RequestBody CuentaDTO dto) {
        return this.service.agregarCuenta(idCliente, dto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void borrarCuenta(@PathVariable Long id) {
        this.service.borrarCuenta(id);
    }

    @PutMapping("/{id}")
    public CuentaDTO actualizarCuenta(@PathVariable Long id, @RequestBody CuentaDTO dto) {
        return this.service.actualizarCuenta(id, dto);
    }
}
