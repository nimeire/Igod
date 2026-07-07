package com.igod.gerenciamento.controller;

import com.igod.gerenciamento.dto.ItemPedidoRequest;
import com.igod.gerenciamento.model.ItemPedido;
import com.igod.gerenciamento.model.Pedido;
import com.igod.gerenciamento.service.ItemPedidoService;
import com.igod.gerenciamento.service.PedidoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/pedidos")
@RequiredArgsConstructor
public class PedidoController {
    private final PedidoService pedidoService;
    private final ItemPedidoService itemPedidoService;

    @PostMapping
    public Pedido criarPedido(@RequestBody Pedido pedido){

        return pedidoService.criarPedido(pedido);
    }
    @PostMapping("/{pedidoId}/itens")
    public ItemPedido adicionarItem(
            @PathVariable Long pedidoId,
            @RequestBody ItemPedidoRequest request
    ){
        return itemPedidoService.adicionarItem(pedidoId, request);
    }
    @PatchMapping("/{id}/pronto")
    public Pedido marcarComoPronto(@PathVariable Long id){
        return pedidoService.marcarComoPronto(id);
    }

}
