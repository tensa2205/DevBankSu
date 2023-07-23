package com.devbanksu.dev.cuenta;

import com.devbanksu.dev.cliente.Cliente;
import com.devbanksu.dev.movimientos.Movimiento;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
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

    @Positive
    @Min(value = 1L)
    @Column(unique = true)
    private Long nroCuenta;

    @NotNull
    private TipoCuenta tipo;

    @Positive
    private BigDecimal saldoInicial;

    @Positive
    private BigDecimal saldoActual;

    @NotNull
    private boolean estado;
}
