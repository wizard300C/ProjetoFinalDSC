package br.ufpb.dsc.cinema_api.service;

import br.ufpb.dsc.cinema_api.exception.PedidoNotFoundException;
import br.ufpb.dsc.cinema_api.exception.ProdutoNotFoundException;
import br.ufpb.dsc.cinema_api.exception.UsuarioNotFoundException;
import br.ufpb.dsc.cinema_api.models.ItemPedido;
import br.ufpb.dsc.cinema_api.models.Pedido;
import br.ufpb.dsc.cinema_api.models.Produto;
import br.ufpb.dsc.cinema_api.models.Usuario;
import br.ufpb.dsc.cinema_api.repositories.ItemPedidoRepository;
import br.ufpb.dsc.cinema_api.repositories.PedidoRepository;
import br.ufpb.dsc.cinema_api.repositories.ProdutoRepository;
import br.ufpb.dsc.cinema_api.repositories.UsuarioRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PedidoService {

    private PedidoRepository pedidoRepository;
    private UsuarioRepository usuarioRepository;
    private ItemPedidoRepository itemPedidoRepository;
    private ProdutoRepository produtoRepository;

    public PedidoService(PedidoRepository pedidoRepository,
                         UsuarioRepository usuarioRepository,
                         ItemPedidoRepository itemPedidoRepository,
                         ProdutoRepository produtoRepository) {
        this.pedidoRepository = pedidoRepository;
        this.usuarioRepository = usuarioRepository;
        this.produtoRepository = produtoRepository;
        this.itemPedidoRepository = itemPedidoRepository;
    }

    public Pedido listarPedido(Long pedidoID) {
        return pedidoRepository.findById(pedidoID)
                .orElseThrow(() -> new PedidoNotFoundException("Pedido" + pedidoID + "não foi encontrado!"));
    }

    public List<Pedido> listarTodosPedidos() {
        return pedidoRepository.findAll();
    }

    public List<Pedido> listarPedidosUsuario(Long usuarioID) {
        if (!usuarioRepository.existsById(usuarioID)) {
            throw new UsuarioNotFoundException("Usuário" + usuarioID + "não foi encontrado!");
        }
        return pedidoRepository.findByUsuario_UsuarioID(usuarioID);
    }

    public Pedido criarPedido(Usuario usuario, Collection<ItemPedido> itens) {
        Pedido pedido = new Pedido();
        pedido.setUsuario(usuario);
        pedido.setData(LocalDateTime.now());
        pedidoRepository.save(pedido);

        List<ItemPedido> itensSalvos = itens.stream()
                .map(item -> {
                    Produto produto = produtoRepository.findById(item.getProduto().getProdutoID())
                            .orElseThrow(() -> new ProdutoNotFoundException("O produto não foi encontrado!"));
                    item.setPedido(pedido);
                    item.setProduto(produto);
                    item.setPrecoTotal(produto.getPreco() * item.getQuantidade());
                    return itemPedidoRepository.save(item);
                })
                .collect(Collectors.toList());
        pedido.setItens(itensSalvos);
        return pedido;
    }

}

