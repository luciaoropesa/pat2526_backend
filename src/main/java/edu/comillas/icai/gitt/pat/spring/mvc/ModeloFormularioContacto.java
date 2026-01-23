package edu.comillas.icai.gitt.pat.spring.mvc;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record ModeloFormularioContacto(
        @NotBlank(message = "El mensaje no puede estar vacío")
        String mensaje,
        @Email(message = "El formato del email es incorrecto")
        @NotBlank(message = "El email es obligatorio")
        String email
) {}

