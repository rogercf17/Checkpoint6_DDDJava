package br.fiap.reservas_equipamentos.application.dto.request;

import jakarta.validation.constraints.*;
import java.time.LocalDateTime;

public record ReservaRequest(
        @NotNull @Positive Integer idEquipamento,
        @NotNull @Positive Integer idProfessor,
        @NotBlank @Size(max = 50) String sala,
        @NotNull @Future LocalDateTime dataRetirada,
        @NotNull @Future LocalDateTime dataEntrega
) { }
