package com.igod.gerenciamento.repository;
import com.igod.gerenciamento.model.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Pedido, Long> {
}
