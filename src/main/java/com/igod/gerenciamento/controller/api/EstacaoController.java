package com.igod.gerenciamento.controller.api;


import com.igod.gerenciamento.model.Estacao;
import com.igod.gerenciamento.model.Pedido;
import com.igod.gerenciamento.service.EstacaoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/estacoes")
@RequiredArgsConstructor
public class EstacaoController {

    private final EstacaoService estacaoService;
    @PatchMapping("/{id}/liberar")
    public Estacao liberarManualmente(@PathVariable Long id) {
        return estacaoService.liberarManualmente(id);
    }
    @GetMapping("/{id}/pedido-atual")
    public ResponseEntity<Pedido> buscarPedidoAtual(@PathVariable Long id) {
        return estacaoService.buscarPedidoAtual(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.noContent().build());
    }


}
