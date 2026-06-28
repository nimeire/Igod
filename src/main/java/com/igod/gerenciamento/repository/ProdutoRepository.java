package com.igod.gerenciamento.repository;
import com.igod.gerenciamento.model.Produto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {


}
