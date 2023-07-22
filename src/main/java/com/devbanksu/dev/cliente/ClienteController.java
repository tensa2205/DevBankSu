package com.devbanksu.dev.cliente;

import com.devbanksu.dev.dto.cliente.ClienteDTO;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/clientes")
public class ClienteController {
    private final ClienteService service;

    public ClienteController(ClienteService service) {
        this.service = service;
    }

    @GetMapping()
    public List<ClienteDTO> obtenerClientes() {
        return this.service.obtenerClientes();
    }
    @GetMapping("/{id}")
    public ClienteDTO obtenerCliente(@PathVariable Long id) {
        return this.service.obtenerClienteDTO(id);
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public ClienteDTO agregarCliente(@RequestBody ClienteDTO dto) {
        return this.service.agregarCliente(dto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void borrarCliente(@PathVariable Long id) {
        this.service.borrarCliente(id);
    }
}
