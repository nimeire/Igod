package com.igod.gerenciamento.service;
import com.igod.gerenciamento.dto.ItemPedidoRequest;
import com.igod.gerenciamento.model.Estacao;
import com.igod.gerenciamento.model.Pedido;
import com.igod.gerenciamento.repository.EstacaoRepository;
import com.igod.gerenciamento.repository.ItemPedidoRepository;
import com.igod.gerenciamento.repository.PedidoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
    private final ItemPedidoRepository itemPedidoRepository;
    private final EstacaoRepository estacaoRepository;

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

    public void popularBanco() {
        if (!pedidoRepository.findAll().isEmpty()) {
            throw new RuntimeException("Já existem pedidos cadastrados.");
        }

        List<String> nomes = List.of(
                "Maria", "João", "Ana", "Carlos", "Fernanda",
                "Lucas", "Patrícia", "Rafael", "Juliana", "Roberto"
        );
        for (String nome : nomes) {
            Pedido pedido = new Pedido();

            pedido.setNomeResponsavel(nome);
            pedido.setTipo(
                    Math.random() > 0.5
                            ? Pedido.TipoPedido.RETIRADA
                            : Pedido.TipoPedido.ENCOMENDA
            );

            pedido.setPagamento(
                    Math.random() > 0.5
                            ? Pedido.FormaPagamento.PIX
                            : Pedido.FormaPagamento.DINHEIRO
            );

            pedido.setObservacoes("");

            Pedido pedidoSalvo = criarPedido(pedido);

            ItemPedidoRequest item1 = new ItemPedidoRequest();
            item1.setProdutoId(1L);
            item1.setQuantidade(2);

            itemPedidoService.adicionarItem(pedidoSalvo.getId(), item1);

            ItemPedidoRequest item2 = new ItemPedidoRequest();
            item2.setProdutoId(4L);
            item2.setQuantidade(1);

            itemPedidoService.adicionarItem(pedidoSalvo.getId(), item2);
        }
    }
    public void limparPedidosDeTeste() {

        itemPedidoRepository.deleteAll();
        pedidoRepository.deleteAll();

        List<Estacao> estacoes = estacaoRepository.findAll();

        for (Estacao estacao : estacoes) {
            estacao.setStatus(Estacao.StatusEstacao.LIVRE);
            estacao.setUltimaLiberacao(LocalDateTime.now());
        }

        estacaoRepository.saveAll(estacoes);
    }




}
