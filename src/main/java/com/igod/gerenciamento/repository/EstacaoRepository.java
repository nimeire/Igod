package com.igod.gerenciamento.repository;
import com.igod.gerenciamento.model.Estacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EstacaoRepository extends JpaRepository<Estacao, Long> {


    List<Estacao> findByStatus(Estacao.StatusEstacao statusEstacao);



}
