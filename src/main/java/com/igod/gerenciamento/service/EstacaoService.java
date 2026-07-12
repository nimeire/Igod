package com.igod.gerenciamento.service;
import com.igod.gerenciamento.model.Estacao;
import com.igod.gerenciamento.repository.EstacaoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.Optional;
import java.util.List;
import com.igod.gerenciamento.model.Pedido;
import com.igod.gerenciamento.repository.PedidoRepository;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class EstacaoService {
    private final EstacaoRepository estacaoRepository;
    private final PedidoRepository pedidoRepository;

    public List<Estacao> buscarEstacoesLivres() {
        return estacaoRepository.findByStatus(Estacao.StatusEstacao.LIVRE);
    }

    @Transactional
    public Optional<Estacao> buscarMelhorEstacaoLivre(){
        List<Estacao> estacoesLivres = estacaoRepository.buscarEstacoesLivresComLock();
        return estacoesLivres.stream().findFirst();
    }

    public Estacao salvar(Estacao estacao) {
        return estacaoRepository.save(estacao);
    }

    public Optional<Pedido> buscarPedidoAtual(Long id) {
        Estacao estacao = estacaoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Estação não encontrada"));

        return pedidoRepository.findByEstacaoAndStatus(
                estacao,
                Pedido.StatusPedido.EM_PREPARO
        );
    }
}
