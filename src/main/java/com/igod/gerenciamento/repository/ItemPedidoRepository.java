package com.igod.gerenciamento.repository;
import com.igod.gerenciamento.model.ItemPedido;
import com.igod.gerenciamento.model.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;



public interface ItemPedidoRepository extends JpaRepository<ItemPedido, Long> {


    List<ItemPedido> findByPedido(Pedido pedido);


}
