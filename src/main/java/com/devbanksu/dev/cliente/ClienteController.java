package com.devbanksu.dev.cliente;

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
    public List<Cliente> obtenerClientes() {
        return this.service.obtenerClientes();
    }
    @GetMapping("/{id}")
    public Cliente obtenerCliente(@PathVariable Long id) {
        return this.service.obtenerCliente(id);
    }

    @PostMapping()
    public Cliente agregarCliente(@RequestBody Cliente cliente) {
        Cliente clienteAgregado = this.service.agregarCliente(cliente);
        return clienteAgregado;
    }

    @DeleteMapping("/{id}")
    public String borrarCliente(@PathVariable Long id) {
        this.service.borrarCliente(id);
        return "Borrado";
    }
}
