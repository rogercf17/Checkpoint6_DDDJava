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
        if (!request.dataRetirada().isBefore(request.dataEntrega())) {
            throw new IllegalArgumentException("Data de retirada deve ser anterior à data de entrega");
        }
        boolean existeConflito = repository.existsReservaConflitante(
                request.idEquipamento(),
                request.dataRetirada(),
                request.dataEntrega()
        );
        if (existeConflito) {
            throw new IllegalStateException("Já existe uma reserva para este equipamento no período selecionado");
        }

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
                    if (reserva.getStatus() != StatusReserva.CRIADA) {
                        throw new IllegalStateException("Só é possível editar reservas com status CRIADA");
                    }

                    if (!reqAtualizado.dataRetirada().isBefore(reqAtualizado.dataEntrega())) {
                        throw new IllegalArgumentException("Datas inválidas");
                    }

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

    @Transactional
    public ReservaResponse efetuarRetirada(Integer id) {
        Reserva reserva = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Reserva não encontrada"));

        if (reserva.getStatus() != StatusReserva.CRIADA) {
            throw new IllegalStateException("Só é possível retirar reservas com status CRIADA");
        }

        reserva.retirar();

        Reserva updated = repository.save(reserva);
        return ReservaMapper.toResponse(updated);
    }

    @Transactional
    public ReservaResponse efetuarDevolucao(Integer id) {
        Reserva reserva = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Reserva não encontrada"));

        if (reserva.getStatus() != StatusReserva.RETIRADA) {
            throw new IllegalStateException("Só é possível devolver reservas com status RETIRADA");
        }

        reserva.devolver();

        Reserva updated = repository.save(reserva);
        return ReservaMapper.toResponse(updated);
    }

    @Transactional
    public ReservaResponse cancelarReserva(Integer id) {
        Reserva reserva = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Reserva não encontrada"));

        reserva.cancelar();

        Reserva updated = repository.save(reserva);
        return ReservaMapper.toResponse(updated);
    }
}
