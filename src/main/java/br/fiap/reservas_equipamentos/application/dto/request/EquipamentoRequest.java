package br.fiap.reservas_equipamentos.application.dto.request;

import jakarta.validation.constraints.*;

public record EquipamentoRequest(
        @NotBlank @Size(max = 100) String descricao,
        @NotNull boolean ativo
) { }
