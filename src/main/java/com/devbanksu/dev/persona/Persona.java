package com.devbanksu.dev.persona;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;
import lombok.*;
import lombok.experimental.SuperBuilder;

@MappedSuperclass
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class Persona {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "Nombre no puede ser vacío")
    private String nombre;

    @NotEmpty(message = "Género no puede ser vacío")
    private String genero;

    @Min(value = 18, message = "Debe ser mayor de 18 años")
    private int edad;

    @NotEmpty(message = "Identificacion no puede ser vacío")
    @Column(unique = true)
    private String identificacion;

    @NotEmpty(message = "Direccion no puede ser vacío")
    private String direccion;

    @NotEmpty(message = "Telefono no puede ser vacío")
    private String telefono;
}
