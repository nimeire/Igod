package com.igod.gerenciamento.repository;
import com.igod.gerenciamento.model.Culto;
import com.igod.gerenciamento.model.Estacao;
import com.igod.gerenciamento.model.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {

    Optional<Pedido> findTopByOrderBySenhaDesc();

    Optional<Pedido> findTopByStatusOrderBySenhaAsc(Pedido.StatusPedido status);

    Optional<Pedido> findByEstacaoAndStatus(Estacao estacao, Pedido.StatusPedido status);

    List<Pedido> findByStatus(Pedido.StatusPedido status);

    List<Pedido> findByCulto(Culto culto);

}
