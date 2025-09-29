package br.ufpb.dsc.cinema_api.controllers;

import br.ufpb.dsc.cinema_api.dtos.FilmeDTO;
import br.ufpb.dsc.cinema_api.dtos.PedidoDTO;
import br.ufpb.dsc.cinema_api.dtos.PedidoResponseDTO;
import br.ufpb.dsc.cinema_api.models.Filme;
import br.ufpb.dsc.cinema_api.models.ItemPedido;
import br.ufpb.dsc.cinema_api.models.Pedido;
import br.ufpb.dsc.cinema_api.models.Usuario;
import br.ufpb.dsc.cinema_api.service.PedidoService;
import br.ufpb.dsc.cinema_api.service.UsuarioService;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "/api/cinema")
@Validated
public class PedidoController {
    private PedidoService pedidoService;
    private ModelMapper modelMapper;
    private UsuarioService usuarioService;

    public PedidoController(ModelMapper modelMapper, PedidoService pedidoService, UsuarioService usuarioService) {
        this.modelMapper = modelMapper;
        this.pedidoService = pedidoService;
        this.usuarioService = usuarioService;
    }

    @GetMapping(path = "/pedidos")
    @PreAuthorize("hasRole('ADMIN')")
    public List<PedidoResponseDTO> listaTodosPedidos() {
        return pedidoService.listarTodosPedidos()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @GetMapping(path = "/usuarios/{usuarioID}/pedidos")
    @PreAuthorize("hasRole('ADMIN')")
    public List<PedidoResponseDTO> listaPedidosUsuario(@PathVariable Long usuarioID) {
        return pedidoService.listarPedidosUsuario(usuarioID).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @GetMapping(path = "/pedidos/{pedidoID}")
    @PreAuthorize("hasRole('ADMIN')")
    public PedidoResponseDTO listaPedido(@PathVariable Long pedidoID) {
        Pedido pedido = pedidoService.listarPedido(pedidoID);
        return convertToDTO(pedido);
    }

    @PostMapping(path = "/pedidos")
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("isAuthenticated()")
    public PedidoResponseDTO criarPedido(@Valid @RequestBody PedidoDTO dto, Authentication authentication) {
        Usuario usuario = usuarioService.buscarPorNomeUsuario(authentication.getName());

        List<ItemPedido> itens = dto.getItens().stream().map(itemDto -> {
            ItemPedido item = new ItemPedido();
            item.setQuantidade(itemDto.getQuantidade());
            br.ufpb.dsc.cinema_api.models.Produto produto = new br.ufpb.dsc.cinema_api.models.Produto();
            produto.setProdutoID(itemDto.getProdutoId());
            item.setProduto(produto);
            return item;
        }).collect(Collectors.toList());

        Pedido pedidoSalvo = pedidoService.criarPedido(usuario, itens);

        return convertToDTO(pedidoSalvo);
    }

    private PedidoResponseDTO convertToDTO(Pedido pedido) {
        return modelMapper.map(pedido, PedidoResponseDTO.class);
    }

    private Pedido convertToEntity(PedidoDTO pedidoDTO) {
        return modelMapper.map(pedidoDTO, Pedido.class);
    }

}
