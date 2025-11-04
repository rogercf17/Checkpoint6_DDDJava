package br.fiap.reservas_equipamentos.application.dto.mapper;

import br.fiap.reservas_equipamentos.application.dto.request.EquipamentoRequest;
import br.fiap.reservas_equipamentos.application.dto.response.EquipamentoResponse;
import br.fiap.reservas_equipamentos.domain.model.Equipamento;

public class EquipamentoMapper {
    public static Equipamento toEntity(EquipamentoRequest req) {
        Equipamento equipamento = new Equipamento();
        equipamento.setDescricao(req.descricao());
        equipamento.setAtivo(req.ativo());
        return equipamento;
    }

    public static EquipamentoResponse toResponse(Equipamento e) {
        return new EquipamentoResponse(
                e.getIdEquipamento(),
                e.getDescricao(),
                e.isAtivo()
        );
    }
}
