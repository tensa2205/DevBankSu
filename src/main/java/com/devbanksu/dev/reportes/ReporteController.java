package com.devbanksu.dev.reportes;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping("/reportes")
public class ReporteController {
    private final ReporteService service;

    public ReporteController(ReporteService service) {
        this.service = service;
    }

    @GetMapping("/{idCliente}")
    public String devolverReporte(@PathVariable Long idCliente,
                                  @RequestParam("inicio") @DateTimeFormat(pattern = "yyyy-MM-dd") Date inicio,
                                  @RequestParam("fin") @DateTimeFormat(pattern = "yyyy-MM-dd") Date fin) {
        return this.service.generarReporte(idCliente, inicio, fin);
    }
}
