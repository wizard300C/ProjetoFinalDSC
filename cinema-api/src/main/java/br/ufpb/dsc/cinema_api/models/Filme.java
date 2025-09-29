package br.ufpb.dsc.cinema_api.models;

import br.ufpb.dsc.cinema_api.models.enums.ClassificacaoIndicativa;
import br.ufpb.dsc.cinema_api.models.enums.Genero;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.bind.annotation.PatchMapping;

import java.util.Collection;

@Entity
@Table(name = "tb_filmes")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Filme {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "filme_id")
    private Long filmeID;

    @Column(name = "nome_filme")
    private String nomeFilme;

    @Column(name = "sinopse")
    private String sinopse;

    @Enumerated(EnumType.STRING)
    private Genero genero;

    @Column(name = "duracao")
    private Integer duracao;

    @Enumerated(EnumType.STRING)
    private ClassificacaoIndicativa classificacaoIndicativa;

    @OneToMany(mappedBy = "filme")
    private Collection<Sessao> sessoes;
}
