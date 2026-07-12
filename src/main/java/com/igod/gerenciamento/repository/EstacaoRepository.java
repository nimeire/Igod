package com.igod.gerenciamento.repository;
import com.igod.gerenciamento.model.Estacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import java.util.List;


@Repository
public interface EstacaoRepository extends JpaRepository<Estacao, Long> {

    List<Estacao> findByStatus(Estacao.StatusEstacao statusEstacao);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT e FROM Estacao e WHERE e.status = 'LIVRE' ORDER BY e.ultimaLiberacao ASC")
    List<Estacao> buscarEstacoesLivresComLock();


}
