package com.devbanksu.dev.cuenta;

import com.devbanksu.dev.cliente.Cliente;
import com.devbanksu.dev.movimientos.Movimiento;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;
import org.springframework.format.annotation.NumberFormat;

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
    @Enumerated(EnumType.STRING)
    private TipoCuenta tipo;

    @NumberFormat
    @Min(value = 0L)
    private BigDecimal saldoInicial;

    @NumberFormat
    @Min(value = 0L)
    private BigDecimal saldoActual;

    @NotNull
    private boolean estado;

    public Cuenta(Cliente cliente, Long nroCuenta, TipoCuenta tipo, BigDecimal saldoInicial, boolean estado) {
        this.cliente = cliente;
        this.nroCuenta = nroCuenta;
        this.tipo = tipo;
        this.saldoInicial = saldoInicial;
        this.saldoActual = saldoInicial;
        this.estado = estado;
    }
}
