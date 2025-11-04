package br.fiap.reservas_equipamentos.application.dto.response;

public record ProfessorResponse(
        Integer idProfessor,
        String nome,
        String email
) { }
