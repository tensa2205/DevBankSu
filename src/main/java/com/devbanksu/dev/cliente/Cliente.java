package com.devbanksu.dev.cliente;

import com.devbanksu.dev.persona.Persona;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.io.Serializable;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Cliente extends Persona {
    @NotEmpty(message = "Se necesita una contraseña!")
    private String password;
    @NotNull
    private boolean estado;
}
