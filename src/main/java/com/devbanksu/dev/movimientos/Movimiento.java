package com.devbanksu.dev.movimientos;

import com.devbanksu.dev.cuenta.Cuenta;
import com.devbanksu.dev.cuenta.TipoCuenta;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

import java.math.BigDecimal;
import java.util.Date;
@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Movimiento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;

    @ManyToOne
    @JoinColumn(name="cuenta_id", nullable = false)
    private Cuenta cuenta;

    @NotNull
    private Date fecha;

    @NotNull
    @Enumerated(EnumType.STRING)
    private TipoMovimiento tipo;

    @Positive
    @Min(value = 1L)
    private BigDecimal valor;

    @NotNull
    private BigDecimal saldoDisponible;

    public Movimiento(Cuenta cuenta, Date fecha, TipoMovimiento tipo, BigDecimal valor, BigDecimal saldoDisponible) {
        this.cuenta = cuenta;
        this.fecha = fecha;
        this.tipo = tipo;
        this.valor = valor;
        this.saldoDisponible = saldoDisponible;
    }
}
