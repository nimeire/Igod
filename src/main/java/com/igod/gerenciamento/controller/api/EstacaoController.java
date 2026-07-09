package com.igod.gerenciamento.controller.api;


import com.igod.gerenciamento.model.Pedido;
import com.igod.gerenciamento.service.EstacaoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/estacoes")
@RequiredArgsConstructor
public class EstacaoController {

    private final EstacaoService estacaoService;

    @GetMapping("/{id}/pedido-atual")
    public ResponseEntity<Pedido> buscarPedidoAtual(@PathVariable Long id) {
        return estacaoService.buscarPedidoAtual(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.noContent().build());
    }


}
