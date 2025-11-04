package br.fiap.reservas_equipamentos.application.service;

import br.fiap.reservas_equipamentos.application.dto.mapper.ReservaMapper;
import br.fiap.reservas_equipamentos.application.dto.request.ReservaRequest;
import br.fiap.reservas_equipamentos.application.dto.response.ReservaResponse;
import br.fiap.reservas_equipamentos.domain.model.Equipamento;
import br.fiap.reservas_equipamentos.domain.model.Professor;
import br.fiap.reservas_equipamentos.domain.model.Reserva;
import br.fiap.reservas_equipamentos.domain.model.StatusReserva;
import br.fiap.reservas_equipamentos.domain.repository.EquipamentoRepository;
import br.fiap.reservas_equipamentos.domain.repository.ProfessorRepository;
import br.fiap.reservas_equipamentos.domain.repository.ReservaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import static java.util.stream.Collectors.toList;

@Service
public class ReservaService {
    private final ReservaRepository repository;
    @Autowired
    private EquipamentoRepository equipamentoRepository;
    @Autowired
    private ProfessorRepository professorRepository;

    public ReservaService(ReservaRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public List<ReservaResponse> list() {
        return repository.findAll().stream()
                .map(ReservaMapper::toResponse)
                .collect(toList());
    }

    @Transactional
    public ReservaResponse findById(Integer id) {
        return repository.findById(id)
                .map(reserva -> ReservaMapper.toResponse(reserva))
                .orElseThrow(() -> new RuntimeException("Reserva não encontrada"));
    }

    @Transactional
    public ReservaResponse create(ReservaRequest request) {
        Reserva reserva = new Reserva();
        reserva.setSala(request.sala());
        reserva.setDataRetirada(request.dataRetirada());
        reserva.setDataEntrega(request.dataEntrega());
        reserva.setStatus(StatusReserva.CRIADA);

        if (request.idEquipamento() != null && request.idProfessor() != null) {
            Equipamento e = equipamentoRepository.findById(request.idEquipamento())
                    .orElseThrow(() -> new RuntimeException("Equipamento não encontrado"));
            reserva.setEquipamento(e);

            Professor p = professorRepository.findById(request.idProfessor())
                    .orElseThrow(() -> new RuntimeException("Professor não encontrado"));
            reserva.setProfessor(p);
        }

        Reserva saved = repository.save(reserva);
        return ReservaMapper.toResponse(saved);
    }

    @Transactional
    public ReservaResponse update(Integer id, ReservaRequest reqAtualizado) {
        return repository.findById(id)
                .map(reserva -> {
                    if (reqAtualizado.idEquipamento() != null && reqAtualizado.idProfessor() != null) {
                        Equipamento e = equipamentoRepository.findById(reqAtualizado.idEquipamento())
                                .orElseThrow(() -> new RuntimeException("Equipamento não encontrado"));
                        reserva.setEquipamento(e);

                        Professor p = professorRepository.findById(reqAtualizado.idProfessor())
                                .orElseThrow(() -> new RuntimeException("Professor não encontrado"));
                        reserva.setProfessor(p);
                    }
                    reserva.setSala(reqAtualizado.sala());
                    reserva.setDataRetirada(reqAtualizado.dataRetirada());
                    reserva.setDataEntrega(reqAtualizado.dataEntrega());

                    Reserva updated = repository.save(reserva);
                    return ReservaMapper.toResponse(updated);
                })
                .orElseThrow(() -> new RuntimeException("Reserva não encontrada"));
    }

    @Transactional
    public void delete(Integer id) {
        repository.deleteById(id);
    }
}
