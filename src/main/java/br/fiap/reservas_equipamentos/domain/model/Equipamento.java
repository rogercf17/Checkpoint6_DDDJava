package br.fiap.reservas_equipamentos.domain.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Equipamento {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Integer idEquipamento;
    @Column
    private String descricao;
    @Column
    private boolean ativo;

    @Override
    public String toString() {
        return "Equipamento - ID: "+ idEquipamento +
                "\nAtivo: " + ativo + "\n" +
                descricao;
    }
}
