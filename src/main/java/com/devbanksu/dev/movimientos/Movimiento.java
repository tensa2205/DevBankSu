package com.devbanksu.dev.movimientos;

import com.devbanksu.dev.cuenta.Cuenta;
import com.devbanksu.dev.cuenta.TipoCuenta;
import jakarta.persistence.*;
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

    private Date fecha;
    private TipoMovimiento tipo;
    private BigDecimal valor;
    private BigDecimal saldoDisponible;
}
