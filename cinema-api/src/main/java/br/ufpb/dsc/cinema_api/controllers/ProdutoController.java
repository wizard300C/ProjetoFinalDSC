package br.ufpb.dsc.cinema_api.controllers;

import br.ufpb.dsc.cinema_api.dtos.ProdutoDTO;
import br.ufpb.dsc.cinema_api.dtos.SalaDTO;
import br.ufpb.dsc.cinema_api.models.Produto;
import br.ufpb.dsc.cinema_api.models.Sala;
import br.ufpb.dsc.cinema_api.service.ProdutoService;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.security.access.prepost.PreAuthorize;
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
    @PreAuthorize("isAuthenticated()")
    public List<ProdutoDTO> listaTodosProdutos() {
        return produtoService
                .listarTodosProdutos()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @GetMapping(path = "/produtos/{produtoID}")
    @PreAuthorize("isAuthenticated()")
    public ProdutoDTO listaProduto(@PathVariable Long produtoID) {
        Produto produto = produtoService.listarProduto(produtoID);
        return convertToDTO(produto);
    }

    @PostMapping(path = "/produtos")
    @PreAuthorize("hasRole('ADMIN')")
    public ProdutoDTO adicionaProduto(@Valid @RequestBody ProdutoDTO produtoDTO) {
        Produto produto = convertToEntity(produtoDTO);
        Produto produtoCriado = produtoService.adicionarProduto(produto);
        return convertToDTO(produtoCriado);
    }

    @DeleteMapping(path = "/produtos/{produtoID}")
    @PreAuthorize("hasRole('ADMIN')")
    public void removeProduto(@PathVariable Long produtoID) {
        produtoService.removerProduto(produtoID);
    }

    @PutMapping(path = "/produtos/{produtoID}")
    @PreAuthorize("hasRole('ADMIN')")
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
