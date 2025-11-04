package br.fiap.reservas_equipamentos.application.dto.mapper;

import br.fiap.reservas_equipamentos.application.dto.request.ProfessorRequest;
import br.fiap.reservas_equipamentos.application.dto.response.ProfessorResponse;
import br.fiap.reservas_equipamentos.domain.model.Professor;

public class ProfessorMapper {
    public static Professor toEntity(ProfessorRequest req) {
        Professor professor = new Professor();
        professor.setNome(req.nome());
        professor.setEmail(req.email());
        return professor;
    }

    public static ProfessorResponse toResponse(Professor p) {
        return new ProfessorResponse(
                p.getIdProfessor(),
                p.getNome(),
                p.getEmail()
        );
    }
}
