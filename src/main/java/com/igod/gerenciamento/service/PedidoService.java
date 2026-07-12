package com.igod.gerenciamento.service;
import com.igod.gerenciamento.dto.ItemPedidoRequest;
import com.igod.gerenciamento.model.Estacao;
import com.igod.gerenciamento.model.Pedido;
import com.igod.gerenciamento.repository.EstacaoRepository;
import com.igod.gerenciamento.repository.ItemPedidoRepository;
import com.igod.gerenciamento.repository.PedidoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PedidoService {
    private final PedidoRepository pedidoRepository;
    private final EstacaoService estacaoService;
    private final CultoService cultoService;
    private final ItemPedidoService itemPedidoService;

    @Transactional
    public Pedido criarPedido(Pedido pedido){
        pedido.setSenha(gerarProximaSenha());
        pedido.setCulto(cultoService.buscarCultoAtivo());
        pedido.setDataPedido(LocalDateTime.now());

        Optional<Estacao> estacaoLivre = estacaoService.buscarMelhorEstacaoLivre();

        if (estacaoLivre.isPresent()) {
            Estacao estacao = estacaoLivre.get();

            pedido.setEstacao(estacao);
            pedido.setStatus(Pedido.StatusPedido.EM_PREPARO);

            estacao.setStatus(Estacao.StatusEstacao.OCUPADA);
            estacaoService.salvar(estacao);
        } else {
            pedido.setStatus(Pedido.StatusPedido.AGUARDANDO);
        }
        return pedidoRepository.save(pedido);
    }

    private Integer gerarProximaSenha() {
        Optional<Pedido> ultimoPedido = pedidoRepository.findTopByOrderBySenhaDesc();

        if (ultimoPedido.isPresent()) {
            return ultimoPedido.get().getSenha() + 1;
        }
        return 1;
    }

    @Transactional
    public Pedido marcarComoPronto(Long id){
        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pedido não encontrado"));

        pedido.setStatus(Pedido.StatusPedido.PRONTO);

        Estacao estacao = pedido.getEstacao();
        pedido.setEstacao(null);

        estacao.setStatus(Estacao.StatusEstacao.LIVRE);
        estacao.setUltimaLiberacao(LocalDateTime.now());
        estacaoService.salvar(estacao);

        Optional<Pedido> proximoPedido = pedidoRepository
                .findTopByStatusOrderBySenhaAsc(Pedido.StatusPedido.AGUARDANDO);

        if (proximoPedido.isPresent()) {
            Pedido proximo = proximoPedido.get();

            Optional<Estacao> melhorEstacao = estacaoService.buscarMelhorEstacaoLivre();

            if (melhorEstacao.isPresent()) {
                Estacao escolhida = melhorEstacao.get();
                proximo.setStatus(Pedido.StatusPedido.EM_PREPARO);
                proximo.setEstacao(escolhida);

                escolhida.setStatus(Estacao.StatusEstacao.OCUPADA);
                estacaoService.salvar(escolhida);

                pedidoRepository.save(proximo);
            }
        }

        return pedidoRepository.save(pedido);
    }

    public Pedido marcarComoEntregue(Long id){
        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pedido não encontrado"));

        pedido.setStatus(Pedido.StatusPedido.ENTREGUE);
        return pedidoRepository.save(pedido);
    }

    public List<Pedido> buscarPedidosPorStatus(Pedido.StatusPedido status){
        return pedidoRepository.findByStatus(status);
    }
}