package br.ufpb.dsc.cinema_api.controllers;

import br.ufpb.dsc.cinema_api.dtos.ProdutoDTO;
import br.ufpb.dsc.cinema_api.dtos.SalaDTO;
import br.ufpb.dsc.cinema_api.models.Produto;
import br.ufpb.dsc.cinema_api.models.Sala;
import br.ufpb.dsc.cinema_api.service.ProdutoService;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "/api/cinema")
@Validated
public class ProdutoController {

    private ProdutoService produtoService;
    private ModelMapper modelMapper;

    public ProdutoController(ModelMapper modelMapper, ProdutoService produtoService) {
        this.produtoService = produtoService;
        this.modelMapper = modelMapper;
    }

    @GetMapping(path = "/produtos")
    public List<ProdutoDTO> listaTodosProdutos() {
        return produtoService
                .listarTodosProdutos()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @GetMapping(path = "/produtos/{produtoID}")
    public ProdutoDTO listaProduto(@PathVariable Long produtoID) {
        Produto produto = produtoService.listarProduto(produtoID);
        return convertToDTO(produto);
    }

    @PostMapping(path = "/produtos")
    public ProdutoDTO adicionaProduto(ProdutoDTO produtoDTO) {
        Produto produto = convertToEntity(produtoDTO);
        produtoService.adicionarProduto(produto);
        return convertToDTO(produto);
    }

    @DeleteMapping(path = "/produtos/{produtoID}")
    public void removeProduto(@PathVariable Long produtoID) {
        produtoService.removerProduto(produtoID);
    }

    @PutMapping(path = "/produtos/{produtoID}")
    public ProdutoDTO atualizaProduto(@PathVariable Long produtoID, @Valid @RequestBody ProdutoDTO produtoDTO) {
        Produto produto = convertToEntity(produtoDTO);
        Produto produtoAtualizado = produtoService.atualizarProduto(produtoID, produto);
        return convertToDTO(produtoAtualizado);
    }

    private ProdutoDTO convertToDTO(Produto produto) {
        return modelMapper.map(produto, ProdutoDTO.class);
    }

    private Produto convertToEntity(ProdutoDTO produtoDTO) {
        return modelMapper.map(produtoDTO, Produto.class);
    }

}
