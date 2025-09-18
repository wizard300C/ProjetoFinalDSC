package br.ufpb.dsc.cinema_api.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;

@Entity
@Table(name = "tb_salas")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Sala {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sala_id")
    private Long salaID;

    @Column(name = "nome_sala")
    private String nomeSala;

    @Column(name = "capacidade")
    private Integer capacidade;

    @OneToMany(mappedBy = "sala")
    private Collection<Sessao> sessoes;
}
