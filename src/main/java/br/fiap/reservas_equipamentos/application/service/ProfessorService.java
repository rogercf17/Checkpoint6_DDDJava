package br.fiap.reservas_equipamentos.application.service;

import br.fiap.reservas_equipamentos.application.dto.mapper.ProfessorMapper;
import br.fiap.reservas_equipamentos.application.dto.request.ProfessorRequest;
import br.fiap.reservas_equipamentos.application.dto.response.ProfessorResponse;
import br.fiap.reservas_equipamentos.domain.model.Professor;
import br.fiap.reservas_equipamentos.domain.repository.ProfessorRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import static java.util.stream.Collectors.toList;

@Service
public class ProfessorService {
    private final ProfessorRepository repository;

    public ProfessorService(ProfessorRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public List<ProfessorResponse> list() {
        return repository.findAll().stream()
                .map(ProfessorMapper::toResponse)
                .collect(toList());
    }

    @Transactional
    public ProfessorResponse findById(Integer id) {
        return repository.findById(id)
                .map(professor -> ProfessorMapper.toResponse(professor))
                .orElseThrow(() -> new RuntimeException("Professor não encontrado"));
    }

    @Transactional
    public ProfessorResponse create(ProfessorRequest request) {
        return ProfessorMapper.toResponse(repository.save(ProfessorMapper.toEntity(request)));
    }

    @Transactional
    public ProfessorResponse update(Integer id, ProfessorRequest reqAtualizado) {
        return repository.findById(id)
                .map(professor -> {
                    professor.setNome(reqAtualizado.nome());
                    professor.setEmail(reqAtualizado.email());

                    Professor updated = repository.save(professor);
                    return ProfessorMapper.toResponse(updated);
                })
                .orElseThrow(() -> new RuntimeException("Professor não encontrado"));
    }

    @Transactional
    public void delete(Integer id) {
        repository.deleteById(id);
    }
}
