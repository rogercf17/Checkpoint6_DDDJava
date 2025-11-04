package br.fiap.reservas_equipamentos.application.dto.request;

import jakarta.validation.constraints.*;

public record ProfessorRequest(
        @NotBlank @Size(max = 50) String nome,
        @Email @NotBlank @Size(max = 25) String email
) { }
