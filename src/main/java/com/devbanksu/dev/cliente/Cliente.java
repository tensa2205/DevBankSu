package com.devbanksu.dev.cliente;

import com.devbanksu.dev.cuenta.Cuenta;
import com.devbanksu.dev.persona.Persona;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Cliente extends Persona {
    @OneToMany(mappedBy = "cliente")
    private Set<Cuenta> cuentas;

    @NotEmpty(message = "Se necesita una contraseña!")
    private String password;
    @NotNull
    private boolean estado;
}
