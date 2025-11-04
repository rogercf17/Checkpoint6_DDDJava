package br.fiap.reservas_equipamentos.application.dto.response;

public record EquipamentoResponse(
        Integer idEquipamento,
        String descricao,
        boolean ativo
) { }
