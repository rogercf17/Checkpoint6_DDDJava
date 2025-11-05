package br.fiap.reservas_equipamentos.controller;

import br.fiap.reservas_equipamentos.application.dto.request.EquipamentoRequest;
import br.fiap.reservas_equipamentos.application.dto.response.EquipamentoResponse;
import br.fiap.reservas_equipamentos.application.service.EquipamentoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/equipamentos")
@Tag(name = "Equipamentos", description = "CRUD de Equipamentos")
public class EquipamentoController {
    private final EquipamentoService service;

    public EquipamentoController(EquipamentoService service) {
        this.service = service;
    }

    @Operation(summary = "Lista todos os equipamentos cadastrados")
    @GetMapping
    public List<EquipamentoResponse> list() {
        return service.list();
    }

    @Operation(summary = "Busca um equipamento por ID")
    @GetMapping("/{id}")
    public ResponseEntity<EquipamentoResponse> findById(@PathVariable Integer id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @Operation(summary = "Cadastra um novo equipamento")
    @PostMapping
    public ResponseEntity<EquipamentoResponse> create(@Valid
                                              @RequestBody EquipamentoRequest request) {
        EquipamentoResponse saved = service.create(request);
        URI location = URI.create("/api/equipamentos/"+ saved.idEquipamento());
        return ResponseEntity.created(location).body(saved);
    }

    @Operation(summary = "Atualiza um equipamento já cadastrado")
    @PutMapping("/{id}")
    public ResponseEntity<EquipamentoResponse> update(@PathVariable Integer id,
                                              @Valid @RequestBody EquipamentoRequest request) {
        return ResponseEntity.ok(service.update(id, request));
    }

    @Operation(summary = "Deleta um equipamento cadastrado")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
