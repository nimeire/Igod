package com.igod.gerenciamento.service;

import com.igod.gerenciamento.dto.ItemPedidoRequest;
import com.igod.gerenciamento.model.ItemPedido;
import com.igod.gerenciamento.model.Produto;
import com.igod.gerenciamento.repository.ItemPedidoRepository;
import com.igod.gerenciamento.repository.PedidoRepository;
import com.igod.gerenciamento.repository.ProdutoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.igod.gerenciamento.model.Pedido;
import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ItemPedidoService {

    private final ItemPedidoRepository itemPedidoRepository;
    private final PedidoRepository pedidoRepository;
    private final ProdutoRepository produtoRepository;

    public ItemPedido adicionarItem(Long pedidoId, ItemPedidoRequest request){

        Pedido pedido = pedidoRepository.findById(pedidoId)
                .orElseThrow(() -> new RuntimeException("Pedido não encontrado"));

        Produto produto = produtoRepository.findById(request.getProdutoId())
                .orElseThrow(() -> new RuntimeException("Produto não encontrado"));

        BigDecimal precoUnitario = produto.getValor();

        BigDecimal subtotal = precoUnitario.multiply(BigDecimal.valueOf(request.getQuantidade()));

        ItemPedido item = new ItemPedido();

        item.setPedido(pedido);
        item.setProduto(produto);
        item.setQuantidade(request.getQuantidade());
        item.setPrecoUnitario(precoUnitario);
        item.setSubtotal(subtotal);

        ItemPedido itemSalvo = itemPedidoRepository.save(item);

        atualizarTotalPedido(pedido);

        return itemSalvo;
    }
    private void atualizarTotalPedido(Pedido pedido){

        List<ItemPedido> itens = itemPedidoRepository.findByPedido(pedido);

        BigDecimal total = itens.stream()
                .map(ItemPedido::getSubtotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        pedido.setTotal(total);
        pedidoRepository.save(pedido);


    }






}