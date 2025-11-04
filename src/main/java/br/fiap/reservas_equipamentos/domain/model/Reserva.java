package br.fiap.reservas_equipamentos.domain.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "RESERVACP")
@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(onlyExplicitlyIncluded = true)
public class Reserva {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Integer idReserva;
    @ManyToOne @JoinColumn(name = "ID_EQUIPAMENTO")
    private Equipamento equipamento;
    @ManyToOne @JoinColumn(name = "ID_PROFESSOR")
    private Professor professor;
    @Column
    private String sala;
    @Column(name = "DATA_RETIRADA")
    private LocalDateTime dataRetirada;
    @Column(name = "DATA_ENTREGA")
    private LocalDateTime dataEntrega;
    @Column @Enumerated(EnumType.STRING)
    private StatusReserva status = StatusReserva.CRIADA;

    public boolean isAtiva() {
        return status == StatusReserva.CRIADA || status == StatusReserva.RETIRADA;
    }

    public void retirar() {
        if (this.status != StatusReserva.CRIADA) {
            throw new IllegalStateException("Só é possível retirar reservas com status CRIADA");
        }
        this.status = StatusReserva.RETIRADA;
    }

    public void devolver() {
        if (this.status != StatusReserva.RETIRADA) {
            throw new IllegalStateException("Só é possível devolver reservas com status RETIRADA");
        }
        this.status = StatusReserva.DEVOLVIDA;
    }

    public void cancelar() {
        if (this.status != StatusReserva.CRIADA) {
            throw new IllegalStateException("Só é possível cancelar reservas com status CRIADA");
        }
        this.status = StatusReserva.CANCELADA;
    }
}
