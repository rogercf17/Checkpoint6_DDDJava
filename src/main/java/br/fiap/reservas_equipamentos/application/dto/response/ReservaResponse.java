package br.fiap.reservas_equipamentos.application.dto.response;

import br.fiap.reservas_equipamentos.domain.model.StatusReserva;
import java.time.LocalDateTime;

public record ReservaResponse(
        Integer idReserva,
        Integer idEquipamento,
        Integer idProfessor,
        String sala,
        LocalDateTime dataRetirada,
        LocalDateTime dataEntrega,
        StatusReserva status
) { }
