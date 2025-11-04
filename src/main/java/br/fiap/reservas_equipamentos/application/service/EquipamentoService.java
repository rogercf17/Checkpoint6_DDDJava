package br.fiap.reservas_equipamentos.application.service;

import br.fiap.reservas_equipamentos.application.dto.mapper.EquipamentoMapper;
import br.fiap.reservas_equipamentos.application.dto.request.EquipamentoRequest;
import br.fiap.reservas_equipamentos.application.dto.response.EquipamentoResponse;
import br.fiap.reservas_equipamentos.domain.model.Equipamento;
import br.fiap.reservas_equipamentos.domain.repository.EquipamentoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import static java.util.stream.Collectors.toList;

@Service
public class EquipamentoService {
    private final EquipamentoRepository repository;

    public EquipamentoService(EquipamentoRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public List<EquipamentoResponse> list() {
        return repository.findAll().stream()
                .map(EquipamentoMapper::toResponse)
                .collect(toList());
    }

    @Transactional
    public EquipamentoResponse findById(Integer id) {
        return repository.findById(id)
                .map(equipamento -> EquipamentoMapper.toResponse(equipamento))
                .orElseThrow(() -> new RuntimeException("Equipamento não encontrado"));
    }

    @Transactional
    public EquipamentoResponse create(EquipamentoRequest request) {
        return EquipamentoMapper.toResponse(repository.save(EquipamentoMapper.toEntity(request)));
    }

    @Transactional
    public EquipamentoResponse update(Integer id, EquipamentoRequest reqAtualizado) {
        return repository.findById(id)
                .map(equipamento -> {
                    equipamento.setDescricao(reqAtualizado.descricao());
                    equipamento.setAtivo(reqAtualizado.ativo());

                    Equipamento updated = repository.save(equipamento);
                    return EquipamentoMapper.toResponse(updated);
                })
                .orElseThrow(() -> new RuntimeException("Equipamento não encontrado"));
    }

    @Transactional
    public void delete(Integer id) {
        repository.deleteById(id);
    }
}
