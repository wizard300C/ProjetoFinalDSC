package br.ufpb.dsc.cinema_api.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;

@Entity
@Table(name = "tb_produtos")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "produto_id")
    private Long produtoID;

    @Column(name = "nome_produto")
    private String nomeProduto;

    @Column(name = "preco")
    private Double preco;

    @OneToMany(mappedBy = "produto")
    private Collection<ItemPedido> itens;

}
