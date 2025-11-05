package br.fiap.reservas_equipamentos.controller;

import br.fiap.reservas_equipamentos.application.dto.request.ReservaRequest;
import br.fiap.reservas_equipamentos.application.dto.response.ReservaResponse;
import br.fiap.reservas_equipamentos.application.service.ReservaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/reservas")
@Tag(name = "Reservas", description = "CRUD de Reservas")
public class ReservaController {
    private final ReservaService service;

    public ReservaController(ReservaService service) {
        this.service = service;
    }

    @Operation(summary = "Listar todas as reservas cadastradas")
    @GetMapping
    public List<ReservaResponse> list() {
        return service.list();
    }

    @Operation(summary = "Busca uma reserva por ID")
    @GetMapping("/{id}")
    public ResponseEntity<ReservaResponse> findById(@PathVariable Integer id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @Operation(summary = "Cadastra uma nova Reserva")
    @PostMapping
    public ResponseEntity<ReservaResponse> create(@Valid @RequestBody ReservaRequest request) {
        ReservaResponse saved = service.create(request);
        URI location = URI.create("/api/reservas/"+ saved.idReserva());
        return ResponseEntity.created(location).body(saved);
    }

    @Operation(summary = "Atualiza uma reserva já cadastrada")
    @PutMapping("/{id}")
    public ResponseEntity<ReservaResponse> update(@PathVariable Integer id,
                                          @Valid @RequestBody ReservaRequest request) {
        return ResponseEntity.ok(service.update(id, request));
    }

    @Operation(summary = "Deleta uma reserva cadastrada")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Efetua retirada do equipamento")
    @PostMapping("/{id}/retirada")
    public ResponseEntity<ReservaResponse> efetuarRetirada(@PathVariable Integer id) {
        return ResponseEntity.ok(service.efetuarRetirada(id));
    }

    @Operation(summary = "Efetua devolução do equipamento")
    @PostMapping("/{id}/devolucao")
    public ResponseEntity<ReservaResponse> efetuarDevolucao(@PathVariable Integer id) {
        return ResponseEntity.ok(service.efetuarDevolucao(id));
    }

    @Operation(summary = "Cancela reserva")
    @PostMapping("/{id}/cancelar")
    public ResponseEntity<ReservaResponse> cancelar(@PathVariable Integer id) {
        return ResponseEntity.ok(service.cancelarReserva(id));
    }
}
