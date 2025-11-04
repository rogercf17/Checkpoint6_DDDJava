package br.fiap.reservas_equipamentos.domain.repository;

import br.fiap.reservas_equipamentos.domain.model.Reserva;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;

@Repository
public interface ReservaRepository extends JpaRepository<Reserva, Integer> {
    @Query("SELECT COUNT(r) > 0 FROM Reserva r " +
            "WHERE r.equipamento.idEquipamento = :idEquipamento " +
            "AND r.status IN ('CRIADA', 'RETIRADA') " +
            "AND ((r.dataRetirada BETWEEN :inicio AND :fim) OR " +
            "     (r.dataEntrega BETWEEN :inicio AND :fim) OR " +
            "     (r.dataRetirada <= :inicio AND r.dataEntrega >= :fim))")
    boolean existsReservaConflitante(
            @Param("idEquipamento") Integer idEquipamento,
            @Param("inicio") LocalDateTime inicio,
            @Param("fim") LocalDateTime fim);
}
