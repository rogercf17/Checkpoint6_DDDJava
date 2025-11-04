package br.fiap.reservas_equipamentos.domain.repository;

import br.fiap.reservas_equipamentos.domain.model.Equipamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EquipamentoRepository extends JpaRepository<Equipamento, Integer> { }
