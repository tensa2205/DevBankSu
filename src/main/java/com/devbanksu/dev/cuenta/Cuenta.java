package com.devbanksu.dev.cuenta;

import com.devbanksu.dev.cliente.Cliente;
import com.devbanksu.dev.movimientos.Movimiento;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.Set;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Cuenta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;

    @ManyToOne
    @JoinColumn(name="cliente_id", nullable = false)
    private Cliente cliente;

    @OneToMany(mappedBy = "cuenta")
    private Set<Movimiento> movimientos;

    private TipoCuenta tipo;
    private BigDecimal saldoInicial;
    private BigDecimal saldoActual;
    private boolean estado;
}
