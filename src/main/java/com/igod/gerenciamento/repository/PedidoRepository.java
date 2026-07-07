package com.igod.gerenciamento.repository;
import com.igod.gerenciamento.model.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {

    Optional<Pedido> findTopByOrderBySenhaDesc();

    Optional<Pedido> findTopByStatusOrderBySenhaAsc(Pedido.StatusPedido status);
}
