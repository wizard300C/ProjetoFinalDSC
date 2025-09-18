package br.ufpb.dsc.cinema_api.models;

import jakarta.persistence.*;

@Entity
@Table(name = "tb_itens_pedidos")
public class ItemPedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "item_pedido_id")
    private Long itemPedidoID;

    @Column(name = "quantidade")
    private Integer quantidade;

    @Column(name = "precp_total")
    private Double precoTotal;

    @ManyToOne
    @JoinColumn(name = "produto_id")
    private Produto produto;
}
