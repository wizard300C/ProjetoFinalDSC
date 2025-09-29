package br.ufpb.dsc.cinema_api.repositories;

import br.ufpb.dsc.cinema_api.models.ItemPedido;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemPedidoRepository extends JpaRepository<ItemPedido, Long> {

}
