package br.fiap.reservas_equipamentos.application.dto.mapper;

import br.fiap.reservas_equipamentos.application.dto.request.ReservaRequest;
import br.fiap.reservas_equipamentos.application.dto.response.ReservaResponse;
import br.fiap.reservas_equipamentos.domain.model.Reserva;

public class ReservaMapper {
    public static Reserva toEntity(ReservaRequest req) {
        Reserva reserva = new Reserva();
        reserva.setSala(req.sala());
        reserva.setDataRetirada(req.dataRetirada());
        reserva.setDataEntrega(req.dataEntrega());
        return reserva;
    }

    public static ReservaResponse toResponse(Reserva r) {
        return new ReservaResponse(
                r.getIdReserva(),
                r.getEquipamento().getIdEquipamento(),
                r.getProfessor().getIdProfessor(),
                r.getSala(),
                r.getDataRetirada(),
                r.getDataEntrega(),
                r.getStatus()
        );
    }
}
