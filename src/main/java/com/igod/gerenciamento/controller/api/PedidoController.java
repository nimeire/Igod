package com.igod.gerenciamento.controller.api;

import com.igod.gerenciamento.dto.ItemPedidoRequest;
import com.igod.gerenciamento.model.ItemPedido;
import com.igod.gerenciamento.model.Pedido;
import com.igod.gerenciamento.service.ItemPedidoService;
import com.igod.gerenciamento.service.PedidoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.Map;
import java.util.List;

@RestController
@RequestMapping("/pedidos")
@RequiredArgsConstructor
public class PedidoController {
    private final PedidoService pedidoService;
    private final ItemPedidoService itemPedidoService;

    @GetMapping("/relatorio/{cultoId}")
    public Map<String, Object> gerarRelatorio(@PathVariable Long cultoId) {
        return pedidoService.gerarRelatorio(cultoId);
    }

    @PostMapping
    public Pedido criarPedido(@RequestBody Pedido pedido) {

        return pedidoService.criarPedido(pedido);
    }

    @PostMapping("/{pedidoId}/itens")
    public ItemPedido adicionarItem(
            @PathVariable Long pedidoId,
            @RequestBody ItemPedidoRequest request
    ) {
        return itemPedidoService.adicionarItem(pedidoId, request);
    }

    @GetMapping("/status/{status}")
    public List<Pedido> buscarPedidosPorStatus(@PathVariable("status") Pedido.StatusPedido status) {
        return pedidoService.buscarPedidosPorStatus(status);
    }

    @PatchMapping("/{id}/pronto")
    public Pedido marcarComoPronto(@PathVariable Long id) {
        return pedidoService.marcarComoPronto(id);
    }

    @PatchMapping("/{id}/entregue")
    public Pedido marcarComoEntregue(@PathVariable Long id) {
        return pedidoService.marcarComoEntregue(id);
    }

}
