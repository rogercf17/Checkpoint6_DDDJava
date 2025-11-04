package br.fiap.reservas_equipamentos.domain.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "PROFESSORCP")
@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Professor {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Integer idProfessor;
    @Column
    private String nome;
    @Column
    private String email;

    @Override
    public String toString() {
        return "Professor - ID: "+ idProfessor +
                "\nNome: "+ nome +
                "\nEmail: "+ email;
    }
}
