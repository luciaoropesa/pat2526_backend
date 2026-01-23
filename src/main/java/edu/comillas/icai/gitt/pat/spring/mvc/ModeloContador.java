package edu.comillas.icai.gitt.pat.spring.mvc;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ModeloContador(

        @NotBlank(message="Debe rellenar el nombre")
        String nombre,
        @NotNull(message="El valor no puede ser nulo")
        Long valor) {
}
