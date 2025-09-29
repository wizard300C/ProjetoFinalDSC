package br.ufpb.dsc.cinema_api.models;

import br.ufpb.dsc.cinema_api.models.enums.StatusIngresso;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tb_ingressos")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Ingresso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ingresso_id")
    private Long ingressoID;

    @Column(name = "preco")
    private Double preco;

    @Column(name = "assento")
    private String assento;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "sessao_id")
    private Sessao sessao;

    @Enumerated(EnumType.STRING)
    private StatusIngresso status;
}
