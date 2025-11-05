package br.fiap.reservas_equipamentos.controller;

import br.fiap.reservas_equipamentos.application.dto.request.ProfessorRequest;
import br.fiap.reservas_equipamentos.application.dto.response.ProfessorResponse;
import br.fiap.reservas_equipamentos.application.service.ProfessorService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.net.URI;
import java.util.List;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/professores")
@Tag(name = "Professores", description = "CRUD de Professores")
public class ProfessorController {
    private final ProfessorService service;

    public ProfessorController(ProfessorService service) {
        this.service = service;
    }

    @Operation(summary = "Lista todos os professores cadastrados")
    @GetMapping
    public List<ProfessorResponse> list() {
        return service.list();
    }

    @Operation(summary = "Busca um professor por ID")
    @GetMapping("/{id}")
    public ResponseEntity<ProfessorResponse> findById(@PathVariable Integer id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @Operation(summary = "Cadastra um novo professor")
    @PostMapping
    public ResponseEntity<ProfessorResponse> create(@Valid
                                        @RequestBody ProfessorRequest request) {
        ProfessorResponse saved = service.create(request);
        URI location = URI.create("/api/professores/"+ saved.idProfessor());
        return ResponseEntity.created(location).body(saved);
    }

    @Operation(summary = "Atualiza um professor já cadastrado")
    @PutMapping("/{id}")
    public ResponseEntity<ProfessorResponse> update(@PathVariable Integer id,
                                            @Valid @RequestBody ProfessorRequest request) {
        return ResponseEntity.ok(service.update(id, request));
    }

    @Operation(summary = "Deleta um professor cadastrado")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
