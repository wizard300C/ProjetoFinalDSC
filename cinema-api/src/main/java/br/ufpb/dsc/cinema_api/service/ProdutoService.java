package br.ufpb.dsc.cinema_api.service;

import br.ufpb.dsc.cinema_api.exception.ProdutoNotFoundException;
import br.ufpb.dsc.cinema_api.models.Produto;
import br.ufpb.dsc.cinema_api.repositories.ProdutoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProdutoService {
    private ProdutoRepository produtoRepository;

    public ProdutoService() {
        this.produtoRepository = produtoRepository;
    }

    public List<Produto> listarTodosProdutos() {
        return produtoRepository.findAll();
    }

    public Produto listarProduto(Long produtoID) {
        return produtoRepository.findById(produtoID)
                .orElseThrow(() -> new ProdutoNotFoundException("O produto " + produtoID + "não foi encontrado!"));
    }

    public Produto adicionarProduto(Produto produto) {
        return produtoRepository.save(produto);
    }

    public void removerProduto(Long produtoID) {
        Produto produto = produtoRepository.findById(produtoID)
                .orElseThrow(() -> new ProdutoNotFoundException("O produto " + produtoID + " não foi enocntrado!"));
        produtoRepository.delete(produto);
    }

    public Produto atualizarProduto(Long produtoID, Produto produto) {
        Produto p = produtoRepository.findById(produtoID)
                .orElseThrow(() -> new ProdutoNotFoundException("O produto " + produtoID + " não foi encontrado!"));

        if (produto.getNomeProduto() != null && !produto.getNomeProduto().isBlank()) {
            p.setNomeProduto(produto.getNomeProduto());
        }

        if (produto.getPreco() != null && produto.getPreco() > 0) {
            p.setPreco(produto.getPreco());
        }

        return produtoRepository.save(p);
    }
}
