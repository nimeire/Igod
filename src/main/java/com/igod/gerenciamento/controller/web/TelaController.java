package com.igod.gerenciamento.controller.web;

import com.igod.gerenciamento.model.Pedido;
import com.igod.gerenciamento.model.Produto;
import com.igod.gerenciamento.repository.ProdutoRepository;
import com.igod.gerenciamento.service.EstacaoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import com.igod.gerenciamento.service.PedidoService;

import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class TelaController {

    private final EstacaoService estacaoService;
    private final PedidoService pedidoService;
    private final ProdutoRepository produtoRepository;

    @GetMapping("/caixa")
    public String caixa(Model model) {

        List<Produto> produtos = produtoRepository.findAll();

        model.addAttribute("produtos", produtos);

        return "caixa";
    }

    @GetMapping("/cozinha/{id}")
    public String cozinha(@PathVariable Long id, Model model) {

        Optional<Pedido> pedidoAtual = estacaoService.buscarPedidoAtual(id);

        model.addAttribute("estacaoId", id);
        model.addAttribute("pedido", pedidoAtual.orElse(null));

        return "cozinha";
    }

    @PostMapping("/cozinha/{estacaoId}/pedido/{pedidoId}/feito")
    public String marcarPedidoFeito(
            @PathVariable Long estacaoId,
            @PathVariable Long pedidoId
    ) {
        pedidoService.marcarComoPronto(pedidoId);
        return "redirect:/cozinha/" + estacaoId;
    }
}