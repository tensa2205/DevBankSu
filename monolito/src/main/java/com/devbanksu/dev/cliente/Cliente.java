package com.devbanksu.dev.cliente;

import com.devbanksu.dev.cuenta.Cuenta;
import com.devbanksu.dev.persona.Persona;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.Set;

@Entity
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class Cliente extends Persona {
    @OneToMany(mappedBy = "cliente", cascade = CascadeType.REMOVE)
    private Set<Cuenta> cuentas;

    @NotEmpty(message = "Se necesita una contraseña!")
    private String password;
    @NotNull
    private boolean estado;

    public Cliente(String nombre, String genero, int edad, String identificacion, String direccion, String telefono, String password, boolean estado) {
        this.setNombre(nombre);
        this.setGenero(genero);
        this.setEdad(edad);
        this.setIdentificacion(identificacion);
        this.setDireccion(direccion);
        this.setTelefono(telefono);
        this.setPassword(password);
        this.setEstado(true);
    }
}
